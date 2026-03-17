package com.nhnacademy.shoppingmall.controller.mypage.order;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.order.domain.OrderItem;
import com.nhnacademy.shoppingmall.order.repository.impl.OrderRepositoryImpl;
import com.nhnacademy.shoppingmall.order.service.OrderService;
import com.nhnacademy.shoppingmall.order.service.impl.OrderServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

@RequestMapping(method = RequestMapping.Method.GET, value = "/mypage/order_detail.do")
public class OrderDetailController implements BaseController {
    private final OrderService orderService = new OrderServiceImpl(new OrderRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String orderIdStr = req.getParameter("order_id");
        if(orderIdStr == null || orderIdStr.isEmpty()) {
            return "redirect:/mypage/order.do";
        }

        int orderId = Integer.parseInt(orderIdStr);

        List<OrderItem> orderItemList = orderService.getOrderDetails(orderId);

        req.setAttribute("orderId", orderId);
        req.setAttribute("orderDetails", orderItemList);

        return "shop/mypage/order_detail";
    }
}
