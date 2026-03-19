package com.nhnacademy.shoppingmall.controller.cart;

import com.nhnacademy.shoppingmall.cart.domain.CartItem;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Slf4j
@Controller
public class CartDeleteController{
    @PostMapping("/cart/deleteAction.do")
    public String execute(@RequestParam("product_id") int productId,
                          HttpServletRequest req) {
        HttpSession session = req.getSession(false);

        if(session != null) {
            Map<Integer, CartItem> cart = (Map<Integer, CartItem>) session.getAttribute("cart");

            if(cart != null && cart.containsKey(productId)) {
                cart.remove(productId);
            }
        }

        return "redirect:/cart.do";
    }
}
