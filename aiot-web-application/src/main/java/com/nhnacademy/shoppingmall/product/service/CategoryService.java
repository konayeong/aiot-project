package com.nhnacademy.shoppingmall.product.service;

import com.nhnacademy.shoppingmall.product.domain.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getCategories();
    List<Category> getCategoriesByProductId(int productId);
    List<Integer> getCategoryIdsByProductId(int productId);

    Category getCategory(int categoryId);

    void saveCategory(Category category);

    void updateCategory(Category category);

    void deleteCategory(int categoryId);
}
