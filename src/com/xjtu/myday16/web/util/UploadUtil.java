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
//��ʱĿ¼	
public static final	String tempPath = "/WEB-INF/temp";
//�ϴ�Ŀ¼
public static final String uploadPath = "/WEB-INF/upload";	


   


/**
 * ȡ����ʵ�ļ���
 * @param realFileName
 * @return
 */
public static String getRealFileName(String realFileName){
	  //��ȡ
	int index = realFileName.lastIndexOf("\\");
	if(index>0){//IE6
		realFileName = realFileName.substring(index+1);
		
	}	
	return realFileName;
}


/**
 * �ļ�����
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
 * ��ͬһ��Ŀ¼�·�ɢ�ɶ����Ŀ¼
 * @param uploadPath
 * @param uuidFileName
 * @return
 */
public static String UuidFilePath(String uploadPath, String uuidFileName) {
	// TODO Auto-generated method stub
	System.out.println("uploadPath:"+uploadPath);
	String uuidFilePath = null;
	//ȡhashcode
	int hashcode  = uuidFileName.hashCode();//8
	int dir1 = hashcode & 0xF; // 8 & F
	int dir2 = (hashcode>>4) & 0xF;
	File file = new File(uploadPath+"/"+dir1+"/"+dir2);
	if(!file.exists()){
	   //��Ŀ¼�����ڣ�һ���Դ���N��Ŀ¼
		file.mkdirs();
	}
	uuidFilePath = file.getPath();
	return uuidFilePath;
}

/**
 * ���ϴ��ļ�������ƴ��
 * @param realFileName
 * @return
 */

public static  String UuidFileName(String realFileName) {
	// TODO Auto-generated method stub
	return UUID.randomUUID().toString()+"_"+realFileName;
}



//���ϴ��ļ���װ��JavaBean������
	public static User doUpload(HttpServletRequest request,List<Up> upList) throws Exception{
	
       User user = new User();
	    //�����ϴ��ļ�����
		DiskFileItemFactory  factory = new DiskFileItemFactory();
		
	     //�����ڴ��л�������С��Ĭ����10K
		factory.setSizeThreshold(100*1024);//100k
		//�����ϴ��ļ���ʱ���Ŀ¼
	   String tempPath = request.getServletContext().getRealPath(UploadUtil.tempPath);
		factory.setRepository(new File(tempPath));	
		//�����ϴ��ļ�����
		ServletFileUpload upload = new ServletFileUpload(factory);
		//�����ϴ��ļ������ı���
		upload.setHeaderEncoding("UTF-8");
		//���ͻ����ϴ��ļ��Ƿ���MIMEЭ��
		Boolean flag  = upload.isMultipartContent(request);
		if(!flag){
			//������MIMEЭ���ϴ�
		
			throw new ServletException();
			
		}else {
			//����MIMEЭ���ϴ�,����request�����е��ϴ����ݣ���ÿ�����ݷ�װ�ɶ���FileItem
		try {
			List<FileItem> FileItemList  =	upload.parseRequest(request);
			
			for(FileItem fileItem:FileItemList){
				//�ж��ǲ�����ͨ�ֶ�
				if(fileItem.isFormField()){
					//����ͨ�ֶ�
					
					String fieldName = fileItem.getFieldName();
					String fieldValue = fileItem.getString("UTF-8");
					user.setUsername(fieldValue);
					//System.out.println("fieldValue:"+user.getUsername());
				}else{
					//���ϴ��ֶ�
			          Up up = new Up();
			         if(fileItem.getSize() == 0){
			        	throw  new NoUpfileException();
			         }
			         
			     	String realFileName = UploadUtil.getRealFileName(fileItem.getName());
			     	//ֻ���ϴ�JPG�ļ�
			    	/*	if(!realFileName.endsWith("JPG")){
						throw new UpfileTypeException();
					}*/
					
			     	//ֻ���ϴ�<=200K���ļ�
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
		
		//ȡ���û��ϴ����ļ�
	   List<FileItem>  fileItemList  =  user.getUpfileList();
	    
		//FileItem  fileItem = user.getUpfile();
	   int  index = 0;
	   for(FileItem fileItem : fileItemList){

		 
		 //ȡ����ʵ�ļ���
			String realFileName = fileItem.getName();
			
			realFileName = UploadUtil.getRealFileName(realFileName);
					
			//ȡ��uuid�ļ���
			//String uuidFileName = UploadUtil.UuidFileName(realFileName);
			String uuidFileName = upList.get(index).getUuidFileName();
	        //ȡ��uuid�ļ�·��
			String uuidFilePath = UploadUtil.UuidFilePath(uploadPath,uuidFileName);
			//ȡ��������
	        InputStream in= fileItem.getInputStream();
	       //����
	       UploadUtil.doSave(in, uuidFilePath, uuidFileName);
	      
	       System.out.println("uuidFilePath:uuidFileName"+uuidFilePath+":"+uuidFileName);      
	       //ɾ����ʱ�ļ���һ��Ҫ�ڹر���֮��
	       fileItem.delete();
	       index++;
	   }
		
       
	}	
		
		
	
	}
