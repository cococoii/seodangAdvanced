<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script src="${pageContext.request.contextPath}/resources/js/jquery-3.6.0.min.js"></script>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.1/font/bootstrap-icons.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/List.css">

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<style>
	.fa-star{margin-right:20px;}
	.btn{margin-top:30px;}
</style>
    
<form:form modelAttribute="ostarVO" action="qnaList.do" 
	                 enctype="multipart/form-data" id="qna_form">
		<form:hidden path="user_num" />
		<form:hidden path="on_num" />		
		<form:textarea path="text" cssClass="form-control" rows="5" />
		
		<form:radiobutton path="rating" onclick="starmark(this)" id="1one" value="1" style="font-size:40px;cursor:pointer;" class="fa fa-star checked" />
		<form:radiobutton path="rating" onclick="starmark(this)" id="2one" value="2" style="font-size:40px;cursor:pointer;" class="fa fa-star "/>
		<form:radiobutton path="rating" onclick="starmark(this)" id="3one" value="3" style="font-size:40px;cursor:pointer;" class="fa fa-star "/>
		<form:radiobutton path="rating" onclick="starmark(this)" id="4one" value="4" style="font-size:40px;cursor:pointer;" class="fa fa-star"/>
		<form:radiobutton path="rating" onclick="starmark(this)" id="5one" value="5" style="font-size:40px;cursor:pointer;" class="fa fa-star"/></br>		
			<button class="btn btn-block btn-primary" type="submit">리뷰 등록</button>     
</form:form>

	<c:forEach var="ostar" items="${list}">
<div class="home">
<ul>
	<li>${ostar.on_num}
		<div class="List">
			<div class="textone">
				${ostar.text}
				<c:choose>
					<c:when test="${ostar.rating == 1}"><i class="bi bi-star-fill"></i><i class="bi bi-star"></i><i class="bi bi-star"></i><i class="bi bi-star"></i><i class="bi bi-star"></i></c:when>
					<c:when test="${ostar.rating == 2}"><i class="bi bi-star-fill"></i><i class="bi bi-star-fill"></i><i class="bi bi-star"></i><i class="bi bi-star"></i><i class="bi bi-star"></i></c:when>
					<c:when test="${ostar.rating == 3}"><i class="bi bi-star-fill"></i><i class="bi bi-star-fill"></i><i class="bi bi-star-fill"></i><i class="bi bi-star"></i><i class="bi bi-star"></i></c:when>
					<c:when test="${ostar.rating == 4}"><i class="bi bi-star-fill"></i><i class="bi bi-star-fill"></i><i class="bi bi-star-fill"></i><i class="bi bi-star-fill"></i><i class="bi bi-star"></i></c:when>
					<c:when test="${ostar.rating == 5}"><i class="bi bi-star-fill"></i><i class="bi bi-star-fill"></i><i class="bi bi-star-fill"></i><i class="bi bi-star-fill"></i><i class="bi bi-star-fill"></i></c:when>
					<c:otherwise>평점 없음</c:otherwise>
					</c:choose>
					<!-- 모달 클릭 -->
					<a data-bs-toggle="modal" data-bs-target="#staticBackdrop"><i class="bi bi-pencil-square"></i></a>
				<button type="button" class="btn btn-dark" onclick="location.href='#'">상세보기</button>
			</div>
		</div>
	</li>	
</ul>	
</div>
	</c:forEach>
	<div class="align-center">${pagingHtml}</div>
<script>
//별점 스타일 시작
var count;
function starmark(item){
	count=item.id[0];
	sessionStorage.starRating = count;
	var subid= item.id.substring(1);
		for(var i=0;i<5;i++){
			if(i<count){
				document.getElementById((i+1)+subid).style.color="orange";
				}
			else{
				document.getElementById((i+1)+subid).style.color="black";
				}
			}
		}
//별점 스타일 끝
</script>