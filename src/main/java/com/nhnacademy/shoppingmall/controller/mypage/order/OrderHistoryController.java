package com.nhnacademy.shoppingmall.controller.mypage.order;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.order.domain.Order;
import com.nhnacademy.shoppingmall.order.service.OrderService;
import com.nhnacademy.shoppingmall.user.domain.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OrderHistoryController {
    private final OrderService orderService;

    public OrderHistoryController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/mypage/order.do")
    public String execute(HttpServletRequest req,
                          @RequestParam(value = "page", defaultValue = "1") int page,
                          Model model) {
        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("loginUser");

        int pageSize = 10;

        Page<Order> orderHistoryPage = orderService.getOrderPage(user.getUserId(), page, pageSize);

        model.addAttribute("orderHistoryPage", orderHistoryPage);
        model.addAttribute("currentPage", page);

        return "shop/mypage/order";
    }
}
