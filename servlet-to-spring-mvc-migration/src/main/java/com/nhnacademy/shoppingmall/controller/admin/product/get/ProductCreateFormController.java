package com.nhnacademy.shoppingmall.controller.admin.product.get;

import com.nhnacademy.shoppingmall.product.domain.Category;
import com.nhnacademy.shoppingmall.product.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ProductCreateFormController{
    private final CategoryService categoryService;

    public ProductCreateFormController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/admin/products/create.do")
    public String execute(Model model) {
        // 카테고리 목록 (생성)
        List<Category> categories = categoryService.getCategories();
        model.addAttribute("categories", categories);

        return "shop/admin/product_create";
    }
}
