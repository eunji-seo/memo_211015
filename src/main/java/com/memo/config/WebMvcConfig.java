package com.memo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer{

	/*
	 * 웹의 이미지 주소와 실제 파일 경로를 매핑해주는 설정
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
		.addResourceHandler("/images/**") // http://localhost/images/toma1019_16456453342/sun.png
		// /D:\서은지_211015\6_spring-project\memo\images
		///Users/seoeunji/git/memo-211015/images/
		.addResourceLocations("file:////D:\\서은지_211015\\6_spring-project\\memo\\images/"); // 실제 파일 저장 위치
		
	}
	
}
