<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<div class="d-flex bg-secondary justify-content-between align-items-center">
	<div class="logo">
		<h1 class="text-white p-4 font-weight-bold">메모 게시판</h1>
	</div>
	<div class="login-info mt-5 mr-4">
		<%-- 세션이 있을 때만(로그인이 되었을 때만 ) 출력 --%>
		<c:if test="${not empty userName}">
		<div>
			<span class="text-white">${userName}님 안녕하세요.</span>
			<a href="/user/sign_out" class="ml-2 text-white font-weight-bold">로그아웃</a> <%-- get 리다이렉트 --%>
		</div>
		</c:if>
	</div>
</div>
