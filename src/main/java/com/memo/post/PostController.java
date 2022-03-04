package com.memo.post;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
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
	public String postListView(
			@RequestParam(value="prevId", required = false) Integer prevIdParam, 
			@RequestParam(value="nextId", required = false) Integer nextIdParam, 
			HttpServletRequest request, 
			Model model) {
		
		HttpSession session = request.getSession();
		// 글쓴이 정보를 가져오기 위해 세션에서 userId 꺼내온다
		int userId = (int) session.getAttribute("userId");
		
	
		// 글목록 DB에서 가져오기 [] 넘어오지 null은 없음
		List<Post> postList = postBO.getPostListByUserId(userId, prevIdParam, nextIdParam); // 현재 페이지
		int prevId = 0;
		int nextId = 0;
		if(CollectionUtils.isEmpty(postList) == false) { // postList가 없는 경우 에러 방지 
			prevId = postList.get(0).getId(); // 첫번쨰 글. 글번호 // 리스트중 가장 앞쪽(제일 큰값) id
			nextId = postList.get(postList.size() - 1).getId(); // 마지막 글 . 글번호 // 리스트중 가장 딋쪽(제일 작은값)id
			
			// 이전이나 다음이 없는 경우 nextId, prevId 를 0으로 세팅한다.
			
			// 마지막 페이지(다음 방향의 끝) => nextId를 0으로 만든다. 
			if(postBO.isLastPage(userId, nextId)) {
				nextId=0;
			}
			
			// 첫페이지 (이전 방향의 끝)=> prevId를 0으로 만든다.
			if(postBO.isFirstPage(userId, prevId)) {
				prevId=0;
			}
		}
		
		model.addAttribute("viewName", "post/post_list");
		model.addAttribute("prevId", prevId);
		model.addAttribute("nextId", nextId);
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
