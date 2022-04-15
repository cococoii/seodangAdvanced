<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>    
<!-- 중앙 컨텐츠 시작 -->
<link href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script type="text/javascript" src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script type="text/javascript" src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script src="https://developers.kakao.com/sdk/js/kakao.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/user.css">
<div>
	<div class="header">
	<a href="${pageContext.request.contextPath }/main/main.do"><img class="logo login" src="${pageContext.request.contextPath}/resources/images/logo2_1.png"></a>
	</div>
	<form:form action="login.do" modelAttribute="userVO" enctype="multipart/form-data" id="login_form">
		<form:errors element="div" cssClass="error-color"/>
		<ul class="login-form">
			<li id="id">
				<%-- <form:label path="id">아이디</form:label> --%>
				<form:input path="id" placeholder="아이디" class="login-id"/><br>
				<form:errors path="id" cssClass="error-color"/>
			</li>
			<li id="passwd">
				<%-- <form:label path="passwd">비밀번호</form:label> --%>
				<form:password path="passwd" placeholder="비밀번호" class="login-passwd"/><br>
				<form:errors path="passwd" cssClass="error-color"/>
			</li>
		</ul>
		<div class="login_input">
			<form:button class="btn-black login">로그인</form:button>
		</div>
	</form:form>
	<div class="footer-login"></div>
	
</div>
<!-- 중앙컨텐츠 끝 -->