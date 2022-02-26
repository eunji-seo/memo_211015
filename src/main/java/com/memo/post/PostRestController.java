package com.memo.post;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.memo.post.bo.PostBO;
import com.memo.post.model.Post;
@RequestMapping("/post")
@RestController
public class PostRestController {

	@Autowired
	private PostBO postBO;
	
	// 테스트용 컨트롤러
	
	
	@PostMapping("/create")
	public Map<String, Object> create(
			@RequestParam("subject") String subject,
			@RequestParam(value="content" , required = false) String content,
			@RequestParam(value="file" , required = false) MultipartFile file,
			HttpServletRequest request){
		
		Map<String, Object> result = new HashMap<>(); // 디버깅 request 잘되는 검증
		result.put("result", "success");
		
		// 글쓴이 정보를 가져온다.(세션에서)
		HttpSession session = request.getSession();
		Integer userId = (Integer) session.getAttribute("userId"); // null 이 들어갈수 있어 Integer로 저장해줌
		String userLoginId = (String) session.getAttribute("userLoginId");

		if(userId == null) { // 로그인 되어있지 않음 
			result.put("result", "error");
			result.put("errorMassage", "로그인 후 사용 가능합니다.");
			return result;
		}
			
		// userId, userLoginId,subject, content, file => BO에 insert 요청함
		postBO.addPost(userId, userLoginId, subject, content, file);
		
		return result;
	}
	
}
