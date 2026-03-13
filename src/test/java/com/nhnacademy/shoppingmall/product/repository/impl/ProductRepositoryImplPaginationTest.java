package com.nhnacademy.shoppingmall.product.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

@Slf4j
class ProductRepositoryImplPaginationTest {
    static ProductRepository productRepository = new ProductRepositoryImpl();

    @BeforeAll
    static void setUp() {
        DbConnectionThreadLocal.initialize();

        productRepository.deleteAll(); // @AfterAll에서 rollback -> 기존 데이터 문제 없음

        for(int i=1; i<=101; i++) {
            String name = "상품 " + i;
            String desc = "상품 설명 " + i;
            int price = 1000;
            int stock = 10;
            Product product = new Product(name, desc, price, stock, null);

            productRepository.save(product);
        }
    }

    @AfterAll
    static void tearDown() {
        DbConnectionThreadLocal.setSqlError(true);
        DbConnectionThreadLocal.reset();
    }

    @Test
    @DisplayName("전체 로우 수")
    void totalCount() {
        long actual = productRepository.totalCount(null);
        Assertions.assertEquals(101, actual);
    }

    @Test
    @DisplayName("전체 로우 수 - 검색")
    void totalCount_search() {
        long actual = productRepository.totalCount("1");
        Assertions.assertEquals(21, actual);
    }

    @ParameterizedTest(name = "{index} page:{0}, rows:{1}") // 여러 개의 테스트를 한 번에 작성하기 위한 어노테이션
    @MethodSource("paginationArguments")
    @DisplayName("pagination - totalRows : 101")
    void findAll(int page, int rows) {
        Page<Product> productPage = productRepository.findAll(page, 10, null);
        for(Product product : productPage.getContent()) {
            log.debug("product:{}", product);
        }
        Assertions.assertEquals(rows, productPage.getContent().size());
    }

    private static Stream<? extends Arguments> paginationArguments(){
        return Stream.of(
                Arguments.of(1,10,null),
                Arguments.of(2,10,null),
                Arguments.of(3,10,null),
                Arguments.of(4,10,null),
                Arguments.of(5,10,null),
                Arguments.of(6,10,null),
                Arguments.of(7,10,null),
                Arguments.of(8,10,null),
                Arguments.of(9,10,null),
                Arguments.of(10,10,null),
                Arguments.of(11,1,null)
        );
    }
}
