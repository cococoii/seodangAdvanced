<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<script src="${pageContext.request.contextPath}/resources/js/jquery-3.6.0.min.js"></script>

    
<form:form modelAttribute="kitVO" action="kitDelete.do" 
	                 enctype="multipart/form-data" id="register_form">
		<form:hidden path="kit_num"/>	                 
		<form:errors element="div" cssClass="error-color"/>
		<div>
		<ul>
			<li>
				<form:password path="deletePasswd" class="form-control" placeholder="비밀번호 입력"/>
				<form:errors path="deletePasswd" cssClass="error-color"/>
			</li>
		</ul>
		<form:button class="align-center" i>삭제하기</form:button>
			<input type="button" value="목록" 
			              onclick="location.href='kitList.do'">
		</div> 
</form:form>
    