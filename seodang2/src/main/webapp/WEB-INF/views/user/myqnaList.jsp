<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>    
<!-- 중앙 컨텐츠 시작 -->
<!-- 부트스트랩 라이브러리 -->
<link href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script type="text/javascript" src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script type="text/javascript" src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/user.css">
<style>
	.page-main a {
		font-size:22px;
	}
</style>
<!-- 중앙 컨텐츠 시작 -->
<div class="page-main">
	<div style="border:2px solid #c0c0c0;height:400px;padding:20px;margin:10px;">
	<h2>QNA 리스트</h2>
	<ul class="profile-photo">
		<li>
			<img id="cen_img" src="${pageContext.request.contextPath}/resources/images/qna.png"
			                     width="160" height="160" class="my-photo" onclick="location.href='myoqnaList.do'">전체 qna
		</li>
		<li>
			<img id="cen_img" src="${pageContext.request.contextPath}/resources/images/online.png"
			                     width="160" height="160" class="my-photo" onclick="location.href='${pageContext.request.contextPath}/onqna/onqnaList.do'">온라인 강의 qna
		</li>
		<li>
			<img id="cen_img" src="${pageContext.request.contextPath}/resources/images/offline.png"
			                     width="160" height="160" class="my-photo">오프라인 강의 qna    
		</li>
	</ul>
	</div>
</div>
<!-- 중앙 컨텐츠 끝 -->

