package com.nhnacademy.shoppingmall.product.service.impl;

import com.nhnacademy.shoppingmall.product.domain.Category;
import com.nhnacademy.shoppingmall.product.exception.CategoryAlreadyExistsException;
import com.nhnacademy.shoppingmall.product.exception.CategoryNotFoundException;
import com.nhnacademy.shoppingmall.product.repository.CategoryRepository;
import com.nhnacademy.shoppingmall.product.repository.ProductCategoryRepository;
import com.nhnacademy.shoppingmall.product.service.CategoryService;

import java.util.List;
import java.util.Optional;

public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductCategoryRepository productCategoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ProductCategoryRepository productCategoryRepository) {
        this.categoryRepository = categoryRepository;
        this.productCategoryRepository = productCategoryRepository;
    }

    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public List<Category> getCategoriesByProductId(int productId) {
        return productCategoryRepository.getByProductId(productId);
    }

    @Override
    public List<Integer> getCategoryIdsByProductId(int productId) {
        return productCategoryRepository.getCategoryIdsByProductId(productId);
    }

    @Override
    public Category getCategory(int categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);

        return category.orElse(null);
    }

    @Override
    public void saveCategory(Category category) {
        // categoryName 중복 확인
        // TODO sortOrder도 확인 필요 ?
        if(categoryRepository.countByCategoryName(category.getCategoryName()) > 0) {
            throw new CategoryAlreadyExistsException(category.getCategoryName());
        }

        categoryRepository.save(category);
    }

    @Override
    public void updateCategory(Category category) {
        int categoryCnt = categoryRepository.countByCategoryId(category.getCategoryId());

        if(categoryCnt == 0) {
            throw new CategoryNotFoundException(category.getCategoryId());
        }

        categoryRepository.update(category);

    }

    @Override
    public void deleteCategory(int categoryId) {
        int categoryCnt = categoryRepository.countByCategoryId(categoryId);

        if(categoryCnt == 0) {
            throw new CategoryNotFoundException(categoryId);
        }

        categoryRepository.deleteByCategoryId(categoryId);
    }
}
