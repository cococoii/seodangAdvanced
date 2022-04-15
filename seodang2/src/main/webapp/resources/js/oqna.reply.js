// 1. 초기데이터(목록) 호출
// 2. textarea에 내용 입력시 글자 수 체크
// 3. 댓글작성 form 초기화
// 4. 댓글 등록
// 5. 댓글 목록
// 6. 다음 댓글보기 버튼클릭시 데이터 추가
// 7. 댓글수정 폼 초기화
// 8. 수정form에서 취소버튼 클릭시 수정form 초기화
// 9. 댓글 수정버튼 클릭시 수정form 노출
// 10. 댓글 수정 
// 11. 댓글 삭제


$(function(){
	let currentPage;
	let count;
	let rowCount;

	//5. 댓글목록
	function selectData(pageNum, qna_num){
		//(1) currentPage를 pageNum으로 저장
		currentPage = pageNum;
		
		//(2) 로딩이미지 노출
		$('#loading').show();
		
		//(3) 목록 호출
		$.ajax({
			type:'post',
			data:{pageNum:pageNum, qna_num:qna_num},
			url:'oqnaListReply.do',
			dataType:'json',
			cache:false,
			timeout:30000,
			success:function(param){
				//<1>로딩 이미지 감추기
				$('#loading').hide();
				
				//<2>전달받은 count, rowCount 값 저장
				count = param.count;
				rowCount = param.rowCount;
				
				//<3> pageNum이 1일 경우
				if(pageNum==1){
					//처음 호출시는 해당 ID의 div내부 내용물을 제거
					$('#output').empty();
				}
			
				//<4> 댓글 목록작업 - each메서드로 list배열 루프돌면서 목록 만들기
				$(param.list).each(function(index,item){
					let output = '<div class = "item">';
					
					output += '<h4>' + item.name;
					if(item.auth ==3) {
						output += '(선생님)'
					}else if(item.auth ==4) {
						output += '(관리자)'
					}
					output += '</h4>'
					
					output += '<div class = "sub-item">'
					output += '<p>' + item.content.replace(/\r\n/g, '<br>') + '</p>';
				
					if(item.re_mdate){
						output += '<span class="modify-date">최근 수정일 : '+ item.re_mdate  + '</span>';
					}else{
						output += '<span class="modify-date">등록일 : '+ item.re_date  + '</span>';
					}
				
					// 로그인회원번호와 글작성 회원번호가 동일하면 수정,삭제 버튼 활성화
					if(param.user_num == item.user_num){
					output += '<input type="button" data-num="'+item.qnare_num+'" value="수정" class="modify-btn" id="listbtn">';
					output += '<input type="button" data-num="'+item.qnare_num+'" value="삭제" class="delete-btn" id="listbtn">';
					}
					
					output += '<hr size="1" noshade>';
					output += '</div>';
					output += '</div>';
					
					// 문서객체에 추가
					$('#output').append(output);
				});
			
				//<5> pagingButton 처리
				if(currentPage >= Math.ceil(count/rowCount)){
					//다음페이지가 없음 
					$('.paging-button').hide();
				}else{
					//다음페이지가 존재
					$('.paging-button').show();
				}
			},
			error:function(){
				//로딩이미지 감추기
				$('#loading').hide();
				alert('네트워크 오류발생!');
			}
		});
	}


	//6. 다음댓글보기 버튼클릭시 데이터 추가
	$('.paging-button input').click(function(){
		pageNum = currentPage +1;
		selectData(pageNum, $('#qna_num').val());
	});


	//4. 댓글 등록	
	$('#re_form').submit(function(event){

		//(1)입력창이 비어있는지 조건체크
		if($('#content').val().trim()==''){
			alert('내용을 입력하세요');
			$('#content').val('').focus();
			return false;
		}
		//(2) 입력창(form)의 모든 정보 다 읽어오기
		let data = $(this).serialize();
		
		//(3) 데이터 전송
		$.ajax({
			type : 'post',
			data : data,
			url : 'oqnaWriteReply.do',
			dataType : 'json',
			cache : false,
			timeout : 30000,
			success : function(param){
				if(param.result =='logout'){
					alert('로그인해야 작성할 수 있습니다.');
				}else if(param.result =='notAdmin'){
					alert('관리자만 작성할 수 있습니다.');
				}else if(param.result =='success'){
					//폼초기화
					initForm();
					//댓글작성 성공시, 새로 삽입한 글을 포함해 첫번째 페이지의 게시글을 다시 호출
					selectData(1,$('#qna_num').val());
				}else{
					alert('등록시 오류 발생!');
				}
			},
			error:function(){
				alert('네트워크 오류발생!');
			}
		});	
		//기본이벤트 제거 (자기자신을 다시 호출하지 않도록)
		event.preventDefault();	
	});
	
	

	//3. 댓글작성 form 초기화
	function initForm(){
		$('textarea').val('');
		$('#re_first .letter-count').text('300/300');
	}

	//2. textarea에 내용 입력시 글자 수 체크
	$(document).on('keyup','textarea',function(){
		//남은 글자수 구하기
		let inputLength = $(this).val().length;
		
		//300자를 넘어선 경우
		if(inputLength>300){
			$(this).val($(this).val().substring(0,300));
		}else{
		//300자 이하인 경우
			let remain = 300 - inputLength;
			remain += '/300';
			
			//등록폼과 수정폼에 모두 적용
			if($(this).attr('id')=='content'){
				//등록폼 글자수
				$('#re_first .letter-count').text(remain);
			}else{
				//수정폼 글자수
				$('#mre_first .letter-count').text(remain);
			}
		}
	});
	

	//9. 댓글 수정버튼(.modify-btn) 클릭시 수정form 노출
	$(document).on('click','.modify-btn',function(){
	
		//<1>댓글 글번호
		let re_num = $(this).attr('data-num');
		//<2>댓글 내용
		let content = $(this).parent().find('p').html().replace(/<br>/gi,'\r\n');
															//줄바꿈처리했던 것을 다시 원래대로
		//<3>댓글 수정폼 UI
		let modifyUI = '<form id="mre_form">';
		modifyUI += '<input type ="hidden" name="qnare_num" id="mre_num" value="'+re_num+'">';
		modifyUI += '<textarea rows="3" cols="50" name="content" id="mre_content" class="rep-content">'+content+'</textarea>';
		modifyUI += '<div id="mre_first"><span class="letter-count">300/300</span></div>';		
		modifyUI += '<div id="mre_second" class="align-right">';
		modifyUI += '<input type="submit" value="수정">';
		modifyUI += '<input type="button" value="취소" class="re-reset" id="listbtn">';
		modifyUI += '</div>';
		modifyUI += '<hr size="1" noshade width="96%">';
		modifyUI += '</form>';
	
		//<4> 이미 수정하는 댓글이 있을경우 수정버튼을 클릭하면, 숨김 sub-item을 환원시키고 수정폼을 초기화
		initModifyForm();
		
		//<5> 지금 클릭해서 수정하고자하는 데이터는 감추기(=수정버튼을 감싸고 있는 div 숨기기)
		$(this).parent().hide();
		
		//<6> 수정폼을 수정하고자하는 데이터가 있는 div에 노출
		$(this).parents('.item').append(modifyUI);

		//<7> 입력한 글자수 셋팅
		let inputLength = $('#mre_content').val().length;
		let remain = 300-inputLength;
		remain += '/300';
		
		//<8> 문서객체에 반영
		$('#mre_first .letter-count').text(remain);
	});
		



	//8. 수정form에서 취소버튼(.re-reset) 클릭시 수정form 초기화
	$(document).on('click','.re-reset',function(){
		initModifyForm();
	});


	//7. 댓글 수정form 초기화
	function initModifyForm(){
		$('.sub-item').show();
		$('#mre_form').remove();
	}
	
	//10. 댓글 수정
	$(document).on('submit','#mre_form',function(event){
	
		//<1> 내용이 입력되지 않았을 경우, 내용을 입력하라고 alert창 띄우기
		if($('#mre_content').val().trim()==''){
			alert('내용을 입력하세요!');
			//공백이 있다면 공백제거
			$('#mre_content').val('').focus();

			return false;
		}

		//<2> 수정 폼에 입력한 전체 데이터 반환
		let data = $(this).serialize();
		
		//<3> 데이터 전송
		$.ajax({
			url:'oqnaUpdateReply.do',
			type:'post',
			data:data,
			dataType:'json',
			cache:false,
			timeout:30000,
			success:function(param){
				if(param.result == 'logout'){
					alert('로그인하세요.');
				}else if(param.result =='notAdmin'){
					alert('관리자만 접근 가능합니다.');
				}else if(param.result =='success'){
					//수정 데이터 표시
					$('#mre_form').parent().find('p').html($('#mre_content').val().replace(/</g,'&lt;').replace(/>/g,'&gt;').replace(/\r\n/g,'<br>').replace(/\r/g,'<br>').replace(/\n/g,'<br>'));
					
					//최근 수정일 처리
					$('#mre_form').parent().find('.modify-date').text('최근 수정일 : 5초 미만');
					
					//수정폼 초기화
					initModifyForm();
					
				}else if(param.result == 'wrongAccess'){
					alert('타인의 댓글은 수정불가합니다.');
				}else{
					alert('댓글 수정시 오류발생!');
				}
			},
			error:function(){
				alert('네트워크 오류발생!');
			}
		});
		
		//<4>기본 이벤트 제거
		event.preventDefault();
	});
	
	//11.댓글 삭제
	$(document).on('click','.delete-btn',function(){
	
		//댓글 번호
		let re_num = $(this).attr('data-num');
		
		let choice = confirm('댓글을 삭제하시겠습니까?');
		if(choice){
			$.ajax({
				type:'post',
				url:'oqnaDeleteReply.do',
				data:{re_num:re_num},
				dataType:'json',
				cache:false,
				timeout:30000,
				success:function(param){
					if(param.result=='logout'){
						alert('로그인하세요.');
					}else if(param.result =='success'){
						alert('삭제 완료!');
						selectData(1,$('#qna_num').val());
					}else if(param.result=='notAdmin'){
						alert('관리자만 접근 가능합니다.');
					}else if(param.result=='wrongAccess'){
						alert('타인의 댓글은 삭제불가합니다.');
					}else{
						alert('댓글 삭제시 오류발생!');
					}	
				},
				error:function(){
					alert('네트워크 오류발생!');
				}
			})};
	});
	
	//1. 초기데이터(목록) 호출
	selectData(1, $('#qna_num').val());	//form에 qna_num이 hidden값으로 들어가있음
	
});










