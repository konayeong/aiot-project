package com.nhnacademy.shoppingmall.product.repository;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.product.domain.Product;

import java.util.Optional;

public interface ProductRepository {
    // pagination
    long totalCount(String productName);
    long totalCount(int categoryId);

    Page<Product> findAll(int page, int size, String productName);
    Page<Product> findAllByCategoryId(int page, int size, int categoryId);
    // 상세 페이지
    Optional<Product> findById(int productId);

    int save(Product product);
    int update(Product product);
    int deleteByProductId(int productId);
    int deleteAll(); // TEST

    // 검증
    int countByProductId(int productId);
    int countByProductName(String productName);

}
