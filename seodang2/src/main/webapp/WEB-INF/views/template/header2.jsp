<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="${pageContext.request.contextPath}/resources/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/resources/js/main.js"></script><header>
	<div class="mainpage">
		<div class="flexdisplay">
			<div class="logo">
				<a href="${pageContext.request.contextPath }/main/main.do"
					class="align-left"><img class="logo" src="${pageContext.request.contextPath}/resources/images/logo2_1.png"></a>
			</div>
			<div class="menu clearfix">
				<a href="${pageContext.request.contextPath}/onclass/onclassList.do"
					class="menu-item"><b>온라인</b></a> <a
					href="${pageContext.request.contextPath}/offclass/offclassList.do"
					class="menu-item"><b>오프라인</b></a> <a
					href="${pageContext.request.contextPath}/kit/kitList.do"
					class="menu-item"><b>키트</b></a>
				<a href="${pageContext.request.contextPath}/oqna/oqnaList.do" class="menu-item"><b>Q&amp;A</b></a>
				<a href="${pageContext.request.contextPath}/news/list.do" class="menu-item"><b>취업 뉴스</b></a>
			</div>
			<div class="search-form display-flex">
				<form action="${pageContext.request.contextPath}/search/total_search.do" method="get" id="total_search">
					<input type="search" name="keyword" id="total_keyword" placeholder="취미를 검색하세요">
					<span class="totalsearch-item"><img src="${pageContext.request.contextPath}/resources/images/magnifier.png" style="height:21px;"></span>
				</form>
			</div>
			<script type="text/javascript">
				$('.totalsearch-item').click(function(){
					if($('#total_keyword').val() == ''){
						alert('검색어을 입력하세요');
						$('#total_keyword').val('').focus();
						return;
					}
					$('#total_search').submit();
				});
			</script>
			<div class="login align-right">
				<div class="header-display">
				<c:if test="${empty session_user_num}">
					<a href="${pageContext.request.contextPath}/user/registerUser.do" class="menu-item">회원가입</a>
					<a href="${pageContext.request.contextPath}/user/login.do" class="menu-item">로그인</a>
				</c:if>
				<c:if test="${!empty session_user_num }">
					<c:if test="${!empty session_user_num && session_user_auth == 4}">
						<a href="${pageContext.request.contextPath}/admin/adminMemberList.do" class="menu-item">회원관리</a>
						<a href="${pageContext.request.contextPath}/cart/adminOrderList.do" class="menu-item">주문관리</a>
					</c:if>
					<a href="${pageContext.request.contextPath}/user/myMenu.do" class="menu-item">마이클래스</a>
					
					<c:if test="${!empty session_user_num && session_user_auth != 4}">
						<a href="${pageContext.request.contextPath}/cart/orderList.do" class="menu-item">주문내역</a>
						<!-- 장바구니 링크 연결-->
						<a href="${pageContext.request.contextPath}/cart/cartList.do" class="menu-item"><img src="${pageContext.request.contextPath}/resources/images/shopping-cart.png" width="26"></a>
					</c:if>
					<!-- 찜한 목록 링크 연결 -->
					<a class="menu-item" href="#"><img src="${pageContext.request.contextPath}/resources/images/heart_black_nofill.png"></a>
					
					
					
					<div id="myPage" class="menu-item">
					<c:if test="${!empty session_user_photo}">
						<img src="${pageContext.request.contextPath}/user/photoView.do" width="34" height="34" class="my-photo">
					</c:if>
					<c:if test="${empty session_user_photo}">
						<img src="${pageContext.request.contextPath}/resources/images/face.png" width="34" height="34" class="my-photo">
					</c:if>
					<span id="myPageArrow">
					<img src="${pageContext.request.contextPath}/resources/images/down_arrow.png">
					</span>
					<div id="myPagePopup">
						<a href="${pageContext.request.contextPath}/user/myMenu.do">
							<div class="display-flex">
								<c:if test="${!empty session_user_photo}">
									<img src="${pageContext.request.contextPath}/user/photoView.do" width="34" height="34" class="my-photo">
								</c:if>
								<c:if test="${empty session_user_photo}">
									<img src="${pageContext.request.contextPath}/resources/images/face.png" width="34" height="34" class="my-photo">
								</c:if>
								<div class="myPageName">
									<c:if test="${session_user_auth==2 }">[회원]</c:if>
									<c:if test="${session_user_auth==3 }">[강사]</c:if>
									<c:if test="${session_user_auth==4 }">[관리자]</c:if>
									<span>${session_user_name}</span>
									<div>
									마이페이지>
									</div>
								</div>
							</div>
						</a>
						<div class="popupNo"></div>			
						<a href="${pageContext.request.contextPath}/user/logout.do">로그아웃</a>
					</div>
					</div>
				</c:if>
				</div>
			</div>
			
			
		</div>

		<div class="header2">
			
			
		</div>
	</div>
</header>
<!— 상단 끝 —>