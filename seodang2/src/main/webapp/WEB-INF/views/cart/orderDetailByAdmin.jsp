<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>       

${list }

<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/style_dw.css" type="text/css"/>

<c:if test="${empty detailList}" >
	<div class="result-display">
   		주문내역이 존재하지 않습니다.
	</div>
</c:if>

<c:if test="${!empty detailList}" >
<h1>주문 내역 상세</h1>
	<table>
		<tr>
			<th style="width:15%">상품 분류</th>
			<th style="width:30%">상품명</th>
			<th style="width:15%">상품가격</th>
			<th style="width:10%">수량</th>
			<th style="width:15%">합계</th>			
			<th style="width:15%">상태</th>
		</tr>
		
	   <c:set var = "total" value = "0" />
	   <c:forEach var="list" items="${detailList}">		
		<tr>	
			<td>
				<c:if test="${list.class_kind eq 'kit'}">키트</c:if>
				<c:if test="${list.class_kind eq 'on'}">온라인 클래스</c:if>
				<c:if test="${list.class_kind eq 'off'}">오프라인 클래스 </c:if>
			</td>       	
			<td>
				<c:if test="${list.class_kind eq 'kit'}">
				<a href="${pageContext.request.contextPath}/kit/kitDetail.do?kit_num=${list.class_num}">
	            	${list.item_name}</a>
				</c:if>	
					
			
				<c:if test="${list.class_kind eq 'off'}">
				<a href="${pageContext.request.contextPath}/offclass/offclassDetail.do?off_num=${list.class_num}">
	            	${list.item_name} 
	            	
	            	<c:set var="class_num" value ="${list.class_num }"/>
	            	<c:forEach var="time" items="${time }">
	            		<c:if test ="${time.off_num == class_num}">
	            			[ ${time.time_date} "${time.time_start}~${time.time_end}" }
	            		</c:if>
	            	</c:forEach>
	            	
	            	</a>
				</c:if>
				
				<c:if test="${list.class_kind eq 'on'}">
				<a href="${pageContext.request.contextPath}/onclass/onclassDetail.do?on_num=${list.class_num}">
	            	${list.item_name}</a>
				</c:if>
			</td> 	
	            	
			<td><fmt:formatNumber value="${list.item_price}" pattern="#,###" /></td>   
			<td>${list.order_quantity}</td>
			<td><fmt:formatNumber value="${list.item_total}" pattern="#,###" /></td> 
			<td>
				<!--배송 및 수강 상태 (1:배송대기 /2:배송준비중 /3:배송중 /4:배송완료 /5:수강신청/6:수강중/7:수강완료/8:주문취소)-->
					<c:if test="${list.status == 1}">배송대기</c:if>
					<c:if test="${list.status == 2}">배송준비중</c:if>
					<c:if test="${list.status == 3}">배송중</c:if>
					<c:if test="${list.status == 4}">배송완료</c:if>
					<c:if test="${list.status == 5}">수강신청 완료</c:if>
					<c:if test="${list.status == 6}">수강중</c:if>
					<c:if test="${list.status == 7}">수강완료</c:if>
					<c:if test="${list.status == 8}">주문취소</c:if>
			</td>
		</tr>
		<c:set var="total" value="${total + list.item_total}"></c:set> 
		</c:forEach>
	</table>
	<table>
		<tr>
			<th colspan="6">구매 및 배송 정보</th>
		</tr>
		<tr>
				<td>
				<form:form modelAttribute="orderVO" action="modifyOrder.do">
					<form:errors element="div" cssClass="error-color"/>
					<ul>
						<form:hidden path="order_num"/>
						<form:hidden path="item_name"/>
						<form:hidden path="order_total" value="${total }"/>
						<form:hidden path="user_num"/>
						<form:hidden path="reg_date" />
								
						<li>
							<label>결제 수단</label>
							<form:radiobutton path="payment" value="1" checked="${orderVO.payment == '1' ? 'checked' : ''}" onclick="return(false);"/>통장입금
							<form:radiobutton path="payment" value="2" checked="${orderVO.payment == '2' ? 'checked' : ''}" onclick="return(false);"/>카드결제
							<form:errors path="payment" cssClass="error-color"/>
						</li>
						<li>
							<form:label path="receive_name">구매자</form:label>
							<form:input path="receive_name"/>
							<form:errors path="receive_name" cssClass="error-color"/>
						</li>
						<li>
							<form:label path="receive_post">우편번호</form:label>	
							<form:input path="receive_post"/>	
							<input type="button" onclick="sample2_execDaumPostcode()" value="우편번호 찾기">	
							<form:errors path="receive_post" cssClass="error-color"/>
						</li>
						<li>
							<form:label path="receive_address1">주소</form:label>
							<form:input path="receive_address1"/>
							<form:errors path="receive_address1" cssClass="error-color"/>
						</li>
						<li>
							<form:label path="receive_address2">상세주소</form:label>
							<form:input path="receive_address2"/>
							<form:errors path="receive_address2" cssClass="error-color"/>
						</li>		
						<li>
							<form:label path="receive_phone">전화번호</form:label>
							<form:input path="receive_phone"/>
							<form:errors path="receive_phone" cssClass="error-color"/>
						</li>						
						<li>
							<form:label path="notice">남기실 말씀</form:label>
							<form:input path="notice"/>
							<form:errors path="notice" cssClass="error-color"/>
						</li>				
					</ul>
						<div class="align-center">
							<form:button>수정</form:button>
						</div>
				</form:form>	
				</td>
				</tr>		
		</table>
</c:if>			
	<div class="align-center">
		<input type="button" value="주문관리" onclick="location.href='${pageContext.request.contextPath}/cart/orderList.do'">
		<input type="button" value="홈으로" onclick="location.href='${pageContext.request.contextPath}/main/main.do'">
	</div>