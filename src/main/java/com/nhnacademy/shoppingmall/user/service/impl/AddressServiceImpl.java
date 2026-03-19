package com.nhnacademy.shoppingmall.user.service.impl;

import com.nhnacademy.shoppingmall.user.domain.Address;
import com.nhnacademy.shoppingmall.user.exception.AddressDeleteException;
import com.nhnacademy.shoppingmall.user.exception.AddressSaveException;
import com.nhnacademy.shoppingmall.user.exception.UserNotFoundException;
import com.nhnacademy.shoppingmall.user.repository.AddressRepository;
import com.nhnacademy.shoppingmall.user.service.AddressService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;

    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public void saveAddress(Address address) {
        int result = addressRepository.save(address);
        if(result < 1) {
            throw new AddressSaveException(address.getAddressId());
        }
    }

    @Override
    public void updateAddress(Address address) {
        int result = addressRepository.update(address);
        if(result < 1) {
            throw new RuntimeException("Address update fail");
        }
    }

    @Override
    public void deleteAddress(int addressId, String userId) {
        if(userId == null || userId.isBlank()) {
            throw new UserNotFoundException(userId);
        }

        int result = addressRepository.deleteByAddressIdAndUserId(addressId, userId);
        if(result < 1) {
            throw new AddressDeleteException(addressId);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Address> getAddressesByUserId(String userId) {
        return addressRepository.findAllByUserId(userId);
    }
}
