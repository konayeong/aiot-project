package com.nhnacademy.shoppingmall.controller.admin.category.get;

import com.nhnacademy.shoppingmall.product.domain.Category;
import com.nhnacademy.shoppingmall.product.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CategoryEditFormController {
    private final CategoryService categoryService;

    public CategoryEditFormController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/admin/categories/edit.do")
    public String execute(@RequestParam(name = "category_id") int categoryId,
                          Model model) {

        Category category = categoryService.getCategory(categoryId);

        model.addAttribute("category", category);
        return "shop/admin/category_edit";
    }
}
