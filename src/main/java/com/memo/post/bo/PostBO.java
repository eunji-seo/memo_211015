package com.memo.post.bo;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.memo.common.FileManagerSerivec;
import com.memo.post.dao.PostDAO;
import com.memo.post.model.Post;
@Service
public class PostBO {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	//private Logger logger = LoggerFactory.getLogger(PostBO.class);
	
	@Autowired
	private PostDAO postDAO;
	
	@Autowired
	private FileManagerSerivec fileManager;
	
	public List<Post> getPostListByUserId(int userId){
		return postDAO.SelectPostList(userId);
	}
	
	public Post getPostById(int id) {
		return postDAO.getPostById(id);
	}
	
	// userId, subject, content, file => BO에 insert 요청함
	public void addPost(int userId, String userLoginId, String subject, String content, MultipartFile file) { // MultipartFile file 주소로 들어가는 형태로 가공을 한다 BO에서
		String imagePath = null; // file 나오면 imagePath 에 따로 만들어 놓는다
		if(file != null) {
			// 파일매니저 서비스 input:MultipartFile output:이미지의 주소
			imagePath = fileManager.saveFile(userLoginId, file);
		}
		
		postDAO.insertPost(userId, subject, content, imagePath);
	}
	
	public int updatePost(int postId, String userLoginId, int userId, 
			String subject, String content, MultipartFile file) {
		
		// postId에 해당하는 게시글이 있는지 DB에서 가져와 본다

		// logger.error("error logging test"); // logging
		
		Post post = getPostById(postId);
		if(post == null) {
			logger.error("[update post] 수정할 메모가 존재하지 않습니다." + postId); // logging
			return 0;
		}
		// 파일이 있는지 본후 있으면 서버에 업로드 하고 image path 받아온다.
		// 파일이 만약 없으면 수정하지 않는다. 
		String imagePath = null;
		if(file != null) {
			imagePath = fileManager.saveFile(userLoginId, file); // 컴퓨터에 파일 업로두 후 URL path 를 얻어온다. 이미지의 경로을 넘겨주는 
			
			// 새로 업로드된 이미지가 성공하면 기존 이미지 삭제  (기존 이미가 있을 떄에만)
			if(post.getImagePath() != null && imagePath != null) {
				// 기존 이미지 삭제
				try {
					fileManager.deleteFile(post.getImagePath());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					logger.error("[update post] 이미지 삭제 실패 {} ,{}", post.getId(),post.getImagePath());
				}
			}
		}
		
		// DB 에서 update
		return postDAO.updatePostByUserIdPostId(userId, postId, subject, content, imagePath);
	}
	public int deletePostByPostIdANDUserId(int postId, int userId) { // 내 글일때 만 지워기는걸 확인하기위해 userId가 있음 // 정확한 에 String 으로 , 자세하게 Map 으로 만들어 할수도 있음
		// 삭제전 게시글을 먼저 가져와 본다.(imagePath 그림이 있을수 있기 때문에)
		
		Post post = getPostById(postId); // 게시물에 해당하는 포스트를 가져옴
		if(post == null) {
			logger.warn("[delete post] 삭제할 메모가 존재하지 않습니다. ");
			return 0;
		}
		// imagePath가 있는 경우 파일 삭제 	
		if(post.getImagePath() != null) {
			// 기존 이미지 삭제
			try {
				fileManager.deleteFile(post.getImagePath());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.error("[delete post] 이미지 삭제 실패 {} ,{}", post.getId(),post.getImagePath()); // 
			} // return 값이 void 임
		}
		
		
		// DB 에서 post 삭제 
		
		return postDAO.deletePostByUserIdPostId(userId, postId);
	}
	

}
