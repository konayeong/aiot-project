package com.nhnacademy.shoppingmall.product.repository;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.product.domain.Product;

import java.util.Optional;

public interface ProductRepository {
    // pagination
    long totalCount();
    long totalCount(String productName);
    Page<Product> findAll(int page, int pageSize, String productName);

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
