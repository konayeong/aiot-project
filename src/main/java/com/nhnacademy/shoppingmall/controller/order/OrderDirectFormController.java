package com.nhnacademy.shoppingmall.controller.order;

import com.nhnacademy.shoppingmall.cart.domain.CartItem;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.repository.impl.CategoryRepositoryImpl;
import com.nhnacademy.shoppingmall.product.repository.impl.ProductCategoryRepositoryImpl;
import com.nhnacademy.shoppingmall.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import com.nhnacademy.shoppingmall.product.service.impl.ProductServiceImpl;
import com.nhnacademy.shoppingmall.user.domain.Address;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.impl.AddressRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.AddressService;
import com.nhnacademy.shoppingmall.user.service.impl.AddressServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

@Slf4j
@RequestMapping(method = RequestMapping.Method.GET, value = "/orders/direct.do")
public class OrderDirectFormController implements BaseController {
    private final AddressService addressService = new AddressServiceImpl(new AddressRepositoryImpl());
    private final ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl(), new ProductCategoryRepositoryImpl(), new CategoryRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("loginUser");

        if(user == null) {
            log.warn("[권한 없음] 주문서 작성 페이지 접근 실패");
            return "redirect:/login.do";
        }

        // 회원 주소 리스트
        List<Address> addressList = addressService.getAddressesByUserId(user.getUserId());

        // 제품
        Product product = productService.getProduct(Integer.parseInt(req.getParameter("product_id")));
        int quantity = req.getParameter("quantity") == null ? 1 : Integer.parseInt(req.getParameter("quantity"));

        List<CartItem> items = Collections.singletonList(new CartItem(product, quantity));

        req.setAttribute("addresses", addressList);
        req.setAttribute("orderItems", items);
        req.setAttribute("user", user);

        return "/shop/order/order";
    }
}
