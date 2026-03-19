package com.nhnacademy.shoppingmall.product.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode // Product 객체 비교 실패한 이유 -> equals() 없어서
public class Product {
    private int productId;
    private String productName;
    private String description;
    private int price;
    private int stock;
    private String image;

    public Product(String productName, String description, int price, int stock, String image) {
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.image =  image;
    }

    public Product(int productId, String productName, String description, int price, int stock, String image) {
        this.productId = productId;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.image = (image == null) ? "/resources/no-image.png" : image;
    }

    // TODO PK 값을 변경하지 못하도록 범위 제한 필요 ?
    public void setProductId(int productId) {
        this.productId = productId;
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
