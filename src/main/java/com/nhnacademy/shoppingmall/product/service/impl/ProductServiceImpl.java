package com.nhnacademy.shoppingmall.product.service.impl;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.exception.CategoryNotFoundException;
import com.nhnacademy.shoppingmall.product.exception.ProductAlreadyExistsException;
import com.nhnacademy.shoppingmall.product.exception.ProductNotFoundException;
import com.nhnacademy.shoppingmall.product.repository.CategoryRepository;
import com.nhnacademy.shoppingmall.product.repository.ProductCategoryRepository;
import com.nhnacademy.shoppingmall.product.repository.ProductRepository;
import com.nhnacademy.shoppingmall.product.service.ProductService;

import java.util.Optional;

public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductCategoryRepository pcRepository;
    private final CategoryRepository categoryRepository;

    // TODO serviceFactory?
    public ProductServiceImpl(ProductRepository productRepository, ProductCategoryRepository pcRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.pcRepository = pcRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Page<Product> getProducts(int page, int size, String keyword) {
        return productRepository.findAll(page, size, keyword);
    }

    @Override
    public Page<Product> getProductsByCategoryId(int page, int size, int categoryId) {
        return productRepository.findAllByCategoryId(page, size, categoryId);
    }

    @Override
    public Product getProduct(int productId) {
        Optional<Product> product = productRepository.findById(productId);

        return product.orElse(null);
    }

    @Override
    public void saveProduct(Product product, String[] categoryIds) {

        // 상품 이름 중복 확인
        if(productRepository.countByProductName(product.getProductName()) > 0) {
            throw new ProductAlreadyExistsException(product.getProductName());
        }
        // 카테고리 존재 확인
        // TODO Q 존재하지 않는 카테고리 -> 에러 or continue
        for(String categoryId : categoryIds) {
            int categoryCnt = categoryRepository.countByCategoryId(Integer.parseInt(categoryId));

            if(categoryCnt == 0) {
                throw new CategoryNotFoundException(Integer.parseInt(categoryId));
            }
        }

        // 상품 저장
        productRepository.save(product);

        int productId = product.getProductId();

        // product_category 저장
        for(String categoryId : categoryIds) {
            pcRepository.save(productId, Integer.parseInt(categoryId));
        }
    }

    @Override
    public void updateProduct(Product product, String[] categoryIds) {
        int productCnt = productRepository.countByProductId(product.getProductId());

        if(productCnt == 0) {
            throw new ProductNotFoundException(product.getProductId());
        }

        for(String categoryId : categoryIds) {
            int categoryCnt = categoryRepository.countByCategoryId(Integer.parseInt(categoryId));

            if(categoryCnt == 0) {
                throw new CategoryNotFoundException(Integer.parseInt(categoryId));
            }
        }

        //product_categories 비웠다가 저장하기
        pcRepository.deleteByProductId(product.getProductId());

        for(String categoryId : categoryIds) {
            pcRepository.save(
                    product.getProductId(),
                    Integer.parseInt(categoryId)
            );
        }

        productRepository.update(product);
    }

    @Override
    public void deleteProduct(int productId) {
        int productCnt = productRepository.countByProductId(productId);

        if(productCnt == 0) {
            throw new ProductNotFoundException(productId);
        }

        productRepository.deleteByProductId(productId);
    }
}
