package com.nhnacademy.shoppingmall.product.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Category {
    private int categoryId;
    private String categoryName;
    private int sortOrder;

    public Category(String categoryName, int sortOrder) {
        this.categoryName = categoryName;
        this.sortOrder = sortOrder;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }
}
