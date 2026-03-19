package com.nhnacademy.shoppingmall.controller.admin.product;

import com.nhnacademy.shoppingmall.product.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

// 삭제
@Controller
public class ProductDeleteController {
    private ProductService productService;

    public ProductDeleteController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/admin/products/delete.do")
    public String execute(@RequestParam(name = "product_id") int productId) {

        productService.deleteProduct(productId);

        return "redirect:/admin/products.do";
    }
}
