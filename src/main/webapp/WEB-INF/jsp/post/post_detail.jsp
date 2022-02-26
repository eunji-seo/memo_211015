<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<div class="d-flex justify-content-center">
	<div class="w-50">
		<h1>글 상세/수정</h1>
		<input type="text" id="subject" class="form-control" placeholder="제목을 입력해주세요." value="${post.subject}">
		<textarea id="content" class="form-control" rows="12" placeholder="내용을 입력해주세요.">${post.content}</textarea>
		
		<div class="d-flex justify-content-between my-3"> 
		 	
			<input type="file" id="file" accept=".jpg,.png,.gif,.jpeg"> <!-- 편의성을 위해 그림만 보여지기위해 만듬 -->
		</div>
		<%-- 이미지가 있을떄만 이미지 추가  --%>
		<c:if test="${not empty post.imagePath}">
			<div class="image-area mb-3">
				<img src="${post.imagePath}" alt="업로드 이미지" width="300"/>
			</div>
		</c:if>
		<div class="d-flex justify-content-between">
			<button type="button" id="postDeleteBtn" class="btn btn-secondary">삭제</button>
			
			<div>
				<button type="button" id="postListBtn" class="btn btn-dark">목록</button>
				<button type="button" id="saveBtn" class="btn btn-primary">저장</button>
			</div>
		</div>
	</div>
</div>
<script>

</script>