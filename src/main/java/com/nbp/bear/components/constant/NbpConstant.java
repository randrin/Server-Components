package com.nbp.bear.components.constant;

public class NbpConstant {

    // TOOLS
    public static final String NBP_SECRET_KEY = "nbp-be-angular-components";
    public static final int NBP_EXPIRATION_TOKEN = 1000 * 60 * 60 * 10;
    public static final String NBP_RANDOM_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_=+[{]}\\\\|;:\\'\\\",<.>/?\"";
    public static final String NBP_RANDOM_NUMBERS = "0123456789";
    public static final String NBP_AUTHORIZATION = "Authorization";
    public static final String NBP_HEADER = "Bearer ";
    public static final String NBP_DEFAULT_ROLE = "ROLE_USER";
    public static final String NBP_ROLE_ADMIN = "ROLE_ADMIN";
    public static final String NBP_ROLE_MODERATOR = "ROLE_MODERATOR";
    public static final String[] NBP_ADMIN_ACCESS = {"ROLE_ADMIN", "ROLE_MODERATOR"};
    public static final String[] NBP_MODERATOR_ACCESS = {"ROLE_MODERATOR"};

    // MODEL
    public static final String NBP_USERNAME_REQUIRED = "Username is required";
    public static final String NBP_EMAIL_REQUIRED = "Email is required";
    public static final String NBP_PASSWORD_REQUIRED = "Password is required";
}
