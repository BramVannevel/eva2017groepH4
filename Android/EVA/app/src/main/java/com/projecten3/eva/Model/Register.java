package com.projecten3.eva.Model;

public class Register {
    private boolean succes;
    private String msg;

    public Register(boolean succes, String msg) {
        this.succes = succes;
        this.msg = msg;
    }

    public boolean isSucces() {
        return succes;
    }

    public String getMsg() {
        return msg;
    }
}
