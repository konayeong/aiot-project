package com.nhnacademy.shoppingmall.controller.admin.category;

import com.nhnacademy.shoppingmall.product.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CategoryDeleteController {
    private final CategoryService categoryService;

    public CategoryDeleteController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/admin/categories/delete.do")
    public String execute(@RequestParam(name = "category_id") int categoryId) {
        categoryService.deleteCategory(categoryId);
        return "redirect:/admin/categories.do";
    }
}
