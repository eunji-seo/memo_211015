package com.memo.post.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.memo.post.model.Post;

@Repository
public interface PostDAO {

	public List<Post> SelectPostListByUserId(
			@Param("userId") int userId, 
			@Param("direction") String direction, // direction이 null 이면 첫 페이지
			@Param("standardId") Integer standardId, 
			@Param("limit") int limit);
	
	public int selectPostIdByUserIdAndSort(
			@Param("userId") int userId, 
			@Param("sort") String sort); //pk 가 넘어가서 int
	
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
	
	public int deletePostByUserIdPostId(
			@Param("userId") int userId, 
			@Param("postId") int postId);
}
