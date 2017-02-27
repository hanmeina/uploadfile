<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<script type="text/javascript">
  	var time=0;
  	function addFile(addButton){
  		
  		//创建一个内部div
    var   innerDiv = document.createElement("div");
  	
    var   inputFile = document.createElement("input");	
    
     
      	inputFile.type="file";
       
  		inputFile.name="上传";
  	
  	  var   inputButton = document.createElement("input");	
  	   
      	 inputButton.type="button";
   		
  	     inputButton.value="删除";
  	     
  	   inputButton.onclick=function(){
 	    	//取得该按钮所在行的直接父元素
 	    	//alert("aa");
 	      var 	divElement = this.parentNode.parentNode;
 	       //通过父元素删除直接子元素
			divElement.removeChild(this.parentNode);
 	       time--;
 	       if(time < 5){
				//按钮生效
				addButton.disabled=false;
				//addButton.style.visibility="visible";
			}
 	     };
 	       
  	     
     	 
  	innerDiv.appendChild(inputFile);

 	innerDiv.appendChild(inputButton);

  	var outDiv = document.getElementById("outDiv");
   
 	outDiv.appendChild(innerDiv);
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
  	    enctype="multipart/form-data"
  		>
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
                   	   <!-- 
                   	     <div>
                   	       <input type="file" name="上传"/>
                   	       <input type="button" value="删除"/>
                   	     </div> 
                   	     -->
                   	
                   	</div>					
  				</td>
  			</tr>
  			
  			<tr>
  				<td colspan="2" align="center" id="addButton">
  					<input 
  					        type="button" 
  					        value="添加上传文件"
  					        onclick="addFile(this)"/>
  					
  				</td>
  			</tr>
  			<tr>
  				<td colspan="2" align="center">
  					<input 
  					        type="submit" 
  					        value="上传"
  					       /> 					
  				</td>
  			</tr>
  			
  		</table>
  	</form>
  </body>
</html>
