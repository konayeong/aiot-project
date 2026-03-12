package com.nhnacademy.shoppingmall.controller.cart;

import com.nhnacademy.shoppingmall.cart.domain.CartItem;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
@RequestMapping(method = RequestMapping.Method.POST, value = "/cart/updateAction.do")
public class CartUpdateController implements BaseController {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        int productId = Integer.parseInt(req.getParameter("product_id"));
        int quantity = Integer.parseInt(req.getParameter("quantity"));

        HttpSession session = req.getSession(false);
        if(session != null) {
            Map<Integer, CartItem> cart = (Map<Integer, CartItem>) session.getAttribute("cart");

            if(cart != null && cart.containsKey(productId)) {
                if(quantity > 0) {
                    cart.put(productId, new CartItem(productId, quantity));
                } else {
                    cart.remove(productId);
                }
            }
        }

        return "redirect:/cart.do";
    }
}
