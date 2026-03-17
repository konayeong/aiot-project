package com.nhnacademy.shoppingmall.controller.product;

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
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// 조회 form
@RequestMapping(method = RequestMapping.Method.GET, value = "/products/detail.do")
public class ProductViewController implements BaseController {
    private final ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl(), new ProductCategoryRepositoryImpl(), new CategoryRepositoryImpl());
    private final CategoryService categoryService = new CategoryServiceImpl(new CategoryRepositoryImpl(), new ProductCategoryRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        int findId = Integer.parseInt(req.getParameter("product_id"));

        Product product = productService.getProduct(findId);
        // 해당 상품의 카테고리만 가져오기
        List<Category> categories = categoryService.getCategoriesByProductId(findId);

        req.setAttribute("product", product);
        req.setAttribute("categories", categories);

        // cookie에서 최근 본 상품 목록 읽기
        List<String> recentList = new ArrayList<>();
        Cookie[] cookies = req.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                if("recentProducts".equals(cookie.getName())){
                    try {
                        String decoded = URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8);
                        // '|'로 분리
                        recentList = new ArrayList<>(Arrays.asList(decoded.split("\\|")));
                    } catch (Exception e) {
                        recentList = new ArrayList<>();
                    }
                    break;
                }
            }
        }

        recentList.remove(String.valueOf(product.getProductId())); // 존재하면 제거
        recentList.add(0, String.valueOf(product.getProductId())); // 제일 앞에 추가
        if(recentList.size() > 5){
            recentList = new ArrayList<>(recentList.subList(0, 5));
        }

        // 쿠키에 저장
        try {
            String newValue = URLEncoder.encode(String.join("|", recentList), StandardCharsets.UTF_8);
            Cookie cookie = new Cookie("recentProducts", newValue);
            cookie.setMaxAge(60 * 60 * 24 * 2); // 2일
            cookie.setPath("/");
            resp.addCookie(cookie);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "/shop/product/product_view";
    }
}
