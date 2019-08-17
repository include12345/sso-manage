package com.lihebin.sso.bean;


/**
 * Created by lihebin on 2019/4/16.
 */
public class LoginRes {

    private String serviceParams;

    private String token;

    private Integer type;



    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
