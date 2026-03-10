package com.nhnacademy.shoppingmall.product.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Product {
    private int productId;
    private String productName;
    private int price;
    private int stock;
    private String image;

    public Product(String productName, int price, int stock, String image) {
        this.productName = productName;
        this.price = price;
        this.stock = stock;
        this.image = image;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
