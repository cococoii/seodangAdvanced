<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/offclass.css">
<!-- 중앙 컨텐츠 시작 -->
<script src="${pageContext.request.contextPath}/resources/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/offreview.reply.js"></script>
<div class="back-title">
	<span class="backspace" >
	<img id="back_img" src="${pageContext.request.contextPath}/resources/images/back_arrow.png" data-off_num="${off_num }">
	</span>
	<div class="title">
		<h3>최근 리뷰</h3>
	</div>
</div>
<div class="review_list">
	<div class="rating">
		<div class="rating_num">
			<div class="avg_rating">
				${rating }
			</div>
			<div id="count" data-count=${count }>
				총 ${count } 개
			</div>
		</div>
		<div class="star_rating">
			<div class="star_num">
				<div class="score">
					5점
				</div>
				<div class="graph">
					<div class="graph-5">
					</div>
				</div>
				<div class="star star_5" data-value=${ star_5 }>
					${ star_5 }
				</div>
			</div>
			<div class="star_num">
				<div class="score">
					4점
				</div>
				<div class="graph">
					<div class="graph-4">
					</div>
				</div>
				<div class="star star_4" data-value=${ star_4 }>
					${ star_4 }
				</div>
			</div>
			<div class="star_num">
				<div class="score">
					3점
				</div>
				<div class="graph">
					<div class="graph-3">
					</div>
				</div>
				<div class="star star_3" data-value=${ star_3 }>
					${ star_3 }
				</div>
			</div>
			<div class="star_num">
				<div class="score">
					2점
				</div>
				<div class="graph">
					<div class="graph-2">
					</div>
				</div>
				<div class="star star_2" data-value=${ star_2 }>
					${ star_2 }
				</div>
			</div>
			<div class="star_num">
				<div class="score">
					1점
				</div>
				<div class="graph">
					<div class="graph-1">
					</div>
				</div>
				<div class="star star_1" data-value=${ star_1 }>
					${ star_1 }
				</div>
			</div>
		</div>
	</div>
	<!-- 별점 끝 list 보여주기 -->
	<div class="sorting-item align-right">
	<div class="display-flex">
	<div class="page-sort review-sort">
		<a class="menu" href="${pageContext.request.contextPath}/offclass/offclassReviewList.do?off_num=${off_num }">최신순</a>
		<span class="spacing">|</span>
		<a class="menu" href="${pageContext.request.contextPath}/offclass/offclassReviewList.do?off_num=${off_num }&&sort=1">별점높은순</a>
		<span class="spacing">|</span>
		<a class="menu" href="${pageContext.request.contextPath}/offclass/offclassReviewList.do?off_num=${off_num }&&sort=2">별점낮은순 </a>
	</div>
	</div></div>
	<div id="reviewList" data-name="${writer_name }" data-user="${writer_num }" data-photo="${photo_name }">
	<c:if test="${count==0 }">
	<div class="no-result">
		등록한 후기가 없습니다.
	</div>
	</c:if>
	<c:if test="${count>0 }">
		<c:forEach var="item" items="${list }">
		<div class="review-set"  data-num="${item.offstar_num}">
			<div class="review" >
				<div class="review_start">
					<div class="user_image">
						<c:if test="${!empty item.photo_name }">
							<img src="imageViewUser.do?user_num=${item.user_num}" >
						</c:if>
						<c:if test="${empty item.photo_name }">
							<img src="${pageContext.request.contextPath}/resources/images/face.png">
						</c:if>
					</div>
					<div class="name-rating">
						<div>
							<div>
								${item.name } <span class="re-date">${item.reg_date }</span>
							</div>
							<div class="star-ratings">
								<div class="star-ratings-fill space-x-2 text-lg"  style="width:${item.rating_percent}%">
									<div>
										<span>★</span><span>★</span><span>★</span><span>★</span><span>★</span>
									</div>
								</div>
								<div class="star-ratings-base space-x-2 text-lg">
									<span>★</span><span>★</span><span>★</span><span>★</span><span>★</span>
								</div>
							</div>
						</div>
					</div>
					<c:if test="${item.user_num==session_user_num }">
					<div class="re-btn">
					<button type="button" class="btn openPopup"  data-num="${item.offstar_num }"><img src="${pageContext.request.contextPath}/resources/images/menu-modify.png" width=22></button>
					</div>
					</c:if>
				</div>
			<div class="review-text">${item.text }</div>
			</div>
			<c:if test="${writer_num==session_user_num }">
			<div class="reply-output" >
				<c:if test="${empty item.writer_num}">
				<div class="reply ${item.offstar_num }">
				<button class="btn display-flex reply-btn">
					<div>
						<img src="${pageContext.request.contextPath}/resources/images/speech_bubble.png">
					</div>
					<div>
						<div class="reply-text">답글 달기</div>
					</div>
				</button>
				</div>
				<div id="output" >
				</div>
				</c:if>
			</div>
			</c:if>
			<div id=${item.offstar_num } data-offre_num=${item.offre_num }>
			<c:if test="${!empty item.writer_num}">
			<div class="reply-complete display-flex">
				<div class="user_image">
					<c:if test="${!empty item.writer_photo }">
						<img src="imageViewUser.do?user_num=${item.writer_num}" >
					</c:if>
					<c:if test="${empty item.writer_photo }">
						<img src="${pageContext.request.contextPath}/resources/images/face.png">
					</c:if>
				</div>
				<div class="balloon">
					<div class="display-flex">
						<div class="writer_name">
							<b>${item.writer_name } 강사님&nbsp;</b>
						</div>
						<div class="re-date">
							${item.offre_date }
						</div>
						<c:if test="${writer_num==session_user_num }">
						<div class="re-btn">
							<div class="display-flex">
								<input type="button" id="modify-btn" class="btn" value="수정">
								<input type="button" id="delete-btn" class="btn" value="삭제">
							</div>
						</div>
						</c:if>
					</div>
					<div class="re-content" data-re_content="${item.offre_content }">
						${item.offre_content }
					</div>
				</div>
			</div>
			</c:if>
			</div>
			<hr size="1" class="hr">
		</div>
		</c:forEach>
	</c:if>
	</div>
	<div id="popup01">
		<div class="close"><img class="popup-close" src="${pageContext.request.contextPath}/resources/images/close.png" onclick=""></div>
		<h2>메뉴</h2>
    	<div class="popup-text"><a href="offclassReviewUpdate.do?off_num=${off_num }"><img src="${pageContext.request.contextPath}/resources/images/pencil.png"> 수정하기</a></div>
    	<div class="popup-text" id="delete-reply"><img src="${pageContext.request.contextPath}/resources/images/trash.png">&nbsp;삭제하기</div>
	</div>
</div>