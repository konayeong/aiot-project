package com.nhnacademy.shoppingmall.controller.mypage.address;

import com.nhnacademy.shoppingmall.user.domain.Address;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.service.AddressService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AddressUpdateController {
    private final AddressService addressService;

    public AddressUpdateController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping("/mypage/addressUpdateAction.do")
    public String execute(HttpServletRequest req,
                          @RequestParam("address_id") String addressIDStr,
                          @RequestParam("zipcode") String zipcode,
                          @RequestParam("base_address") String baseAddress,
                          @RequestParam("detail_address") String detailAddress) {
        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("loginUser");


        if(addressIDStr!=null && zipcode!=null && baseAddress!=null && detailAddress!=null) {
            int addressId = Integer.parseInt(addressIDStr);

            String fullAddress = String.format("[%s] %s %s", zipcode, baseAddress, detailAddress);

            Address address = new Address(addressId, user.getUserId(), fullAddress);
            addressService.updateAddress(address);
        }

        return "redirect:/mypage/address.do";
    }
}
