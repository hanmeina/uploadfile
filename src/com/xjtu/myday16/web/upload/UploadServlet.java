package com.xjtu.myday16.web.upload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
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
import com.xjtu.myday16.web.domin.Up;
import com.xjtu.myday16.web.domin.User;
import com.xjtu.myday16.web.exception.NoUpfileException;
import com.xjtu.myday16.web.exception.UpfileSizeException;
import com.xjtu.myday16.web.exception.UpfileTypeException;
import com.xjtu.myday16.web.service.UpService;
import com.xjtu.myday16.web.util.UploadUtil;

import sun.awt.image.BytePackedRaster;
import sun.nio.cs.ext.ISCII91;
import sun.security.util.ByteArrayTagOrder;

/**
 * �ϴ��ļ�ʹ��FileUpload���
 */
public class UploadServlet extends HttpServlet {
	UpService upService = new UpService();
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	try {
		User user = UploadUtil.doUpload(request);
	   
		    if(user!=null){
		    //System.out.println(user!=null);
		    String uploadPath = this.getServletContext().getRealPath(UploadUtil.uploadPath);
		    List<Up> upList = new ArrayList<>();		    
		     UploadUtil.doSave(user,uploadPath,upList);
		     upService.addUp(upList);
			 request.setAttribute("message", "�ϴ��ļ��ɹ�");
			 request.getRequestDispatcher("/WEB-INF/message.jsp").forward(request, response);
		 	
		 	}
	}catch (NoUpfileException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		  request.setAttribute("message", "���ϴ��ļ�");
		   request.getRequestDispatcher("/WEB-INF/message.jsp").forward(request, response);
	}catch (UpfileTypeException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		  request.setAttribute("message", "ֻ���ϴ�JPG��ʽ");
		   request.getRequestDispatcher("/WEB-INF/message.jsp").forward(request, response);
	} catch (UpfileSizeException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		  request.setAttribute("message", "ֻ���ϴ���СΪ500K���ļ�");
		   request.getRequestDispatcher("/WEB-INF/message.jsp").forward(request, response);
	}   catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		  request.setAttribute("message", "�ϴ��ļ�ʧ��");
		   request.getRequestDispatcher("/WEB-INF/message.jsp").forward(request, response);
	}   
	
	}
	
	
}
