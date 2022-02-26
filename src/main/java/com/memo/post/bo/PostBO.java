package com.memo.post.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.memo.common.FileManagerSerivec;
import com.memo.post.dao.PostDAO;
import com.memo.post.model.Post;
@Service
public class PostBO {
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
	

}
