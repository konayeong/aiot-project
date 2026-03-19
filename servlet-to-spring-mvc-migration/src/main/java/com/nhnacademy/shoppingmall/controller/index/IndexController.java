package com.nhnacademy.shoppingmall.controller.index;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.product.domain.Category;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.service.CategoryService;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import jakarta.servlet.http.Cookie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class IndexController {
    private final ProductService productService;
    private final CategoryService categoryService;

    public IndexController(ProductService productService,
                           CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/index.do")
    public String execute(
            @CookieValue(value = "recentProducts", required = false) Cookie cookie,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "category_id", required = false) String categoryId,
            @RequestParam(value = "searchKeyword", required = false) String keyword,
            Model model

    ) {
        // == 최근 본 상품 == //
        List<Product> recentProducts = new ArrayList<>();

        if(cookie != null) {
            try {
                String decoded = URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8);
                String[] ids = decoded.split("\\|");
                for(String id : ids){
                    Product p = productService.getProduct(Integer.parseInt(id));
                    if(p != null) {
                        recentProducts.add(p);
                    }
                }
            } catch(Exception e) {
                throw new RuntimeException(e);
            }
        }

        model.addAttribute("recentProducts", recentProducts);

        // == 페이지네이션 == //
        int size = 12;
        Page<Product> productPage;

        // 카테고리 선택 -> keyword 무시
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

        model.addAttribute("products", productPage.getContent());
        model.addAttribute("categories", categories);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("searchKeyword", keyword);
        model.addAttribute("click", (categoryId!=null) ? Integer.parseInt(categoryId) : null);

        return "shop/main/index";
    }
}