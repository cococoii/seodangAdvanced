$(function(){
	
	let off_num=$('#off_num').val();
	
	function getContextPath() {
		var hostIndex = location.href.indexOf( location.host ) + location.host.length;
		return location.href.substring( hostIndex, location.href.indexOf('/', hostIndex + 1) );
	}
	let contextPath = getContextPath();

	//찜하기
	selectLikeCount();
	$('#like_btn').click(function(){
		$.ajax({
			url:'like.do',
			type:'post',
			data:{off_num:off_num},
			cache:false,
			timeout:30000,
			success:function(param){
				if(param.result=='logout'){
					alert('로그인이 필요합니다.');
					//location.href='${pageContext.request.contextPath}//user/login.do';
				}else if(param.result=='success'){
					$('#like_btn').find('img').attr('src',contextPath +'/resources/images/heart_fill.png');
					selectLikeCount();
				}else if(param.result=='cancelLike'){
					$('#like_btn').find('img').attr('src',contextPath +'/resources/images/heart_black_nofill.png');
					selectLikeCount();
				}else{
					alert('찜 오류 발생');
				}
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
		});
	})
	function selectLikeCount(){
		$.ajax({
			url:'countLike.do',
			type:'post',
			data:{off_num:off_num},
			cache:false,
			timeout:30000,
			success:function(param){
				if(param.result=='success'){
					$('#like_count').text(param.count);
				}else{
					alert('찜 오류 발생!');
				}
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
		});
	}
	
	//타임 테이블 보여주기
	
	//처음 초기화
	selectTimedate($('#time_date').data('timedate'));
	
	$('.time_date').click(function(){
		let time_date = $(this).parents('div').data('timedate');
		selectTimedate(time_date);
	});
	
	function selectTimedate(time_date){
		let timeUI;
		$.ajax({
			url:'selectTimeDate.do',
			type:'post',
			data:{off_num:off_num,time_date:time_date},
			cache:false,
			timeout:30000,
			success:function(param){
				if(param.result=='success'){
					$('#timetable  *').remove();
					$.each(param.list,function(key,value){
						let time_start = Object.values(value)[5];
						let time_end = Object.values(value)[6];
						let off_personcount=Object.values(value)[7];
						let off_limit=Object.values(value)[8];
						timeUI  = '<a class="timetable">';
						timeUI += '		<div class="time_start" data-value="'+time_start+'">'+time_start+'</div>';
						timeUI += '		<div>~</div>';
						timeUI += '		<div class="time_end" data-value="'+time_end+'">'+time_end+'</div>';
						timeUI += '		<div class="off_personcount" data-value="'+off_personcount+'">'+off_personcount+" / "+off_limit+" 명"+'</div>'
						timeUI += '</a>';
						$('#timetable').append(timeUI);
					});
				}else{
					alert('타임테이블 오류 발생!');
				}
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
		});
	}
	
	
})