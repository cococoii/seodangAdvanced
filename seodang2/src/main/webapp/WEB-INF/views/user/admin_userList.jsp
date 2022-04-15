<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="${pageContext.request.contextPath}/resources/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	$(function(){
		$('#search_form').submit(function(){
			if($('#keyword').val().trim()==''){
				alert('검색어를 입력하세요!');
				$('#keyword').val('').focus();
				return false;
			}
		});
	});
</script>
<div class="page-main">
	<h2>회원목록(관리자용)</h2>
	<form action="admin_list.do" id="search_form" method="get">
		<ul class="search">
			<li>
				<select name="keyfield">
					<option value="1" <c:if test="${param.keyfield == 1}">selected</c:if>>ID</option>
					<option value="2" <c:if test="${param.keyfield == 2}">selected</c:if>>이름</option>
					<option value="3" <c:if test="${param.keyfield == 3}">selected</c:if>>이메일</option>
				</select>
			</li>
			<li>
				<input type="search" name="keyword" id="keyword" value="${param.keyword}">
			</li>
			<li>
				<input type="submit" value="검색">
				<input type="button" value="목록" 
				               onclick="location.href='admin_list.do'">
			</li>
		</ul>
	</form>
	<c:if test="${count == 0}">
	<div class="result-display">표시할 회원 정보가 없습니다.</div>
	</c:if>
	<c:if test="${count > 0}">
	<table>
		<tr>
			<th>아이디</th>
			<th>이름</th>
			<th>전화번호</th>
			<th>이메일</th>
			<th>주소</th>
			<th>나이</th>
			<th>가입일</th>
			<th>권한</th>
		</tr>
		<c:forEach var="user" items="${list}">
		<tr>
			<td>
				<c:if test="${user.auth==0}">${user.id}</c:if>
				<c:if test="${user.auth>0}"><a href="admin_update.do?user_num=${user.user_num}">${user.id}</a></c:if>
			</td>
			<td>${user.name}</td>
			<td>${user.phone}</td>
			<td>${user.email}</td>
			<td>${user.address1}</td>
			<td>${user.age}</td>
			<td>${user.reg_date}</td>
			<td>
				<c:if test="${user.auth==0}">탈퇴</c:if>
				<c:if test="${user.auth==1}">정지</c:if>
				<c:if test="${user.auth==2}">학생</c:if>
				<c:if test="${user.auth==3}">선생님</c:if>
				<c:if test="${user.auth==4}">관리자</c:if>
			</td>
		</tr>	
		</c:forEach>
	</table>
	<div class="align-center">${pagingHtml}</div>
	</c:if>
</div>