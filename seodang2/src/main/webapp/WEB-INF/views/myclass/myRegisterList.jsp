<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
    
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/myclassList.css">    
<style>
	.main-left{
		background-color:yellow;
		width:300px;
		height:500px;
		float:left;
	}
	.main-center{
		width:600px;
		height:500px;
		margin-left:300px;
	}
	.main-left ul li{
		padding-top:50px;
		margin-left:30px;
		margin-top:30px;
		font-size:20px;
		font-color:gray;
	}
</style>

<div class="main-left">
	<ul>
		<li>
			<a href="${pageContext.request.contextPath}/myclass/myclassMain.do">홈</a>
		</li>
		<c:if test="${session_user_auth == 3}">
		<li>
			<a href="${pageContext.request.contextPath}/myclass/myclassList.do">내가 올린 강의</a>
		</li>
		</c:if>
		<c:if test="${session_user_auth == 2}">
		<li>
			<a href="${pageContext.request.contextPath}/myclass/myRegisterList.do">구매한 강의</a>
		</li>
		</c:if>
		<li>
			<a href="${pageContext.request.contextPath}/myclass/myclassMain.do">수익</a>
		</li>
	</ul>
</div>
<div class="main-center">
	<div class="home">
<ul>
<li>
	<form action="myclassList.do" id="search_form" method="get">
		<ul class="search">
			<li>
				<select name="keyfield" id="keyfield">
					<option value="1" <c:if test="${param.keyfield == 1}">selected</c:if>>강의 이름</option>
				</select>
			</li>
			<li>
			<input type="search" name="keyword" id="keyword" value="${param.keyword}">
			</li>
			<li>
				<input type="submit" value="검색">
			</li>
		</ul>
	</form>
	</li>
	<c:forEach var="myreg" items="${list}">
	<li>
		<div class="List">
			<div class="textone">
				<p>회원번호 : ${user_num} status : ${myreg.on_status}</p>			
				<p>강의 명 : ${myreg.onreg_num}</p>
				<c:if test="${myreg.on_status == 1}">
					<p>구매 일자 : ${myreg.on_regdate}</p>
				</c:if>
				<c:if test="${myreg.on_status == 2}">
					<p style="color:red;">취소 일자 : ${myreg.on_moregdate}</p>
				</c:if>
				<button type="button" class="btn btn-dark" onclick="location.href='${pageContext.request.contextPath}/onclass/qnaList.do?on_num=${myreg.on_num}'">후기</button>
				<button type="button" class="btn btn-dark" onclick="location.href='#'">상세보기</button>
				<c:if test="${myreg.on_status == 1}">
					<button type="button" class="btn btn-dark" onclick="location.href='${pageContext.request.contextPath}/myclass/deletePayment.do?onreg_num=${myreg.onreg_num}'">수강취소</button>
				</c:if>
			</div>
		</div>
	</li>	
	</c:forEach>
</ul>	
</div>

	<div class="align-center">${pagingHtml}</div>
</div>