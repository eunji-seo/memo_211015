<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="d-flex justify-content-center align-items-center">
	<div class="login-box text-center">
		<h1 class="mb-4">로그인</h1>
		<form id="loginForm" action="/user/sign_in" method="post">

			<div class="d-flex justify-content-center mr-2 mb-3">
				<span class="">아이디</span>
				<input type="text" id="loginId" name="loginId" class="form-control input-form">
			</div>
			<div class="d-flex justify-content-center mr-3 mb-3">
				<span class="">비밀번호</span>
				<input type="password" id="password" name="password" class="form-control input-form">
			</div>
				<input type="submit"s class="joinBtn btn btn-primary btn-form mb-3 " value="로그인">
			<div>
				<a href="/user/sign_up_view"class="signUpBtn btn btn-dark btn-form ">회원가입</a>
	
			</div>
		</form>
	</div>
</div>
<script>
$(document).ready(function(){
	$('#loginForm').on('submit',function(e){
		e.preventDefault(); // 서브밋 기능 중단 
		
		// validation
		let loginId = $('#loginId').val().trim();
		if(loginId.length < 1){
			alert("아이디를 입력해주세요");
			return false;
		}
		
		let password = $('#password').val();
		if(password == ''){
			alert("비밀번호 입력해주세요.")
			return false;
		}
		
		// ajax 호출 (주로 많이 사용함) 
		let url = $(this).attr('action'); // form태그에 있는 action 주소를 가져옴
		let params = $(this).serialize(); // form 태그에 있는 name 값들을 쿼리 스트링으로 구성
		
		$.post(url, params)
		.done(function(data){
			if(data.result == 'success'){
				location.href = "/post/post_list_view"; // 로그인이 성공하면 글 목록으로 이동
			} else {
				alert(data.errorMassage);
			}
			
		});
		
		
	});
	
	
});
</script>