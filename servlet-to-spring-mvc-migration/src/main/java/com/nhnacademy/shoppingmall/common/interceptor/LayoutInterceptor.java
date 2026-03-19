package com.nhnacademy.shoppingmall.common.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LayoutInterceptor implements HandlerInterceptor {
    // 컨트롤러 실행 이후, View 렌더링 직전 실행
    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView mv) {

        if (mv == null || mv.getViewName() == null) return;

        String viewName = mv.getViewName();

        if (viewName.startsWith("redirect:")) return;

        String layout = viewName.contains("/admin/")
                ? "layout/admin"
                : "layout/shop";

        mv.addObject("layout_content_holder", viewName);
        mv.setViewName(layout);
    }
}