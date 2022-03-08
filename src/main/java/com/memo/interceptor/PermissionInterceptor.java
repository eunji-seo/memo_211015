package com.memo.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class PermissionInterceptor implements HandlerInterceptor{

	private Logger logger = LoggerFactory.getLogger(PermissionInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws IOException {
		
		// /user/sign_in_view => 로그인페이지, 회원가입       로그인 상태&&/user...=>/post_list_view로 이동시킨다.
		// 로그아웃까지 처리하면post 로 이동되면서 로그아웃 안되는 현상이 발생되므로 권한검사 제외
		
		//post/post_detall, list, 로그인이 안된상태 && /post => /user/sign_in_view로 이동시킨다.
		
		//세션이 있는지 확인한다
		HttpSession session = request.getSession();		
		Integer userId = (Integer) session.getAttribute("userId"); // null 이 들어갈수 있게 반드시 integer로 받는다.
		
		
		
		// URI 확인 (url path를 가져온다.)
		String uri = request.getRequestURI();
		
		if(userId != null && uri.startsWith("/user")) {
			// 로그인상태 && 접근을 시도한 uri path가 /user.. 이면 게시판 목록으로 리다이렉트			
			response.sendRedirect("/post/post_list_view");
			return false;
		} else if(userId == null && uri.startsWith("/post")) {
			// 비로그인 상태 && 접근을 시도한 uri path 가 /post...이면 로그인 페이지로 리다이렉트
			response.sendRedirect("/user/sign_in_view");
			
		}
		
		logger.warn("###### preHandler 호출 uri:{}", uri );
		
		return true;
		
	}
	
	@Override
	public void postHandle(HttpServletRequest request, 
			HttpServletResponse response, Object handler, ModelAndView ModelAndView) {
		// URI 확인 (url path를 가져온다.)
		String uri = request.getRequestURI();
	
		logger.warn("####### postHandle 호출 uri:{}", uri );
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, 
			HttpServletResponse response, Object handler, Exception ex) {
		// URI 확인 (url path를 가져온다.)
		String uri = request.getRequestURI();
	
		logger.warn("######## afterCompletion 호출 uri:{}", uri );
	}
}