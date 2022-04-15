<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script src="${pageContext.request.contextPath}/resources/js/jquery-3.6.0.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/style_dw.css" type="text/css"/>

<script type="text/javascript">
$(function(){

	//장바구니 상품 수량변경
	$('.cart-modify').on('click',function(){
	let input_quantity = $(this).parent().find('input[name="order_quantity"]');
	
	if(input_quantity.val()==''){
		alert('변경할 kit상품의 수량을 입력하세요.')
		input_quantity.focus();
		return;
	}
	if(input_quantity.val()<1){
		alert('최소 수량은 1입니다.')
		input_quantity.val(input_quantity.attr('value'));
		return;
	}
	$.ajax({
		url:'modifyCart.do',
		type:'post',
		data:{cart_num:$(this).attr('data-cartnum'),
				order_quantity:input_quantity.val(), kit_quantity:$(this).attr('data-kitquantity')},
		dataType:'json',
		cache:false,
		timeout:30000,
		success:function(param){
			if(param.result=='logout'){
				alert('로그인 후 사용하세요!');
			}else if(param.result =='noQuantity'){
				alert('재고수량이 부족합니다.')
				location.href='cartList.do';
			}else if(param.result == 'success'){
				alert('수량이 수정되었습니다.');
				location.href='cartList.do';
			}else{
				alert('수정시 오류발생!');
			}
		},
		error:function(){
			alert('네트워크 오류 발생!');
		}
	});
	});
	
	//장바구니 상품 삭제
	$('.cart-del').on('click',function(){
		$.ajax({
			url:'deleteCart.do',
			data:{cart_num:$(this).attr('data-cartnum')},
			dataType:'json',
			cache:false,
			timeout:30000,
			success:function(param){
				if(param.result == 'logout'){
					alert('로그인 후 사용하세요.');
				}else if(param.result == 'success'){
					alert('삭제되었습니다.');
					location.href='cartList.do';	//삭제 후 자기자신 호출
					
				}else{
					alert('삭제시 오류발생');
				}
			},
			error:function(){
				alert('네트워크 오류발생');
				}
			});
		});	
	
	$('#cart_order').submit(function(){
		let check = true;
		$('.quantity-msg').each(function(index,item){
			if($(item).text().indexOf('정원 마감')>0){
				alert('정원 마감으로 구매하실 수 없습니다.');
				check = false;
				return;
			}
			if($(item).text().indexOf('재고 부족')>0){
				alert('재고 부족으로 구매하실 수 없습니다.');
				check = false;
				return;
			}
		});
		if(!check){
			return false;
		}
	});
});
</script>


<div class="cartList_div">

   <c:if test="${empty list}" >
   	<h1 class="main_title">장바구니</h1>
   		<div id="order_form4">
   			상품이 존재하지 않습니다.
   		</div>
   </c:if>
 
<c:if test="${!empty list}" >  
<form id="cart_order" method="post" action="${pageContext.request.contextPath}/cart/orderForm.do" class="order">

<h1 class="main_title">장바구니</h1>
<table class="cartList_table">
   <tr class="cartList_tr">
      
      <th style="width:80px"> 분류</th>
      <th style="width:400px"> 상품명</th>
      <th > 상품 가격</th>
      <th > 상품 수량</th>
      <th style="width:120px"> 합계 </th>
      <th style="width:80px"> 삭제 </th>
   </tr>
   
   <c:set var = "total" value = "0" />
   <c:forEach var="cart" items="${list}">
         <tr>
            <c:if test="${cart.class_kind eq 'kit'}">
            	<td>키트</td>
	            <td><a href="${pageContext.request.contextPath}/kit/kitDetail.do?kit_num=${cart.kitVO.kit_num}">
	            	${cart.kitVO.kit_name}</a></td>
				<td><fmt:formatNumber value="${cart.kitVO.kit_price }" pattern="#,###" />
	            <td class="quantity-msg">
	            	<c:if test = "${cart.kitVO.kit_quantity < cart.order_quantity}"> [ 재고 부족 ]
	            	</c:if>
	            	<c:if test = "${cart.kitVO.kit_quantity >= cart.order_quantity}">
	            		
	            		<input type="number" name="order_quantity" min="1" max="999" 
	            			value="${cart.order_quantity}" class="quantity-width">
	            		<br>
	            		<input type="button" value="변경" class="cart-modify" id="listbtn-white"
	            			data-cartnum="${cart.cart_num}" data-kitquantity="${cart.kitVO.kit_quantity}">
	            	</c:if>
	            </td>
	            <td><fmt:formatNumber value="${cart.sub_total}" pattern="#,###" />
            </c:if>
            
            <c:if test="${cart.class_kind eq 'off'}">
            	<td>오프라인 클래스</td>
	            <td><a href="${pageContext.request.contextPath}/offclass/offclassDetail.do?off_num=${cart.offclassVO.off_num}">
	            	${cart.offclassVO.off_name} [ ${time} ]</a></td>
	            <td><fmt:formatNumber value="${cart.offclassVO.off_price}" pattern="#,###" />
	            <td class="quantity-msg">
	            	<c:if test="${cart.offclassVO.off_limit < cart.order_quantity}"> [ 정원 마감 ]</c:if>
	            	<c:if test="${cart.offclassVO.off_limit >= cart.order_quantity}">${cart.order_quantity}</c:if>
	            	
	            </td>
	            <td><fmt:formatNumber value="${cart.sub_total}" pattern="#,###" />          
            </c:if>
            
            <c:if test="${cart.class_kind eq 'on'}">
            	<td>온라인 클래스</td>
 	            <td><a href="${pageContext.request.contextPath}/onclass/onclassDetail.do?on_num=${cart.onclassVO.on_num}">
 	            	${cart.onclassVO.on_name}</a></td>
	            <td><fmt:formatNumber value="${cart.onclassVO.on_price}" pattern="#,###" />
	            <td>${cart.order_quantity} </td>
	            <td><fmt:formatNumber value="${cart.sub_total}" pattern="#,###" />            
            </c:if>
            
         <c:set var="total" value="${total + cart.sub_total}"></c:set>
            
         <td>
               <input type="button" value="삭제" class="cart-del" data-cartnum="${cart.cart_num}" id="listbtn-white">
         </td>           
            
       </tr>
   </c:forEach>
	</table>
	
   	<table style="padding:30px 0">
   	 <!--
   	  <tr >
	      <td style="color:red;">
	         <fmt:formatNumber value="${total }" pattern="#,###" /> 원
	      </td>
	      <td style="color:red;">
	      	<input type="submit" value="구매하기" class="cart-btn" id="btn-orange">
	      </td>
      </tr>
       --> 
      <tr >
      	<td class="gradation">
	     <span id="total">총합 <fmt:formatNumber value="${total }" pattern="#,###" /> 원 &nbsp;&nbsp;&nbsp;</span> 
	     <input type="submit" value="구매하기" class="cart-btn" id="clear-listbtn">
	    </td>
      </tr> 
       
    </table>   
</form>
</c:if>
</div>

