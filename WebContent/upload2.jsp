<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	
  </head>
  <body>
  	<form 
  		action="${pageContext.request.contextPath}/UploadServlet" 
  		method="POST"
  	    enctype="multipart/form-data"
  		>
  		<table border="1" align="center">
  			<caption>文件上传</caption>
  			<tr>
  				<th>上传用户</th>
  				<td><input type="text" name="username"/></td>
  			</tr>
  			<tr>
  				<th>上传文件1</th>
  				<td>
                    <input type="file" name="upfile"/>						
  				</td>
  			</tr>
  			<tr>
  				<th>上传文件2</th>
  				<td>
                    <input type="file" name="upfile"/>						
  				</td>
  			</tr>
  			<tr>
  				<td colspan="2" align="center">
  					<input type="submit" value="上传"/>
  					
  				</td>
  			</tr>
  			
  		</table>
  	</form>
  </body>
</html>
