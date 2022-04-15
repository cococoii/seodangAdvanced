<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!-- 중앙컨텐츠 시작 -->
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/style_dw.css" type="text/css"/>
<script src="${pageContext.request.contextPath}/resources/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/resources/js/videoAdapter.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/resources/js/oqna.reply.js"></script>

<div class="page-main" id="qnaDetail">

	<h2>${oqna.title }</h2>
	<ul>
		<li>작성자 : [
			<c:if test="${oqna.auth==0 }">탈퇴회원</c:if>
			<c:if test="${oqna.auth==1 }">정지회원</c:if>
			<c:if test="${oqna.auth==2 }">일반회원</c:if>
			<c:if test="${oqna.auth==3 }">선생님</c:if>
			<c:if test="${oqna.auth==4 }">관리자</c:if>
			] - ${oqna.name}(${oqna.id })
		</li>
		<li>작성일 : ${oqna.reg_date}</li>
		
		<c:if test = "${!empty oqna.modify_date}">
			<li>수정일 : ${oqna.modify_date}</li>
		</c:if>	
		
		<c:if test = "${!empty oqna.filename }">
			<li>첨부파일 : <a href ="file.do?qna_num=${oqna.qna_num}">${oqna.filename}</a></li>
		</c:if>
	</ul>

	<hr size="1" width="100%" noshade="noshade" color="#dfe0e6">
	<c:if test="${fn:endsWith(oqna.filename, '.jpg') || 
					  fn:endsWith(oqna.filename, '.JPG') ||
					  fn:endsWith(oqna.filename, '.gif') ||
					  fn:endsWith(oqna.filename, '.GIF') ||
					  fn:endsWith(oqna.filename, '.png') ||
					  fn:endsWith(oqna.filename, '.PNG')}">
		<div class="align-center">
			<img src="imageView.do?qna_num=${oqna.qna_num }" style="max-width:500px">
		</div>
	</c:if>

	<div style="margin:40px 40px">
		${oqna.content }
	</div>
	<hr size="1" width="100%" noshade="noshade" color="#dfe0e6">
	<br>




	<!--  =================댓글=============== -->
	<div id="reply_div2">
		<form id="re_form">
			<input type="hidden" name="qna_num" value="${oqna.qna_num }" id="qna_num">
			
			<textarea rows="5" cols="80" name="content" id="content" class="rep-content"
			 <c:if test="${session_user_auth==null || session_user_auth<3 }">disabled="disabled"</c:if>
			><c:if test="${session_user_auth==null || session_user_auth<3 }">선생님과 관리자만 작성할 수 있습니다.</c:if></textarea>
		
			<c:if test="${session_user_auth!=null && session_user_auth>=3 }">
				<div id="re_first">
					<span class="letter-count">300/300</span>
				</div>
				<div id="re_second" class="align-right">
					<input type="submit" value="등록" class="gradation2" >
				</div>
			</c:if>
		</form>
	</div>
	<br>
	
	<!-- 댓글 목록 출력 -->
	<hr size="1" noshade style="width:93%;" >
	<div id="output"></div>
	<div class="paging-button" style="display:none;">
		<input type="button" value="다음글 보기">
	</div>
	<div id="loading" style="display:none;">
		<img src="${pageContext.request.contextPath }/resources/images/ajax-loader.gif">
	</div>

	<div class="align-center" >
	<input type="button" value="목록" onclick="location.href='oqnaList.do'" class="btn-black">
		<c:if test="${!empty session_user_num && session_user_num==oqna.user_num }"> 
		<!--로그인이 되어있고, 로그인한 사람이 작성자일 경우 -->
			<input type="button" value="수정" class="btn-black" onclick="location.href='oqnaUpdate.do?qna_num=${oqna.qna_num}'">
			<input type="button" value="삭제" class="btn-black" id="delete_btn">
			<script type="text/javascript">
				let delete_btn = document.getElementById('delete_btn');
				delete_btn.onclick=function(){
					let choice = confirm('삭제하시겠습니까?');
					if(choice){
						location.replace('oqnaDelete.do?qna_num=${oqna.qna_num}');
					}
				};
			</script>
		</c:if>
	</div>
	
	<br>
</div>