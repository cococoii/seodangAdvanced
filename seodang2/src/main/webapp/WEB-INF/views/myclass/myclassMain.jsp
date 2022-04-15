<%-- <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>      
<style>
	.main-left{
		background-color:yellow;
		width:300px;
		height:500px;
		float:left;
	}
	.main-center{
		background-color:green;
		width:600px;
		height:500px;
		margin-left:300px;
	}
	.main-left ul li{
		padding-top:50px;
		margin-left:30px;
		margin-top:30px;
		font-size:20px;
		font-color:gray;
	}
</style>

<div class="main-left">
	<ul>
		<li>
			<a href="${pageContext.request.contextPath}/myclass/myclassMain.do">홈</a>
		</li>
		<c:if test="${session_user_auth == 3}">
		<li>
			<a href="${pageContext.request.contextPath}/myclass/myclassList.do">내가 올린 강의</a>
		</li>
		</c:if>
		<c:if test="${session_user_auth == 2}">
		<li>
			<a href="${pageContext.request.contextPath}/myclass/myRegisterList.do">구매한 강의</a>
		</li>
		</c:if>
		<li>
			<a href="${pageContext.request.contextPath}/myclass/myclassMain.do">수익</a>
		</li>
	</ul>
</div>
<div class="main-center">
	qew
</div> --%>