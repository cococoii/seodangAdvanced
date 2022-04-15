<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!-- 회원상태 (0탈퇴회원,1정지회원,2일반회원,선생님회원,3관리자), 디폴트값 2 -->
<!-- 중앙 컨텐츠 시작 -->
<script src="${pageContext.request.contextPath}/resources/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/board.reply.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/videoAdapter.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/kit.css">
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
<script type="text/javascript">
$(function(){
	var status; //noFav or yesFav
	function selectData(kit_num){ //77라인 초기값 세팅
	   $.ajax({
	      type:'post',
	      data:{kit_num:kit_num}, //초기값 세팅에서 매개변수로 받아서 el 안씀
	      url:'getFav.do', //LikecountAction
	      dataType:'json',
	      cache:false,
	      timeout:30000,
	      success:function(data){
	         if(data.result=='success'){
	            displayFav(data);
	         }else{
	            alert('좋아요 읽기 오류');
	         }
	      },
	      error:function(){
	         alert('네트워크 오류');
	      }
	   });
	}
	$('#output_fav').click(function(){ //좋아요를 클릭했을때 실행되는 ajax
		$.ajax({
			url:'kitLike.do',
			type:'post',
			data:{kit_num:${kit.kit_num}},
			dataType:'json',
			cache:false,
			timeout:30000,
			success:function(data){
					if(data.result=='logout'){
		               alert('로그인 후 누르세요');
		            }else if(data.result=='success'){ //추천하트 표시
		            	displayFav(data);
		            }
		            else{
		               alert('등록시 오류 발생!');
		            }
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
		});
	});
	//좋아요 표시
	   function displayFav(data){
	      status = data.status;
	      var count = data.count;
	      var output;
	      if(status=='noFav'){
	         output = '../resources/image/heart1.png';
	      }else{
	         output = '../resources/image/heart2.png';
	      }         
	      //문서 객체에 추가
	      $('#output_fav').attr('src',output); //id가 output_fav인 태그 src에 output 저장
	      $('#output_fcount').text(count); //id가 output_fcount인 태그 text에 count(좋아요 총 개수)저장
	   }
	
	   selectData(${kit.kit_num}); //초기값 세팅
	   
		$('#rateInsert').submit(function(){
			if($('#rate_text').val().trim()==''){
				alert('평가 내용을 입력하세요');
				$('#rate_text').val('').focus();
				return false;
			}
		});

	    
});
</script>
<div class="listpage">
	<h2>키트 목록</h2>
	<div class="align-right">
	<c:if test="${!empty session_user_num && session_user_auth>=3}">
		<input type="button" value="키트 등록" onclick="location.href='kitWrite.do'">
	</c:if>
		<input type="button" value="목록" onclick="location.href='kitList.do'">
	</div>
	<form action="kitList.do" id="search_form" method="get">
		<ul class="search">
			<li>
				<select name="keyfield" id="keyfield">
					<option value="1" <c:if test="${param.keyfield == 1}">selected</c:if>>키트이름</option>
					<option value="2" <c:if test="${param.keyfield == 2}">selected</c:if>>판매자</option>
					<option value="3" <c:if test="${param.keyfield == 3}">selected</c:if>>내용</option>
				</select>
			</li>
			<li>
			<input type="search" name="keyword" id="keyword" value="${param.keyword}">
			</li>
			<li>
				<input type="submit" value="검색">
			</li>
			<li> </li>
		</ul>
	</form>
	
	<c:if test="${count == 0}">
	<div class="result-display">표시할 상품이 없습니다.</div>
	</c:if>
	
	<div class="list-main">	
	<c:if test="${count > 0}">
	<c:forEach var="kit" items="${list}">
	<ul>
	<li>
	<div class="box">
	 <div id="kit_num">${kit.kit_num}</div>
	<div><a href="kitDetail.do?kit_num=${kit.kit_num}"><img src="imageView.do?kit_num=${kit.kit_num}"  style="width:250px; height:200px; border-radius:5px;"></a></div>
		<!-- <input type="button" value="❤ " id="kitLike">
		<input type="button" value="장바구니" id="button2">
		<div id="">등록일 : ${kit.reg_date}</div> -->
		<table id="box3">
		<tr><th><div id="kit_name"><a href="kitDetail.do?kit_num=${kit.kit_num}">${kit.kit_name}</a></div></th></tr>
	    <tr><td><div id="kit_content2">${kit.kit_content2}</div></td>
	</tr>
	<tr>
		<td id="kit_quantity"><c:if test = "${kit.kit_quantity != 0}">
			<span>재고 수량 : ${kit.kit_quantity}</span>
		</c:if>
		<c:if test = "${kit.kit_quantity == 0}">
			<span style="color:red;">품절</span>
		</c:if></td>
	</tr>
	<tr>
		<td><div class="price"></div>
		<div id="kit_price">판매가 ${kit.kit_price}원</div></td>
	
	</tr>
		</table>	
	</div>
	</li>
	</ul>
	</c:forEach>
	</c:if>
	</div>
	
</div>

<!-- 중앙 컨텐츠 끝 <div id="pagenum">${pagingHtml}</div> -->
