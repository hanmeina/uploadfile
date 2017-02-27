<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<script type="text/javascript">
  		//全局变量
  		var time = 0;
  		function addLine(addButton){
  			//创建内部div对象
  			var divElement = document.createElement("div");
  			//创建input对象[file类型]
  			var inputElement1 = document.createElement("input");
  			inputElement1.type="file";
  			inputElement1.name="upfile";
  			//创建input对象[button类型]
  			var inputElement2 = document.createElement("input");
  			inputElement2.type="button";
  			inputElement2.value="删除";
  			//对删除按钮添加事件监听
  			inputElement2.onclick=function(){
  				//取得该按钮所在行的直接父元素
  				var divElement = this.parentNode.parentNode;
  				//通过父元素删除直接子元素
  				divElement.removeChild(this.parentNode);
  				time--;
  				if(time < 5){
  					//按钮生效
  					addButton.disabled=false;
  					//addButton.style.visibility="visible";
  				}
  			}
  			//依次将file类型和button类型的input对象加入到内部div对象中
  			divElement.appendChild(inputElement1);
  			divElement.appendChild(inputElement2);
  			//再次内部div对象加入到外部div对象
  			var outDivElement = document.getElementById("outDiv");
  			outDivElement.appendChild(divElement);
  			time++;
  			if(time == 5){
  				//将按钮失效
  				addButton.disabled=true;
  				//addButton.style.visibility="hidden";
  			}	
  		}
  	</script>
  </head>
  <body>
  	<form 
  		action="${pageContext.request.contextPath}/UploadServlet" 
  		method="POST"
  		enctype="multipart/form-data">
  		<table border="1" align="center">
  			<caption>文件上传</caption>
  			<tr>
  				<th>上传用户</th>
  				<td><input type="text" name="username"/></td>
  			</tr>
  			<tr>
  				<th></th>
  				<td>
  					<div id="outDiv">
  						<%--
  						<div>
							<input type="file" name="upfile"/>
							<input type="button" value="删除"/>  							
  						</div>
  						--%>
  					</div>
  				</td>
  			</tr>
  			<tr>
  				<th></th>
  				<td>
  					<input 
  						type="button" 
  						value="添加上传文件"
  						onclick="addLine(this)"
  					/>
  				</td>
  			</tr>
  			<tr>
  				<td colspan="2" align="center">
  					<input type="submit" value="上传"/>
  					<a href="${pageContext.request.contextPath}/ListFileServlet">
  						显示下载文件
  					</a>
  				</td>
  			</tr>
  		</table>
  	</form>
  </body>
</html>
