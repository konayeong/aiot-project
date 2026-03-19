package com.nhnacademy.shoppingmall.controller.mypage.address;

import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.service.AddressService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AddressDeleteController {
    private final AddressService addressService;

    public AddressDeleteController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping("/mypage/addressDeleteAction.do")
    public String execute(HttpServletRequest req,
                          @RequestParam("address_id") String addressIdStr) {
        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("loginUser");

        if(addressIdStr != null && !addressIdStr.isEmpty()) {
            int addressId = Integer.parseInt(addressIdStr);
            addressService.deleteAddress(addressId, user.getUserId());
        }

        return "redirect:/mypage/address.do";
    }
}
