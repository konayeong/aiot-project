package com.nhnacademy.shoppingmall.controller.order;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrderCompleteController {
    @GetMapping("/orders/detail.do")
    public String execute() {
        return "/shop/order/order_detail";
    }
}
