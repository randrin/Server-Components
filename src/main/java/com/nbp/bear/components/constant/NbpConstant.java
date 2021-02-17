package com.nbp.bear.components.constant;

public class NbpConstant {

    public static final String NBP_SECRET_KEY = "nbp-be-angular-components";
    public static final int NBP_EXPIRATION_TOKEN = 1000 * 60 * 60 * 10;
    public static final String NBP_AUTHORIZATION = "Authorization";
    public static final String NBP_HEADER = "Bearer ";
    public static final String NBP_DEFAULT_ROLE = "ROLE_USER";
    public static final String[] NBP_ADMIN_ACCESS = {"ROLE_ADMIN", "ROLE_MODERATOR"};
    public static final String[] NBP_MODERATOR_ACCESS = {"ROLE_MODERATOR"};

}
