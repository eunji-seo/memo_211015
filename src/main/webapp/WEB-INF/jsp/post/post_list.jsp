<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="d-flex justify-content-center">
	<div class="w-50">
		<h1>글 목록</h1>
		<table class="postList table table-bordered table-hover h-50 mt-5" width="700px">
			<thead>
				<tr>
					<th>NO.</th>
					<th>제목</th>
					<th>생성날짜</th>
					<th>수정날짜</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${postList}" var="post">
				<tr>
					<td>${post.id}</td>
					<td><a href="/post/post_detail_view?postId=${post.id}">${post.subject}</a></td>
					<td>
						<fmt:formatDate value="${post.createdAt}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td>
						<fmt:formatDate value="${post.updatedAt}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		<div> 
		<%-- 페이징 --%>	
		<div class="d-flex justify-content-around mt-5 mb-5">
			<c:if test="${prevId != 0}">
				<a href="/post/post_list_view?prevId=${prevId}">&lt;&lt; 이전</a>
			</c:if>
			<c:if test="${nextId != 0}">
				<a href="/post/post_list_view?nextId=${nextId}">다음 &gt;&gt;</a>
			</c:if>
		</div>
		<div class="indicate float-right">
				<a href="/post/post_create_view" class="btn btn-primary">글쓰기</a>
		</div>
		</div>
	</div>
</div>