package com.nhnacademy.shoppingmall.controller.mypage.address;

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

@RequestMapping(method = RequestMapping.Method.POST, value = "/mypage/addressUpdateAction.do")
public class AddressUpdateController implements BaseController {
    private final AddressService addressService = new AddressServiceImpl(new AddressRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("loginUser");

        String addressIDStr = req.getParameter("address_id");
        String zipcode = req.getParameter("zipcode");
        String baseAddress = req.getParameter("base_address");
        String detailAddress = req.getParameter("detail_address");

        if(addressIDStr!=null && zipcode!=null && baseAddress!=null && detailAddress!=null) {
            int addressId = Integer.parseInt(addressIDStr);

            String fullAddress = String.format("[%s] %s %s", zipcode, baseAddress, detailAddress);

            Address address = new Address(addressId, user.getUserId(), fullAddress);
            addressService.updateAddress(address);
        }

        return "redirect:/mypage/address.do";
    }
}
