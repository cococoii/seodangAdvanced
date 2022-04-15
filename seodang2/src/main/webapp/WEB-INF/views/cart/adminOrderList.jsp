<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/style_dw.css" type="text/css"/>

<h1 class="main_title">주문관리</h1>  

<c:if test="${empty list}" >
 	<div id="order_form4">
 		주문내역이 존재하지 않습니다.
 	</div>
</c:if>
<c:if test="${!empty list}" >
<form action="orderDetail.do" id="orderList_form" method="get" class="order">
	<table>
		<tr>
			<th style="width:10%">주문번호</th>
			<th style="width:35%">주문상품</th>
			<th style="width:10%">총 주문금액</th>
			<th style="width:10%">회원번호</th>
			<th style="width:10%">주문자 성명</th>
			<th style="width:15%">결제수단</th>
			<th style="width:20%">주문날짜</th>
			
		</tr>
		<c:forEach var="order" items="${list }">
			<tr>
				<td>${order.order_num}</td>
				<td><a href="orderDetail.do?order_num=${order.order_num}">${order.item_name}</a></td>
				<td><fmt:formatNumber value="${order.order_total}" pattern="#,###" /></td>   
				<td>${order.user_num }</td>
				<td>${order.receive_name}</td>
				<td>
					<c:if test = "${order.payment == 1}"> 통장입금 </c:if>
					<c:if test = "${order.payment == 2}"> 카드결제 </c:if>
				</td>
				<td>${order.reg_date}</td>
			</tr>
		</c:forEach>
	</table>
</form>
</c:if>
