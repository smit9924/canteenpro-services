package com.app.canteenpro.services.userapi;

import com.app.canteenpro.DataObjects.UserLoginDto;
import com.app.canteenpro.DataObjects.UserRegistrationDto;
import com.app.canteenpro.common.LoginResponse;
import com.app.canteenpro.database.models.Address;
import com.app.canteenpro.database.models.Canteen;
import com.app.canteenpro.database.models.User;
import com.app.canteenpro.database.repositories.AddressRepo;
import com.app.canteenpro.database.repositories.CanteenRepo;
import com.app.canteenpro.database.repositories.RolesRepo;
import com.app.canteenpro.database.repositories.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepo userRepo;
    private final RolesRepo rolesRepo;
    private final AddressRepo addressRepo;
    private final CanteenRepo canteenRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    public LoginResponse signup(UserRegistrationDto registrationData) {
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
}
