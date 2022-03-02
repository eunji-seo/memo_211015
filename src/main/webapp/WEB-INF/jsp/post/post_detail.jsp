<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<div class="d-flex justify-content-center">
	<div class="w-50">
		<h1>글 상세/수정</h1>
		<input type="text" id="subject" class="form-control" placeholder="제목을 입력해주세요." value="${post.subject}">
		<textarea id="content" class="form-control" rows="12" placeholder="내용을 입력해주세요.">${post.content}</textarea>
		
		<div class="d-flex justify-content-between my-3"> 
		 	
		<%-- 이미지가 있을떄만 이미지 추가  --%>
		<c:if test="${not empty post.imagePath}">
				<img src="${post.imagePath}" alt="업로드 이미지" width="150" class="mr-2"/>
		</c:if>
			<input type="file" id="file" accept=".jpg,.png,.gif,.jpeg"> <!-- 편의성을 위해 그림만 보여지기위해 만듬 -->
		
		</div>
		<div class="d-flex justify-content-between">
			<button type="button" id="postDeleteBtn" class="btn btn-secondary">삭제</button>
			
			<div>
				<button type="button" id="postListBtn" class="btn btn-dark">목록</button>
				<button type="button" id="saveBtn" class="btn btn-primary" data-post-id="${post.id}">저장</button>
			</div>
		</div>
	</div>
</div>
<script>
$(document).ready(function(){
	
	// 목록 버튼 클릭
	$('#postListBtn').on('click', function(){
		location.href = "/post/post_list_view";

	});
	
	
	// 수정 
	$('#saveBtn').on('click', function(){
		// varidationCheck
		let subject = $('#subject').val().trim();	
		if(subject ==''){
			alert("제목을 입력해주세요.")
			return;
		}
		
		
		let content = $('#content').val();
		
		let file = $('#file').val(); // 파일 경로만 가져온다
		console.log(file); //C:\fakepath\partlyCloudy.jpg
		//validation
		if(file != ''){
			let ext = file.split('.').pop().toLowerCase(); // 파일 경로를 .으로 나누고 확장자가 있는 마지막 문자열을 가져온 후 모두 소문자로 변경 
			if($.inArray(ext, ['gif','png','jpg','jpeg']) == -1){ // -1 확장자가 포함되지 않음
				alert("gif,png,jpg,jpeg 파일만 업로드 할 수 있습니다.");
				 $('#file').val(''); // 파일을 비운다.
				return;
			}
		}
		// 폼태그 객체 를 자바 스크립트에서 만든다.
		
		let formData = new FormData();
		let postId = $(this).data('post-id');
		
		console.log(postId);
		formData.append("postId", postId);
		formData.append("subject", subject);
		formData.append("content", content);
		formData.append("file", $('#file')[0].files[0]);
		
		// AJAX
		$.ajax({
			url: "/post/update"
			, type: "PUT"
			, data: formData
			, enctype: "multipart/form-data" // 파일 업로드를 위한 필수 설정
			, processData: false
			, contentType: false
			, success: function(data){
				if(data.result == 'success'){
					alert("메모가 수정되었습니다.")
					location.reload(true); // 새로고침
				} else {
					alert(data.errorMessage);
				}
				
			}
			, error: function(e){
				alert("메모저장에 실패했습니다. 관리자에게 문의해주세요.")
			}
		});
		
	});
	
});
</script>