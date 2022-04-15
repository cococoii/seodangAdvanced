$(function(){
	let currentPage;
	let count;
	let rowCount;
	
	//목록
	function selectData(pageNum,ostar_num){
		currentPage = pageNum;
		//로딩 이미지 노출
		$('#loading').show();
		
		$.ajax({
			type:'post',
			data:{pageNum:pageNum,ostar_num:ostar_num},
			url:'ostarListReply.do',
			dataType:'json',
			cache:false,
			timeout:30000,
			success:function(param){
				//로딩 이미지 감추기
				$('#loading').hide();
				count = param.count;
				rowCount = param.rowCount;
				
				if(pageNum == 1){
					//처음 호출시는 해당 ID의 div의 내부 내용물을 제거
					$('#output').empty();
				}
				
				$(param.list).each(function(index,item){
					let output = '<div class="item">';
					output += '<h4>' + item.id + '</h4>';
					output += '<div class="sub-item">';
					output += '<p>' + item.ore_content.replace(/\r\n/g,'<br>') + '</p>';
					
					if(item.ore_date){
						output += '<span class="modify-date">등록일 : ' + item.ore_date + '</span>';
					}
					
					if(param.user_num==item.user_num){
						output += ' <input type="button" data-num="'+item.ore_num+'" value="수정" class="modify-btn">';
						output += ' <input type="button" data-num="'+item.ore_num+'" value="삭제" class="delete-btn">';
					}
					output += '<hr size="1" noshade>';
					output += '</div>';
					output += '</div>';
					
					//문서 객체에 추가
					$('#output').append(output);
				});
				//paging button 처리
				if(currentPage>=Math.ceil(count/rowCount)){
					//다음 페이지가 없음
					$('.paging-button').hide();
				}else{
					//다음 페이지가 존재
					$('.paging-button').show();
				}
			},
			error:function(){
				//로딩 이미지 감추기
				$('#loading').hide();
				alert('네트워크 오류 발생!');	
			}
		});
		
	}
	//다음 댓글 버튼
	$('.paging-button input').click(function(){
		let pageNum = currentPage + 1;
		selectData(pageNum,$('#ostar_num').val());
	});

	
	//댓글 등록
	$('#re_form').submit(function(event){
		if($('#ore_content').val().trim()==''){
			alert('내용을 입력하세요');
			$('#ore_content').val('').focus();
			return false;
		}
		
		let data = $(this).serialize();
		$.ajax({
			type:'post',
			data:data,
			url:'writeReply.do',
			dataType:'json',
			cache:false,
			timeout:30000,
			success:function(param){
				if(param.result == 'logout'){
					alert('로그인해야 작성할 수 있습니다.');
				}else if(param.result == 'success'){
					//폼초기화
					initForm();
					//댓글 작성이 성공하면 새로 삽입한 글을 포함해서 첫번째 페이지의
					//게시글을 다시 호출함
					selectData(1,$('#ostar_num').val());
				}else{
					alert('등록시 오류 발생!');
				}
			},
			error:function(){
				alert('네트워크 오류 발생!');
			}
		});
		//기본 이벤트 제거
		event.preventDefault();
	});
	
	//댓글 폼 초기화
	function initForm(){
		$('textarea').val('');
		$('#re_first .letter-count').text('300/300');
	}
	//글자수 체크
	$(document).on('keyup','textarea',function(){
		let inputLength = $(this).val().length;
		if(inputLength>300){
			$(this).val($(this).val().substring(0,300));
		}else{
			let remain = 300 - inputLength;
			remain += '/300';

			if($(this).attr('id')=='ore_content'){
				$('#re_first .letter-count').text(remain);
			}else{
				//수정폼 글자수
				$('#mre_first .letter-count').text(remain);
			}
		}
	});
	//댓글 수정폼
	//댓글 수정 버튼 클릭시 수정폼 노출
	$(document).on('click','.modify-btn',function(){
		//댓글 글번호
		let re_num = $(this).attr('data-num');
		//댓글 내용
		let content = $(this).parent().find('p').html().replace(/<br>/gi,'\r\n');
		//댓글 수정폼 UI
		let modifyUI = '<form id="mre_form">';
		modifyUI += '<input type="hidden" name="ore_num" id="mre_num" value="'+re_num+'">';
		modifyUI += '<textarea rows="3" cols="50" name="ore_content" id="mre_content" class="rep-content">'+content+'</textarea>';
		modifyUI += '<div id="mre_first"><span class="letter-count">300/300</span></div>';
		modifyUI += '<div id="mre_second" class="align-right">';
		modifyUI += '<input type="submit" value="수정">';
		modifyUI += '<input type="button" value="취소" class="re-reset">';
		modifyUI += '</div>';
		modifyUI += '<hr size="1" noshade width="96%">';
		modifyUI += '</form>';
		
		//이전에 이미 수정하는 댓글이 있을 경우 수정버튼을 클릭하면
		//숨김 sub-item를 환원시키고 수정폼을 초기화함
		initModifyForm();
		
		//지금 클릭해서 수정하고자 하는 데이터는 감추기
		//수정버튼을 감싸고 있는 div
		$(this).parent().hide();
		
		//수정폼을 수정하고자하는 데이터가 있는 div에 노출
		$(this).parents('.item').append(modifyUI);
		
		//입력한 글자수 셋팅
		let inputLength = $('#mre_content').val().length;
		let remain = 300 - inputLength;
		remain += '/300';
		
		//문서 객체에 반영
		$('#mre_first .letter-count').text(remain);		
	});
	//수정폼 취소클릭 -> 수정폼 초기화
	$(document).on('click','.re-reset',function(){
		initModifyForm();
	});
	//댓글 수정폼 초기화
	function initModifyForm(){
		$('.sub-item').show();
		$('#mre_form').remove();
	}
	//댓글 수정
	$(document).on('submit','#mre_form',function(event){
		if($('#mre_content').val().trim()==''){
			alert('내용을 입력하세요!');
			$('#mre_content').val('').focus();
			return false;
		}
		
		//폼에 입력한 데이터 반환
		let data = $(this).serialize();
		
		//데이터 전송
		$.ajax({
			url:'updateReply.do',
			type:'post',
			data:data,
			dataType:'json',
			cache:false,
			timeout:30000,
			success:function(param){
				if(param.result == 'logout'){
					alert('로그인해야 수정할 수 있습니다.');
				}else if(param.result == 'success'){
					//수정 데이터 표시
					$('#mre_form').parent().find('p').html($('#mre_content').val().replace(/</g,'&lt;').replace(/>/g,'&gt;').replace(/\r\n/g,'<br>').replace(/\r/g,'<br>').replace(/\n/g,'<br>'));
					//최근 수정일 처리
					$('#mre_form').parent().find('.modify-date').text('최근 수정일 : 5초미만');
					
					//수정폼 초기화
					initModifyForm();				
				}else if(param.result == 'wrongAccess'){
					alert('타인의 글은 수정할 수 없습니다.');
				}else{
					alert('수정시 오류 발생!');
				}
			},
			error:function(){
				alert('네트워크 오류 발생!');
			}
		});
		//기본 이벤트 제거
		event.preventDefault();
	});
	//댓글 삭제
	$(document).on('click','.delete-btn',function(){
		//댓글 번호
		let ore_num = $(this).attr('data-num');
		
		$.ajax({
			type:'post',
			url:'deleteReply.do',
			data:{ore_num:ore_num},
			dataType:'json',
			cache:false,
			timeout:30000,
			success:function(param){
				if(param.result == 'logout'){
					alert('로그인해야 삭제할 수 있습니다.');
				}else if(param.result == 'success'){
					alert('삭제 완료!');
					selectData(1,$('#ostar_num').val());
				}else if(param.result == 'wrongAccess'){
					alert('타인의 글을 삭제할 수 없습니다.');
				}else{
					alert('댓글 삭제시 오류 발생');
				}
			},
			error:function(){
				alert('네트워크 오류 발생!');
			}
		});
	});
	//초기 데이터 목록 호출
	selectData(1,$('#ostar_num').val());
});