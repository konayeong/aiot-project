package com.nhnacademy.shoppingmall.product.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.product.domain.Category;
import com.nhnacademy.shoppingmall.product.repository.CategoryRepository;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Optional;

class CategoryRepositoryImplTest {
    CategoryRepository categoryRepository = new CategoryRepositoryImpl();
    Category testCategory;

    @BeforeEach
    void setUp() {
        DbConnectionThreadLocal.initialize();

        categoryRepository.deleteAll();

        testCategory = new Category("테스트 카테고리", 1);
        categoryRepository.save(testCategory);
    }

    @AfterEach
    void tearDown() {
        DbConnectionThreadLocal.setSqlError(true);
        DbConnectionThreadLocal.reset();
    }

    @Test
    @Order(1)
    @DisplayName("category 조회 by categoryId")
    void findById() {
        Optional<Category> category = categoryRepository.findById(testCategory.getCategoryId());
        Assertions.assertEquals(testCategory, category.get());
    }

    @Test
    @Order(2)
    @DisplayName("category 전체 조회")
    void findAll() {
        categoryRepository.save(new Category("테스트 카테고리2",2));
        categoryRepository.save(new Category("테스트 카테고리3",3));
        categoryRepository.save(new Category("테스트 카테고리4",4));

        List<Category> categories = categoryRepository.findAll();
        Assertions.assertEquals(  4, categories.size());

    }

    @Test
    @Order(3)
    @DisplayName("category save")
    void save() {
        Category category = new Category("카테고리 추가", 2);
        int result = categoryRepository.save(category);

        Assertions.assertAll(
                () -> Assertions.assertEquals(1, result),
                () -> Assertions.assertEquals(category, categoryRepository.findById(category.getCategoryId()).get())
        );
    }

    @Test
    @Order(4)
    @DisplayName("category update")
    void update() {
        testCategory.setCategoryName("카테고리 업데이트");
        testCategory.setSortOrder(4);
        int result = categoryRepository.update(testCategory);

        Assertions.assertAll(
                () -> Assertions.assertEquals(1, result),
                () -> Assertions.assertEquals(testCategory, categoryRepository.findById(testCategory.getCategoryId()).get())
        );
    }

    @Test
    @Order(5)
    @DisplayName("category delete")
    void delete() {
        int result = categoryRepository.deleteByCategoryId(testCategory.getCategoryId());
        Assertions.assertAll(
                () -> Assertions.assertEquals(1, result),
                () -> Assertions.assertFalse(categoryRepository.findById(testCategory.getCategoryId()).isPresent())
        );

    }

}
