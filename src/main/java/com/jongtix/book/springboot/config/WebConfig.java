package com.jongtix.book.springboot.config;

import com.jongtix.book.springboot.config.auth.LoginUserArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * LoginUserArgumentResolver가 스프링에서 인식할 수 있도록 추가
 */
@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final LoginUserArgumentResolver loginUserArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {   //HandlerMethodArgumentResolver는 항상 webMvcConfigurer의 addArgumentResolvers()를 통해 추가해야 함
        argumentResolvers.add(loginUserArgumentResolver);
    }

}
