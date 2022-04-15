function handleButtonOnclick() {
	document.getElementById('btnAtt').click();
}
let sel_files = [];
let attZone = document.getElementById('swiper-wrapper');
let btnAtt = document.getElementById('btnAtt');

function imageView(att_zone, btn){
  
	document.addEventListener(btnAtt, function(e) {
	  if (e.target.id === 'c_region') {
	     //
	  }
	});
  btnAtt.onchange = function(e){
    var files = e.target.files;
    var fileArr = Array.prototype.slice.call(files)
    for(f of fileArr){
      imageLoader(f);
    }
  }   
  
  //첨부된 이미리즐을 배열에 넣고 미리보기 
  imageLoader = function(file){
    sel_files.push(file);
    var reader = new FileReader();
    reader.onload = function(ee){
      let img = document.createElement('img');
      img.setAttribute('class', 'file_img_style');
      img.src = ee.target.result;
      attZone.appendChild(makeDiv(img, file));
    }
    
    reader.readAsDataURL(file);
  }
  
  //첨부된 파일이 있는 경우 checkbox와 함께 attZone에 추가할 div를 만들어 반환
  makeDiv = function(img, file){
    var div = document.createElement('div')
    div.setAttribute('class', 'file_div')
    
    let btn = document.createElement('input')
    btn.setAttribute('type', 'button')
    btn.setAttribute('value', 'X')
    btn.setAttribute('delFile', file.name);
    btn.setAttribute('class', 'chk_style');
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
imageView(attZone, btnAtt);
