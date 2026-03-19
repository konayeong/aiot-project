package com.nhnacademy.shoppingmall.controller.admin.category;

import com.nhnacademy.shoppingmall.product.domain.Category;
import com.nhnacademy.shoppingmall.product.exception.CategoryAlreadyExistsException;
import com.nhnacademy.shoppingmall.product.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CategoryCreateController {
    private final CategoryService categoryService;

    public CategoryCreateController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/admin/categories/create.do")
    public String execute(@RequestParam(name = "category_name") String categoryName,
                          @RequestParam(name = "sort_order") int sortOrder,
                          Model model) {
        try {
            categoryService.saveCategory(new Category(categoryName, sortOrder));
        }catch (CategoryAlreadyExistsException e) {
            model.addAttribute("error", "이미 등록된 상품입니다.");
            return "shop/admin/category_create";
        }
        return "redirect:/admin/categories.do";
    }
}
