package com.nhnacademy.shoppingmall.product.service.impl;

import com.nhnacademy.shoppingmall.product.domain.Category;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.exception.CategoryAlreadyExistsException;
import com.nhnacademy.shoppingmall.product.exception.CategoryNotFoundException;
import com.nhnacademy.shoppingmall.product.repository.CategoryRepository;
import com.nhnacademy.shoppingmall.product.repository.ProductCategoryRepository;
import com.nhnacademy.shoppingmall.product.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {
    CategoryRepository categoryRepository = Mockito.mock(CategoryRepository.class);
    ProductCategoryRepository pcRepository = Mockito.mock(ProductCategoryRepository.class);
    CategoryService categoryService = new CategoryServiceImpl(categoryRepository, pcRepository);

    Category testCategory = new Category("테스트 카테고리", 1);
    Product testProduct = new Product("테스트 상품", "설명", 1000, 10, null);

    // TODO Q getCategories(), getCategoriesByProductId(), getCategoryIdsByProductId()는 repository 쪽에서 테스트하는걸로 충분한가?

    @Test
    @DisplayName("get category")
    void getCategory() {
        Mockito.when(categoryRepository.findById(anyInt())).thenReturn(Optional.of(testCategory));
        categoryService.getCategory(testCategory.getCategoryId());
        Mockito.verify(categoryRepository, Mockito.times(1)).findById(anyInt());
    }

    @Test
    @DisplayName("save category")
    void saveCategory() {
        Mockito.when(categoryRepository.countByCategoryNameOrSortOrder(anyString(), anyInt())).thenReturn(0);
        Mockito.when(categoryRepository.save(any())).thenReturn(1);
        categoryService.saveCategory(testCategory);
        Mockito.verify(categoryRepository, Mockito.times(1)).countByCategoryNameOrSortOrder(anyString(), anyInt());
        Mockito.verify(categoryRepository, Mockito.times(1)).save(any());
    }

    @Test
    @DisplayName("save category - already exists")
    void saveCategory_already_exists() {
        Mockito.when(categoryRepository.countByCategoryNameOrSortOrder(anyString(), anyInt())).thenReturn(1);
        Throwable throwable = Assertions.assertThrows(CategoryAlreadyExistsException.class, () -> categoryService.saveCategory(testCategory));
        log.debug("error : {}", throwable.getMessage());
    }

    @Test
    @DisplayName("update category")
    void updateCategory() {
        Mockito.when(categoryRepository.countByCategoryId(anyInt())).thenReturn(1);
        Mockito.when(categoryRepository.countByCategoryNameOrSortOrder(anyString(), anyInt())).thenReturn(1);
        Mockito.when(categoryRepository.update(any())).thenReturn(1);

        categoryService.updateCategory(testCategory);

        Mockito.verify(categoryRepository, Mockito.times(1)).countByCategoryId(anyInt());
        Mockito.verify(categoryRepository, Mockito.times(1)).countByCategoryNameOrSortOrder(anyString(), anyInt());
        Mockito.verify(categoryRepository, Mockito.times(1)).update(any());
    }

    @Test
    @DisplayName("update category - not found")
    void updateCategory_not_found() {
        Mockito.when(categoryRepository.countByCategoryId(anyInt())).thenReturn(0);
        Throwable throwable = Assertions.assertThrows(CategoryNotFoundException.class, () -> categoryService.updateCategory(testCategory));
        log.debug("error : {}", throwable.getMessage());
    }

    @Test
    @DisplayName("update category - already exists")
    void updateCategory_already_exists() {
        Mockito.when(categoryRepository.countByCategoryId(anyInt())).thenReturn(1);
        Mockito.when(categoryRepository.countByCategoryNameOrSortOrder(anyString(), anyInt())).thenReturn(2);
        Throwable throwable = Assertions.assertThrows(CategoryAlreadyExistsException.class, () -> categoryService.updateCategory(testCategory));
        log.debug("error : {}", throwable.getMessage());
    }

    @Test
    @DisplayName("delete category")
    void deleteCategory() {
        Mockito.when(categoryRepository.countByCategoryId(anyInt())).thenReturn(1);
        Mockito.when(categoryRepository.deleteByCategoryId(anyInt())).thenReturn(1);

        categoryService.deleteCategory(testCategory.getCategoryId());

        Mockito.verify(categoryRepository, Mockito.times(1)).countByCategoryId(anyInt());
        Mockito.verify(categoryRepository, Mockito.times(1)).deleteByCategoryId(anyInt());
    }
}
