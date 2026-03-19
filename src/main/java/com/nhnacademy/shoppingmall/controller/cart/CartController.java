package com.nhnacademy.shoppingmall.controller.cart;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CartController {

    @GetMapping("/cart.do")
    public String execute() {
        return "shop/cart/cart";
    }
}
