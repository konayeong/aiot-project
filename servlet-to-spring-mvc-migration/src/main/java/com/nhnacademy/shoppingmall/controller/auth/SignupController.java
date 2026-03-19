package com.nhnacademy.shoppingmall.controller.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SignupController {
    @GetMapping("/signup.do")
    public String execute() {
        return "shop/login/signup_form";
    }
}
