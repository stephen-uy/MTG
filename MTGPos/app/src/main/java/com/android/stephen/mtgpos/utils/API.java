package com.android.stephen.mtgpos.utils;

public enum API {
    API("api"),
    USER("User_Api");

    private String api;

    API(String api) {
        this.api = api;
    }

    public String getApi() {
        return api;
    }
}
