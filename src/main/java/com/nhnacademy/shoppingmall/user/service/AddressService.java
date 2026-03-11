package com.nhnacademy.shoppingmall.user.service;

import com.nhnacademy.shoppingmall.user.domain.Address;

import java.util.List;

public interface AddressService {
    void saveAddress(Address address);
    void updateAddress(Address address);
    void deleteAddress(int addressId, String userId);
    List<Address> getAddressesByUserId(String userId);
}
