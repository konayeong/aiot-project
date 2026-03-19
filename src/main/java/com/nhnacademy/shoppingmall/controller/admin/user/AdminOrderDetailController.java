package com.nhnacademy.shoppingmall.controller.admin.user;

import com.nhnacademy.shoppingmall.order.domain.OrderItem;
import com.nhnacademy.shoppingmall.order.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AdminOrderDetailController {
    private final OrderService orderService;

    public AdminOrderDetailController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/admin/orderDetail.do")
    public String execute(@RequestParam(name = "order_id") String orderIdStr,
                          @RequestParam(name = "user_id") String userId,
                          Model model) {

        if(orderIdStr == null || orderIdStr.isEmpty()) {
            return "redirect:/admin/memberDetail.do";
        }

        int orderId = Integer.parseInt(orderIdStr);

        List<OrderItem> orderItemList = orderService.getOrderDetails(orderId);

        model.addAttribute("orderId", orderId);
        model.addAttribute("orderDetails", orderItemList);
        model.addAttribute("userId", userId);

        return "shop/admin/user_orderDetail";
    }
}
