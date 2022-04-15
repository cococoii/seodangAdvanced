<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>    
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script src="${pageContext.request.contextPath}/resources/js/jquery-3.6.0.min.js"></script>
<script src="https://unpkg.com/swiper/swiper-bundle.min.js"></script>
<link rel="stylesheet" href="https://unpkg.com/swiper/swiper-bundle.min.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/offclass.css">
<!-- 메인 시작 -->
<div class="main">
	<div class="swiper">
	  <!-- Additional required wrapper -->
	  <div class="swiper-wrapper">
	    <!-- Slides -->
	    <div class="swiper-slide"><img src="${pageContext.request.contextPath}/resources/images/main2_3.png" width="100%"></div>
	    <div class="swiper-slide"><a href="${pageContext.request.contextPath}//onclass/onclassList.do"><img src="${pageContext.request.contextPath}/resources/images/main4.png" width="100%"></a></div>
	    <div class="swiper-slide"><img src="${pageContext.request.contextPath}/resources/images/main5.png" width="100%"></div>
	    <div class="swiper-slide"><img src="${pageContext.request.contextPath}/resources/images/main6.png" width="100%"></div>
	    ...
	  </div>
	  <!-- If we need pagination -->
	  <div class="swiper-pagination"></div>
	
	  <!-- If we need navigation buttons -->
	  <div class="swiper-button-prev"></div>
	  <div class="swiper-button-next"></div>
	
	  <!-- If we need scrollbar -->
	  <div class="swiper-scrollbar"></div>
	</div>
</div>
<script>
const swiper = new Swiper('.swiper', {
	  loop: true,

	  // If we need pagination
	  pagination: {
	    el: '.swiper-pagination',
	  },

	  // Navigation arrows
	  navigation: {
	    nextEl: '.swiper-button-next',
	    prevEl: '.swiper-button-prev',
	  },
	  autoplay: { 
		  delay: 7000, 
		},
	});
</script>