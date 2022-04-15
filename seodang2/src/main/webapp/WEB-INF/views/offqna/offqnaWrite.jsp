<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/style_dw.css" type="text/css"/>
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


<div class="page-main" id="qnaWrite">
	<h2>글쓰기</h2>										  <!-- 파일업로드 처리하기 위해  enctype -->
	<form:form modelAttribute="offqnaVO" action="offqnaWrite.do" enctype="multipart/form-data" id="register_form">
		<form:errors element="div" cssClass="error-color"/>
		<ul class="editor-form">
			<li>
				<form:label path="title"><b>제목</b></form:label>
				<form:input class="title" path="title" placeholder="제목은 1자이상 30자 이하로 입력하세요."/>
				<form:errors path="title" cssClass="error-color" style="font-size:15px"/>
			</li>

			<li><b>내용</b></li>
			<li>
				<form:textarea path="content"/>
				<form:errors path="content" cssClass="error-color" style="font-size:15px"/>
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
				<input type="file" name="upload" id="upload">
				<form:errors path="upload" cssClass="error-color"/>	
				<!-- 파일업로드는 필수항목은 아니다.(그래도 에러처리는 해봤음) -->
			</li>			
		</ul>
		<div class="align-center">
			<input type="button" value="목록" onclick="location.href='onqnaList.do'">
			<form:button>전송</form:button>
		</div>
	</form:form>
</div>
<!-- 중앙컨텐츠 끝 -->