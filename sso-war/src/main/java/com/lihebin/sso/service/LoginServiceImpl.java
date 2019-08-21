package com.lihebin.sso.service;

import com.lihebin.sso.bean.Code;
import com.lihebin.sso.bean.Login;
import com.lihebin.sso.bean.LoginRes;
import com.lihebin.sso.dao.RedisDao;
import com.lihebin.sso.dao.SsoUserDao;
import com.lihebin.sso.exception.SsoException;
import com.lihebin.sso.model.SsoUser;
import com.lihebin.sso.utils.MD5Util;
import com.lihebin.sso.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Created by lihebin on 2019/8/16.
 */
@Service
public class LoginServiceImpl implements LoginService {


    @Value("${salt_password}")
    private String SALT;

    @Autowired
    private RedisDao redisDao;

    @Autowired
    private SsoUserDao ssoUserDao;

    @Override
    public LoginRes login(String username, String password, String uuid) {
        SsoUser ssoUser = ssoUserDao.findSsoUserByUsername(username);
        if (ssoUser == null) {
            throw new SsoException(Code.CODE_NOT_EXIST, "用户名密码错误");
        }
        if (!password.equals(ssoUser.getPassword())) {
            throw new SsoException(Code.CODE_NOT_EXIST, "用户名密码错误");
        }
        String con = username.concat(SALT).concat(uuid);
        String sign = MD5Util.getSign(con);
        String value = String.format("%s-%s", username, uuid);
        redisDao.removeValue(sign);
        redisDao.cacheValue(sign, value, 120, TimeUnit.MINUTES);
        LoginRes loginRes = new LoginRes();
        loginRes.setToken(sign);
        loginRes.setType(ssoUser.getType());
        return loginRes;
    }

    @Override
    public void logout(String token, String uuid) {
        String value = redisDao.getValue(token);
        if (StringUtil.empty(value)) {
            throw new SsoException(Code.CODE_NOT_EXIST, "令牌不存在");
        }
        String[] values = value.split("-");
        if (!uuid.equals(values[1])) {
            throw new SsoException(Code.CODE_NOT_EXIST, "令牌不匹配");
        }
        boolean result = redisDao.removeValue(token);
        if (!result) {
            throw new SsoException(Code.CODE_NOT_EXIST, "退出失败");
        }
    }

    @Override
    public boolean checkToken(String token, String uuid) {
        String value = redisDao.getValue(token);
        if (StringUtil.empty(value)) {
            throw new SsoException(Code.CODE_NOT_EXIST, "令牌不存在");
        }
        String[] values = value.split("-");
        if (!uuid.equals(values[1])) {
            throw new SsoException(Code.CODE_NOT_EXIST, "令牌不匹配");
        }
        return true;
    }
}
