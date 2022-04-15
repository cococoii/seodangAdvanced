<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!-- 중앙 컨텐츠 시작 -->
<div class="page-main">
   <h2>취업 뉴스</h2>
   <table>
      <c:forEach var="news" items="${list}">
      <tr>
         <td height="100">
         <h3>${news.title}</h3> (${news.pubDate})
         <p>
            ${news.description}
         </p>
         </td>
      </tr>
      </c:forEach>
   </table>
</div>