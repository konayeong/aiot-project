package com.nhnacademy.shoppingmall.controller.admin.product.get;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.product.domain.Category;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.repository.impl.CategoryRepositoryImpl;
import com.nhnacademy.shoppingmall.product.repository.impl.ProductCategoryRepositoryImpl;
import com.nhnacademy.shoppingmall.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.product.service.CategoryService;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import com.nhnacademy.shoppingmall.product.service.impl.CategoryServiceImpl;
import com.nhnacademy.shoppingmall.product.service.impl.ProductServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

@RequestMapping(method = RequestMapping.Method.GET, value = "/admin/products/edit.do")
public class ProductEditFormController implements BaseController {
    private ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl(), new ProductCategoryRepositoryImpl(), new CategoryRepositoryImpl());
    private CategoryService categoryService = new CategoryServiceImpl(new CategoryRepositoryImpl(), new ProductCategoryRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        int productId = Integer.parseInt(req.getParameter("product_id"));

        Product product = productService.getProduct(productId);
        List<Category> categories = categoryService.getCategories();
        List<Integer> checkCategoryIds = categoryService.getCategoryIdsByProductId(productId);

        req.setAttribute("product", product);
        req.setAttribute("categories", categories);
        req.setAttribute("checkCategoryIds", checkCategoryIds);

        return "shop/admin/product_edit";
    }
}
