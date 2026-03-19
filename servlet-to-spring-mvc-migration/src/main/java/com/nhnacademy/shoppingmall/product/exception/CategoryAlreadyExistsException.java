package com.nhnacademy.shoppingmall.product.exception;

public class CategoryAlreadyExistsException extends RuntimeException {
    public CategoryAlreadyExistsException(String categoryName) {
        super(String.format("category already exists : %s", categoryName));
    }
}
