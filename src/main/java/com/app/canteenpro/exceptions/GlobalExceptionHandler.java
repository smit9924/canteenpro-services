package com.app.canteenpro.exceptions;

import com.app.canteenpro.responses.ApiResponse;
import jakarta.mail.MessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleUserNotFoundException(UserNotFoundException ex) {
        System.out.println(ex.getMessage());
        ApiResponse<?> apiResponse = new ApiResponse<>(false, false, "User not found!", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse<?>> handleBadCredentialsException(BadCredentialsException ex) {
        ApiResponse<?> apiResponse = new ApiResponse<>(false, false, ex.getMessage(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiResponse);
    }

    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<ApiResponse<?>> handleEmailTransmissionFailedException(MessagingException ex) {
        ApiResponse<?> apiResponse = new ApiResponse<>(false, false, "Error occurred while sending the email!", ex.toString());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
    }

    @ExceptionHandler(EmailTransmissionFailedException.class)
    public ResponseEntity<ApiResponse<?>> handleEmailTransmissionFailedException(EmailTransmissionFailedException ex) {
        ApiResponse<?> apiResponse = new ApiResponse<>(false, false, "Error occurred while sending the email!", ex.toString());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(apiResponse);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<?>> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        ApiResponse<?> apiResponse = new ApiResponse<>(false, false, ex.getMessage(), ex.toString());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
    }

    @ExceptionHandler(OTPExpiredException.class)
    public ResponseEntity<ApiResponse<?>> handleOTPExpiredException(OTPExpiredException ex) {
        ApiResponse<?> apiResponse = new ApiResponse<>(false, false, ex.getMessage(), ex.toString());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleGenericException(Exception ex) {
        ApiResponse<?> apiResponse = new ApiResponse<>(false, false, "Unexpected error occured!", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
    }
}
