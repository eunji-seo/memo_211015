<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="d-flex justify-content-center my-5">
	<div class="w-50">
		<h1>글쓰기</h1>
		<input type="text" id="subject" class="form-control" placeholder="제목을 입력해주세요.">
		<textarea id="content" class="form-control" rows="12" placeholder="내용을 입력해주세요."></textarea>
		
		<div class="d-flex justify-content-end my-3"> 
			<input type="file" id="file" accept=".jpg,.png,.gif,.jpeg"> <!-- 편의성을 위해 그림만 보여지기위해 만듬 -->
		</div>
		
		<div class="d-flex justify-content-between">
			<button type="button" id="postListBtn" class="btn btn-dark">목록</button>
			
			<div>
				<button type="button" id="clearBtn" class="btn btn-secondary">모두지우기</button>
				<button type="button" id="saveBtn" class="btn btn-primary">저장</button>
			</div>
		</div>
	</div>
</div>
<script>
$(document).ready(function(){
	// 목록버튼 클릭 => 글 목록으로 이동
	$('#postListBtn').on('click', function(){
		location.href="/post/post_list_view";
	});
	// 모두지우기
	$('#clearBtn').on('click', function(){
		// 제목과 내용 부분을 빈칸으로 만든다.
		$('#subject').val('');
		$('#content').val('');
		
	});
	
	// 글 내용 저장
	$('#saveBtn').on('click',function(e){
		//alert("글내용 저장 버튼");
		let subject = $('#subject').val().trim();
		if(subject.length < 1){
			alert("제목을 입력해주세요");
			return;
		}
		let content = $('#content').val();
		//console.log(content);
		
		// 파일이 업로드 된 경우 확장자 체크
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
		
		// 폼태그를 자바스크립트에서 만든다.
		let formData = new FormData();
		formData.append("subject", subject);
		formData.append("content", content);
		formData.append("file", $('#file')[0].files[0]); // $('#file')[0] 첫번째 input file 태그를 의미 , flies[0] 업로드된 첫번째 파일을 의미 
		
		//AJAX from 데이터 전송 $.ajax({ ㅌ
		$.ajax({
			type: "post"
			, url: "/post/create"
			, data: formData
			, enctype: "multipart/form-data" // 파일업로드를 위한 필수 설정
			, processData: false // 파일업로드를 위한 필수 설정
			, contentType: false // 파일업로드를 위한 필수 설정
			//---request
			, success: function(data){ //response
				if(data.result == 'success'){
					alert("메모가 저장되었습니다.");
					location.href="/post/post_list_view";
				} else {
					alert(data.errorMassage)
					location.href="/user/sign_in_view";
				}
			}
			, error: function(e) {
				alert("메모 저장에 실패했습니다. 관리자에게 문의해주세요.");
			}
		});
	});
});
</script>