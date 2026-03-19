package com.nhnacademy.shoppingmall.address.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.user.domain.Address;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.AddressRepository;
import com.nhnacademy.shoppingmall.user.repository.UserRepository;
import com.nhnacademy.shoppingmall.user.repository.impl.AddressRepositoryImpl;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class AddressRepositoryTest {
    AddressRepository addressRepository = new AddressRepositoryImpl();
    UserRepository userRepository = new UserRepositoryImpl();
    User user;

    @BeforeEach
    void setUp() throws SQLException {
        DbConnectionThreadLocal.initialize();

        LocalDateTime now = LocalDateTime.now();
        user = new User("testUser", "testUser", "testUser", "00000000", User.Auth.ROLE_USER, 10000, now, now);
        userRepository.save(user);
    }
    @AfterEach
    void tearDown() throws SQLException {
        DbConnectionThreadLocal.setSqlError(true);
        DbConnectionThreadLocal.reset();
    }

    @Test
    @Order(1)
    @DisplayName("주소 저장 테스트")
    void testSaveAddress() {
        Address address = new Address(user.getUserId(), "testAddress");

        int result = addressRepository.save(address);

        assertTrue(result>1);
    }

    @Test
    @Order(2)
    @DisplayName("주소 수정 테스트")
    void testUpdateAddress() {
        Address address = new Address(user.getUserId(), "testAddress");
        int addressId = addressRepository.save(address);

        Address updateAddress = new Address(addressId, user.getUserId(), "updateAddress");
        int result = addressRepository.update(updateAddress);

        assertEquals(1, result);

        List<Address> list = addressRepository.findAllByUserId(user.getUserId());
        assertEquals("updateAddress", list.getFirst().getAddress());
    }

    @Test
    @Order(3)
    @DisplayName("주소 삭제 테스트")
    void testDeleteAddress() {
        Address address = new Address(user.getUserId(), "testAddress");
        int addressId = addressRepository.save(address);

        int deleteCount = addressRepository.deleteByAddressIdAndUserId(addressId, "testUser");

        assertEquals(1, deleteCount);
        assertEquals(0, addressRepository.findAllByUserId("testUser").size());
    }

    @Test
    @Order(4)
    @DisplayName("특정 유저의 주소 목록 조회 테스트")
    void testFindAllByUserId() {
        addressRepository.save(new Address(user.getUserId(), "testAddress1"));
        addressRepository.save(new Address(user.getUserId(), "testAddress2"));

        List<Address> addressList = addressRepository.findAllByUserId("testUser");

        assertEquals(2, addressList.size());
        assertEquals("testAddress1", addressList.get(0).getAddress());
        assertEquals("testAddress2", addressList.get(1).getAddress());
    }
}
