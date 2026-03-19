package com.nhnacademy.shoppingmall.product.service;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.product.domain.Product;

public interface ProductService {
    Page<Product> getProducts(int page, int size, String keyword);
    Page<Product> getProductsByCategoryId(int page, int size, int categoryId);

    Product getProduct(int productId);

    void saveProduct(Product product, String[] categoryIds);

    void updateProduct(Product product, String[] categoryIds);

    void deleteProduct(int productId);
}
