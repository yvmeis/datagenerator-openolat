package com.frentix.datagenerator.VOs;

public class LoginVO {
    private String url;
    private String username;
    private String password;

    public LoginVO(){

    }

    public void setUrl(String url){
        this.url = url;
    }

    public String getUrl(){
        return this.url;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getUsername(){
        return this.username;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getPassword(){
        return this.password;
    }

    public String getBaseURL(){
        return this.url;
    }
}
