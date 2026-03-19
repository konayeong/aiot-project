package com.nhnacademy.shoppingmall.controller.order;

import com.nhnacademy.shoppingmall.user.domain.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class OrderCreateController {
    @PostMapping("/orders.do")
    public String execute(HttpServletRequest req) {
        User user = (User) req.getSession(false).getAttribute("loginUser");

        if(user == null) {
            log.warn("[권한 없음] 로그인 후 주문 가능");
            return "redirect:/login.do";
        }

       return "/shop/order/order_detail";
    }
}
