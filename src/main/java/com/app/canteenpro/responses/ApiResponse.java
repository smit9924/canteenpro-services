package com.app.canteenpro.responses;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T>  {
    private T data;
    private boolean success;
    private String message;
    private String exception;
}
