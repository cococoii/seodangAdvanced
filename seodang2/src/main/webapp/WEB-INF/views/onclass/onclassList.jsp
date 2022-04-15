<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script src="${pageContext.request.contextPath}/resources/js/jquery-3.6.0.min.js"></script>
<!-- <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script> -->
<%-- <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/List.css"> --%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/onclass2.css">

<div class="container-right">
	<h3>온라인 CLASS</h3>
	<c:if test="${!empty session_user_num && session_user_auth>=3}">
	<div class="align-right">
		<input type="button" value="CLASS 등록" onclick="location.href='onclassInsert.do'" class="btn btn-outline-secondary">
	</div>
	</c:if>
	<c:if test="${count==0 }">
		<div >등록된 클래스가 없습니다.</div>
	</c:if>
	<c:if test="${count>0 }">
	<div class="item-space">
	<div class="banner-img">
	<img src="${pageContext.request.contextPath}/resources/images/onbanner.png" width="100%">
	</div>
		<c:forEach var="item" items="${list }">
			<div class="horizontal-area">
				<a href="onclassDetail.do?on_num=${item.on_num }">
					<div class="image-container">
						<img src="${pageContext.request.contextPath}/resources/image_upload/${item.mimage}">
					</div>
					<div class="item-category" style="width:80px">
					<c:if test="${item.category_num ==1}">드로잉</c:if>
					<c:if test="${item.category_num ==2}">플라워</c:if>
					<c:if test="${item.category_num ==3}">공예</c:if>
					<c:if test="${item.category_num ==4}">요리</c:if>
					<c:if test="${item.category_num ==5}">베이킹</c:if>
					</div>
					<div class="name">${item.name }</div>
					<div>${item.on_name }</div>
					<%-- <div><img src="${pageContext.request.contextPath}/resources/images/heart_gray.png"><span>${item.like_count }</span></div> --%>
					<div>
						<div class="display-flex">
						<div class="heart-padding"><img class="heart-gray" src="${pageContext.request.contextPath}/resources/images/heart_gray.png" width="20"><span>${item.like_count }</span></div>
						<div class="display-flex star">
							<div class="star-ratings stars">
								<div class="star-ratings-fill space-x-2 text-lg"
									style="width:${item.rating_percent}%">
									<div>
										<span>★</span><span>★</span><span>★</span><span>★</span><span>★</span>
									</div>
								</div>
								<div class="star-ratings-base space-x-2 text-lg">
									<span>★</span><span>★</span><span>★</span><span>★</span><span>★</span>
								</div>
							</div>
							<div>${item.rating }</div>
							</div>						
					</div>
						<div class="align-right"><b><fmt:formatNumber type="number" maxFractionDigits="3" value="${item.on_price }" />원</b></div>
					</div>
				</a>
			</div>					
		</c:forEach>
	</div>
	<div class="align-center">${pagingHtml}</div>
	</c:if>
</div>