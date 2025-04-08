package com.app.canteenpro.services.userapi;

import com.app.canteenpro.DataObjects.EmailDto;
import com.app.canteenpro.DataObjects.UserLoginDto;
import com.app.canteenpro.DataObjects.UserRegistrationDto;
import com.app.canteenpro.common.Enums;
import com.app.canteenpro.common.appConstants;
import com.app.canteenpro.database.models.OTP;
import com.app.canteenpro.database.repositories.*;
import com.app.canteenpro.exceptions.OTPExpiredException;
import com.app.canteenpro.exceptions.UserAlreadyExistsException;
import com.app.canteenpro.responses.ApiResponse;
import com.app.canteenpro.responses.LoginResponse;
import com.app.canteenpro.database.models.Address;
import com.app.canteenpro.database.models.Canteen;
import com.app.canteenpro.database.models.User;
import com.app.canteenpro.responses.UserProfileResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepo userRepo;
    private final RolesRepo rolesRepo;
    private final AddressRepo addressRepo;
    private final CanteenRepo canteenRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Value("${security.jwt.otp-expiration-time}")
    private long otpExpairationTime;

    @Autowired
    public UserService userService;

    @Autowired
    public CommonService commonService;

    @Autowired
    public OTPRepo otpRepo;

    @Autowired
    private RestTemplate restTemplate;

    public LoginResponse signup(UserRegistrationDto registrationData) {
        // Check whether user with given email/username already exists
        Optional<User> existingUser = userRepo.findByEmail(registrationData.getEmail());
        if(existingUser.isPresent()) {
            throw new UserAlreadyExistsException("User with given email is already exists");
        }

        // Set address details object
        Address address = new Address();
        address.setGuid(UUID.randomUUID().toString());
        address.setShop_no(registrationData.getShop());
        address.setApartment(registrationData.getApartment());
        address.setArea(registrationData.getArea());
        address.setLandmark(registrationData.getLandmark());
        address.setCity(registrationData.getCity());
        address.setState(registrationData.getState());
        address.setCountry(registrationData.getCountry());
        address.setZipcode(registrationData.getZipcode());
        addressRepo.save(address);

        // Set Canteen details
        Canteen canteen = new Canteen();
        canteen.setName(registrationData.getCanteenName());
        canteen.setType(registrationData.getCanteenType());
        canteen.setGuid(UUID.randomUUID().toString());
        canteen.setAddress(address);
        canteenRepo.save(canteen);

        User user = new User();
        user.setGuid(UUID.randomUUID().toString());
        user.setFirstname(registrationData.getFirstname());
        user.setLastname(registrationData.getLastname());
        user.setEmail(registrationData.getEmail());
        user.setRole(rolesRepo.findByLevel(registrationData.getRoleLevel()));
        user.setPassword(passwordEncoder.encode(registrationData.getPassword()));
        user.setDefaultPasswordUpdated(true);
        user.setCanteen(canteen);
        userRepo.save(user);

        return this.authenticateUser(registrationData);
    }

    public LoginResponse authenticateUser(UserLoginDto userLoginDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLoginDto.getEmail(),
                        userLoginDto.getPassword()
                )
        );

        User authenticatedUser = userRepo.findByEmail(userLoginDto.getEmail()).orElseThrow();
        String token = jwtService.generateToken(authenticatedUser);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(token);

        return loginResponse;
    }

    public LoginResponse refreshToken(String refreshToken) {

        var user = userRepo.findByEmail(jwtService.extractUsername(refreshToken)).orElseThrow(() -> new IllegalArgumentException("Invalid refresh token"));
        var newRefreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(newRefreshToken);
        return loginResponse;
    }

    public UserProfileResponse changeDefaultPassword(String newPassword) {
        final User currentUser = commonService.getLoggedInUser();
        currentUser.setPassword(passwordEncoder.encode(newPassword));
        currentUser.setDefaultPasswordUpdated(true);
        userRepo.save(currentUser);

        final UserProfileResponse userProfileData = userService.getUserProfileData();
        return  userProfileData;
    }

    public void generateAndSendOTP(String email) throws IOException {
        // Generate OTP
        String generatedOTP = commonService.generatePassword(6, true);

        // Store OTP in database
        OTP otp = new OTP();
        otp.setEmail(email);
        otp.setOtp(generatedOTP);
        otpRepo.save(otp);

        // Get email template and set data
        String emailTemplate = Files.readString(Paths.get("src/main/resources/templates/customer-otp-email-template.html"));
        emailTemplate = emailTemplate.replace("{{otpCode}}", generatedOTP)
                .replace("{{loginUrl}}", appConstants.CUSTOMER_ACCOUNT_LOGIN_URL);

        // Send credentials to user via email
        EmailDto emailDto = EmailDto.builder()
                .recipient(email)
                .subject(appConstants.CUSTOMER_LOGIN_OTP_EMAIL_SUBJECT)
                .msgBody(emailTemplate)
                .build();
        restTemplate.postForEntity(appConstants.INTERNAL_API_EMAIL_WITH_MIME, emailDto, ApiResponse.class);
    }

    public LoginResponse loginCustomUser(UserLoginDto userLoginDto) {
        // Get the latest OTP generated
        Optional<OTP> mostRecentOTP = otpRepo.findFirstByEmailOrderByCreatedOnDesc(userLoginDto.getEmail());
        if(mostRecentOTP.isEmpty() || !mostRecentOTP.get().getOtp().equals(userLoginDto.getPassword())) {
            throw new BadCredentialsException("Please enter correct OTP");
        }

        LocalDateTime currentTime = LocalDateTime.now();
        long minutesSinceOtpGenerated  = Duration.between(currentTime, mostRecentOTP.get().getCreatedOn()).toMinutes();
        if(minutesSinceOtpGenerated > otpExpairationTime) {
            throw new OTPExpiredException("OTP Expired. Please re-generated OTP and try again");
        }

        // Make entry of new user
        Optional<User> existingUser = userRepo.findByEmail(userLoginDto.getEmail());
        if(existingUser.isEmpty()) {
            User newUser = new User();
            newUser.setGuid(UUID.randomUUID().toString());
            newUser.setEmail(userLoginDto.getEmail());
            newUser.setDefaultPasswordUpdated(true);
            newUser.setPassword(passwordEncoder.encode(mostRecentOTP.get().getOtp()));
            newUser.setRole(rolesRepo.findByLevel(Enums.USER_ROLES.CUSTOMER.getValue()));
            userRepo.save(newUser);
        } else {
            if(existingUser.get().getRole().getLevel().equals(Enums.USER_ROLES.CUSTOMER.getValue())) {
                existingUser.get().setPassword(passwordEncoder.encode(mostRecentOTP.get().getOtp()));
                userRepo.save(existingUser.get());
            } else {
                throw new UserAlreadyExistsException("User with this email already exists");
            }
        }

        // Make custom using login
        final LoginResponse loginResponse = this.authenticateUser(userLoginDto);
        return loginResponse;
    }
}
