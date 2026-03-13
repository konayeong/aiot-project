package com.nhnacademy.shoppingmall.product.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.product.domain.Category;
import com.nhnacademy.shoppingmall.product.repository.ProductCategoryRepository;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.util.List;

public class ProductCategoryRepositoryImplTest {
    ProductCategoryRepository pcRepository = new ProductCategoryRepositoryImpl();

    int productId;
    int categoryId1;
    int categoryId2;
    Connection connection;

    @BeforeEach
    void setUp() throws SQLException {
        DbConnectionThreadLocal.initialize();
        connection = DbConnectionThreadLocal.getConnection();


        // product 생성
        try (PreparedStatement ps = connection.prepareStatement(
                "insert into products(product_name, description, price, stock) values ('test','설명',1000,10)",
                Statement.RETURN_GENERATED_KEYS
        )) {
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                rs.next();
                productId = rs.getInt(1);
            }
        }

        // categories 생성
        try (PreparedStatement ps = connection.prepareStatement(
                "insert into categories(category_name, sort_order) values ('A',1),('B',2)",
                Statement.RETURN_GENERATED_KEYS
        )) {
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                rs.next();
                categoryId1 = rs.getInt(1);
                rs.next();
                categoryId2 = rs.getInt(1);
            }
        }

        // product_categories 저장
        pcRepository.save(productId, categoryId1);
        pcRepository.save(productId, categoryId2);
    }

    @AfterEach
    void tearDown() {
        DbConnectionThreadLocal.setSqlError(true);
        DbConnectionThreadLocal.reset();
    }

    @Test
    @Order(1)
    @DisplayName("getByProductId")
    void getByProductId() {
        List<Category> categories = pcRepository.getByProductId(productId);
        Assertions.assertAll(
                () -> Assertions.assertEquals(2, categories.size()),
                () -> Assertions.assertEquals(categoryId1, categories.getFirst().getCategoryId())
        );
    }

    @Test
    @Order(2)
    @DisplayName("getCategoryIds")
    void getCategoryIdsByProductId() {
        List<Integer> categoryIds = pcRepository.getCategoryIdsByProductId(productId);
        Assertions.assertAll(
                () -> Assertions.assertEquals(2, categoryIds.size()),
                () -> Assertions.assertEquals(categoryId2, categoryIds.get(1))
        );
    }

    @Test
    @Order(3)
    @DisplayName("save")
    void save() throws SQLException {
        // category 생성
        int categoryId3;
        try (PreparedStatement ps = connection.prepareStatement(
                "insert into categories(category_name, sort_order) values ('C',3)",
                Statement.RETURN_GENERATED_KEYS
        )) {
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                rs.next();
                categoryId3 = rs.getInt(1);
            }
        }

        int result = pcRepository.save(productId,categoryId3);
        Assertions.assertEquals(1, result);
    }

    @Test
    @Order(4)
    @DisplayName("delete")
    void delete() {
        int result = pcRepository.deleteByProductId(productId);
        Assertions.assertEquals(2, result);
    }
}
