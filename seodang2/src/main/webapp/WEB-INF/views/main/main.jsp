<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>    
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script src="${pageContext.request.contextPath}/resources/js/jquery-3.6.0.min.js"></script>
<script src="https://unpkg.com/swiper/swiper-bundle.min.js"></script>
<link rel="stylesheet" href="https://unpkg.com/swiper/swiper-bundle.min.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/offclass.css">


<!-- 메인 시작 -->
<div class="main">
	<div class="swiper">
	  <!-- Additional required wrapper -->
	  <div class="swiper-wrapper">
	    <!-- Slides -->
	    <div class="swiper-slide"><img src="${pageContext.request.contextPath}/resources/images/main2_3.png" width="100%"></div>
	    <div class="swiper-slide"><a href="${pageContext.request.contextPath}//onclass/onclassList.do"><img src="${pageContext.request.contextPath}/resources/images/main4.png" width="100%"></a></div>
	    <div class="swiper-slide"><a href="${pageContext.request.contextPath}/offclass/offclassList.do"><img src="${pageContext.request.contextPath}/resources/images/main5.png" width="100%"></a></div>
	    <div class="swiper-slide"><a href="${pageContext.request.contextPath}/kit/kitList.do"><img src="${pageContext.request.contextPath}/resources/images/main6.png" width="100%"></a></div>
	    ...
	  </div>
	  <!-- If we need pagination -->
	  <div class="swiper-pagination"></div>
	
	  <!-- If we need navigation buttons -->
	  <div class="swiper-button-prev" style="width:85px"></div>
	  <div class="swiper-button-next" style="width:85px"></div>
	
	  <!-- If we need scrollbar -->
	  <div class="swiper-scrollbar"></div>
	</div>
<section class="section">
	<div>
		<h2>온.오프 만족도가 높은 강의</h2>
	</div>
	<div class="item-space">
	<c:forEach var="onListRank" items="${onListRank}">
		<div class="horizontal-area">
			<c:choose>
				<c:when test="${onListRank.onoff == 1}"><a href="${pageContext.request.contextPath}/onclass/onclassDetail.do?on_num=${onListRank.on_num}"></c:when>
				<c:when test="${onListRank.onoff == 2}"><a href="${pageContext.request.contextPath}/offclass/offclassDetail.do?off_num=${onListRank.on_num}"></c:when>
			</c:choose>
					<div class="main-image">
						<img src="${pageContext.request.contextPath}/resources/image_upload/${onListRank.mimage}">
					</div>
					<div class="item-category">
					<c:if test="${onListRank.category_num ==1}">드로잉</c:if>
					<c:if test="${onListRank.category_num ==2}">플라워</c:if>
					<c:if test="${onListRank.category_num ==3}">공예</c:if>
					<c:if test="${onListRank.category_num ==4}">요리</c:if>
					<c:if test="${onListRank.category_num ==5}">베이킹</c:if>
					</div>
					<div class="name">${onListRank.name }</div>
					<div>${onListRank.on_name }</div>
					<div class="star-heart-money">
					<%-- <div><img src="${pageContext.request.contextPath}/resources/images/heart_gray.png"><span>${item.like_count }</span></div> --%>
					<div>
						<div class="display-flex">

						
						<div class="display-flex star">
							<div class="star-ratings stars">
								<div class="star-ratings-fill space-x-2 text-lg"
									style="width:${onListRank.rating_percent}%">
									<div>
										<span>★</span><span>★</span><span>★</span><span>★</span><span>★</span>
									</div>
								</div>
								<div class="star-ratings-base space-x-2 text-lg">
									<span>★</span><span>★</span><span>★</span><span>★</span><span>★</span>
								</div>
							</div>
							<div>${onListRank.rating}</div>
							</div>	
							</div>					
					</div>
					<div class="space"></div>
						<div class="align-right"><b><fmt:formatNumber type="number" maxFractionDigits="3" value="${onListRank.on_price }" />원</b></div>
					</div>
				</a>
			</div>					
	</c:forEach>
</div>	
</section>
<section class="section">
	<div>
		<h2>오프라인 강의</h2>		<!-- 댓글평점이 존재해야 메인에 오프라인 강의 출력 -->
	</div>
	<div class="item-space">
	<c:forEach var="offList" items="${offList}">
		<div class="horizontal-area">
				<a href="${pageContext.request.contextPath}/offclass/offclassDetail.do?off_num=${offList.off_num }" class="display-column">
					<div class="main-image">
						<img src="${pageContext.request.contextPath}/resources/image_upload/${offList.mimage}">
					</div>
					<div class="item-category">
					<c:if test="${offList.category_num ==1}">드로잉</c:if>
					<c:if test="${offList.category_num ==2}">플라워</c:if>
					<c:if test="${offList.category_num ==3}">공예</c:if>
					<c:if test="${offList.category_num ==4}">요리</c:if>
					<c:if test="${offList.category_num ==5}">베이킹</c:if>
					</div>
					<div class="name">${offList.name }</div>
					<div class="title">${offList.off_name }</div>
					<div class="star-heart-money">
					<div>
						<div class="display-flex">
						<div class="heart-padding"><img class="heart-gray" src="${pageContext.request.contextPath}/resources/images/heart_gray.png" width="20"><span>${offList.like_sum }</span></div>
						<div class="display-flex star">
							<div class="star-ratings stars">
								<div class="star-ratings-fill space-x-2 text-lg"
									style="width:${offList.rating_percent}%">
									<div>
										<span>★</span><span>★</span><span>★</span><span>★</span><span>★</span>
									</div>
								</div>
								<div class="star-ratings-base space-x-2 text-lg">
									<span>★</span><span>★</span><span>★</span><span>★</span><span>★</span>
								</div>
							</div>
							<div>${offList.rating}</div>
							</div>
						</div>
					</div>
					<div class="space"></div>
					<div class="align-right list-money"><b><fmt:formatNumber type="number" maxFractionDigits="3" value="${offList.off_price }" />원</b></div>
					</div>
				</a>
			</div>
	</c:forEach>
</div>	

</section>
<section class="section">
	<div>
		<h2>온라인 강의</h2>
	</div>
	<div class="item-space">
		<c:forEach var="onList" items="${onList }">
			<div class="horizontal-area">
				<a href="${pageContext.request.contextPath}/onclass/onclassDetail.do?on_num=${onList.on_num }">
					<div class="main-image">
						<img src="${pageContext.request.contextPath}/resources/image_upload/${onList.mimage}" class="display-column">
					</div>
					<div class="item-category">
					<c:if test="${onList.category_num ==1}">드로잉</c:if>
					<c:if test="${onList.category_num ==2}">플라워</c:if>
					<c:if test="${onList.category_num ==3}">공예</c:if>
					<c:if test="${onList.category_num ==4}">요리</c:if>
					<c:if test="${onList.category_num ==5}">베이킹</c:if>
					</div>
					<div class="name">${onList.name }</div>
					<div class="title">${onList.on_name }</div>
					<div class="star-heart-money">
					<div>
						<div class="display-flex">
						<div class="heart-padding"><img class="heart-gray" src="${pageContext.request.contextPath}/resources/images/heart_gray.png" width="20"><span>${onList.like_count }</span></div>
						<div class="display-flex star">
							<div class="star-ratings stars">
								<div class="star-ratings-fill space-x-2 text-lg"
									style="width:${onList.rating_percent}%">
									<div>
										<span>★</span><span>★</span><span>★</span><span>★</span><span>★</span>
									</div>
								</div>
								<div class="star-ratings-base space-x-2 text-lg">
									<span>★</span><span>★</span><span>★</span><span>★</span><span>★</span>
								</div>
							</div>
							<div>${onList.rating}</div>
							</div>	
							</div>					
					</div>
					
						<div class="align-right"><b><fmt:formatNumber type="number" maxFractionDigits="3" value="${onList.on_price }" />원</b></div>
					</div>
				</a>
			</div>					
		</c:forEach>
	</div>
</section>
<section class="section">
	<div>
		<h2>키트</h2>
	</div>
	<div class="item-space">
	<c:forEach var="kitList" items="${kitList}">
		<div class="horizontal-area">
				<a href="${pageContext.request.contextPath}/kit/kitDetail.do?kit_num=${kitList.kit_num }" class="kit-area">
					<div class="kit-image">
						<img src="${pageContext.request.contextPath}/resources/image_upload/${kitList.mimage}">
					</div>
					<div class="title">${kitList.kit_name }</div>
					<div class="star-heart-money">
					<div>
						<div class="display-flex">
						<div class="heart-padding"><img class="heart-gray" src="${pageContext.request.contextPath}/resources/images/heart_gray.png" width="20"><span>${kitList.like_count }</span></div>
						</div>
					</div>
					<div class="space"></div>
					<div class="display-flex kit-price-quantity">
					<c:if test = "${kitList.kit_quantity != 0}">
						<span>재고 수량 : ${kitList.kit_quantity}</span>
						<span class="kit-price"><b><fmt:formatNumber type="number" maxFractionDigits="3" value="${kitList.kit_price }" />원</b></span>
					</c:if>
					<c:if test = "${kitList.kit_quantity == 0}">
						<span style="color:red;">품절</span>
					</c:if>
					</div>
					</div>
				</a>
			</div>
	</c:forEach>
</div>	

</section>

<!-- 시작 -->
</div>
<!-- 끝 -->
<script>
const swiper = new Swiper('.swiper', {
	  loop: true,

	  // If we need pagination
	  pagination: {
	    el: '.swiper-pagination',
	  },

	  // Navigation arrows
	  navigation: {
	    nextEl: '.swiper-button-next',
	    prevEl: '.swiper-button-prev',
	  },
	  autoplay: { 
		  delay: 5000, 
		},
	});
</script>