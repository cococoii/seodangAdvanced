<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!-- 중앙 컨텐츠 시작 -->
<script src="${pageContext.request.contextPath}/resources/js/jquery-3.6.0.min.js"></script>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap-theme.min.css">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/user.css">
<style>
	.page-main a {
		font-size:22px;
	}
</style>
<script type="text/javascript">
	$(function(){
		let checkId=0;
		
		$('#id').keyup(function(){
			if($('#id').val().trim()==''){
				$('#message_id').css('color','red').text('아이디를 입력하세요.');
				//$('#id').val('').focus();
				return;
			}
			$.ajax({
				url:'confirmId.do',
				type:'post',
				data:{id:$('#id').val()},
				dataType:'json',
				cache:false,
				timeout:30000,
				success:function(param){
					if(param.result=='idNotFound'){
						$('#message_id').css('color','#000').text('등록가능한ID');
						checkId=1;
					}else if(param.result=='idDuplicated'){
						$('#message_id').css('color','red').text('중복된 ID');
						//$('#id').val('').focus();
						checkId=0;
					}else if(param.result=='notMatchPattern'){
						$('#message_id').css('color','red').text('영문,숫자 4~12');
						//$('#id').val('').focus();
						checkId=0;
					}else{
						checkId=0;
						alert('ID중복체크 오류');
					}
				},
				error:function(){
					checkId=0;
					alert('네트워크 오류 발생');
				}
			});
		});//end of click
		$('#register_form').submit(function(){
			if(checkId==0){
				$('#message_id').css('color','red').text('아이디를 확인해보세요.');
				if($('#id').val().trim()==''){
					$('#id').val('').focus();
				}
				return false;
			}
		})
	});
</script>
<div class="register-header"></div>
	<form:form modelAttribute="userVO" action="registerUser.do" enctype="multipart/form-data" id="modify_form">
		<form:errors element="div" cssClass="error-color"/>
		<!-- 필드가 없는 에러 발생 시 -->
		<ul class="register_form">
			<li class="align-center"><a href="${pageContext.request.contextPath }/main/main.do"><img class="logo register" src="${pageContext.request.contextPath}/resources/images/logo2_1.png"></a></li>
			<li>
			<div class="display-flex register_auth">
				<div class="register-auth">
				<form:radiobutton path="auth" value="2"/>학생
				</div>
				<div class="register-auth">
				<form:radiobutton path="auth" value="3"/>선생님
				</div>
			</div>
			</li>
			<li style="clear:both;">
				<form:label path="id">아이디</form:label><br>
				<form:input path="id" placeholder="영문,숫자만 허용"/>
				<span id="message_id"></span>
				<form:errors path="id" cssClass="error-color"/>
			</li>
			<li>
				<form:label path="name">이름</form:label><br>
				<form:input path="name"/>
				<form:errors path="name" cssClass="error-color"/>
			</li>
			<li>
				<form:label path="age">나이</form:label><br>
				<form:input type="number" path="age"/>
				<form:errors path="age" cssClass="error-color"/>
			</li>
			<li>
				<form:label path="passwd">비밀번호</form:label><br>
				<form:password path="passwd"/>
				<form:errors path="passwd" cssClass="error-color"/>
			</li>
			<li>
				<form:label path="phone">전화번호</form:label><br>
				<form:input path="phone"/>
				<form:errors path="phone" cssClass="error-color"/>
			</li>
			<li>
				<form:label path="email">이메일</form:label><br>
				<form:input path="email"/>
				<form:errors path="email" cssClass="error-color"/>
			</li>
			<li>
				<form:label path="zipcode">주소</form:label><br>
				<form:input path="zipcode" onclick="sample2_execDaumPostcode()"/>
				<input type="button" class="btn-black postcode" onclick="sample2_execDaumPostcode()" value="우편번호 찾기">
				<form:errors path="zipcode" cssClass="error-color"/>
			</li>
			<li>
				<form:input path="address1" placeholder="주소"/>
				<form:errors path="address1" cssClass="error-color"/>
			</li>
			<li>
				<form:input path="address2" placeholder="상세주소"/>
				<form:errors path="address2" cssClass="error-color"/>
			</li>
		</ul>
		<div class="align-center">
			<form:button class="btn-black login register">회원가입</form:button>
		</div>
	</form:form>
	<div class="footer-login"></div>
<!-- 우편번호 스크립트 시작 -->
<!-- iOS에서는 position:fixed 버그가 있음, 적용하는 사이트에 맞게 position:absolute 등을 이용하여 top,left값 조정 필요 -->
<div id="layer" style="display:none;position:fixed;overflow:hidden;z-index:1;-webkit-overflow-scrolling:touch;">
<img src="//t1.daumcdn.net/postcode/resource/images/close.png" id="btnCloseLayer" style="cursor:pointer;position:absolute;right:-3px;top:-3px;z-index:1" onclick="closeDaumPostcode()" alt="닫기 버튼">
</div>

<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
	document.getElementById('address1').readOnly = true;
	document.getElement
    // 우편번호 찾기 화면을 넣을 element
    var element_layer = document.getElementById('layer');

    function closeDaumPostcode() {
        // iframe을 넣은 element를 안보이게 한다.
        element_layer.style.display = 'none';
    }

    function sample2_execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var addr = ''; // 주소 변수
                var extraAddr = ''; // 참고항목 변수

                //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                    addr = data.roadAddress;
                } else { // 사용자가 지번 주소를 선택했을 경우(J)
                    addr = data.jibunAddress;
                }

                // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
                if(data.userSelectedType === 'R'){
                    // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                    // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                    if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                        extraAddr += data.bname;
                    }
                    // 건물명이 있고, 공동주택일 경우 추가한다.
                    if(data.buildingName !== '' && data.apartment === 'Y'){
                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    }
                    // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                    if(extraAddr !== ''){
                        extraAddr = ' (' + extraAddr + ')';
                    }
                    //(주의)address1에 참고항목이 보여지도록 수정
                    // 조합된 참고항목을 해당 필드에 넣는다.
                    //(수정) document.getElementById("address2").value = extraAddr;
                
                } 
                //(수정) else {
                //(수정)    document.getElementById("address2").value = '';
                //(수정) }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('zipcode').value = data.zonecode;
				document.getElementById('zipcode').readOnly=true;
                //(수정) + extraAddr를 추가해서 address1에 참고항목이 보여지도록 수정
                document.getElementById("address1").value = addr + extraAddr;
				document.getElementById('address1').readOnly=true;
                // 커서를 상세주소 필드로 이동한다.
                document.getElementById("address2").focus();

                // iframe을 넣은 element를 안보이게 한다.
                // (autoClose:false 기능을 이용한다면, 아래 코드를 제거해야 화면에서 사라지지 않는다.)
                element_layer.style.display = 'none';
            },
            width : '100%',
            height : '100%',
            maxSuggestItems : 5
        }).embed(element_layer);

        // iframe을 넣은 element를 보이게 한다.
        element_layer.style.display = 'block';

        // iframe을 넣은 element의 위치를 화면의 가운데로 이동시킨다.
        initLayerPosition();
    }

    // 브라우저의 크기 변경에 따라 레이어를 가운데로 이동시키고자 하실때에는
    // resize이벤트나, orientationchange이벤트를 이용하여 값이 변경될때마다 아래 함수를 실행 시켜 주시거나,
    // 직접 element_layer의 top,left값을 수정해 주시면 됩니다.
    function initLayerPosition(){
        var width = 300; //우편번호서비스가 들어갈 element의 width
        var height = 400; //우편번호서비스가 들어갈 element의 height
        var borderWidth = 5; //샘플에서 사용하는 border의 두께

        // 위에서 선언한 값들을 실제 element에 넣는다.
        element_layer.style.width = width + 'px';
        element_layer.style.height = height + 'px';
        element_layer.style.border = borderWidth + 'px solid';
        // 실행되는 순간의 화면 너비와 높이 값을 가져와서 중앙에 뜰 수 있도록 위치를 계산한다.
        element_layer.style.left = (((window.innerWidth || document.documentElement.clientWidth) - width)/2 - borderWidth) + 'px';
        element_layer.style.top = (((window.innerHeight || document.documentElement.clientHeight) - height)/2 - borderWidth) + 'px';
    }
</script>
<!-- 우편번호 스크립트 끝 -->
<!-- 중앙 컨텐츠 끝 -->