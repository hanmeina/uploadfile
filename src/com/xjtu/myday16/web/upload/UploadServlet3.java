package com.xjtu.myday16.web.upload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.sun.xml.internal.messaging.saaj.packaging.mime.util.OutputUtil;
import com.xjtu.myday16.web.util.UploadUtil;

import sun.awt.image.BytePackedRaster;
import sun.nio.cs.ext.ISCII91;
import sun.security.util.ByteArrayTagOrder;

/**
 * 上传文件使用FileUpload组件
 */
public class UploadServlet3 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	   //创建上传文件工厂
		DiskFileItemFactory  factory = new DiskFileItemFactory();
		
	     //设置内存中缓冲区大小，默认是10K
		factory.setSizeThreshold(100*1024);//100k
		//设置上传文件临时存的目录
	   String tempPath = this.getServletContext().getRealPath(UploadUtil.tempPath);
		factory.setRepository(new File(tempPath));	
		//创建上传文件对象
		ServletFileUpload upload = new ServletFileUpload(factory);
		//设置上传文件的中文编码
		upload.setHeaderEncoding("UTF-8");
		//看客户端上传文件是否以MIME协议
		Boolean flag  = upload.isMultipartContent(request);
		if(!flag){
			//不是以MIME协议上传
		
			throw new ServletException();
			
		}else {
			//是以MIME协议上传,解析request中所有的上传内容，将每个内容封装成对象FileItem
		try {
			List<FileItem> FileItemList  =	upload.parseRequest(request);
			for(FileItem file:FileItemList){
				//判断是不是普通字段
				if(file.isFormField()){
					//是普通字段
					String fieldName = file.getFieldName();
					String fieldValue = file.getString("UTF-8");
					//System.out.println("fieldName:"+fieldName+"fieldValue:"+fieldValue);
				}else{
					//是上传字段
				String realFileName = file.getName();
				
				
				realFileName = UploadUtil.getRealFileName(realFileName);
						
				//对realFileName进行拼接，为了解决同一个目录中相同文件的问题，UUID
				String uuidFileName = UploadUtil.UuidFileName(realFileName);
				//System.out.println(uuidFileName);
				
				 String uploadPath = this.getServletContext().getRealPath(UploadUtil.uploadPath);
				String uuidFilePath = UploadUtil.UuidFilePath(uploadPath,uuidFileName);
				//输入流
		       InputStream in= file.getInputStream();
		       //输出流
		       UploadUtil.doSave(in, uuidFilePath, uuidFileName);
		      		System.out.println(uuidFilePath+":"+uuidFileName);      
		       //删除临时文件，一定要在关闭流之后
		     //  file.delete();
		       request.setAttribute("message", "上传文件成功");
			   request.getRequestDispatcher("/WEB-INF/message.jsp").forward(request, response);
				}
			}
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("message", "上传文件失败");
			request.getRequestDispatcher("/WEB-INF/message.jsp").forward(request, response);
		}
			
			
		}
	}
	
	
}
