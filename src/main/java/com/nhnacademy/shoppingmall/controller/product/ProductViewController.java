package com.nhnacademy.shoppingmall.controller.product;

import com.nhnacademy.shoppingmall.product.domain.Category;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.service.CategoryService;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// 조회 form
@Controller
public class ProductViewController {
    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductViewController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/products/detail.do")
    public String execute(@RequestParam("product_id") int productId,
                          @CookieValue(name = "recentProducts", required = false) Cookie cookie,
                          HttpServletResponse resp,
                          Model model
                          ) {
        Product product = productService.getProduct(productId);

        // 해당 상품의 카테고리만 가져오기
        List<Category> categories = categoryService.getCategoriesByProductId(productId);

        model.addAttribute("product", product);
        model.addAttribute("categories", categories);

        // cookie에서 최근 본 상품 목록 읽기
        List<String> recentList = new ArrayList<>();
        try {
            String decoded = URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8);
            // '|'로 분리
            recentList = new ArrayList<>(Arrays.asList(decoded.split("\\|")));
        } catch (Exception e) {
            recentList = new ArrayList<>();
        }

        recentList.remove(String.valueOf(product.getProductId())); // 존재하면 제거
        recentList.add(0, String.valueOf(product.getProductId())); // 제일 앞에 추가
        if(recentList.size() > 5){
            recentList = new ArrayList<>(recentList.subList(0, 5));
        }

        // 쿠키에 저장
        try {
            String newValue = URLEncoder.encode(String.join("|", recentList), StandardCharsets.UTF_8);
            Cookie newCookie = new Cookie("recentProducts", newValue);
            cookie.setMaxAge(60 * 60 * 24 * 2); // 2일
            cookie.setPath("/");
            resp.addCookie(newCookie);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "/shop/product/product_view";
    }
}
