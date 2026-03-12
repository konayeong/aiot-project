package com.nhnacademy.shoppingmall.controller.cart;

import com.nhnacademy.shoppingmall.cart.domain.CartItem;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequestMapping(method = RequestMapping.Method.POST, value = "/cart/addAction.do")
public class CartAddController implements BaseController {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        int productId = Integer.parseInt(req.getParameter("product_id"));
        int quantity = Integer.parseInt(req.getParameter("quantity"));

        HttpSession session = req.getSession(true);

        Map<Integer, CartItem> cart = (Map<Integer, CartItem>) session.getAttribute("cart");
        if(cart == null) {
            cart = new HashMap<>();
            session.setAttribute("cart", cart);
        }

        if(cart.containsKey(productId)) {
            CartItem existingItem = cart.get(productId);
            int updateQuantity = existingItem.getQuantity() + quantity;
            cart.put(productId, new CartItem(productId, updateQuantity));
            log.info("existingItem: productId={}, quantity={}", productId, updateQuantity);
        } else {
            cart.put(productId, new CartItem(productId, quantity));
            log.info("New Item: product={}, quantity={}", productId, quantity);
        }

        return "redirect:/cart.do";
    }
}
