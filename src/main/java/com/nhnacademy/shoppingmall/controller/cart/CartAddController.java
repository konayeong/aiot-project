package com.nhnacademy.shoppingmall.controller.cart;

import com.nhnacademy.shoppingmall.cart.domain.CartItem;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.repository.impl.CategoryRepositoryImpl;
import com.nhnacademy.shoppingmall.product.repository.impl.ProductCategoryRepositoryImpl;
import com.nhnacademy.shoppingmall.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import com.nhnacademy.shoppingmall.product.service.impl.ProductServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
public class CartAddController{
    private final ProductService productService;

    public CartAddController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/cart/addAction.do")
    public String execute(@RequestParam("product_id") int productId,
                          @RequestParam("quantity") int quantity,
                          HttpSession session) {
        Product product = productService.getProduct(productId);

        Map<Integer, CartItem> cart = (Map<Integer, CartItem>) session.getAttribute("cart");
        if(cart == null) {
            cart = new HashMap<>();
            session.setAttribute("cart", cart);
        }

        if(cart.containsKey(productId)) {
            CartItem existingItem = cart.get(productId);
            int updateQuantity = existingItem.getQuantity() + quantity;
            cart.put(productId, new CartItem(product, updateQuantity));
            log.info("existingItem: productId={}, quantity={}", productId, updateQuantity);
        } else {
            cart.put(productId, new CartItem(product, quantity));
            log.info("New Item: product={}, quantity={}", productId, quantity);
        }

        return "redirect:/cart.do";
    }
}
