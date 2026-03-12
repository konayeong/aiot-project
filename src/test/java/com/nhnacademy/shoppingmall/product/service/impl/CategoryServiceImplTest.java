//package com.nhnacademy.shoppingmall.product.service.impl;
//
//import com.nhnacademy.shoppingmall.product.domain.Category;
//import com.nhnacademy.shoppingmall.product.exception.CategoryAlreadyExistsException;
//import com.nhnacademy.shoppingmall.product.exception.CategoryNotFoundException;
//import com.nhnacademy.shoppingmall.product.repository.CategoryRepository;
//import com.nhnacademy.shoppingmall.product.service.CategoryService;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.mockito.ArgumentMatchers.*;
//
//@Slf4j
//@ExtendWith(MockitoExtension.class)
//class CategoryServiceImplTest {
//    CategoryRepository categoryRepository = Mockito.mock(CategoryRepository.class);
//    CategoryService categoryService = new CategoryServiceImpl(categoryRepository);
//    Category testCategory = new Category("테스트 카테고리", 1);
//
//    @Test
//    @DisplayName("get category list")
//    void getCategoryList() {
//        List<Category> categories = new ArrayList<>();
//        categories.add(testCategory);
//        categories.add(new Category("테스트 카테고리2", 2));
//        categories.add(new Category("테스트 카테고리3", 3));
//
//        Mockito.when(categoryRepository.findAll()).thenReturn(categories);
//        List<Category> actual = categoryService.getCategories();
//        Assertions.assertEquals(3, actual.size());
//    }
//
//    @Test
//    @DisplayName("get category")
//    void getCategory() {
//        Mockito.when(categoryRepository.findById(anyInt())).thenReturn(Optional.of(testCategory));
//        categoryService.getCategory(testCategory.getCategoryId());
//        Mockito.verify(categoryRepository, Mockito.times(1)).findById(anyInt());
//    }
//
//    @Test
//    @DisplayName("save category")
//    void saveCategory() {
//        Mockito.when(categoryRepository.countByCategoryName(anyString())).thenReturn(0);
//        Mockito.when(categoryRepository.save(any())).thenReturn(1);
//        categoryService.saveCategory(testCategory);
//        Mockito.verify(categoryRepository, Mockito.times(1)).countByCategoryName(anyString());
//        Mockito.verify(categoryRepository, Mockito.times(1)).save(any());
//    }
//
//    @Test
//    @DisplayName("save category - already exists")
//    void saveCategory_already_exists() {
//        Mockito.when(categoryRepository.countByCategoryName(anyString())).thenReturn(1);
//        Throwable throwable = Assertions.assertThrows(CategoryAlreadyExistsException.class, () -> categoryService.saveCategory(testCategory));
//        log.debug("error : {}", throwable.getMessage());
//    }
//
//    @Test
//    @DisplayName("update category")
//    void updateCategory() {
//        Mockito.when(categoryRepository.countByCategoryId(anyInt())).thenReturn(1);
//        Mockito.when(categoryRepository.update(any())).thenReturn(1);
//
//        categoryService.updateCategory(testCategory);
//
//        Mockito.verify(categoryRepository, Mockito.times(1)).countByCategoryId(anyInt());
//        Mockito.verify(categoryRepository, Mockito.times(1)).update(any());
//    }
//
//    @Test
//    @DisplayName("update category - not found")
//    void updateCategory_not_found() {
//        Mockito.when(categoryRepository.countByCategoryId(anyInt())).thenReturn(0);
//        Throwable throwable = Assertions.assertThrows(CategoryNotFoundException.class, () -> categoryService.updateCategory(testCategory));
//        log.debug("error : {}", throwable.getMessage());
//    }
//
//    @Test
//    @DisplayName("delete category")
//    void deleteCategory() {
//        Mockito.when(categoryRepository.countByCategoryId(anyInt())).thenReturn(1);
//        Mockito.when(categoryRepository.deleteByCategoryId(anyInt())).thenReturn(1);
//
//        categoryService.deleteCategory(testCategory);
//
//        Mockito.verify(categoryRepository, Mockito.times(1)).countByCategoryId(anyInt());
//        Mockito.verify(categoryRepository, Mockito.times(1)).deleteByCategoryId(anyInt());
//    }
//}
