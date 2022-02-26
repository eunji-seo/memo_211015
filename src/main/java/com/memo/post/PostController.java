package com.memo.post;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.memo.post.bo.PostBO;
import com.memo.post.model.Post;

@RequestMapping("/post")
@Controller
public class PostController {

	@Autowired
	private PostBO postBO;
	
	@RequestMapping("/post_list_view")
	public String postListView(HttpServletRequest request, 
			Model model) {
		
		HttpSession session = request.getSession();
		
		int userId = (int) session.getAttribute("userId");
		
		List<Post> postList = postBO.getPostListByUserId(userId);
		model.addAttribute("viewName", "post/post_list");
		model.addAttribute("postList", postList);
		
		return"template/layout";
	}
	@RequestMapping("/post_create_view")
	public String postCreateView(Model model) {
		model.addAttribute("viewName", "post/post_create");
		return"template/layout";
	}
	
	@RequestMapping("/post_detail_view")
	public String postDetailView(
			@RequestParam("postId") int postId
			,Model model) {
		
		// postId 에 해당하는 글을 가져옴 
		Post post = postBO.getPostById(postId);
		model.addAttribute("post", post);
		model.addAttribute("viewName", "post/post_detail");
		return"template/layout";
	}
}
