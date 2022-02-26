package com.memo.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/user")
@Controller
public class UserController {

	/**
	 * 회원가입화면
	 * @param model
	 * @return
	 */
	@RequestMapping("/sign_up_view")
	public String signUpView(Model model) {
		model.addAttribute("viewName", "user/sign_up"); // user 레이아웃 변경 
		return"template/layout";
	}
	/**
	 * 회원가입 수행 (form) - 사용하지 않음
	 * @param loginId
	 * @param password
	 * @param name
	 * @param email
	 * @return
	 */
	@PostMapping("/sign_up_for_submit")
	public String signUpForSubmit(
			@RequestParam("loginId") String loginId,
			@RequestParam("password") String password,
			@RequestParam("name") String name,
			@RequestParam("email") String email
			) {
		// DB insert
		
		// 리다이렉트 => 로그인 화면으로 이동
		
		return "redirect:/user/sign_in_view";
	}
	/**
	 * 로그인 화면
	 * @param model
	 * @return
	 */
	@RequestMapping("/sign_in_view")
	public String signInView(Model model) {
		model.addAttribute("viewName", "user/sign_in");
		return"template/layout";
	}
	
	@RequestMapping("/sign_out")
	public String signOut(HttpServletRequest request) {
		HttpSession session = request.getSession();
		// 로그아웃 - 세션에 있는 키값들을 모두 지운다.
		session.removeAttribute("userLoginId");
		session.removeAttribute("userId");
		session.removeAttribute("userName");
		
		// 리다이렉트 => 로그인 화면
		return"redirect:/user/sign_in_view"; // requestMapping과 같은 주소 매핑
		
	}
	
}
