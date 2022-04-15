<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/style_dw.css" type="text/css"/>
<!-- 중앙 컨텐츠 시작 -->

<!-- 부트스트랩 라이브러리 -->
<link href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css.bootstrap.min.css">
<!-- jquery 라이브러리 -->
<script type="text/javascript" src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script type="text/javascript" src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

<style>
.ck-editor__editable_inline{
	min-height:450px;
}
</style>

<!-- ckeditor 라이브러리 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/ckeditor.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/uploadAdapter.js"></script>


<script src="${pageContext.request.contextPath}/resources/js/jquery-3.6.0.min.js"></script>
<div class="page-main" id="qnaWrite">
	<h2>글수정</h2>
	<form:form modelAttribute="oqnaVO" action="oqnaUpdate.do" id="update_form" enctype="multipart/form-data">
	<form:hidden path="qna_num"/>
	<form:errors element="div" cssClass="error-color"/>
	<ul class="editor-form">
		<li>
			<form:label path="title"><b>제목</b></form:label>
			<form:input class="title" path="title"/>
			<form:errors path="title" cssClass="error-color"/>
		</li>
		<li><b>내용</b></li>
		<li>
			<form:textarea path="content"/>
			<form:errors path="content" cssClass="error-color"/>
			<script>
				 function MyCustomUploadAdapterPlugin(editor) {
					    editor.plugins.get('FileRepository').createUploadAdapter = (loader) => {
					        return new UploadAdapter(loader);
					    }
					}
				 
				 ClassicEditor
		            .create( document.querySelector( '#content' ),{
		            	extraPlugins: [MyCustomUploadAdapterPlugin]
		            })
		            .then( editor => {
						window.editor = editor;
					} )
		            .catch( error => {
		                console.error( error );
		            } );
			    </script>      
		</li>
		<li>
			<form:label path="upload"><b>파일업로드</b></form:label>
			<input type="file" name="upload" id="upload" >
			
				<c:if test="${!empty qnaVO.filename }">
				<br>
				<span id="file_detail">(${qnaVO.filename })파일이 등록되어있습니다.
				다시 업로드하면 기존파일은 삭제됩니다.
				<input type="button" value="파일 삭제" id="file_del">
				</span>
					<script type="text/javascript">
						$(function(){
							$('#file_del').click(function(){
								let choice = confirm('삭제하시겠습니까?');
								if(choice){
									$.ajax({
										url:'deleteFile.do',
										data:{qna_num:${oqnaVO.qna_num}},
										type:'post',
										dataType:'json',
										cache:false,
										timeout:30000,
										success:function(param){
											if(param.result == 'logout'){
												alert('로그인 후 사용하세요');
											}else if(param.result =='success'){
												$('#file_detail').hide();
											}else{
												alert('파일삭제 오류 발생');
											}
										},
										error:function(){
											alert('네트워크 오류발생');
										}
									});
								}
							});
						});
					</script>		
				</c:if>
		</li>
	</ul>
	<div class="align-center">
		<input type="button" value="목록" onclick="location.href='oqnaList.do'" class="btn-black">
		<form:button class="btn-black">수정</form:button>
		<input type="button" value="취소" onclick="location.href='oqnaDetail.do?qna_num=${oqnaVO.qna_num}'" class="btn-black">
		
	</div>	
	<br>
	</form:form>
</div>
<!-- 중앙 컨텐츠 끝 -->