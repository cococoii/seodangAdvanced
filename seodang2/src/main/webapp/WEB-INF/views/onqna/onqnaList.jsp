<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    

<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Gowun+Dodum&display=swap" rel="stylesheet">

<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/style_dw.css" type="text/css"/>
<script src="${pageContext.request.contextPath}/resources/js/jquery-3.6.0.min.js"></script>


<div class="page-main">
	<h2>온라인 QnA 목록 - Test</h2>

	<div>
		<img src="${pageContext.request.contextPath }/resources/image/oqnaImage.png" style="width:953px; hieght:338px">
	</div>

	<c:if test="${!empty session_user_num }">
		<div class="align-right">
			<input type="button" value="글쓰기" onclick="location.href='onqnaWrite.do'">
		</div>
	</c:if>

	<c:if test="${count == 0 }">
		<div class="result-display">표시할 게시물이 없습니다.</div>		
	</c:if>
	
	
	<c:if test="${count > 0 }">
		<table>
			<tr style="font-family: 'Gowun Dodum', sans-serif;">
				<th style="width:50%;">제목</th>
				<th style="width:10%;">작성자</th>
				<th style="width:12%;">작성자ID</th>
				<th style="width:10%;">작성일</th>
			</tr>
			<c:forEach var="onqna" items="${list }">
				<tr>
					<td style="width:50%;"><a href="onqnaDetail.do?onqna_num=${onqna.qna_num }">${onqna.title }</a></td>				
					<td style="width:10%;">${onqna.name }</td>
					<td style="width:12%;">${onqna.id }</td>
					<td style="width:10%;">${onqna.reg_date }</td>
				</tr>
			</c:forEach>
		</table>
		<div class="align-center">${pagingHtml }</div>
	</c:if>
</div>