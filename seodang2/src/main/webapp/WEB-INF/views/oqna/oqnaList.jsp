<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    

<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Gowun+Dodum&display=swap" rel="stylesheet">

<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/style_dw.css" type="text/css"/>
<script src="${pageContext.request.contextPath}/resources/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	$(function(){
		$('#search_form').submit(function(){
			if($('#keyword').val().trim()==''){
				alert('검색어를 입력하세요!');
				$('#keyword').val('').focus();
				return false;
			}
		});
	});
</script>



<div class="page-main">

	<div class="align-center">
		<img src="${pageContext.request.contextPath }/resources/image/neonQnA.png" style="width:90%; hieght:338px; margin:50px 0px;">
	</div>

	
		<form action="oqnaList.do" id="search_form" method="get" style="padding:40px 0px 0px 0px;">
			<ul class="search">
				<li>
					<select name="keyfield" id="keyfield">			
						<option value="1"<c:if test="${param.keyfield==1}">selected</c:if>>제목</option>
						<option value="2"<c:if test="${param.keyfield==2}">selected</c:if>>선생님</option>
						<option value="3"<c:if test="${param.keyfield==3}">selected</c:if>>관리자</option>
						<option value="4"<c:if test="${param.keyfield==4}">selected</c:if>>작성자(이름+ID)</option>
						<option value="5"<c:if test="${param.keyfield==5}">selected</c:if>>제목+내용</option>
					</select>
				</li>
				<li>
					<input type="search" name="keyword" id="keyword" value="${param.keyword }" placeholder='검색어를 입력하세요!'>
				</li>
				<li>
					<input type="submit" value="검색" class="gradation2">
					<input type="button" value="목록" onclick="location.href='oqnaList.do'" class="gradation2"> 
				</li>
				<!-- 
				<li>
					<input type="submit" value="검색" >
					<input type="button" id="listbtn" value="목록" onclick="location.href='oqnaList.do'"> 
				</li>
				 -->
			</ul>
		</form>
	
	
	

	<c:if test="${!empty session_user_num }">
		<div class="align-right" style="margin : 0 60px">
			<input type="button" value="글쓰기" onclick="location.href='oqnaWrite.do'" class="btn-black">
		</div>
	</c:if>

	<c:if test="${count == 0 }">
		<div class="result-display">표시할 게시물이 없습니다.</div>		
	</c:if>
	
	
	<c:if test="${count > 0 }">
		<table>
			<tr style="font-family: 'Gowun Dodum', sans-serif;">
				<th style="width:8%;">글번호</th>
				<th style="width:50%;">제목</th>
				<th style="width:10%;">회원등급</th>
				<th style="width:10%;">작성자</th>
				<th style="width:12%;">작성자ID</th>
				<th style="width:10%;">작성일</th>
			</tr>
			<c:forEach var="oqna" items="${list }">
				<tr>
					<td style="width:8%;">${oqna.qna_num }</td>
					<td style="width:50%;"><a href="oqnaDetail.do?qna_num=${oqna.qna_num }">${oqna.title }</a></td>
					<td style="width:10%;">
						<c:if test="${oqna.auth==0 }">탈퇴회원</c:if>
						<c:if test="${oqna.auth==1 }">정지회원</c:if>
						<c:if test="${oqna.auth==2 }">일반회원</c:if>
						<c:if test="${oqna.auth==3 }">선생님</c:if>
						<c:if test="${oqna.auth==4 }">관리자</c:if>
					</td>					
					<td style="width:10%;">${oqna.name }</td>
					<td style="width:12%;">${oqna.id }</td>
					<td style="width:10%;">${oqna.reg_date }</td>
				</tr>
			</c:forEach>
		</table>
		<div class="align-center">${pagingHtml }</div>
	</c:if>
</div>
