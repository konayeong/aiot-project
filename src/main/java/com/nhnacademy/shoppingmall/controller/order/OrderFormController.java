package com.nhnacademy.shoppingmall.controller.order;

import com.nhnacademy.shoppingmall.cart.domain.CartItem;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.user.domain.Address;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.impl.AddressRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.AddressService;
import com.nhnacademy.shoppingmall.user.service.impl.AddressServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Slf4j
@RequestMapping(method = RequestMapping.Method.GET, value = "/orders.do")
public class OrderFormController implements BaseController {
    private final AddressService addressService = new AddressServiceImpl(new AddressRepositoryImpl());

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

        // 주문서 작성 (상품 리스트)
        Map<Integer, CartItem> cartItemMap = (Map<Integer, CartItem>) session.getAttribute("cart");
        List<CartItem> cartItems = cartItemMap.values().stream().toList();

        req.setAttribute("addresses", addressList);
        req.setAttribute("orderItems", cartItems);
        req.setAttribute("user", user);

        return "shop/order/order";
    }
}
