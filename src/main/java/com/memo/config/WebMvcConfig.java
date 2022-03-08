package com.memo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.memo.common.FileManagerSerivec;
import com.memo.interceptor.PermissionInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer{

	@Autowired
	private PermissionInterceptor interceptor;
	
	/*
	 * 웹의 이미지 주소와 실제 파일 경로를 매핑해주는 설정
	 */
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
		.addResourceHandler("/images/**") // http://localhost/images/toma1019_16456453342/sun.png
		// /D:\서은지_211015\6_spring-project\memo\images
		///Users/seoeunji/git/memo-211015/images/
		.addResourceLocations("file:/"+FileManagerSerivec.FILE_UPLOAD_PATH); // 실제 파일 저장 위치
		
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(interceptor)
		.addPathPatterns("/**")// **아래 디렉토리까지 확인
		.excludePathPatterns("/static/**","/error","/user/sign_out"); //권한 검사 하지 않는 Path 예외
		
	}
	
}
