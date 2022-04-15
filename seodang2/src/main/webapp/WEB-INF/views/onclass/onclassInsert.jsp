<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!-- 중앙 컨텐츠 시작 -->
<!-- 부트스트랩 라이브러리 -->
<link href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script type="text/javascript" src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

<script src="${pageContext.request.contextPath}/resources/js/jquery-3.6.0.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/onclass2.css">
<style>
#att_zone {
  width: 660px;
  min-height: 150px;
  padding: 10px;
  border:1px solid #dde0e2; 
  float:right; 
  margin-top:25px;
}

#att_zone:empty:before {
  content: attr(data-placeholder);
  color: #999;
  font-size: .9em;
}
</style>

<!-- ckedior 라이브러리 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/ckeditor.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/uploadAdapter.js"></script>
<div class="page-main">
	<h2>글쓰기</h2>
	<form:form modelAttribute="onclassVO" action="onclassInsert.do" 
	                 enctype="multipart/form-data" id="open_form">
		<form:errors element="div" cssClass="error-color"/>
		<ul>
			<li>
				<form:label path="category_num"><b>01</b></form:label>
				<form:label path="category_num">CLASS 분류</form:label><br>
				<form:select class="on-form-input" path="category_num">
					<form:option value="1">드로잉</form:option>
					<form:option value="2">플라워</form:option>
					<form:option value="3">공예</form:option>
					<form:option value="4">요리</form:option>
					<form:option value="5">베이킹</form:option>
				</form:select>
				<form:errors path="category_num" cssClas="error-color"/>
				<hr size="1" noshade>
			</li>
			<li>
				<form:label path="on_name"><b>02</b></form:label>
				<form:label path="on_name">수업명</form:label><br>
				<form:input class="on-form-input" path="on_name"/>
				<form:errors path="on_name" cssClass="error-color"/>
				<hr size="1" noshade>
			</li>
			<li>
				<form:label path="on_price"><b>03</b></form:label>
				<form:label path="on_price">가격</form:label><br>
				<form:input class="on-form-input" type="number" path="on_price"/>
				<form:errors path="on_price" cssClass="error-color"/>
				<hr size="1" noshade>
			</li>
			<li><label for="on_content"><b>04</b></label></li>
			<li><label for="on_content">수업 상세</label><br></li>
			<li>
				<form:textarea path="on_content"/>
				<form:errors path="on_content" cssClass="error-color"/>
				<hr size="1" noshade>
				<script>
				 function MyCustomUploadAdapterPlugin(editor) {
					    editor.plugins.get('FileRepository').createUploadAdapter = (loader) => {
					        return new UploadAdapter(loader);
					    }
					} 
				 ClassicEditor
		            .create( document.querySelector( '#on_content' ),{
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
				<div id='image_preview'>
				<img src="../resources/image/choice.png" onclick='handleButtonOnclick()'>
			    <input type='file' id='btnAtt' name="uploadFile" multiple='multiple' style="display:none;"/>
			    <div id='att_zone'></div>
			  	</div>		
			</li>
		</ul>	 
		<div class="align-center">
			<form:button id="imageGO" onclick='fileNullCheck()'>전송</form:button>
		</div>                                          
	</form:form>
</div>

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