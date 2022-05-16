<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/offclass.css">
<!-- 중앙 컨텐츠 시작 -->
<script src="${pageContext.request.contextPath}/resources/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/resources/js/videoAdapter.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/resources/js/offclass.detail.js"></script>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=c937f1e64d49fa65f28e4eafd42ed12d&libraries=services"></script>
<script type="text/javascript">
	$('#delete-btn').click(function(){
		let off_num = ${offclass.off_num};
		alert(off_num);
		let choice = confirm('정말 삭제하시겠습니까?삭제 후 복구 불가능합니다.');
		if(choice){
			$.ajax({
			type:'post',
			data:{off_num:off_num},
			url:'deleteOffClass.do',
			cache:false,
			timeout:30000,
			success:function(param){
				if(param.result=='logout'){
					alert('로그인해야 삭제할 수 있습니다.');
				}else if(param.result=='success'){
					location.reload();
				}else{
					alert('등록 시 오류 발생');
				}
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
		});
		}
	});
</script>
<div class="display-flex margin-100">
	<div class="img-one"">
	<img src="${pageContext.request.contextPath}/resources/image_upload/${upfile0.file_name}">
	</div>
	<div class="display-flex flex-column">
		<div class="img-two">
			<img src="${pageContext.request.contextPath}/resources/image_upload/${upfile1.file_name}">
		</div>
		<div class="display-flex flex-width-100">
			<div class="img-three">
			<img src="${pageContext.request.contextPath}/resources/image_upload/${upfile2.file_name}">
			</div>
			<div class="img-four">
			<img src="${pageContext.request.contextPath}/resources/image_upload/${upfile3.file_name}">
			</div>
		</div>
	</div>
</div>
<div class="container-right">
	<div class="container-head">
		<h3>오프라인 클래스 상세</h3>
		<div class="modify-delete-btn">
			<c:if test="${offclass.user_num == session_user_num }">
				<input class="btn-black  width-170" type="button" value="수정"
					onclick="location.href='offclassUpdate.do?off_num=${offclass.off_num}'">
			</c:if>
			<span class="list-space"></span>
			<c:if
				test="${offclass.user_num == session_user_num || session_user_auth==4 }">
				<input class="btn-orange  width-170" type="button" value="삭제" id="delete-btn"
					onclick="location.href='offclassDelete.do?off_num=${offclass.off_num}'">
			</c:if>
			<span class="list-space"></span>
			<input class="btn-black  width-170" type="button" value="목록"
					onclick="location.href='offclassList.do'">
		</div>
	</div>
	<div class="detail_content">
		<div></div>
		<div class="ck_content">
			<div>${offclass.off_content }</div>
			<!-- 위치 넣기 -->
			<div>
				<div>
					<div>오프라인 장소</div>
					<span id="offaddress1">${offclass.offaddress1 }</span>
					<span id="offaddress2">${offclass.offaddress2 }</span>
					<span id="offzipcode">(${offclass.offzipcode })</span>
				</div>
			<div id="map" style="width: 100%; height: 350px;"></div>
			</div>
			<hr class="off_detail" size="1">
			<div class="off_review align-right">
				<button  class="btn detail-replybtn" onclick="location.href='offclassReview.do?off_num=${offclass.off_num}'"><img src="${pageContext.request.contextPath}/resources/images/white-pencil.png" width="20px">&nbsp;후기작성하기</button>
			</div>
			<div class="align-center">
				<div class="average-review display-flex">
					<div class="avg_rating">${rating }</div>
					<div>총 ${review_count }개</div>
				</div>
			</div>
			<div class="reviewList">
				<c:forEach var="offstarVO" items="${list2 }">
					<div class="review width-50">
							<div>
								<div class="review_start">
									<div class="user_image">
										<c:if test="${!empty offstarVO.photo_name }">
											<img src="imageViewUser.do?user_num=${offstarVO.user_num}">
										</c:if>
										<c:if test="${empty offstarVO.photo_name }">
											<img
												src="${pageContext.request.contextPath}/resources/images/face.png">
										</c:if>
									</div>
									<div class="name-rating">
										<div>
											<div>
												${offstarVO.name } <span class="re-date">${offstarVO.reg_date }</span>
											</div>
											<div class="star-ratings">
												<div class="star-ratings-fill space-x-2 text-lg"
													style="width:${offstarVO.rating_percent}%">
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
								</div>
								<c:set var="offstarVO_text" value="${offstarVO.text }" />
								<c:choose>
									<c:when test="${fn:length(offstarVO_text)<=100}">
						        ${offstarVO.text }
						    </c:when>
									<c:otherwise>
						    	${fn:substring(offstarVO_text, 0,100)} ......<small>(더보기)</small>
									</c:otherwise>
								</c:choose>
							</div>
							<div class="writer-reply">
								<img
									src="${pageContext.request.contextPath}/resources/images/speech_bubble.png">
								<c:if test="${!empty offstarVO.writer_num}">
									<span> 1 </span>
								</c:if>
								<c:if test="${empty offstarVO.writer_num}">
									<span> 0 </span>
								</c:if>
							</div>
					</div>
				</c:forEach>
			</div>
			<div class="align-center">
				<input type="button" class="btn replylist-btn" value="후기 목록 더보기" onclick="location.href='offclassReviewList.do?off_num=${offclass.off_num}'">
			</div>
		</div>
		<div class="sidebar">
			<div class="sidbar_content">
				<input type="hidden" value="${offclass.off_num }" id="off_num">
				<div class="display-flex">
				<div class="item-category">
				<c:if test="${offclass.category_num ==1}">드로잉</c:if>
				<c:if test="${offclass.category_num ==2}">플라워</c:if>
				<c:if test="${offclass.category_num ==3}">공예</c:if>
				<c:if test="${offclass.category_num ==4}">요리</c:if>
				<c:if test="${offclass.category_num ==5}">베이킹</c:if>
				</div>
				<div class="like">
					<c:choose>
						<c:when test="${likecheck eq '0' or empty likecheck}">
							<button class="btn" id="like_btn">
								<img src="${pageContext.request.contextPath }/resources/images/heart_black_nofill.png" width="20">
								<span id="like_count"></span>
							</button>
						</c:when>
						<c:otherwise>
							<button class="btn" id="like_btn">
								<img src="${pageContext.request.contextPath }/resources/images/heart_fill.png"  width="20">
								<span id="like_count"></span>
							</button>
						</c:otherwise>
					</c:choose>
				</div>
				</div>
				<h3>${offclass.off_name }</h3>
				<h4><fmt:formatNumber type="number" maxFractionDigits="3" value="${offclass.off_price }" />원</h4>
				<hr size="1">
				<div class="schedule_calendar">
					<h4>클래스 일정</h4>
					<div class="swiper-wrapper"
						style="transition-duration: 0ms; transform: translate3d(0px, 0px, 0px);">
						<c:forEach var="offTimetableVO" items="${list }">
							<div id="time_date" data-timedate="${offTimetableVO.time_date }">
								<a class="schedules">
									<div class="time_date">${offTimetableVO.string_date }</div>
									<div class="time_date">${offTimetableVO.day }</div>
								</a>
							</div>
						</c:forEach>
					</div>
					<hr size="1" noshade>
					<div id="timetable"></div>
				</div>
				<hr size="1" noshade>
				
				
				<!-- <input class="btn btn-outline-secondary shopping" type="button" value="바로 구매"> -->
				
		<div class="display-flex">
			<form id="detail-shopping" action="${pageContext.request.contextPath}/cart/cartInsert.do" method="post">
				<input type="hidden" name="off_quantity" value="${offclass.off_limit}"> <!-- 현재  오프클래스 정원 남은 수량 -->
				<input type="hidden" name="order_quantity" value="1"> <!-- 주문시 기본 1 -->
				<input type="hidden" name="class_kind" value="off">
				<input type="hidden" name="class_num" value="${offclass.off_num}">
				<input class="btn-black width_100" type="submit" value="장바구니">
				<!--  
				<c:if test="${offTimetableVO.off_limit != offTimetableVO.off_personcount}">
					<input type="submit" value="장바구니">
				</c:if>
				<c:if test="${offTimetableVO.off_limit == offTimetableVO.off_personcount}">
					<span style="color:red">품절</span>
				</c:if>
				-->
			</form>
			<span class="list-space"></span>
			<!-- 결제버튼 -->
			<input class="btn-orange width_100" onclick="iamport()" type="button" value="바로 구매">
		</div>				
			<!-- 안보이게 하기 -->
				<form action="paymentoff.do" id="paymentData_btn" method="post" id="paymentData"> 
					<input type="hidden" name="off_num" value="${offclass.off_num}"/>
					<input type="hidden" name="on_payment" value="2"/>
					<input type="hidden" name="on_status" value="1"/>  
				   	<input type="submit" value="임시 구매 버튼">
				</form>
			<!-- 안보이게 하기 -->
				
				
			</div>
		</div>
	</div>
</div>
<script>
	let offaddress1 = document.getElementById('offaddress1').innerText;
	let offaddress2 = document.getElementById('offaddress2').innerText;
	let offzipcode = document.getElementById('offzipcode').innerText;
	
	var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
	    mapOption = {
	        center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
	        level: 3 // 지도의 확대 레벨
	    };  
	
	// 지도를 생성합니다    
	var map = new kakao.maps.Map(mapContainer, mapOption); 
	map.setDraggable(false);
	map.setZoomable(false); 
	
	// 주소-좌표 변환 객체를 생성합니다
	var geocoder = new kakao.maps.services.Geocoder();
	
	// 주소로 좌표를 검색합니다
	geocoder.addressSearch(offaddress1, function(result, status) {
	
	    // 정상적으로 검색이 완료됐으면 
	     if (status === kakao.maps.services.Status.OK) {
	
	        var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
	
	        // 결과값으로 받은 위치를 마커로 표시합니다
	        var marker = new kakao.maps.Marker({
	            map: map,
	            position: coords
	        });
	
	        // 인포윈도우로 장소에 대한 설명을 표시합니다
	        var infowindow = new kakao.maps.InfoWindow({
	            content: '<div class="map-title">'+offaddress1+' '+ offaddress2+'</div>'
	        });
	        infowindow.open(map, marker);
	
	        // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
	        map.setCenter(coords);
	    } 
});    
	
/* 결제 */
 function iamport(){
		
		const email = "${ouser.email}";
		const on_price = "${offclass.off_price}";
		const id = "${ouser.id}";
		const phone = "${ouser.phone}";
		const address1 = "${ouser.address1}";
		const address2 = "${ouser.address2}";
		const on_name = "${offclass.off_name}";
		
		const user_num = "${session_user_num}";
		
		console.log(user_num);
		
		if(user_num == ""){
			alert('로그인 후 구매 가능합니다.')
		}else if(user_num != null){
			//가맹점 식별코드
			IMP.init('imp62760166');
			IMP.request_pay({
			    pg : 'kcp',
			    pay_method : 'card',
			    merchant_uid : 'merchant_' + new Date().getTime(),
			    name : on_name , //결제창에서 보여질 이름
			    amount : on_price, //실제 결제되는 가격, 최소금액 500원 이상 , 500이하시 결제 오류
			    buyer_email : email,
			    buyer_name : id,
			    buyer_tel : phone,
			    buyer_addr : address1+address2
			    /* buyer_postcode : '123-456' */
			}, function(rsp) {
				console.log(rsp);
			    if ( rsp.success ) {
			    	var msg = '결제가 완료되었습니다.';
			        msg += '고유ID : ' + rsp.imp_uid;
			        msg += '상점 거래ID : ' + rsp.merchant_uid;
			        msg += '결제 금액 : ' + rsp.paid_amount;
			        msg += '카드 승인번호 : ' + rsp.apply_num;
			        document.getElementById('paymentData_btn').submit();
			    } else {
			    	 var msg = '결제에 실패하였습니다.';
			         msg += '에러내용 : ' + rsp.error_msg;
			    }
			    alert(msg);
			});
		
	}
	}
</script>
<!-- 중앙 컨텐츠 끝 -->