package com.app.canteenpro.common;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class appConstants {
    // Exception handling messages
    public static String USER_HAVING_ID_NOT_FOUND = "User with given id is not found.";
    public static String USER_HAVING_GUID_NOT_FOUND = "User with given GUID is not found.";
    public static String CATEGORY_HAVING_GUID_NOT_FOUND = "Category with given GUID is not found.";
    public static String USER_HAVING_CREDENTIALS_NOT_FOUND = "User having given credentials not found";
    public static String INVALID_CREDENTIALS = "Invalid email or password.";
    public static String USER_UPDATE_FAILED_DUE_TO_NULL_GUID = "User update failed. No user found with the provided GUID";

    // API urls
    public static String INTERNAL_API_BASE_URL = "http://localhost:8080/";
    public static String INTERNAL_API_EMAIL_WITH_MIME = INTERNAL_API_BASE_URL + "api/email/sendwithattchments";
    public static String ADD_ITEM_INTO_CART= INTERNAL_API_BASE_URL + "api/cart/item";
    public static String INCREASE_CART_ITEM_QUANTITY = INTERNAL_API_BASE_URL + "api/cart/item/decrease";
    public static String DECREASE_CART_ITEM_QUANTITY = INTERNAL_API_BASE_URL + "api/cart/item/decrease";

    // Frontend URLs
    public static String FRONTEND_BASE_URL = "http://localhost:4200/";
    public static String ACCOUNT_LOGIN_URL = FRONTEND_BASE_URL + "auth/login";
    public static String CUSTOMER_ACCOUNT_LOGIN_URL = FRONTEND_BASE_URL + "auth/login/customer";
    public static String FOOD_ITEM_MENU_URL = FRONTEND_BASE_URL + "food-items/menu";

    // Email
    public static String INITIAL_LOGIN_CREDENTIALS_EMAIL_SUBJECT = "Welcome to Canteenpro – Your Account is Ready!";
    public static String CUSTOMER_LOGIN_OTP_EMAIL_SUBJECT = "Canteenpro Login Code";

    public static String EMPTY_STRING = "";
    public static String BASE64_VALIDATION_REGEX = "^(?:[A-Za-z0-9+/]{4})*(?:[A-Za-z0-9+/]{2}==|[A-Za-z0-9+/]{3}=)?$";
    public static Integer INITIAL_CART_ITEM_QUANTITY = 1;
}
