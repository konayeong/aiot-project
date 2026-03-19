package com.nhnacademy.shoppingmall.controller.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Objects;

@Controller
public class LogoutController {
    //todo#13-3 로그아웃 구현
    @PostMapping("/logout.do")
    public String execute(HttpServletRequest req) {
        HttpSession session = req.getSession(false);

        if(Objects.nonNull(session)) {
            session.invalidate();
        }
        return "redirect:/index.do";
    }
}
