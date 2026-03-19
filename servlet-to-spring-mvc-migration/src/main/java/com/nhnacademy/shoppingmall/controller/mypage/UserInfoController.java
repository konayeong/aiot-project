package com.nhnacademy.shoppingmall.controller.mypage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserInfoController {
    @GetMapping("/mypage/info.do")
    public String execute() {
        return "shop/mypage/info";
    }
}
