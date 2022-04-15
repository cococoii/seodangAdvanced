<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!-- 중앙컨텐츠 시작 -->
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Gowun+Dodum&display=swap" rel="stylesheet">
<script src="${pageContext.request.contextPath}/resources/js/jquery-3.6.0.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/style_dw.css" type="text/css"/>
<script type="text/javascript">
	$(function()){
		$('#search_form').submit(function(){
			if($('#keyword').val().trim()==''){
				alert('검색어를 입력하세요');
				$('keyword').val('').focus();
				return false;
			}
		});
	});
</script>

<div class="page-main">
	<h1 class="main_title">회원목록</h1> 
	<form action="adminMemberList.do" id="search_form" method="get">
		<ul class="search">
			<li>
				<select name="keyfield" id="keyfield">
					<option value="1"<c:if test="${param.keyfield==1 }">selected</c:if>>관리자</option>
					<option value="2"<c:if test="${param.keyfield==2 }">selected</c:if>>강사</option>
					<option value="3"<c:if test="${param.keyfield==3 }">selected</c:if>>회원</option>
					<option value="4"<c:if test="${param.keyfield==4 }">selected</c:if>>ID</option>
					<option value="5"<c:if test="${param.keyfield==5 }">selected</c:if>>이름</option>
				</select>
			</li>
			<li>
				<input type="search" name="keyword" id="keyword" value="${param.keyword}">
			</li>
			<li>
				<input type="submit" value="검색">
				<input type="button" value="목록" onclick="location.href='adminMemberList.do'" id="listbtn">
			</li>
		</ul>
	</form>
	
	<c:if test="${count ==0 }">
		 <div id="order_form4">회원이 존재하지 않습니다.</div>
	</c:if>	
	
	
	
	<c:if test="${count >0 }">
		<table>
			<tr style="font-family: 'Gowun Dodum', sans-serif;">
				<th>회원등급</th>
				<th colspan="2">ID</th>
				<th>이름</th>
				<th>회원번호</th>
				<th>전화번호</th>
				<th>Email</th>
				<th>가입일</th>
				<th>주문내역</th>
			</tr>
			<c:forEach var="member" items="${list }">
				<tr>
					<td>	<!--  0탈퇴회원,1.정지회원,2일반회원,3강사,4관리자-->
						<c:if test="${member.auth==0 }">탈퇴회원</c:if>
						<c:if test="${member.auth==1 }">정지회원</c:if>
						<c:if test="${member.auth==2 }">일반회원</c:if>
						<c:if test="${member.auth==3 }">강사</c:if>
						<c:if test="${member.auth==4 }">관리자</c:if>
					</td>	
									
					<td style="width:50px; padding:10px 0px">
					<div style="width:25; height: 25px; border-radius: 50%;">
					<c:if test="${!empty member.photo_name}">
			    		<img src="${pageContext.request.contextPath}/admin/photoView.do?user_num=${member.user_num}" 
			    	                        width="25" height="25" class="my-photo">
			    	</c:if>
			    	<c:if test="${empty member.photo_name}">
			    		<img src="${pageContext.request.contextPath}/resources/images/face.png" 
			    	                        width="25" height="25" class="my-photo">
			    	</c:if>
					</div>
					</td>
					
					<td style="padding:10px 0px">
						<c:if test="${member.auth==0 }">${member.id }</c:if>	<!-- 탈퇴회원 : 링크없음-->
						<c:if test="${member.auth>0 }"><a href="adminUpdateUser.do?user_num=${member.user_num }">${member.id }</a></c:if>
					</td>
					
					<td>${member.name }</td>
					<td>${member.user_num }</td>
					<td>${member.phone }</td>
					<td>${member.email }</td>
					<td>${member.reg_date }</td>
					<td><a href="${pageContext.request.contextPath}/cart/orderListByUser.do?user_num=${member.user_num }">주문내역</a>
					</td>
				</tr>
			</c:forEach>
		</table>
		<div class="align-center">${pagingHtml }</div>
	</c:if>
</div>
