package com.memo.post.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.memo.post.model.Post;

@Repository
public interface PostDAO {

	public List<Post> SelectPostList(int userId);
	
	public Post getPostById(int id);
	
	//int userId, String userLoginId, String subject, String content, MultipartFile file
	public void insertPost(
			@Param("userId") int userId, 
			@Param("subject") String subject, 
			@Param("content") String content, 
			@Param("imagePath") String imagePath);
	
	public int updatePostByUserIdPostId( // UserIdPostId 일치하는 조건 where 절 조건 By 필드명을 넣어줌
 			@Param("userId") int userId,
			@Param("postId") int postId,
			@Param("subject") String subject,
			@Param("content") String content,
			@Param("imagePath") String imagePath); 
}
