package com.nhnacademy.shoppingmall.product.repository;

import com.nhnacademy.shoppingmall.product.domain.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {
    List<Category> findAll();
    Optional<Category> findById(int categoryId);

    int save(Category category);
    int update(Category category);
    int deleteByCategoryId(int categoryId);
    int deleteAll(); // TEST

    int countByCategoryId(int productId);
    int countByCategoryNameOrSortOrder(String categoryName, int sortOrder);
}
