<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script src="${pageContext.request.contextPath}/resources/js/jquery-3.6.0.min.js"></script>    
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/onclass2.css">

<style>
h3{
	padding:10px;
}

h3:hover {
  background:#F3B178;
  border-radius:5px;
  padding:10px;
}
h4:hover{
	border-bottom: 2px solid #F3B178;
}
</style>
  
	<div class="detail_content">
		<div class="ck_content">
			<h2>찜 목록</h2>
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
	<h3>내가 찜한 과목</h3>
</div>    
	
<c:if test="${count==0 }">
	<div>찜한 클래스가 없습니다</div>
</c:if>

<c:if test="${count>0}">
	<div class="item-space">
		<c:forEach var="like" items="${list}">
			<div class="horizontal-area">
				<c:choose>
					<c:when test="${like.onoff == 1}"><a href="${pageContext.request.contextPath}/onclass/onclassDetail.do?on_num=${like.on_num}"></c:when>
					<c:when test="${like.onoff == 2}"><a href="${pageContext.request.contextPath}/offclass/offclassDetail.do?off_num=${like.on_num}"></c:when>
					<c:when test="${like.onoff == 3}"><a href="${pageContext.request.contextPath}/kit/kitDetail.do?kit_num=${like.on_num}"></c:when>
				</c:choose>
					<div class="image-container">
						<c:choose>
							<c:when test="${like.onoff == 1}">
								<%-- <img src="${pageContext.request.contextPath}/onclass/imageView.do?on_num=${like.on_num}" 
	                          		style="max-width:200px;max-height:200px;margin-left:15px; margin-top:10px; border-radius: 10%;"> --%>
	                          	<!-- 오프라인 완성하면 교체 -->
	                          	<img src="${pageContext.request.contextPath}/resources/image_upload/${like.mimage}">
	                          	<!-- 오프라인 완성하면 교체 -->
	                        </c:when>
	                        <c:when test="${like.onoff == 2}">
	                        	<%-- <img src="${pageContext.request.contextPath}/offclass/imageView.do?off_num=${like.on_num}" 
	                          		style="max-width:200px;max-height:200px;margin-left:15px; margin-top:10px; border-radius: 10%;"> --%>
	                          	<img src="${pageContext.request.contextPath}/resources/image_upload/${like.mimage}">
	                        </c:when>
	                        <c:when test="${like.onoff == 3}">
	                        	<%-- <img src="${pageContext.request.contextPath}/offclass/imageView.do?off_num=${like.on_num}" 
	                          		style="max-width:200px;max-height:200px;margin-left:15px; margin-top:10px; border-radius: 10%;"> --%>
	                          	<img src="${pageContext.request.contextPath}/resources/image_upload/${like.mimage}">
	                        </c:when>
                        </c:choose>
					</div>
					<div class="item-category">
						<c:if test="${like.category_num ==1}">드로잉</c:if>
						<c:if test="${like.category_num ==2}">플라워</c:if>
						<c:if test="${like.category_num ==3}">공예</c:if>
						<c:if test="${like.category_num ==4}">요리</c:if>
						<c:if test="${like.category_num ==5}">베이킹</c:if>
						<c:if test="${like.category_num ==7}">키트</c:if>
					</div>
					<div class="name">${like.on_name}</div>
					<div class="align-right"><b>${like.on_price}원</b></div>	
					</a>			
					<img onclick="likeDelete()" id="output_fav" src="../resources/image/heart2.png">
					<!-- 찜 누르기 (숨김)-->
					<form action="likeDelete.do" method="post"> 
					<input type="hidden" name="on_num" value="${like.on_num}"/>
					<input type="hidden" name="onoff" value="${like.onoff}"/>
					<input id="likeDelete_btn" type="submit" value="전송" style="display:none;">
					</form>
					<!-- 찜 누르기 -->
			</div>
		</c:forEach>
	</div>
	<div class="align-center">${pagingHtml}</div>
	</c:if>
	
<script>
function likeDelete() {
	 if (confirm("찜 목록에서 지우시겠습니까?") == true){    //확인
		 document.getElementById('likeDelete_btn').click();
	 }else{   //취소
	     return false;
	 }
	/* document.getElementById('likeDelete_btn').click(); */
}
</script>    