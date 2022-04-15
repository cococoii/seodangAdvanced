<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<link rel="stylesheet"
	href="${pageContext.request.contextPath }/resources/css/style_dw.css"
	type="text/css" />
	
	
<!--//////////////// ////////////////////////////// 주문내역 상세 ////// //////////////////////////////////////// -->
<h1 class="main_title">주문내역 상세</h1>
<table>
	<tr>
		<th style="width: 10%">상품 번호</th>
		<th style="width: 15%">상품 분류</th>
		<th style="width: 25%">상품명</th>
		<th style="width: 10%">상품가격</th>
		<th style="width: 5%">수량</th>
		<th style="width: 15%">합계</th>
		<th style="width: 20%">상태</th>
	</tr>

	<c:set var="total" value="0" />
	<c:set var="check_kit" value="0" />
	<c:set var="kit_delivery" value="0" />


<!--////////////////  forEach 시작  //////////////////// -->
	<c:forEach var="list" items="${detailList}">
		<c:if test="${list.class_kind eq 'kit'}">
			<c:set var="check_kit" value="1" />
		</c:if>
		<tr>
			<!-- 상품번호 -->		
			<td>${list.class_num}</td>

			<!-- 상품분류 -->
			<td>
				<c:if test="${list.class_kind eq 'kit'}">키트</c:if> 
				<c:if test="${list.class_kind eq 'on'}">온라인 클래스</c:if> 
				<c:if test="${list.class_kind eq 'off'}">오프라인 클래스 </c:if>
			</td>
					
			<!-- 상품명 -->
			<td>
				<c:if test="${list.class_kind eq 'kit'}">
					<a href="${pageContext.request.contextPath}/kit/kitDetail.do?kit_num=${list.class_num}"> ${list.item_name}</a>
				</c:if> 
				
				<c:if test="${list.class_kind eq 'off'}">
					<a href="${pageContext.request.contextPath}/offclass/offclassDetail.do?off_num=${list.class_num}"> ${list.item_name} 
					<c:set var="class_num" value="${list.class_num }" /> 
						<c:forEach var="time" items="${time }">
							<c:if test="${time.off_num == class_num}">
	            				[ ${time.time_date} "${time.time_start}~${time.time_end}" }
	            			</c:if>
						</c:forEach>
					</a>
				</c:if> 
				
				<c:if test="${list.class_kind eq 'on'}">
					<a href="${pageContext.request.contextPath}/onclass/onclassDetail.do?on_num=${list.class_num}"> ${list.item_name}</a>
				</c:if>
			</td>
			
			<!-- 상품가격 -->
			<td><fmt:formatNumber value="${list.item_price}" pattern="#,###" /></td>
			
			<!-- 수량 -->
			<td>${list.order_quantity}</td>
			
			<!-- 합계 -->
			<td><fmt:formatNumber value="${list.item_total}" pattern="#,###" /></td>
			
			<!-- 상태 : 배송 및 수강 상태 (1:배송대기 /2:배송준비중 /3:배송중 /4:배송완료 /5:수강신청/6:수강중/7:수강완료/8:주문취소)-->
			<td>
				<span style="color: red"> 
					<c:if test="${list.status == 1}">
						<c:set var="kit_delivery" value="1" />배송대기
					</c:if> 
					<c:if test="${list.status == 2}">배송준비중</c:if> 
					<c:if test="${list.status == 3}">배송중</c:if>
					<c:if test="${list.status == 4}">배송완료</c:if>
				</span> 
					<c:if test="${list.status == 5}">수강신청 완료</c:if> 
					<c:if test="${list.status == 6}">수강중</c:if> 
					<c:if test="${list.status == 7}">수강완료</c:if> 
					<c:if test="${list.status == 8}">주문취소</c:if>
			</td>
		</tr>
		<c:set var="total" value="${total + list.item_total}"></c:set>
	</c:forEach>
	</table>
<!--////////////////  forEach 끝  //////////////////// -->



<!--//////////////// ////////////////////////////// 배송상태 변경 ////// //////////////////////////////////////// -->
		<c:if test="${user_auth > '3'&& check_kit=='1'}">	<!-- 배송대기 상태일 때  + 관리자일때 (일단 관리자만 변경가능 / 추후 강사가 변경가능하도록 바꾸기)-->
			<table>
			<tr>
				<th colspan="3">kit 배송상태 변경</th>
			</tr>
				<tr style="font-weight: bold">
					<td style="width: 10%">상품 번호</td>
					<td style="width: 40%">상품명</td>
					<td style="width: 50%">상태</td>
				</tr>
			<c:forEach var="list" items="${detailList}">
			<c:if test="${list.class_kind eq 'kit'}">				
				<tr>
					<td>${list.class_num}</td>
					<td>${list.item_name}</td>
					<td>
						<form action="ModifyDelivery.do" class="order_form">
						
							<input type="hidden" name="detail_num" value="${list.detail_num}"/>
							<input type="hidden" name="order_num" value="${list.order_num}"/>
							
							<input type="hidden" name="class_num" value="${list.class_num}"/>
							<input type="hidden" name="class_kind" value="${list.class_kind}"/>
							<input type="hidden" name="item_name" value="${list.item_name}"/>
							<input type="hidden" name="item_price" value="${list.item_price}"/>
							<input type="hidden" name="item_total" value="${list.item_total}"/>
							<input type="hidden" name="order_quantity" value="${list.order_quantity}"/>

							<input type="radio" name="status" value="1"
								<c:if test ="${list.status == '1'}">checked</c:if>/>배송대기
							<input type="radio" name="status" value="2"
								<c:if test ="${list.status == '2'}">checked</c:if>/>배송준비중
							<input type="radio" name="status" value="3"
								<c:if test ="${list.status == '3'}">checked</c:if>/>배송중
							<input type="radio" name="status" value="4"
								<c:if test ="${list.status == '4'}">checked</c:if>/>배송완료
							
							<input type="submit" value="변경" id="listbtn">
						</form>
					</td>
				</tr>
			</c:if>
			</c:forEach>
			</table>
		</c:if>

<!--//////////////// ////////////////////////////// 구매 및 배송정보(키트상품이 존재할 때) ////// //////////////////////////////////////// -->

<c:if test="${check_kit =='1'}">
    <table>
	<tr>
		<th>구매 및 배송 정보</th>
	</tr>
	<tr>
		<td>
			<form:form modelAttribute="orderVO" action="modifyOrder.do" class="order_form3">
				<form:errors element="div" cssClass="error-color" />
				<ul>
					<form:hidden path="order_num" />
					<form:hidden path="item_name" />
					<form:hidden path="order_total" value="${total }" />
					<form:hidden path="user_num" />
					<form:hidden path="reg_date" />

					<li style="color: blue; font-weight: bold; font-size:13pt">주문번호 : ${orderVO.order_num }</li>
					<li style="font-size:13pt">
						<span style="color: red">'배송대기'</span> 상태일 경우에만 배송지 변경이 가능합니다.
					</li>
					<br>
					
					<li>
						<label>결제 수단</label> 
						<form:radiobutton path="payment" value="1" checked="${orderVO.payment == '1' ? 'checked' : ''}" onclick="return(false);" />통장입금
					    <form:radiobutton path="payment" value="2" checked="${orderVO.payment == '2' ? 'checked' : ''}" onclick="return(false);" />카드결제 
					    <form:errors path="payment" cssClass="error-color" />
					</li>
					<li>
						<form:label path="receive_name">구매자</form:label> 
							<c:if test="${kit_delivery == '1'}">
								<form:input path="receive_name" />
							</c:if> 
							<c:if test="${kit_delivery != '1'}">
								<form:input path="receive_name" readonly="true" />
							</c:if> 
						<form:errors path="receive_name" cssClass="error-color" />
					</li>
					<li>
						<form:label path="receive_post">우편번호</form:label> 
							<c:if test="${kit_delivery == '1'}">
								<form:input path="receive_post" />
								<input type="button" onclick="sample2_execDaumPostcode()" value="우편번호 찾기" class="listbtn2">
							</c:if> 
							<c:if test="${kit_delivery != '1'}">
							<form:input path="receive_post" readonly="true" />
							</c:if> 
							<form:errors path="receive_post" cssClass="error-color" />
					</li>
					<li>
						<form:label path="receive_address1">주소</form:label> 
							<c:if test="${kit_delivery == '1'}">
								<form:input path="receive_address1" />
							</c:if> 
							<c:if test="${kit_delivery != '1'}">
								<form:input path="receive_address1" readonly="true" />
							</c:if> 
						<form:errors path="receive_address1" cssClass="error-color" /></li>
					<li>
						<form:label path="receive_address2">상세주소</form:label> 
							<c:if test="${kit_delivery == '1'}">
								<form:input path="receive_address2" />
							</c:if> 
							<c:if test="${kit_delivery != '1'}">
								<form:input path="receive_address2" readonly="true" />
							</c:if> 
						<form:errors path="receive_address2" cssClass="error-color" />
					</li>
					<li>
						<form:label path="receive_phone">전화번호</form:label> 
							<c:if test="${kit_delivery == '1'}">
								<form:input path="receive_phone" />
							</c:if> 
							<c:if test="${kit_delivery != '1'}">
								<form:input path="receive_phone" readonly="true" />
							</c:if> 
						<form:errors path="receive_phone" cssClass="error-color" />
					</li>
					<li>
						<form:label path="notice">남기실 말씀</form:label> 
							<c:if test="${kit_delivery == '1'}">
								<form:input path="notice" />
							</c:if> 
							<c:if test="${kit_delivery != '1'}">
								<form:input path="notice" readonly="true" />
							</c:if> 
						<form:errors path="notice" cssClass="error-color" />
					</li>
				</ul>
				
				<div class="align-center">
					<c:if test="${kit_delivery == '1'}">
						<form:button class="listbtn3">수정</form:button>
					</c:if>
				</div>
			</form:form>
		</td>
	</tr>
	</table>
</c:if>

<!--//////////////// ////////////////////////////// 구매 및 배송정보(키트상품이 존재하지 않을 때) ////// //////////////////////////////////////// -->
<c:if test="${check_kit == '0'}">
		<tr>
			<td><form:form modelAttribute="orderVO" class="order_form2">
					<ul style="text-align:center">
						<li>
							<form:label path="payment">결제 수단</form:label> 
							<form:radiobutton
								path="payment" value="1"
								checked="${orderVO.payment == '1' ? 'checked' : ''}"
								onclick="return(false);" />통장입금 <form:radiobutton path="payment"
								value="2" checked="${orderVO.payment == '2' ? 'checked' : ''}"
								onclick="return(false);" />카드결제
						</li>
						<li>
							<form:label path="reg_date">주문 일시</form:label>
							${orderVO.reg_date }
						</li>
					</ul>

					<!-- ** 주문취소 버튼 만들기** -->
				</form:form></td>
		</tr>
</c:if>
</table>


<div class="align-center">
	<c:if test="${user_auth < 3}">
		<input type="button" value="주문내역" class="listbtn-white2"
			onclick="location.href='${pageContext.request.contextPath}/cart/orderList.do'">
		<input type="button" value="장바구니" class="listbtn-white2"
			onclick="location.href='${pageContext.request.contextPath }/cart/cartList.do'">
		<input type="button" value="홈으로" class="listbtn-white2"
			onclick="location.href='${pageContext.request.contextPath}/main/main.do'">
	</c:if>

	<c:if test="${user_auth > 3}">
		<!-- 회원, 강사, 관리자 의 메뉴가 달라야한다 / 일단 관리자만 -->
		<input type="button" value="회원 주문내역" class="listbtn-white2"
			onclick="location.href='${pageContext.request.contextPath}/cart/orderListByUser.do?user_num=${orderVO.user_num}'">
		<input type="button" value="회원목록" class="listbtn-white2"
			onclick="location.href='${pageContext.request.contextPath }/admin/adminMemberList.do'">
		<input type="button" value="홈으로" class="listbtn-white2"
			onclick="location.href='${pageContext.request.contextPath}/main/main.do'">
	</c:if>
</div>