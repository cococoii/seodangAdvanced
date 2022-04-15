<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>    
<!-- 중앙 컨텐츠 시작 -->
<!-- 부트스트랩 라이브러리 -->
<link href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/resources/js/ckeditor_style.js"></script>

<script type="text/javascript" src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script type="text/javascript" src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/kit_2.css">
<style>
.ck-editor__editable_inline{
	min-height:250px;
}
</style>
<!-- ckedior 라이브러리 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/ckeditor.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/uploadAdapter.js"></script>
<div>
	<h2>Kit 등록하기</h2>
	<form:form modelAttribute="kitVO" action="kitWrite.do" enctype="multipart/form-data" id="kit_register_form">
		<form:errors element="div" cssClass="error-color"/>
		<div class="align-right">
			<form:button onclick='fileNullCheck()'>등록하기</form:button>
			<input type="button" value="목록" onclick="location.href='kitList.do'">
		</div> 
		
		<ul id="box4">
		<li></li>
		    <li id="name">
		        <form:label path="kit_name">1</form:label>
				<form:label path="kit_name" class="kit_name">키트이름</form:label>
				<form:input path="kit_name"/>
				<form:errors path="kit_name" cssClass="error-color"/>
			</li>
			<li><hr size=1px;></li>
			<li id="price">
			    <form:label path="kit_price">2 </form:label>
				<form:label path="kit_price">가격</form:label>
				<form:input type="number" path="kit_price" min="0"/>
				<form:errors path="kit_price" cssClass="error-color"/>
			</li>
			<li><hr size=1px;></li>
			<li id="quantity">
			    <form:label path="kit_quantity">3</form:label>
				<form:label path="kit_quantity">판매수량</form:label>
				<form:input type="number" path="kit_quantity" min="1"/>
				<form:errors path="kit_quantity" cssClass="error-color"/>
			</li>
			<li><hr size=1px;></li>
			<li id="content2">
		    	 <form:label path="kit_content2">4</form:label>
				<form:label path="kit_content2">키트설명</form:label>
				<form:input path="kit_content2"/>
				<form:errors path="kit_content2" cssClass="error-color"/>
			</li>
			<li><hr size=1px;></li>
			<li id="content">상세내용</li>
			<li>
				<form:textarea path="kit_content"/>
				<form:errors path="kit_content" cssClass="error-color"/>
				<script>
				 function MyCustomUploadAdapterPlugin(editor) {
					    editor.plugins.get('FileRepository').createUploadAdapter = (loader) => {
					        return new UploadAdapter(loader);
					    }
					}
				 
				 ClassicEditor
		            .create( document.querySelector( '#kit_content' ),{
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
			<li id="file">
				<form:label path="upload">대표사진</form:label>
				<input type="file" name="upload" id="upload">
				<form:errors path="upload" cssClass="error-color"/>
			</li>
			<!-- 다중이미지 업로드 시작 -->
			<li>
				<div id='image_preview'>
				<img src="../resources/image/choice.png" onclick='handleButtonOnclick()'>
			    <input type='file' id='btnAtt' name="uploadfilekit" multiple='multiple' style="display:none;"/>
			    <div id='att_zone'></div>
			  	</div>		
			</li>
			<!-- 다중이미지 업로드 끝 -->
		</ul>
		</form:form>		                                     
</div>
<!-- 중앙 컨텐츠 끝 -->
<!-- 다중 이미지 올리기 -->
<script>

function fileNullCheck(){
	var fileCK = document.getElementById("btnAtt").value;
	if(!fileCK){
	 alert("파일을 첨부하세요");
	 event.preventDefault();
	    return;
	}
}

$(document).ready(function() {
  $('#btnAtt').change(function() {
    if (this.files.length > 4){
      alert('최대 사진 4개 까지 가능합니다');   

      location.reload();
    }
  });  
});

$(document).ready(function() {
  $('#btnAtt').change(function() {
    if (this.files.length < 4){
      alert('4개 이미지 일괄 업로드');   

      location.reload();
    }
  });  
});

//파일선택 (css)
function handleButtonOnclick() {
		document.getElementById('btnAtt').click();
	
		console.log('클릭');
}

( /* att_zone : 이미지들이 들어갈 위치 id, btn : file tag id */
  imageView = function imageView(att_zone, btn){

    var attZone = document.getElementById(att_zone);
    var btnAtt = document.getElementById(btn)
    var sel_files = [];
    
    // 이미지와 체크 박스를 감싸고 있는 div 속성
    var div_style = 'display:inline-block;position:relative;'
                  + 'width:150px;height:120px;margin:5px;border:1px solid #fff;z-index:1';
    // 미리보기 이미지 속성
    var img_style = 'width:100%;height:100%;z-index:none';
    // 이미지안에 표시되는 체크박스의 속성
    var chk_style = 'width:30px;height:30px;position:absolute;font-size:24px;'
                  + 'right:0px;bottom:0px;z-index:999;background-color:rgba(255,255,255,0.1);color:#000';
  
    btnAtt.onchange = function(e){
      var files = e.target.files;
      var fileArr = Array.prototype.slice.call(files)
      for(f of fileArr){
        imageLoader(f);
      }
    }   
    
    /*첨부된 이미리즐을 배열에 넣고 미리보기 */
    imageLoader = function(file){
      sel_files.push(file);
      var reader = new FileReader();
      reader.onload = function(ee){
        let img = document.createElement('img')
        img.setAttribute('style', img_style)
        img.src = ee.target.result;
        attZone.appendChild(makeDiv(img, file));
      }
      
      reader.readAsDataURL(file);
    }
    
    /*첨부된 파일이 있는 경우 checkbox와 함께 attZone에 추가할 div를 만들어 반환 */
    makeDiv = function(img, file){
      var div = document.createElement('div')
      div.setAttribute('style', div_style)
      
      var btn = document.createElement('input')
      btn.setAttribute('type', 'button')
      btn.setAttribute('value', 'X')
      btn.setAttribute('delFile', file.name);
      btn.setAttribute('style', chk_style);
      btn.onclick = function(ev){
        var ele = ev.srcElement;
        var delFile = ele.getAttribute('delFile');
        for(var i=0 ;i<sel_files.length; i++){
          if(delFile== sel_files[i].name){
            sel_files.splice(i, 1);      
          }
        }
        
        dt = new DataTransfer();
        for(f in sel_files) {
          var file = sel_files[f];
          dt.items.add(file);
        }
        btnAtt.files = dt.files; 
        var p = ele.parentNode;
        attZone.removeChild(p)
      }
      div.appendChild(img) 
      div.appendChild(btn)
      return div
    }
  }
)('att_zone', 'btnAtt')

</script>


	              
