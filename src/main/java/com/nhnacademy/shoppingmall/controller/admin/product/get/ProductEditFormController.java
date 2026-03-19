package com.nhnacademy.shoppingmall.controller.admin.product.get;

import com.nhnacademy.shoppingmall.product.domain.Category;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.service.CategoryService;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ProductEditFormController {
    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductEditFormController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/admin/products/edit.do")
    public String execute(@RequestParam(name = "product_id") int productId,
                          Model model) {
        Product product = productService.getProduct(productId);
        List<Category> categories = categoryService.getCategories();
        List<Integer> checkCategoryIds = categoryService.getCategoryIdsByProductId(productId);

        model.addAttribute("product", product);
        model.addAttribute("categories", categories);
        model.addAttribute("checkCategoryIds", checkCategoryIds);

        return "shop/admin/product_edit";
    }
}
