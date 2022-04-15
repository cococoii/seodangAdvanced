<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/offclass.css">
<!-- 중앙 컨텐츠 시작 -->
<script src="${pageContext.request.contextPath}/resources/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	$(function(){
		$("input[name=rating]").change(function() {
			let radioValue = $(this).val();
			if (radioValue == "1") {
				$("#star_review").text('별로였어요')
			}else if (radioValue == "2") {
				$("#star_review").text('조금 아쉬워요')
			}else if (radioValue == "3") {
				$("#star_review").text('보통이에요')
			}else if (radioValue == "4") {
				$("#star_review").text('만족했어요')
			}else if (radioValue == "5") {
				$("#star_review").text('너무 좋았습니다')
			}
		});
		$('#review_text').keyup(function(){
			let inputLength = $(this).val().length;
			
			if(inputLength>300){//300자를 넘어선 경우
				$(this).val($(this).val().substring(0,300));
			}else{//300자 이하인 경우
				let remain = 300 - inputLength;
				remain += '/300';
				if($(this).attr('id')=='re_content'){
					//등록폼 글자수
					$('.text-count').text(remain);
				}else{
					//수정폼 글자수
					$('.text-count').text(remain);
				}
			}
		});
		$("#back_img").click(function(){
			window.history.back();
		});
	});
</script>
<div class="review">
	<div class="back-title">
		<span class="backspace">
		<img id="back_img" src="${pageContext.request.contextPath}/resources/images/back_arrow.png">
		</span>
		<div class="title">
			<h3>후기 작성</h3>
		</div>
	</div>
	<div class="reviewForm">
	<div class="align-center">
		<img class="review_img" src="imageView.do?on_num=${onstarVO.on_num}">
		<div>${onstarVO.on_name }</div>
	</div>
	<form:form action="onclassReview.do" modelAttribute="onstarVO" id="review_form">
	<form:errors element="div" cssClass="error-color"/>
	<form:hidden path="on_num"/>
	<ul>
		<li class="starpoint_box">
			<div class="starpoint">
			<input type="radio" id="1-stars" name="rating" value="5"/>
			<label for="1-stars" class="star pr-4">★</label>
			<input type="radio" id="2-stars" name="rating" value="4"/>
			<label for="2-stars" class="star">★</label>
			<input type="radio" id="3-stars" name="rating" value="3"/>
			<label for="3-stars" class="star">★</label>
			<input type="radio" id="4-stars" name="rating" value="2"/>
			<label for="4-stars" class="star">★</label>
			<input type="radio" id="5-star" name="rating" value="1" />
			<label for="5-star" class="star">★</label>
			</div>
			<div id="star_review"></div>
			<form:errors path="rating" cssClass="error-color"/>
		</li>
		<li>
			<div class="textarea-label">
			<form:label path="text">후기 작성</form:label><br>
			</div>
			<form:textarea id="review_text" path="text" cols="30" rows="5"/>
			<div class="text-count align-right">300/300</div>
			<form:errors path="text" cssClass="error-color"/>
		</li>
		<li>
			<form:button class="btn detail-replybtn">후기 작성완료</form:button>
		</li>
	</ul>
	</form:form>
	</div>
</div>