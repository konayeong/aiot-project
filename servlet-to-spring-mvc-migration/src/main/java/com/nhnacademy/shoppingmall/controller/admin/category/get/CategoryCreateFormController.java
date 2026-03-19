package com.nhnacademy.shoppingmall.controller.admin.category.get;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CategoryCreateFormController{

    @GetMapping("/admin/categories/create.do")
    public String execute() {
        return "shop/admin/category_create";
    }
}
