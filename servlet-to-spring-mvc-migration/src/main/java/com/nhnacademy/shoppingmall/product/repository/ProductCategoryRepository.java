package com.nhnacademy.shoppingmall.product.repository;

import com.nhnacademy.shoppingmall.product.domain.Category;

import java.util.List;

public interface ProductCategoryRepository {
    List<Category> getByProductId(int productId);

    List<Integer> getCategoryIdsByProductId(int productId);

    int save(int productId, int categoryId);
    int deleteByProductId(int productId);
}
