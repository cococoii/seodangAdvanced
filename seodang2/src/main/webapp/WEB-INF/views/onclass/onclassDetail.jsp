<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!-- 외부이미지 불러오기 -->
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%> 

<script src="${pageContext.request.contextPath}/resources/js/jquery-3.6.0.min.js"></script>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.1/font/bootstrap-icons.css">
<!-- 아임포트 임포트 -->
<script type="text/javascript" src="https://cdn.iamport.kr/js/iamport.payment-1.1.5.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/onclass2.css">
<%-- <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/offclass.css"> --%>


<script type="text/javascript">
$(function(){
	var status; //noFav or yesFav
	function selectData(on_num){ //77라인 초기값 세팅
	   $.ajax({
	      type:'post',
	      data:{on_num:on_num}, //초기값 세팅에서 매개변수로 받아서 el 안씀
	      url:'getFav.do', //LikecountAction
	      dataType:'json',
	      cache:false,
	      timeout:30000,
	      success:function(data){
	         if(data.result=='success'){
	            displayFav(data);
	         }else{
	            alert('좋아요 읽기 오류');
	         }
	      },
	      error:function(){
	         alert('네트워크 오류');
	      }
	   });
	}
	$('#output_fav').click(function(){ //좋아요를 클릭했을때 실행되는 ajax
		$.ajax({
			url:'like.do',
			type:'post',
			data:{on_num:${onclass.on_num}},
			dataType:'json',
			cache:false,
			timeout:30000,
			success:function(data){
					if(data.result=='logout'){
		               alert('로그인 후 누르세요');
		            }else if(data.result=='success'){ //추천하트 표시
		            	displayFav(data);
		            }
		            else{
		               alert('등록시 오류 발생!');
		            }
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
		});
	});
	//좋아요 표시
	   function displayFav(data){
	      status = data.status;
	      var count = data.count;
	      var output;
	      if(status=='noFav'){
	         output = '../resources/image/heart1.png';
	      }else{
	         output = '../resources/image/heart2.png';
	      }         
	      //문서 객체에 추가
	      $('#output_fav').attr('src',output); //id가 output_fav인 태그 src에 output 저장
	      $('#output_fcount').text(count); //id가 output_fcount인 태그 text에 count(좋아요 총 개수)저장
	   }
	
	   selectData(${onclass.on_num}); //초기값 세팅
});

//결제 코드
function iamport(){
	
	const email = "${ouser.email}";
	const on_price = "${onclass.on_price}";
	const id = "${ouser.id}";
	const phone = "${ouser.phone}";
	const address1 = "${ouser.address1}";
	const address2 = "${ouser.address2}";
	const on_name = "${onclass.on_name}";
	
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
/////////////////////////////////////////////////////////////////////

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
		<h3>온라인 클래스 상세</h3>
		<div class="modify-delete-btn">
			<c:if test="${onclass.user_num == session_user_num }">
				<input class="btn btn-outline-secondary" type="button" value="수정"
					onclick="location.href='onclassModify.do?on_num=${onclass.on_num}'">
			</c:if>
			<c:if
				test="${onclass.user_num == session_user_num || session_user_auth==4}">
				<input class="btn btn-outline-secondary" type="button" value="삭제"
					onclick="location.href='onclassDelete.do?on_num=${onclass.on_num}'">
			</c:if>
		</div>
	</div>
	<div class="detail_content">
		<div></div>
		<div class="ck_content">
			<div>${onclass.on_content }</div>
			<hr class="on_detail" size="1">
			<div class="on_review align-right">
				<button  class="btn detail-replybtn" onclick="location.href='onclassReview.do?on_num=${onclass.on_num}'"><img src="${pageContext.request.contextPath}/resources/images/white-pencil.png" width="20px">&nbsp;후기작성하기</button>
			</div>
			<div class="align-center">
				<div class="average-review display-flex">
					<div class="avg_rating">${rating }</div>
					<div>총 ${review_count }개</div>
				</div>
			</div>
			<div class="reviewList">
				<c:forEach var="onstarVO" items="${list2 }">
					<div class="review width-50">
							<div>
								<div class="review_start">
									<div class="user_image">
										<c:if test="${!empty onstarVO.photo_name }">
											<img src="imageViewUser.do?user_num=${onstarVO.user_num}">
										</c:if>
										<c:if test="${empty onstarVO.photo_name }">
											<img
												src="${pageContext.request.contextPath}/resources/images/face.png">
										</c:if>
									</div>
									<div class="name-rating">
										<div>
											<div>
												${onstarVO.name } <span class="re-date">${onstarVO.reg_date }</span>
											</div>
											<div class="star-ratings">
												<div class="star-ratings-fill space-x-2 text-lg"
													style="width:${onstarVO.rating_percent}%">
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
								<c:set var="onstarVO_text" value="${onstarVO.text }" />
								<c:choose>
									<c:when test="${fn:length(onstarVO_text)<=100}">
						        ${onstarVO.text }
						    </c:when>
									<c:otherwise>
						    	${fn:substring(onstarVO_text, 0,100)} ......<small>(더보기)</small>
									</c:otherwise>
								</c:choose>
							</div>
							<div class="writer-reply">
								<img
									src="${pageContext.request.contextPath}/resources/images/speech_bubble.png">
								<c:if test="${!empty onstarVO.writer_num}">
									<span> 1 </span>
								</c:if>
								<c:if test="${empty onstarVO.writer_num}">
									<span> 0 </span>
								</c:if>
							</div>
					</div>
				</c:forEach>
			</div>
			<div class="align-center">
				<input type="button" class="btn replylist-btn" value="후기 목록 더보기" onclick="location.href='onclassReviewList.do?on_num=${onclass.on_num}'">
			</div>
		</div>
		<div class="sidebar">
			<div class="sidbar_content">
				<input type="hidden" value="${onclass.on_num }" id="on_num">
				<div class="display-flex">
				<div class="item-category">
				<c:if test="${onclass.category_num ==1}">드로잉</c:if>
				<c:if test="${onclass.category_num ==2}">플라워</c:if>
				<c:if test="${onclass.category_num ==3}">공예</c:if>
				<c:if test="${onclass.category_num ==4}">요리</c:if>
				<c:if test="${onclass.category_num ==5}">베이킹</c:if>
				</div>
				<div class="like">
					<!-- 찜 누르기 -->
					<img id="output_fav" src="../resources/image/heart1.png">
					<span id="output_fcount" class="margin_right_10"></span>
					<!-- 찜 누르기 -->
				</div>
				</div>
				<h3>${onclass.on_name }</h3>
				<h4><fmt:formatNumber type="number" maxFractionDigits="3" value="${onclass.on_price}"/>원</h4>
				<hr size="1">
				<div>
					<ul>
						<li>강의자 : ${onclass.name}</li>
						<li>수강기간 : <b>무제한</b></li>
						<li>수강중인 인원 : ${peopleCount}명</li>
					</ul>
				</div>
				<hr size="1" noshade>				
		<div class="display-flex">
	         <form action="${pageContext.request.contextPath}/cart/cartInsert.do" method="post">
	            <input type="hidden" name="order_quantity" value="1"> <!-- 주문시 기본 1 -->
	            <input type="hidden" name="class_kind" value="on">
	            <input type="hidden" name="class_num" value="${onclass.on_num}">
	            <input class="btn btn-outline-dark" type="submit" value="장바구니">
	         </form>
			<input class="btn btn-outline-dark" onclick="iamport()" type="button" value="바로 구매">
			</div>	
			<!-- 안보이게 하기 -->
				<form action="payment.do" id="paymentData_btn" method="post" id="paymentData"> 
					<input type="hidden" name="on_num" value="${onclass.on_num}"/>
					<input type="hidden" name="on_payment" value="2"/>
					<input type="hidden" name="on_status" value="1"/>  
				   	<input type="submit" value="임시 구매 버튼">
				</form>
			<!-- 안보이게 하기 -->		
			</div>
		</div>
	</div>
</div>