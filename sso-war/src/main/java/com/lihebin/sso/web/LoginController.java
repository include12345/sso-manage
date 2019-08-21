package com.lihebin.sso.web;

import com.lihebin.sso.bean.Code;
import com.lihebin.sso.bean.LoginRes;
import com.lihebin.sso.exception.SsoException;
import com.lihebin.sso.service.LoginService;
import com.lihebin.sso.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by lihebin on 2019/8/16.
 */
@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;


    /**
     * 登录页面
     *
     * @param originalUrl
     * @param uuid
     * @param response
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/loginPage")
    public String loginPage(@RequestParam("originalUrl")String originalUrl, @RequestParam("uuid")String uuid, HttpServletResponse response) {
        CookieUtil.setCookie(response, "originalUrl", originalUrl, 60);
        CookieUtil.setCookie(response, "uuid", uuid,60);
        return "login";
    }


    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password, Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) {
        String uuid = CookieUtil.getCookie(request, "uuid");
        String originalUrl = CookieUtil.getCookie(request, "originalUrl");
        try {
            LoginRes loginRes = loginService.login(username, password, uuid);
            String rebackUrl = String.format("%s?token=%s", originalUrl, loginRes.getToken());
            response.sendRedirect(rebackUrl);
        } catch (Exception e) {
            map.put("result", e.getMessage());
            return "login";
        }
        return null;
    }


    /**
     * token
     *
     * @param token token
     * @param uuid   客户端唯一标识
     */
    @ResponseBody
    @RequestMapping("/checkToken")
    public boolean checkToken(String token, String uuid) {
       return loginService.checkToken(token, uuid);
    }

}
