<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>    
<!-- 중앙 컨텐츠 시작 -->
<link href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script type="text/javascript" src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script type="text/javascript" src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/user.css">


<div>
	<h2 style="text-align:center;">회원탈퇴</h2>
	<form:form modelAttribute="userVO" action="delete.do" id="delete_form" enctype="multipart/form-data">
		<form:errors element="div" cssClass="error-color"/>
		<ul>
			<li id="id">
				<form:label path="id">아이디</form:label>
				<form:input path="id"/>
				<form:errors path="id" cssClass="error-color"/>
			</li>
			<li id="passwd">
				<form:label path="passwd">비밀번호</form:label>
				<form:password path="passwd"/>
				<form:errors path="passwd" cssClass="error-color"/>
			</li>
		</ul>
		<div style="margin-top:80px;text-align:center;">
			<form:button class="btn btn-outline-secondary">전송</form:button>
			<input type="button" value="홈으로"
			         onclick="location.href='${pageContext.request.contextPath}/main/main.do'" class="btn btn-outline-secondary">
		</div>
	</form:form>
</div>