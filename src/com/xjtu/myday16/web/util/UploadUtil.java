package com.xjtu.myday16.web.util;

import java.io.File;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


import com.xjtu.myday16.web.domin.Up;
import com.xjtu.myday16.web.domin.User;
import com.xjtu.myday16.web.exception.NoUpfileException;
import com.xjtu.myday16.web.exception.UpfileSizeException;
import com.xjtu.myday16.web.exception.UpfileTypeException;

public class UploadUtil {
//临时目录	
public static final	String tempPath = "/WEB-INF/temp";
//上传目录
public static final String uploadPath = "/WEB-INF/upload";	


   


/**
 * 取得真实文件名
 * @param realFileName
 * @return
 */
public static String getRealFileName(String realFileName){
	  //截取
	int index = realFileName.lastIndexOf("\\");
	if(index>0){//IE6
		realFileName = realFileName.substring(index+1);
		
	}	
	return realFileName;
}


/**
 * 文件复制
 * @param in
 * @param uuidFilePath
 * @param uuidFileName
 */
public static void doSave(InputStream in,String uuidFilePath,String uuidFileName) {	
	OutputStream os = null;
	try {
		os = new FileOutputStream(uuidFilePath+"/"+uuidFileName);
		byte[] buf = new byte[1024];
		int len = 0;
		while( (len=in.read(buf))>0 ){
			os.write(buf,0,len);
		}
	} catch (Exception e) {
		e.printStackTrace();
	}finally{
		if(in!=null){
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(os!=null){
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	} 
}

/**
 * 将同一个目录下分散成多个子目录
 * @param uploadPath
 * @param uuidFileName
 * @return
 */
public static String UuidFilePath(String uploadPath, String uuidFileName) {
	// TODO Auto-generated method stub
	System.out.println("uploadPath:"+uploadPath);
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

public static  String UuidFileName(String realFileName) {
	// TODO Auto-generated method stub
	return UUID.randomUUID().toString()+"_"+realFileName;
}



//将上传文件封装成JavaBean对象中
	public static User doUpload(HttpServletRequest request,List<Up> upList) throws Exception{
	
       User user = new User();
	    //创建上传文件工厂
		DiskFileItemFactory  factory = new DiskFileItemFactory();
		
	     //设置内存中缓冲区大小，默认是10K
		factory.setSizeThreshold(100*1024);//100k
		//设置上传文件临时存的目录
	   String tempPath = request.getServletContext().getRealPath(UploadUtil.tempPath);
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
			
			for(FileItem fileItem:FileItemList){
				//判断是不是普通字段
				if(fileItem.isFormField()){
					//是普通字段
					
					String fieldName = fileItem.getFieldName();
					String fieldValue = fileItem.getString("UTF-8");
					user.setUsername(fieldValue);
					//System.out.println("fieldValue:"+user.getUsername());
				}else{
					//是上传字段
			          Up up = new Up();
			         if(fileItem.getSize() == 0){
			        	throw  new NoUpfileException();
			         }
			         
			     	String realFileName = UploadUtil.getRealFileName(fileItem.getName());
			     	//只能上传JPG文件
			    	/*	if(!realFileName.endsWith("JPG")){
						throw new UpfileTypeException();
					}*/
					
			     	//只有上传<=200K的文件
					/*if(fileItem.getSize() > 500 * 1024){
						throw new UpfileSizeException();
					}*/
					
			        user.getUpfileList().add(fileItem);
			         up.setUsername(user.getUsername());
			         up.setRealFileName(realFileName);
			         String uuidFileName = UploadUtil.UuidFileName(realFileName);
			         up.setUuidFileName(uuidFileName);
			         upList.add(up);
			          
				}
			}
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
			
			
		     }
		
		
		}
		return user;
	}


	public static void doSave(User user, String uploadPath,List<Up> upList) throws IOException {
		// TODO Auto-generated method stub
		
		//取得用户上传的文件
	   List<FileItem>  fileItemList  =  user.getUpfileList();
	    
		//FileItem  fileItem = user.getUpfile();
	   int  index = 0;
	   for(FileItem fileItem : fileItemList){

		 
		 //取得真实文件名
			String realFileName = fileItem.getName();
			
			realFileName = UploadUtil.getRealFileName(realFileName);
					
			//取得uuid文件名
			//String uuidFileName = UploadUtil.UuidFileName(realFileName);
			String uuidFileName = upList.get(index).getUuidFileName();
	        //取得uuid文件路径
			String uuidFilePath = UploadUtil.UuidFilePath(uploadPath,uuidFileName);
			//取得输入流
	        InputStream in= fileItem.getInputStream();
	       //保存
	       UploadUtil.doSave(in, uuidFilePath, uuidFileName);
	      
	       System.out.println("uuidFilePath:uuidFileName"+uuidFilePath+":"+uuidFileName);      
	       //删除临时文件，一定要在关闭流之后
	       fileItem.delete();
	       index++;
	   }
		
       
	}	
		
		
	
	}
