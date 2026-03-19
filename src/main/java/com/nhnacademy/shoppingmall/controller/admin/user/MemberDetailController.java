package com.nhnacademy.shoppingmall.controller.admin.user;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.order.domain.Order;
import com.nhnacademy.shoppingmall.order.service.OrderService;
import com.nhnacademy.shoppingmall.user.domain.Address;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.service.AddressService;
import com.nhnacademy.shoppingmall.user.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MemberDetailController{
    private final UserService userService;
    private final OrderService orderService;
    private final AddressService addressService;

    public MemberDetailController(UserService userService, OrderService orderService, AddressService addressService) {
        this.userService = userService;
        this.orderService = orderService;
        this.addressService = addressService;
    }

    @GetMapping("/admin/memberDetail.do")
    public String execute(@RequestParam(name = "userId") String userId,
                          @RequestParam(name = "page", defaultValue = "1") int page,
                          Model model) {

        if(userId == null || userId.isEmpty()) {
            return "redirect:/admin/memberList.do";
        }

        User user = userService.getUser(userId);
        if(user == null) {
            return "redirect:/admin/memberList.do";
        }

        int pageSize = 10;

        Page<Order> orderPage = orderService.getOrderPage(userId, page, pageSize);
        long totalCount = orderPage.getTotalCount();
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);

        List<Address> addressList = addressService.getAddressesByUserId(userId);

        model.addAttribute("user", user);
        model.addAttribute("orderPage", orderPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("addressList", addressList);

        return "shop/admin/user_detail";
    }
}
