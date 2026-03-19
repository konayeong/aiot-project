package com.nhnacademy.shoppingmall.controller.mypage.order;

import com.nhnacademy.shoppingmall.order.domain.OrderItem;
import com.nhnacademy.shoppingmall.order.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class OrderDetailController {
    private final OrderService orderService;

    public OrderDetailController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/mypage/order_detail.do")
    public String execute(@RequestParam("orderId") String orderIdStr,
                          Model model) {
        if(orderIdStr == null || orderIdStr.isEmpty()) {
            return "redirect:/mypage/order.do";
        }

        int orderId = Integer.parseInt(orderIdStr);

        List<OrderItem> orderItemList = orderService.getOrderDetails(orderId);

        model.addAttribute("orderId", orderId);
        model.addAttribute("orderDetails", orderItemList);

        return "shop/mypage/order_detail";
    }
}
