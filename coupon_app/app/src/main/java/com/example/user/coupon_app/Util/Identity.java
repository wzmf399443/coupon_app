package com.example.user.coupon_app.Util;

public class Identity {
    private static String token;
    private static String identity;

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        Identity.token = token;
    }

    public static String getIdentity() {
        return identity;
    }

    public static void setIdentity(String identity) {
        Identity.identity = identity;
    }
}
