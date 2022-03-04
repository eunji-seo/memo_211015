package com.memo.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component // 서비스도 아니고 Repository 아닐때는 일반 스프링 빈인 컴포넌트 사용 1. 스프링 빈을 사용시, 2. Autowired 해야 할때 
public class FileManagerSerivec {

	// CDN 서버 (이미지, css, js) 정적인 내용을 다른 서버로 분리할때 경로는 명시해야함 
	//D:\서은지_211015\6_spring-project\memo\images
	///Users/seoeunji/Library/Containers/com.linearity.vn/Data/Documents/6.spring_project/memo
	public final static String FILE_UPLOAD_PATH = "/Users/seoeunji/Library/Containers/com.linearity.vn/Data/Documents/6.spring_project/memo/images/";
	
	public String saveFile(String userLoginId, MultipartFile file) {
		// 파일 디렉토리 경로 예: toma1019_16456453342/sun.png
		// 파일명이 겹치지 않게 하기 위해 현재시간을 경로에 붙여준다.
		String directoryName = userLoginId + "_" + System.currentTimeMillis() + "/";
		//D:\\서은지_211015\\6_spring-project\\memo\\workspace\\images/toma1019_16456453342/
		String filePath = FILE_UPLOAD_PATH + directoryName;
		
		// 디렉토리 만들기
		File directory = new File(filePath);
		if (directory.mkdir() == false) {
			return null; // 디렉토리 생성시 실패하면 null 리턴
		}
		
		// 파일 업로드 : byte 단위로 업로드 한다.
		try {
			byte[] bytes = file.getBytes();
			Path path = Paths.get(filePath + file.getOriginalFilename());//getOriginalFilename()는 input에서 올린 파일명, 파일이름이 한글이면 동작되지 않음 그래서 영어로 변환해야한다.
			Files.write(path,bytes);
			
			// 이미지 URL을 리턴한다.(WebMvcConfig에서 매핑한 이미지 path)
			// 예) http://localhost/images/toma1019_16456453342/sun.png // imagepath : /images/toma1019_16456453342/sun.png (BD에 들어있는 값 ) 
			return "/images/" + directoryName + file.getOriginalFilename();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void deleteFile(String imagePath) throws IOException { // BO로 try catch 문 처리 
		// imagePath의 /images/toma1019_16456453342/sun.png 에서 /images/ 를 제거한 path를 실제 저장경로 뒤에 붙인다.
		//D:\\서은지_211015\\6_spring-project\\memo\\images/      /images/toma1019_16456453342/sun.png
		
		Path path = Paths.get(FILE_UPLOAD_PATH + imagePath.replace("/images/", ""));
		if(Files.exists(path)) { // 이미지 파일이 있으면 삭제
			Files.delete(path);
		}
		// 디렉토리(폴더) 삭제 // sun.png 부모 
		path = path.getParent(); 
		if(Files.exists(path)) {
			Files.delete(path);
		
		}
	}
}
