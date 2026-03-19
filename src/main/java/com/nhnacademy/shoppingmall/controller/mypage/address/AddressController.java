package com.nhnacademy.shoppingmall.controller.mypage.address;

import com.nhnacademy.shoppingmall.user.domain.Address;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.service.AddressService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AddressController {
    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/mypage/address.do")
    public String execute(HttpServletRequest req,
                          Model model) {
        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("loginUser");

        List<Address> addressList = addressService.getAddressesByUserId(user.getUserId());
        model.addAttribute("addressList", addressList);

        return "shop/mypage/address";
    }
}
