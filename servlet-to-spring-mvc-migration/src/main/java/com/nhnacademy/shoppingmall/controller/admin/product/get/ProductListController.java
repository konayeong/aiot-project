package com.nhnacademy.shoppingmall.controller.admin.product.get;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Slf4j
@Controller
public class ProductListController {
    private final ProductService productService;

    public ProductListController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/admin/products.do")
    public String execute(@RequestParam(name = "searchKeyword",required = false) String keyword,
                          @RequestParam(name = "page", defaultValue = "1") int page,
                          Model model) {

        int size = 10;

        Page<Product> products = productService.getProducts(page, size, keyword);

        model.addAttribute("products", products.getContent());
        long totalCount = products.getTotalCount();
        int totalPages = (int) Math.ceil((double) totalCount / size);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("searchKeyword", keyword);

        return "shop/admin/product_list";
    }
}
