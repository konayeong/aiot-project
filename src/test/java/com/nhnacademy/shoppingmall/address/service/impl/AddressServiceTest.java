package com.nhnacademy.shoppingmall.address.service.impl;

import com.nhnacademy.shoppingmall.user.domain.Address;
import com.nhnacademy.shoppingmall.user.exception.AddressDeleteException;
import com.nhnacademy.shoppingmall.user.exception.AddressSaveException;
import com.nhnacademy.shoppingmall.user.exception.UserNotFoundException;
import com.nhnacademy.shoppingmall.user.repository.AddressRepository;
import com.nhnacademy.shoppingmall.user.service.impl.AddressServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AddressServiceTest{
    @Mock
    AddressRepository addressRepository;
    @InjectMocks
    AddressServiceImpl addressService;

    @Test
    @DisplayName("주소 저장")
    void testSaveAddress() {
        Address address = new Address("testUser", "testAddress");
        when(addressRepository.save(any(Address.class))).thenReturn(1);

        assertDoesNotThrow(()->addressService.saveAddress(address));
        verify(addressRepository, times(1)).save(any(Address.class));
    }

    @Test
    @DisplayName("주소 저장 실패")
    void testSaveAddress_Fail() {
        Address address = new Address("testUser", "testAddress");
        when(addressRepository.save(any(Address.class))).thenReturn(0);

        assertThrows(AddressSaveException.class, () -> addressService.saveAddress(address));
    }

    @Test
    @DisplayName("주소 수정")
    void testUpdateAddress() {
        Address address = new Address(1, "updateUser", "updateAddress");
        when(addressRepository.update(any(Address.class))).thenReturn(1);

        assertDoesNotThrow(()->addressService.updateAddress(address));
        verify(addressRepository, times(1)).update(any(Address.class));
    }

    @Test
    @DisplayName("주소 수정 실패")
    void testUpdateAddress_Fail() {
        Address address = new Address(1, "updateUser", "updateAddress");
        when(addressRepository.update(any(Address.class))).thenReturn(0);

        assertThrows(RuntimeException.class, ()->addressService.updateAddress(address));
    }

    @Test
    @DisplayName("주소 삭제")
    void testDeleteAddress() {
        int addressId = 1;
        String userId = "testUser";
        when(addressRepository.deleteByAddressIdAndUserId(addressId, userId)).thenReturn(1);

        assertDoesNotThrow(()->addressService.deleteAddress(addressId, userId));
        verify(addressRepository, times(1)).deleteByAddressIdAndUserId(addressId, userId);
    }

    @Test
    @DisplayName("주소 삭제 - 실패 (잘못된 userId로 UserNotFoundException 발생)")
    void testDeleteAddress_Fail_InvalidUserId() {
        assertThrows(UserNotFoundException.class, () -> addressService.deleteAddress(1, ""));
        assertThrows(UserNotFoundException.class, () -> addressService.deleteAddress(1, null));
        verify(addressRepository, never()).deleteByAddressIdAndUserId(anyInt(), anyString());
    }
    @Test
    @DisplayName("주소 삭제 - 실패 (AddressDeleteException 발생)")
    void testDeleteAddress_Fail_NotDeleted() {
        int addressId = 1;
        String userId = "testUser";
        when(addressRepository.deleteByAddressIdAndUserId(addressId, userId)).thenReturn(0);

        assertThrows(AddressDeleteException.class, () -> addressService.deleteAddress(addressId, userId));
    }

    @Test
    @DisplayName("특정 유저의 주소 목록 조회")
    void testGetAddressesByUserId() {
        String userId = "testUser";
        List<Address> mockList = List.of(
                new Address(1, userId, "주소1"),
                new Address(2, userId, "주소2")
        );
        when(addressRepository.findAllByUserId(userId)).thenReturn(mockList);

        List<Address> resultList = addressService.getAddressesByUserId(userId);

        assertNotNull(resultList);
        assertEquals(2, resultList.size());
        verify(addressRepository, times(1)).findAllByUserId(userId);
    }
}