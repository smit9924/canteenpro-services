package com.app.canteenpro.exceptions;

import com.app.canteenpro.common.ApiResponse;
import jakarta.validation.constraints.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @Autowired
    public ApplicationContext applicationContext;

    @ExceptionHandler(UserNotFoundException.class)
    public ApiResponse<?> handleUserNotFoundException(UserNotFoundException ex) {
        return applicationContext.getBean(ApiResponse.class, null, false, "this is smit patel", ex.getMessage());
    }
}
