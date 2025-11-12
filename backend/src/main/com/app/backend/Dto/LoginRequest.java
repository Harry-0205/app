package com.app.backend.dto;

public class LoginRequest {
    private String username;
    private String Password;

    public LoginRequest(){
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }
    
    public String getPassword(){
        return Password;
    }

    public void setPassword(String password){
        this.Password = password;
    }
}
