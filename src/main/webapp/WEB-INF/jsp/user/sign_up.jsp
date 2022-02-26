<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="container p-3">
	<h1>회원가입</h1>
	<form id="signUpForm" method="post" action="/user/sign_up"> 
	<table class="table table-bordered" width="700px">
		<tr>
			<th>* 아이디</th>
			<td>
				<div class="d-flex justify-content-between">
					<input type="text" class="form-control col-5" id="loginId" name="loginId">
					<button type="button" id="loginIdCheckBtn"class="btn btn-success">중복 체크</button>
				</div>
				<div id="idCheckLength" class="small text-danger d-none">Id를 4자 이상 입력해주세요.</div>
				<div id="idCheckDuplicated" class="small text-danger d-none">이미 사용중인 Id 입니다.</div>
				<div id="idCheckOk" class="small text-success d-none">사용 가능한 ID 입니다.</div>
			</td>
				
		</tr>
		<tr>
			<th>* 비밀번호</th>
			<td><input type="password" class="form-control col-5" id="password" name="password"></td>	
		</tr>
		<tr>
			<th>* 비밀번호 확인</th>
			<td><input type="password" class="form-control col-5" id="passwordConfim" name="passwordConfim"></td>	
		</tr>
		<tr>
			<th>* 이름</th>
			<td><input type="text" class="form-control col-5" id="name" name="name"></td>	
		</tr>
		<tr>
			<th>* 이메일</th>
			<td><input type="text" class="form-control col-7" id="email" name="email"></td>	
		</tr>		
	</table>
	<button type="submit" id="signUpBtn" class="joinBtn btn btn-primary float-right">회원가입</button>
	</form>
</div>

<script>
$(document).ready(function(){
	// id 중복확인 
		$('#loginIdCheckBtn').on('click', function(){
			//alert("중복확인 버튼클릭");
		let loginId = $('#loginId').val().trim();
		
		// 상황 문구 안보이게 모두 초기화
		$('#idCheckLength').addClass('d-none');
		$('#idCheckDuplicated').addClass('d-none');
		$('#idCheckOk').addClass('d-none');
			
		if(loginId.length < 4){
		// id가 4자 미만 일때 경고 문구 노출하고 끝낸다.
			$('#idCheckLength').removeClass('d-none');
			return;
		}
		
		// AJAX - 중복 확인
		$.ajax({
			url:"/user/is_duplicated_id"
			,data:{"loginId":loginId}
			,success: function(data){
				// 중복인 경우 
				if(data.result) {
					$('#idCheckDuplicated').removeClass('d-none');
				} else if (data.result == false) {
					// 아닌 경우 => 사용 가능한 아이디
					$('#idCheckOk').removeClass('d-none');
 				}  
			}
			,error: function(e){
				alert("아이디 중복확인에 실패했습니다. 관리자에게 문의해주세요.");
			}
		});
		
	});
	
	// 회원가입 
	$('#signUpForm').on('submit',function(e){
		e.preventDefault(); // 서브밋 기능 중단
		// validation
		
		let loginId = $('#loginId').val().trim();
		if(loginId ==''){
			alert("아이디를 입력해주세요.");
			return false;
		}
		let password = $('#password').val();
		let passwordConfim = $('#passwordConfim').val(); //val(); get or set 도 됨
		
		if(password == '' || passwordConfim == ''){
			alert("비밀번호를 입력하세요");
			return false;
		}
		
		if (password != passwordConfim) {
			alert("비밀번호가 일치하지 않습니다.")
			 // val(''); 텍스트의 값을 초기화 한다 
			$('#password').val('');
			$('#passwordConfim').val('');
			return false;
		}
		let name = $('#name').val().trim();
		if(name == ''){
			alert("이름을 입력해주세요.")
			return false;
		}
		let email = $('#email').val().trim();
		if(email == ''){
			alert("이메일을 입력해주세요");
			return false;
		}
		
		// 아이디 중복확인이 되었는지 확인
		// idCheckOk <div>에 클래스 중 d-none이 있는 경우 => 성공아님 => alert 띄우고 return(회원가입X)
		if($('#idCheckOk').hasClass('d-none')) {
			alert("아이디 중복확인을 다시 해주세요.");
			return;
		}
		// submit
		// 1. form 서브밋 => 응답이 화면이 됨
		// 2. ajax 서브밋 => 응답은 data가 됨
		
		// 1. form 서브밋
		// $(this)[0].submit(); // [0]넣어야 동작됨 화면이동
		
		// 2. ajax 서브밋
		let url = $(this).attr('action'); // form 태그에 있는 action 주소를 가져 오는 법
		
		let params = $(this).serialize(); // 폼 태그에 들어있는 값을 한번에 보낼수 있게 구성한다.(name)속성 (requestParam)
		//console.log(params);
		
		$.post(url, params) // api 요청값이 데이터로 내려오는 주고, 
		.done(function(data){ // 성공이 된경우 
			if(data.result == 'success'){
				alert("회원가입을 환영합니다. 로그인을 해주세요.");
				location.href = "/user/sign_in_view"; // 응답이 성공 화면 주소 
				
			} else {
				alert("회원가입의 실패했습니다. 다시 시도해 주세요.");
			}
		
			
		});
		
	});
});
</script>