package com.woc.jangarana.models;

public class FamilyHeadSignup {

    private String name;
    private String email;
    private String password;
    private String token;
    private String otp;

    public FamilyHeadSignup(String name, String email, String password, String token, String otp) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.token = token;
        this.otp = otp;
    }


    public FamilyHeadSignup(String token, String otp) {
        this.token = token;
        this.otp = otp;
    }

    public FamilyHeadSignup(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
