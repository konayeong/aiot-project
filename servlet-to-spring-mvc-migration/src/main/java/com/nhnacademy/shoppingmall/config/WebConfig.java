package com.nhnacademy.shoppingmall.config;

import com.nhnacademy.shoppingmall.common.interceptor.LayoutInterceptor;
import com.nhnacademy.shoppingmall.controller.ControllerBase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

// 웹 계층 (controller..) 빈 등록
@EnableWebMvc // Spring MVC 활성화
@ComponentScan(
        basePackageClasses = {ControllerBase.class},
        includeFilters = @ComponentScan.Filter(
                type = FilterType.ANNOTATION,
                classes = Controller.class
        ),
        useDefaultFilters = false // true인 경우 @Component가 포함된 클래스 전체를 탐색함
)
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        // InternalResourceViewResolver : Spring에서 제공해주는 viewResolver [jsp]
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        registry.viewResolver(viewResolver);
    }

    // 정적 파일은 직접 매핑 필요
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/resources/", "classpath:/resources/");

        // 저장된 파일 보여주기
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:" + System.getProperty("user.home") + "/upload/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LayoutInterceptor());
    }

    @Bean
    // 톰캣이 임시 저장한 복잡한 파일 데이터(Binary)를 MultipartFile로 포장
    public MultipartResolver multipartResolver(){
        // 서블릿 3.0 표준 인터페이스 사용 -> 미리 정의된 서블릿에 대해서만 멀티파트 요청 (파일 업로드) 허용
        // -> MultipartConfigElement 없으면 서블릿 컨테이너는 보안을 위해 파싱하지 않고 무시
        return new StandardServletMultipartResolver();
    }
}