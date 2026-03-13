package com.nhnacademy.shoppingmall.product.service.impl;

import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.exception.CategoryNotFoundException;
import com.nhnacademy.shoppingmall.product.exception.ProductAlreadyExistsException;
import com.nhnacademy.shoppingmall.product.exception.ProductNotFoundException;
import com.nhnacademy.shoppingmall.product.repository.CategoryRepository;
import com.nhnacademy.shoppingmall.product.repository.ProductCategoryRepository;
import com.nhnacademy.shoppingmall.product.repository.ProductRepository;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    ProductRepository productRepository = Mockito.mock(ProductRepository.class);
    ProductCategoryRepository pcRepository = Mockito.mock(ProductCategoryRepository.class);
    CategoryRepository categoryRepository = Mockito.mock(CategoryRepository.class);
    ProductService productService = new ProductServiceImpl(productRepository, pcRepository, categoryRepository);
    Product testProduct = new Product("테스트 상품", "상품 설명", 10000, 10, null);

    @Test
    @DisplayName("getProduct")
    void getProduct() {
        Mockito.when(productRepository.findById(anyInt())).thenReturn(Optional.of(testProduct));
        productService.getProduct(testProduct.getProductId());
        Mockito.verify(productRepository, Mockito.times(1)).findById(anyInt());
    }

    @Test
    @DisplayName("getProduct -> product not found -> return null")
    void getProduct_not_found() {
        Mockito.when(productRepository.findById(anyInt())).thenReturn(Optional.empty());
        Product product = productService.getProduct(testProduct.getProductId());
        Assertions.assertNull(product);
    }

    @Test
    @DisplayName("save product")
    void saveProduct() {
        String[] categoryIds = {"1", "2", "3"};

        // 상품 저장
        Mockito.when(productRepository.countByProductName(anyString())).thenReturn(0);
        Mockito.when(productRepository.save(any())).thenReturn(1);

        // 상품 - 카테고리 연결
        Mockito.when(categoryRepository.countByCategoryId(anyInt())).thenReturn(1);
        Mockito.when(pcRepository.save(anyInt(), anyInt())).thenReturn(1);

        productService.saveProduct(testProduct, categoryIds);

        Mockito.verify(productRepository, Mockito.times(1)).countByProductName(anyString());
        Mockito.verify(productRepository, Mockito.times(1)).save(any());
        Mockito.verify(categoryRepository, Mockito.times(3)).countByCategoryId(anyInt());
        Mockito.verify(pcRepository, Mockito.times(3)).save(anyInt(), anyInt());
    }


    @Test
    @DisplayName("save product - already exists")
    void saveProduct_already_exists() {
        String[] categoryIds = {"1", "2", "3"};
        Mockito.when(productRepository.countByProductName(anyString())).thenReturn(1);
        Throwable throwable = Assertions.assertThrows(ProductAlreadyExistsException.class, () -> productService.saveProduct(testProduct,categoryIds));
        log.debug("error:{}", throwable.getMessage());
    }

    @Test
    @DisplayName("save product - category not found")
    void saveProduct_category_not_found() {
        String[] categoryIds = {"1", "2", "3"};
        Mockito.when(productRepository.countByProductName(anyString())).thenReturn(0);
        Mockito.when(categoryRepository.countByCategoryId(anyInt())).thenReturn(0);

        Throwable throwable = Assertions.assertThrows(CategoryNotFoundException.class, () -> productService.saveProduct(testProduct, categoryIds));
        Mockito.verify(productRepository, Mockito.never()).save(any());
        log.debug("error : {}", throwable.getMessage());
    }

    @Test
    @DisplayName("update product")
    void updateProduct() {
        String[] categoryIds = {"1", "2", "3"};

        Mockito.when(productRepository.countByProductId(anyInt())).thenReturn(1);
        Mockito.when(categoryRepository.countByCategoryId(anyInt())).thenReturn(1);
        Mockito.when(pcRepository.deleteByProductId(anyInt())).thenReturn(0); // 아직 아무것도 들어있지 않음
        Mockito.when(pcRepository.save(anyInt(), anyInt())).thenReturn(1);
        Mockito.when(productRepository.update(any())).thenReturn(1);

        productService.updateProduct(testProduct,categoryIds);

        Mockito.verify(productRepository, Mockito.times(1)).countByProductId(anyInt());
        Mockito.verify(pcRepository, Mockito.times(1)).deleteByProductId(anyInt());
        Mockito.verify(pcRepository, Mockito.times(3)).save(anyInt(), anyInt());
        Mockito.verify(productRepository, Mockito.times(1)).update(any());
    }

    @Test
    @DisplayName("update product - product not found")
    void updateProduct_product_not_found () {
        String[] categoryIds = {"1", "2", "3"};

        Mockito.when(productRepository.countByProductId(anyInt())).thenReturn(0);
        Throwable throwable = Assertions.assertThrows(ProductNotFoundException.class, () -> productService.updateProduct(testProduct, categoryIds));
        log.debug("error : {}", throwable.getMessage());
    }

    @Test
    @DisplayName("update product - category not found")
    void updateProduct_category_not_found() {
        String[] categoryIds = {"1", "2", "3"};

        Mockito.when(productRepository.countByProductId(anyInt())).thenReturn(1);
        Mockito.when(categoryRepository.countByCategoryId(anyInt())).thenReturn(0);
        Throwable throwable = Assertions.assertThrows(CategoryNotFoundException.class, () -> productService.updateProduct(testProduct, categoryIds));
        log.debug("error : {}", throwable.getMessage());
        Mockito.verify(productRepository, Mockito.never()).deleteByProductId(anyInt());

    }

    @Test
    @DisplayName("delete product")
    void deleteProduct() {
        Mockito.when(productRepository.countByProductId(anyInt())).thenReturn(1);
        Mockito.when(productRepository.deleteByProductId(anyInt())).thenReturn(1);
        productService.deleteProduct(testProduct.getProductId());
        Mockito.verify(productRepository, Mockito.times(1)).countByProductId(anyInt());
        Mockito.verify(productRepository, Mockito.times(1)).deleteByProductId(anyInt());
    }
}
