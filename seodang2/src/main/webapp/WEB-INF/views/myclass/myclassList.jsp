<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
    
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/onclass2.css">

<style>
h3{
	padding:10px;
}

h3:hover {
  background:#F3B178;
  border-radius:5px;
  padding:10px;
  cursor:pointer;
}
h4:hover{
	border-bottom: 2px solid #F3B178;
}
</style>

<div class="detail_content">
		<div class="ck_content">
			<h2>내가 등록한 클래스 목록</h2>
		</div>
	</div>
	
	<div style="padding:0 10px;display: block;width: 20.3333%;float: left;">
		<div style="position: sticky;padding:20px;font-family: 'Jeju Gothic', sans-serif;">
			<h3 onclick="location.href='${pageContext.request.contextPath}/user/myMenu.do'">홈</h3>
			<h3 onclick="location.href='${pageContext.request.contextPath}/user/myPage.do'">개인정보</h3>
			<h3 onclick="location.href='${pageContext.request.contextPath}/user/myoqnaList.do'">Q&A 리스트</h3>
			<h3 onclick="location.href='${pageContext.request.contextPath}/myclass/myclassList.do'">내 클래스 목록</h3> 
			<h3 onclick="location.href='${pageContext.request.contextPath}/myclass/likeList.do'">찜 목록</h3>
			<h3 onclick="location.href='${pageContext.request.contextPath}/cart/orderList.do'">내 주문내역</h3>
		</div>
	</div>
	


<div class="container-right">
	<h3>내가 등록한 과목</h3>
</div>    
	<div class="item-space">
		<!-- foreach 문 시작 -->
		<c:forEach var="myclass" items="${list}">
			<div class="horizontal-area">
				<a href="myclassData.do?on_num=${myclass.on_num}">
					<div class="image-container">
						<!-- 폴더 저장 메인 이미지 -->
						<img src="${pageContext.request.contextPath}/resources/image_upload/${myclass.mimage}">
					</div>
					<div class="item-category">
					<c:if test="${myclass.category_num ==1}">드로잉</c:if>
					<c:if test="${myclass.category_num ==2}">플라워</c:if>
					<c:if test="${myclass.category_num ==3}">공예</c:if>
					<c:if test="${myclass.category_num ==4}">요리</c:if>
					<c:if test="${myclass.category_num ==5}">베이킹</c:if>
					</div>
					<div class="name">${myclass.on_name}</div>
					<div></div>
					<div>
					<%-- <img src="${pageContext.request.contextPath}/resources/images/heart_gray.png"> --%>
					</div>
					<div class="align-right"><b>${myclass.on_price}원</b></div>
					<div></div>
				</a>
			</div>
		</c:forEach>
	</div>
	
	<div class="align-center">${pagingHtml}</div>
	
	<!-- 헤더 겹침 임시 위치 -->
	<a href="${pageContext.request.contextPath}/myclass/myclassList.do">전체보기</a>
	<a href="${pageContext.request.contextPath}/myclass/#">수강생 많은 순</a>
	
	
	