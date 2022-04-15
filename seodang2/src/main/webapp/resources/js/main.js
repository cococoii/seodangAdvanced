$(function(){
	function getContextPath() {
		var hostIndex = location.href.indexOf( location.host ) + location.host.length;
		return location.href.substring( hostIndex, location.href.indexOf('/', hostIndex + 1) );
	}
	let contextPath = getContextPath();
	
	$("#myPagePopup").css("display","none"); 
	$("#myPage").on("click", function() { 
			$("#myPagePopup").show();
			$("#myPage").append('<div class="myPagebackon"></div>'); //뒷배경 생성
			$('#myPageArrow').find('img').attr('src',contextPath +'/resources/images/up_arrow.png');
			$('body').css('overflow', 'hidden');
    });
	 $("body").on("click", function(event) { 
        if(event.target.className == 'myPagebackon'){
            $("#myPagePopup").hide(); //뒷배경 클릭시 팝업 삭제
            $(".myPagebackon").remove();
			$('#myPageArrow').find('img').attr('src',contextPath +'/resources/images/down_arrow.png');
			$('body').css('overflow', 'auto');
			num='';
        }
    });
});