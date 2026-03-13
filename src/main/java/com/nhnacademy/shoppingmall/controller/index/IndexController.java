package com.nhnacademy.shoppingmall.controller.index;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.product.domain.Category;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.repository.CategoryRepository;
import com.nhnacademy.shoppingmall.product.repository.impl.CategoryRepositoryImpl;
import com.nhnacademy.shoppingmall.product.repository.impl.ProductCategoryRepositoryImpl;
import com.nhnacademy.shoppingmall.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.product.service.CategoryService;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import com.nhnacademy.shoppingmall.product.service.impl.CategoryServiceImpl;
import com.nhnacademy.shoppingmall.product.service.impl.ProductServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequestMapping(method = RequestMapping.Method.GET,value = {"/index.do"})
public class IndexController implements BaseController {
    private final ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl(), new ProductCategoryRepositoryImpl(), new CategoryRepositoryImpl());
    private final CategoryService categoryService = new CategoryServiceImpl(new CategoryRepositoryImpl(), new ProductCategoryRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        int page = 1;
        int size = 12;
        String pageParam = req.getParameter("page");
        if (pageParam != null && !pageParam.isEmpty()) {
            page = Integer.parseInt(pageParam);
        }
        Page<Product> productPage;

        // 카테고리 선택 -> keyword 무시
        String categoryId = req.getParameter("category_id");
        String keyword = req.getParameter("searchKeyword");
        if(categoryId != null) {
            productPage = productService.getProductsByCategoryId(page, size, Integer.parseInt(categoryId));
        }else {
            // 카테고리 x, keyword 검색 or 검색 x
            log.debug("keyword : {}", keyword);
            productPage = productService.getProducts(page, size, keyword);
        }

        // 카테고리 선택지 제공
        List<Category> categories = categoryService.getCategories();

        long totalCount = productPage.getTotalCount();
        int totalPages = (int) Math.ceil((double) totalCount / size);

        req.setAttribute("products", productPage.getContent());
        req.setAttribute("categories", categories);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("searchKeyword", keyword);
        req.setAttribute("click", (categoryId!=null) ? Integer.parseInt(categoryId) : null);

        return "shop/main/index";
    }
}