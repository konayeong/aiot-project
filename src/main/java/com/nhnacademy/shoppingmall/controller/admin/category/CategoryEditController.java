package com.nhnacademy.shoppingmall.controller.admin.category;

import com.nhnacademy.shoppingmall.product.domain.Category;
import com.nhnacademy.shoppingmall.product.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CategoryEditController {
    private final CategoryService categoryService;

    public CategoryEditController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/admin/categories/edit.do")
    public String execute(@RequestParam(name = "category_id") int categoryId,
                          @RequestParam(name = "category_name") String categoryName,
                          @RequestParam(name = "sort_order") int sortOrder) {
        categoryService.updateCategory(new Category(categoryId, categoryName, sortOrder));

        return "redirect:/admin/categories.do";
    }
}
