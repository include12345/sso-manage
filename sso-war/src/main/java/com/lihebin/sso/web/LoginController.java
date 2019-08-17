package com.lihebin.sso.web;

import com.lihebin.sso.bean.Code;
import com.lihebin.sso.bean.LoginRes;
import com.lihebin.sso.exception.SsoException;
import com.lihebin.sso.service.LoginService;
import com.lihebin.sso.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
    @RequestMapping("/loginPage")
    public String loginPage(String originalUrl, String uuid, HttpServletResponse response) {
        CookieUtil.setCookie(response, "originalUrl", originalUrl, 60);
        CookieUtil.setCookie(response, "uuid", uuid,60);
        return "/login";
    }


    @ResponseBody
    @RequestMapping("/login")
    public String login(String username, String password, HttpServletRequest request, HttpServletResponse response) {
        String uuid = CookieUtil.getCookie(request, "uuid");
        String originalUrl = CookieUtil.getCookie(request, "originalUrl");
        try {
            LoginRes loginRes = loginService.login(username, password, uuid);
            String rebackUrl = String.format("%s?token=%s", originalUrl, loginRes.getToken());
            response.sendRedirect(rebackUrl);
        } catch (Exception e) {
            request.setAttribute("result", e.getMessage());
            return "login";
//            throw new SsoException(Code.CODE_SYSTEM_ERROR, e.getMessage());
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
