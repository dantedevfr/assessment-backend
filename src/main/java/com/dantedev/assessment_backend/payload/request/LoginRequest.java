package com.dantedev.assessment_backend.payload.request;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    public String getFirstname() {
        return firstName;
    }
    public void setFirstname(String firstName) {
        this.firstName = firstName;
    }

    public String getlastname() {
        return lastName;
    }

    public void setlastname(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}