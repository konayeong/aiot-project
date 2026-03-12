package com.nhnacademy.shoppingmall.controller.admin.category.get;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RequestMapping(method = RequestMapping.Method.GET, value = "/admin/categories/create.do")
public class CategoryCreateFormController implements BaseController {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        return "shop/admin/category_create";
    }
}
