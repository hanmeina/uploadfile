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

import sun.awt.image.BytePackedRaster;
import sun.nio.cs.ext.ISCII91;
import sun.security.util.ByteArrayTagOrder;

/**
 * 上传文件使用FileUpload组件
 */
public class UploadServlet2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	String tempPath = this.getServletContext().getRealPath("/temp");
	String uploadPath = this.getServletContext().getRealPath("/upload");	
	   //创建上传文件工厂
		DiskFileItemFactory  factory = new DiskFileItemFactory();
		
	     //设置内存中缓冲区大小，默认是10K
		factory.setSizeThreshold(100*1024);//100k
		//设置上传文件临时存的目录
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
				  //截取
				int index = realFileName.lastIndexOf("\\");
				if(index>0){//IE6
					realFileName = realFileName.substring(index+1);
					
				}		
				//对realFileName进行拼接，为了解决同一个目录中相同文件的问题，UUID
				String uuidFileName = UuidFileName(realFileName);
				//System.out.println(uuidFileName);
				String uuidFilePath = UuidFilePath(uploadPath,uuidFileName);
				//输入流
		       InputStream in= file.getInputStream();
		       //输出流
		       OutputStream  os = new FileOutputStream(uuidFilePath+"/"+uuidFileName);
		      System.out.println(uuidFilePath+"/"+uuidFileName);
		       byte[] buf = new byte[1024];
		       int len=0;
		       while((len=in.read(buf))>0){
		    	   os.write(buf, 0, len);		    	   
		       }
		       in.close();
		       os.close(); 
		       //删除临时文件，一定要在关闭流之后
		       file.delete();
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
	
	/**
	 * 将同一个目录下分散成多个子目录
	 * @param uploadPath
	 * @param uuidFileName
	 * @return
	 */
	private String UuidFilePath(String uploadPath, String uuidFileName) {
		// TODO Auto-generated method stub
		String uuidFilePath = null;
		//取hashcode
		int hashcode  = uuidFileName.hashCode();//8
		int dir1 = hashcode & 0xF; // 8 & F
		int dir2 = (hashcode>>4) & 0xF;
		File file = new File(uploadPath+"/"+dir1+"/"+dir2);
		if(!file.exists()){
		   //该目录不存在，一次性创建N层目录
			file.mkdirs();
		}
		uuidFilePath = file.getPath();
		return uuidFilePath;
	}

	/**
	 * 对上传文件名进行拼接
	 * @param realFileName
	 * @return
	 */
	
	private String UuidFileName(String realFileName) {
		// TODO Auto-generated method stub
		return UUID.randomUUID().toString()+"_"+realFileName;
	}

}
