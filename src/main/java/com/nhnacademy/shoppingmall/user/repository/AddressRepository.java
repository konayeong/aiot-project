package com.nhnacademy.shoppingmall.user.repository;

import com.nhnacademy.shoppingmall.user.domain.Address;

import java.util.List;

public interface AddressRepository {
    int save(Address address);
    int deleteByAddressIdAndUserId(int addressId, String userId);
    List<Address> findAllByUserId(String userId);
}
