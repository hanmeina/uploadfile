<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <body>
  	<table border="1" align="center">
  		<caption>下载文件列表</caption>
  		<tr>
  			<th>文件名</th>
  			<th>操作</th>
  		</tr>
  		<c:forEach var="entry" items="${requestScope.map}">
	  		<tr>
	  			<td>${entry.value}</td>	
	  			<td>
	  				<c:url var="myURL" value="/DownloadServlet">
	  					<c:param name="uuidFileName" value="${entry.key}"/>
	  				</c:url>
	  				<a  href="${myURL}" 
	  					style="text-decoration:none">
	  					下载
	  				</a>
	  			</td>
	  		</tr>
  		</c:forEach>
  	</table>
  </body>
</html>
