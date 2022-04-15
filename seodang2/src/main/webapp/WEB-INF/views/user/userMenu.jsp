<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>    
<!-- 중앙 컨텐츠 시작 -->
<!-- 부트스트랩 라이브러리 -->
<link href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script type="text/javascript" src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script type="text/javascript" src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/userMain.css">

<!-- 중앙 컨텐츠 시작 -->
	<div class="detail_content">
		<div class="ck_content">
		<h2>회원 상세 정보</h2>

		</div>
		</div>
	<div class="sidebar">
	<div class="sidbar_content">
	<h3  onclick="location.href='myMenu.do'">홈</h3>
	<h3 onclick="location.href='myPage.do'">개인정보</h3>
	<h3 onclick="location.href='myoqnaList.do'">나의 Q&A</h3>
	<h3 onclick="location.href='${pageContext.request.contextPath}/myclass/myclassList.do'">내 클래스 목록</h3> 
	<h3 onclick="location.href='${pageContext.request.contextPath}/myclass/likeList.do'">찜 목록</h3>
	<h3 onclick="location.href='${pageContext.request.contextPath}/cart/orderList.do'">내 주문내역</h3>
	
			</div>
			</div>

<div class="page-main">
	<h2></h2>
	<div class="mypage-photo">
		<c:if test="${empty user.photo_name}">
			<img src="${pageContext.request.contextPath}/resources/images/face.png"
			                     width="200" height="200" class="my-photo">
			
			</c:if>
			<c:if test="${!empty user.photo_name}">
			<img src="${pageContext.request.contextPath}/user/photoView.do"
			                     width="200" height="200" class="my-photo">
			</c:if>
	</div>

		<div class="user_name">
			<c:if test="${session_user_auth==2 }">[회원]</c:if>
			<c:if test="${session_user_auth==3 }">[강사]</c:if>
			<c:if test="${session_user_auth==4 }">[관리자]</c:if>
			<span>${session_user_name} 님 환영합니다!</span>
		</div>
</div>
<!-- 중앙 컨텐츠 끝 -->


