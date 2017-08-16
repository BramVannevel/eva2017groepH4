package com.projecten3.eva.Model;

public class Authenticate {
    private boolean succes;
    private String token;

    public Authenticate(boolean succes, String token) {
        this.succes = succes;
        this.token = token;
    }

    public boolean isSucces() {
        return succes;
    }

    public String getToken() {
        return token;
    }
}
