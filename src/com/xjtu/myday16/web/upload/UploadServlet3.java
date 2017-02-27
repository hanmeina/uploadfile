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
 * �ϴ��ļ�ʹ��FileUpload���
 */
public class UploadServlet3 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	   //�����ϴ��ļ�����
		DiskFileItemFactory  factory = new DiskFileItemFactory();
		
	     //�����ڴ��л�������С��Ĭ����10K
		factory.setSizeThreshold(100*1024);//100k
		//�����ϴ��ļ���ʱ���Ŀ¼
	   String tempPath = this.getServletContext().getRealPath(UploadUtil.tempPath);
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
			for(FileItem file:FileItemList){
				//�ж��ǲ�����ͨ�ֶ�
				if(file.isFormField()){
					//����ͨ�ֶ�
					String fieldName = file.getFieldName();
					String fieldValue = file.getString("UTF-8");
					//System.out.println("fieldName:"+fieldName+"fieldValue:"+fieldValue);
				}else{
					//���ϴ��ֶ�
				String realFileName = file.getName();
				
				
				realFileName = UploadUtil.getRealFileName(realFileName);
						
				//��realFileName����ƴ�ӣ�Ϊ�˽��ͬһ��Ŀ¼����ͬ�ļ������⣬UUID
				String uuidFileName = UploadUtil.UuidFileName(realFileName);
				//System.out.println(uuidFileName);
				
				 String uploadPath = this.getServletContext().getRealPath(UploadUtil.uploadPath);
				String uuidFilePath = UploadUtil.UuidFilePath(uploadPath,uuidFileName);
				//������
		       InputStream in= file.getInputStream();
		       //�����
		       UploadUtil.doSave(in, uuidFilePath, uuidFileName);
		      		System.out.println(uuidFilePath+":"+uuidFileName);      
		       //ɾ����ʱ�ļ���һ��Ҫ�ڹر���֮��
		     //  file.delete();
		       request.setAttribute("message", "�ϴ��ļ��ɹ�");
			   request.getRequestDispatcher("/WEB-INF/message.jsp").forward(request, response);
				}
			}
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("message", "�ϴ��ļ�ʧ��");
			request.getRequestDispatcher("/WEB-INF/message.jsp").forward(request, response);
		}
			
			
		}
	}
	
	
}
