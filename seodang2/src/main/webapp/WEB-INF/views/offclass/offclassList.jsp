<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/offclass.css">
<div class="container-right">
<div class="display-flex page-header">
	<h3>오프라인 CLASS</h3>
	<div class="display-flex page-sort">
		<select onchange="if(this.value) location.href=(this.value);">
			<option value="${pageContext.request.contextPath}/offclass/offclassList.do<c:if test="${category_num==0 }"></c:if><c:if test="${category_num!=0 }">?category_num=${category_num }</c:if>">최신순</option>
			<option value="${pageContext.request.contextPath}/offclass/offclassList.do?<c:if test="${category_num==0 }"></c:if><c:if test="${category_num!=0 }">category_num=${category_num }&&</c:if>sort=1"<c:if test="${sort==1}">selected</c:if>>추천순</option>
			<option value="${pageContext.request.contextPath}/offclass/offclassList.do?<c:if test="${category_num==0 }"></c:if><c:if test="${category_num!=0 }">category_num=${category_num }&&</c:if>sort=2"<c:if test="${sort==2}">selected</c:if>>만족도순</option>
		</select>
	</div>
</div>
	<c:if test="${!empty session_user_num && session_user_auth>=3}">
	<div class="align-right">
		<input type="button" value="CLASS 등록" onclick="location.href='offclassOpen.do'" class="btn-black">
	</div>
	</c:if>
	<c:if test="${count==0 }">
		<div >등록된 클래스가 없습니다.</div>
	</c:if>
	<c:if test="${count>0 }">
	<div class="item-space">
	<div class="banner-img">
	<img src="${pageContext.request.contextPath}/resources/images/offbanner.png" width="100%">
	</div>
		<c:forEach var="item" items="${list }">
			<div class="horizontal-area">
				<a href="offclassDetail.do?off_num=${item.off_num }" class="display-column">
					<div class="image-container">
						<img src="imageView.do?off_num=${item.off_num}" >
					</div>
					<span class="item-category">
					<c:if test="${item.category_num ==1}">드로잉</c:if>
					<c:if test="${item.category_num ==2}">플라워</c:if>
					<c:if test="${item.category_num ==3}">공예</c:if>
					<c:if test="${item.category_num ==4}">요리</c:if>
					<c:if test="${item.category_num ==5}">베이킹</c:if>
					</span>
					<span class="name">${item.name }</span>
					<div class="title">${item.off_name }</div>
					<div class="star-heart-money">
					<div>
						<div class="display-flex">
						<div class="heart-padding"><img class="heart-gray" src="${pageContext.request.contextPath}/resources/images/heart_gray.png" width="20"><span>${item.like_sum }</span></div>
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
					</div>
					<div class="space"></div>
					<div class="align-right"><b><fmt:formatNumber type="number" maxFractionDigits="3" value="${item.off_price }" />원</b></div>
					</div>
				</a>
			</div>
		</c:forEach>
	</div>
	<div class="display-flex">
	<div class="page-align-right"></div>
	<div class="page-align-center">${pagingHtml }</div>
	</div>
	</c:if>
</div>