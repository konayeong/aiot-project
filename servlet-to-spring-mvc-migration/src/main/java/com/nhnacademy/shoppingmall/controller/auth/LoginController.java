package com.nhnacademy.shoppingmall.controller.auth;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Objects;

@Controller
public class LoginController{

    @GetMapping("/login.do")
    public String execute(HttpServletRequest req) {
        //todo#13-1 session이 존재하고 로그인이 되어 있다면 redirect:/index.do 반환 합니다.
        HttpSession session = req.getSession(false);

        if(Objects.nonNull(session) && Objects.nonNull(session.getAttribute("loginUser"))) {
            return "redirect:/index.do";
        }

        return "shop/login/login_form";
    }
}
