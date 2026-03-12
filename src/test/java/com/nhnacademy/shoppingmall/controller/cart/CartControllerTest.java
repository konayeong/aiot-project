package com.nhnacademy.shoppingmall.controller.cart;

import com.nhnacademy.shoppingmall.cart.domain.CartItem;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class CartControllerTest {
    @Mock
    HttpServletRequest req;
    @Mock
    HttpServletResponse resp;
    @Mock
    HttpSession session;

    Map<Integer, CartItem> cart;

    @BeforeEach
    void setUp() {
        cart = new HashMap<>();
        lenient().when(req.getSession(anyBoolean())).thenReturn(session);
        lenient().when(session.getAttribute("cart")).thenReturn(cart);
    }

    @Test
    @DisplayName("장바구니에 새로운 상품 담기")
    void testAddNewItemToCart() {
        CartAddController controller = new CartAddController();
        when(req.getParameter("product_id")).thenReturn("100");
        when(req.getParameter("quantity")).thenReturn("2");

        when(session.getAttribute("cart")).thenReturn(null);

        String view = controller.execute(req, resp);

        assertEquals("redirect:/cart.do", view);
        verify(session, times(1)).setAttribute(eq("cart"), anyMap());
    }

    @Test
    @DisplayName("이미 장바구니에 상품이 담겨 있다면 중복 방지 및 수량 누적")
    void testAddExistingItemToCart() {
        CartAddController addController = new CartAddController();
        int productId = 200;

        cart.put(productId, new CartItem(productId, 3));

        when(req.getParameter("product_id")).thenReturn(String.valueOf(productId));
        when(req.getParameter("quantity")).thenReturn("2"); // 2개 추가로 담기

        addController.execute(req, resp);

        assertTrue(cart.containsKey(productId));
        assertEquals(5, cart.get(productId).getQuantity());
    }

    @Test
    @DisplayName("장바구니 수량 변경")
    void testUpdateCartItemQuantity() {
        CartUpdateController updateController = new CartUpdateController();
        int productId = 300;

        cart.put(productId, new CartItem(productId, 1));

        when(req.getParameter("product_id")).thenReturn(String.valueOf(productId));
        when(req.getParameter("quantity")).thenReturn("10");

        updateController.execute(req, resp);

        assertEquals(10, cart.get(productId).getQuantity());
    }

    @Test
    @DisplayName("장바구니에서 상품 삭제")
    void testDeleteCartItem() {
        CartDeleteController deleteController = new CartDeleteController();
        int productId = 400;

        cart.put(productId, new CartItem(productId, 5));

        when(req.getParameter("product_id")).thenReturn(String.valueOf(productId));

        deleteController.execute(req, resp);

        assertFalse(cart.containsKey(productId));
    }
}