package com.nhnacademy.shoppingmall.controller.admin.product.get;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.repository.impl.CategoryRepositoryImpl;
import com.nhnacademy.shoppingmall.product.repository.impl.ProductCategoryRepositoryImpl;
import com.nhnacademy.shoppingmall.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import com.nhnacademy.shoppingmall.product.service.impl.ProductServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RequestMapping(method = RequestMapping.Method.GET, value = "/admin/products.do")
public class ProductListController implements BaseController {
    private final ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl(), new ProductCategoryRepositoryImpl(),  new CategoryRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        String keyword = req.getParameter("searchKeyword");
        int page = 1;
        int size = 9;
        if(req.getParameter("page")!=null) {
            page = Integer.parseInt(req.getParameter("page"));
        }

        Page<Product> products = productService.getProducts(page, size, keyword);

        req.setAttribute("products", products.getContent());
        long totalCount = products.getTotalCount();
        int totalPages = (int) Math.ceil((double) totalCount / size);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("searchKeyword", keyword);

        return "shop/admin/product_list";
    }
}
