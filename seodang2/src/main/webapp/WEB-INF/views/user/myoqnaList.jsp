<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Gowun+Dodum&display=swap" rel="stylesheet">
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/userMain.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/resources/css/style_dw.css"
	type="text/css" />
<script
	src="${pageContext.request.contextPath}/resources/js/jquery-3.6.0.min.js"></script>
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
	<div style="height:130px;"></div>
	

	<div class="align-center">
		<img src="${pageContext.request.contextPath }/resources/image/neonQnA.png" style="width:90%; hieght:338px; margin:50px 0px;">
	</div>

<h2>나의 Q&A 목록</h2>
	<table>
		<tr style="font-family: 'Gowun Dodum', sans-serif;">
			<th style="width: 12%;">작성자ID</th>
			<th style="width: 50%;">제목</th>
			<th style="width: 10%;">회원등급</th>
			<th style="width: 10%;">작성일</th>
		</tr>
		<c:forEach var="oqna" items="${list}">
			<tr>
				<td style="width:12%;">${oqna.id }</td>
				<td style="width: 50%;"><a
					href="${pageContext.request.contextPath}/oqna/oqnaDetail.do?qna_num=${oqna.qna_num }">${oqna.title }</a></td>
				<td style="width: 10%;"><c:if test="${oqna.auth==0 }">탈퇴회원</c:if>
					<c:if test="${oqna.auth==1 }">정지회원</c:if> <c:if
						test="${oqna.auth==2 }">일반회원</c:if> <c:if test="${oqna.auth==3 }">선생님</c:if>
					<c:if test="${oqna.auth==4 }">관리자</c:if></td>
				<td style="width: 10%;">${oqna.reg_date }</td>
			</tr>
		</c:forEach>
	</table>

</div>

