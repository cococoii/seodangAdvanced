<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/kit.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/onclass2.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/offclass.css">
<!-- 회원상태 (0탈퇴회원,1정지회원,2일반회원,선생님회원,3관리자), 디폴트값 2 -->
<!-- 중앙 컨텐츠 시작 -->
<div class="listpage">
	<h2>통합 검색</h2>
	
	<h4>온라인클래스</h4>	
	<c:if test="${on_count==0 }">
		<div >검색된 온라인클래스가 없습니다.</div>
	</c:if>
	<c:if test="${on_count>0 }">
	<div class="item-space">
		<c:forEach var="item" items="${onList }">
			<div class="horizontal-area">
				<a href="${pageContext.request.contextPath}/onclass/onclassDetail.do?on_num=${item.on_num }">
					<div class="image-container">
						<img src="${pageContext.request.contextPath}/resources/image_upload/${item.mimage}">
					</div>
					<div class="item-category">
					<c:if test="${item.category_num ==1}">드로잉</c:if>
					<c:if test="${item.category_num ==2}">플라워</c:if>
					<c:if test="${item.category_num ==3}">공예</c:if>
					<c:if test="${item.category_num ==4}">요리</c:if>
					<c:if test="${item.category_num ==5}">베이킹</c:if>
					</div>
					<div class="name">${item.id }</div>
					<div>${item.on_name }</div>
					<div><img src="${pageContext.request.contextPath}/resources/images/heart_gray.png"><span>${item.like_count }</span></div>
					<div class="align-right"><b><fmt:formatNumber type="number" maxFractionDigits="3" value="${item.on_price }" />원</b></div>
				</a>
			</div>
		</c:forEach>
	</div>
	</c:if>
	<hr width="100%" size="1" noshade="noshade">
	
	<h4>오프라인클래스</h4>
	
	<c:if test="${off_count==0 }">
		<div >검새된 오프라인클래스가 없습니다.</div>
	</c:if>
	<c:if test="${off_count>0 }">
	<div class="item-space">
		<c:forEach var="item" items="${offList }">
			<div class="horizontal-area">
				<a href="${pageContext.request.contextPath}/offclass/offclassDetail.do?off_num=${item.off_num }">
					<div class="image-container">
						<img src="${pageContext.request.contextPath}/offclass/imageView.do?off_num=${item.off_num}" >
					</div>
					<div class="item-category">
					<c:if test="${item.category_num ==1}">드로잉</c:if>
					<c:if test="${item.category_num ==2}">플라워</c:if>
					<c:if test="${item.category_num ==3}">공예</c:if>
					<c:if test="${item.category_num ==4}">요리</c:if>
					<c:if test="${item.category_num ==5}">베이킹</c:if>
					</div>
					<div class="name">${item.name }</div>
					<div>${item.off_name }</div>
					<div class="display-flex">
						<div><img src="${pageContext.request.contextPath}/resources/images/heart_gray.png"><span>${item.like_sum }</span></div>
						<div class="star-ratings">
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
					<div class="space"></div>
					<div class="align-right"><b><fmt:formatNumber type="number" value="${item.off_price}" />원</b></div>
					</div>
				</a>
			</div>
		</c:forEach>
	</div>
	</c:if>
	
	<hr width="100%" size="1" noshade="noshade">
	<h4>키트</h4>	
	<c:if test="${kit_count == 0}">
	<div class="result-display">검색된 키트가 없습니다.</div>
	</c:if>
	<div class="list-main">	
	<c:if test="${kit_count > 0}">
	<c:forEach var="kit" items="${kitList}">
	<ul>
	<li>
	<div class="box">
	 <div id="kit_num">${kit.kit_num}</div>
	<div><a href="${pageContext.request.contextPath}/kit/kitDetail.do?kit_num=${kit.kit_num}"><img src="${pageContext.request.contextPath}/kit/imageView.do?kit_num=${kit.kit_num}"  style="width:250px; height:200px; border-radius:5px;"></a></div>
		<div id="">등록일 : ${kit.reg_date}</div> -->
		<table id="box3">
		<tr><th><div id="kit_name"><a href="${pageContext.request.contextPath}/kit/kitDetail.do?kit_num=${kit.kit_num}">${kit.kit_name}</a></div></th></tr>
	    <tr><td><div id="kit_content2">${kit.kit_content2}</div></td>
	</tr>
	<tr>
		<td id="kit_quantity"><c:if test = "${kit.kit_quantity != 0}">
			<span>재고 수량 : ${kit.kit_quantity}</span>
		</c:if>
		<c:if test = "${kit.kit_quantity == 0}">
			<span style="color:red;">품절</span>
		</c:if></td>
	</tr>
	<tr>
		<td><div class="price"></div>
		<div id="kit_price">판매가 ${kit.kit_price}원</div></td>
	</tr>
		</table>	
	</div>
	</li>
	</ul>
	</c:forEach>
	</c:if>
	</div>
</div>

<!-- 중앙 컨텐츠 끝 <div id="pagenum">${pagingHtml}</div> -->
