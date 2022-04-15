<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 

<script src="${pageContext.request.contextPath}/resources/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/resources/js/onclass.reply.js"></script>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.1/font/bootstrap-icons.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/ratestar.css">



<div class="outer2">
<div style="float:left">
<a href="javascript:history.back(-1)">
	<!-- 뒤로가기 아이콘 -->
	<svg xmlns="http://www.w3.org/2000/svg" width="50" height="50" fill="currentColor" class="bi bi-arrow-left-circle" viewBox="0 0 16 16">
	  <path fill-rule="evenodd" d="M1 8a7 7 0 1 0 14 0A7 7 0 0 0 1 8zm15 0A8 8 0 1 1 0 8a8 8 0 0 1 16 0zm-4.5-.5a.5.5 0 0 1 0 1H5.707l2.147 2.146a.5.5 0 0 1-.708.708l-3-3a.5.5 0 0 1 0-.708l3-3a.5.5 0 1 1 .708.708L5.707 7.5H11.5z"/>
	</svg>
	<!-- 뒤로가기 아이콘 -->
</a>
</div>
	<div class="inner2">
		<div style="font-size:30px"><b>${ostar.rating}</b></div>
		<div style="font-size:30px"><b>${ostar.text}</b></div>
		<div style="font-size:14px"><p>${ostar.reg_date}</p></div>
		
		<!-- 댓글 목록 시작 -->
		<div id="output"></div>
			<div class="paging-button" style="display:none;">
				<input type="button" value="다음글 보기">
			</div>
		<div id="loading" style="display:none;">
			<img src="${pageContext.request.contextPath }/resources/images/ajax-loader.gif">
		</div>						
		<!-- 댓글 목록 끝 -->

			<!-- 댓글 시작  -->
			<div id="reply_div">
			<form id="re_form">
			<input type="hidden" name="ostar_num" value="${ostar.ostar_num}" id="ostar_num">
			<textarea rows="5" cols="80" name="ore_content" id="ore_content" class="rep-content"
			 <c:if test="${empty session_user_auth || session_user_auth<=2 }">disabled="disabled"</c:if>
			><c:if test="${empty session_user_auth}">로그인 후 작성</c:if></textarea>
			<c:if test="${!empty session_user_auth && session_user_auth>=3 }">
				<div id="re_first">
					<span class="letter-count">300/300</span>
				</div>
					<div id="re_second" class="align-right">
						<input type="submit" value="등록" >
					</div>
				</c:if>
				</form>
			</div>
			<!-- 댓글 끝 -->
			</div>
</div>

${ostar.ostar_num}
${ostar.user_num}
${ostar.on_num}
${ostar.text}
${ostar.rating}
${ostar.reg_date}