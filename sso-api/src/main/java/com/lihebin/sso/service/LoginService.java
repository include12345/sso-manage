package com.lihebin.sso.service;

import com.lihebin.sso.bean.LoginRes;

/**
 * Created by lihebin on 2019/8/16.
 */
public interface LoginService {

    /**
     * 登录
     *
     * @param
     * @return
     */
    LoginRes login(String username, String password, String uuid);

    /**
     * 登出
     *
     * @param token
     */
    void logout(String token, String uuid);

    boolean checkToken(String token, String uuid);
}
