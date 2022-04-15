<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<script src="${pageContext.request.contextPath}/resources/js/jquery-3.6.0.min.js"></script>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
        
        
<form:form modelAttribute="onclassVO" action="onclassDelete.do" enctype="multipart/form-data" id="register_form">
		<form:hidden path="on_num"/>	                 
		<form:errors element="div" cssClass="error-color"/>
		<div class="input-group mb-3" style="padding-left:100px;padding-bottom:300px;padding-top:100px">
		<ul>
			<li>
				<form:password path="deletePasswd" class="form-control" placeholder="비밀번호 입력"/>
				<form:errors path="deletePasswd" cssClass="error-color"/>
			</li>
		</ul>
		<form:button class="btn btn-outline-secondary" id="button-addon1" style="height:40px">전송</form:button>
		</div>
</form:form>
