<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
    
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/myclassList.css">

<link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
	
<%-- <div style="padding-top:200px">    
<table>
		<tr>
			<th>아이디</th>
			<th>수강신청한 날짜</th>
			<th>강의 취소</th>
		</tr>
		<c:forEach var="myclass" items="${list}" >
		<tr>
			<td>${myclass.id} 온레그넘 : ${myclass.onreg_num}</td>
			<td>${myclass.on_regdate}</td>
			<c:choose>
			<c:when test="${myclass.on_status == 1}">
			<td><a href="${pageContext.request.contextPath}/myclass/myclassDelete.do?onreg_num=${myclass.onreg_num}&on_num=${myclass.on_num}&user_num=${myclass.user_num}">강의취소</a></td>
			</c:when>
			<c:when test="${myclass.on_status == 2}">
			<td>강의취소 날짜 : ${myclass.on_moregdate}</td>
			</c:when>
			</c:choose>		
		</tr>
		</c:forEach>
	</table>
	<b>총 인원 : ${people}</b>
</div> --%>



<section class="main-content">
		<div class="container">
			<h1>수강신청한 학생 목록</h1>
			<br>
			<br>
<hr size="1">
			<table class="table">
				<thead>
					<tr>
						<th>아이디</th>
						<th>강의상태</th>
						<th>수강신청 날짜</th>
						<th>수강취소 날짜<br>(재시작 날짜)</th>
						<th>중단/재시작</th>
						<th>제거</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="myclass" items="${list}" >
					<tr>
						<td>
							<div class="user-info">

								<div class="user-info__basic">
									<h5 class="mb-0">${myclass.id}</h5>
								</div>
							</div>
						</td>
						<td>
							<c:choose>
							<c:when test="${myclass.on_status == 1}">
							<span class="active-circle bg-success"></span> 수강중
							</c:when>
							<c:when test="${myclass.on_status == 2}">
							<span class="active-circle bg-danger"></span> 수강중단
							</c:when>
							</c:choose>
						</td>
						<td>${myclass.on_regdate}</td>
						<td>${myclass.on_moregdate}</td>
						<td>
							<c:choose>
							<c:when test="${myclass.on_status == 1}">
							<button class="btn btn-danger btn-sm" onclick="location.href='myclassDelete.do?onreg_num=${myclass.onreg_num}&on_num=${myclass.on_num}&user_num=${myclass.user_num}'">강의 중단</button>
							</c:when>
							<c:when test="${myclass.on_status == 2}">
							<button class="btn btn-primary btn-sm" onclick="location.href='myclassUpdate.do?onreg_num=${myclass.onreg_num}&on_num=${myclass.on_num}&user_num=${myclass.user_num}'">강의 재시작</button>
							</c:when>
							</c:choose>
						</td>
						<td>
							<button class="btn btn-danger btn-sm" onclick="location.href='myclassUserDelete.do?onreg_num=${myclass.onreg_num}&on_num=${myclass.on_num}'">목록 삭제</button>
						</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
</section>

<div class="align-center">${pagingHtml}</div>