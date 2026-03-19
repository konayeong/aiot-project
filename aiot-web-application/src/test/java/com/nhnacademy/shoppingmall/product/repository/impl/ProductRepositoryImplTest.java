package com.nhnacademy.shoppingmall.product.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

import java.util.Optional;

// TODO repository 수정 반영
@Slf4j
class ProductRepositoryImplTest {
    ProductRepository productRepository = new ProductRepositoryImpl();

    Product testProduct;

    @BeforeEach
    void setUp() {
        DbConnectionThreadLocal.initialize();
        productRepository.deleteAll();
        testProduct = new Product("테스트 제품", "제품 설명", 10000, 20, "/resources/no-image.png");
        productRepository.save(testProduct);
    }

    @AfterEach
    void tearDown() {
        DbConnectionThreadLocal.setSqlError(true);
        DbConnectionThreadLocal.reset();
    }

    @Test
    @Order(1)
    @DisplayName("product 조회 by productId")
    void findById() {
        // id를 찾아야 됨
        Optional<Product> productOptional = productRepository.findById(testProduct.getProductId());
        Assertions.assertEquals(testProduct, productOptional.get());
    }

    @Test
    @Order(2)
    @DisplayName("product 등록")
    void save() {
        Product newProduct = new Product("테스트 제품2", "제품 설명2", 10000, 20, "/resources/no-image.png");
        int result = productRepository.save(newProduct);
        Assertions.assertAll(
                () -> Assertions.assertEquals(1, result),
                () -> Assertions.assertEquals(newProduct, productRepository.findById(newProduct.getProductId()).get())
        );
    }

    @Test
    @Order(3)
    @DisplayName("product 수정")
    void update() {
        testProduct.setProductName("테스트 제품 수정");
        testProduct.setPrice(20000);
        testProduct.setStock(10);

        int result = productRepository.update(testProduct);
        Assertions.assertAll(
                () -> Assertions.assertEquals(1, result),
                () -> Assertions.assertEquals(testProduct, productRepository.findById(testProduct.getProductId()).get())
        );
    }

    @Test
    @Order(4)
    @DisplayName("product 삭제")
    void deleteByProductId() {
        int result = productRepository.deleteByProductId(testProduct.getProductId());

        Assertions.assertAll(
                () -> Assertions.assertEquals(1, result),
                () -> Assertions.assertFalse(productRepository.findById(testProduct.getProductId()).isPresent())
        );
    }

}