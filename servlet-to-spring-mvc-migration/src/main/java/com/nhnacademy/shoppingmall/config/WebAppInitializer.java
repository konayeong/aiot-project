package com.nhnacademy.shoppingmall.config;

import jakarta.servlet.MultipartConfigElement;
import jakarta.servlet.ServletRegistration;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        // Root ApplicationContext를 생성하기 위한 설정 클래스 반환
        return new Class[]{
            RootConfig.class
        };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        // Web ApplicationContext를 생성하기 위한 설정 클래스 반환
        return new Class[]{
                WebConfig.class
        };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {
                "*.do", "/"
        };
    }

    //
    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        MultipartConfigElement multipartConfig = new MultipartConfigElement(
                null,                   // 임시 저장 경로 (null이면 기본값)
                10 * 1024 * 1024,       // 최대 파일 크기 (10MB)
                20 * 1024 * 1024,       // 전체 요청 최대 크기 (20MB)
                0                       // 메모리 내 임계값 (0이면 모든 파일이 임시 디렉토리에 저장됨)
        );

        registration.setMultipartConfig(multipartConfig);
    }
}
