package com.xjtu.myday16.web.upload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
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
import com.xjtu.myday16.web.util.JdbcUtil;
import com.xjtu.myday16.web.util.UploadUtil;

import sun.awt.image.BytePackedRaster;
import sun.nio.cs.ext.ISCII91;
import sun.security.util.ByteArrayTagOrder;

/**
 * 上传文件使用FileUpload组件，DB版
 */
public class UploadServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	try {
		String uploadPath = this.getServletContext().getRealPath(UploadUtil.uploadPath);
		List<Up> upList = new ArrayList<>();
		User user = UploadUtil.doUpload(request,upList);
		    //事务开始
			JdbcUtil.begin();
		     //写入表
			 UpService upService = new UpService();
		     upService.addUp(upList);
		     //int  i = 5/0;
		     //写入硬盘
		     UploadUtil.doSave(user,uploadPath,upList);
		        //事务提交
				JdbcUtil.commit();
			 request.setAttribute("message", "上传文件成功");
			 request.getRequestDispatcher("/WEB-INF/message.jsp").forward(request, response);
		 	
		 	
	}catch (NoUpfileException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		  request.setAttribute("message", "无上传文件");
		   request.getRequestDispatcher("/WEB-INF/message.jsp").forward(request, response);
	}catch (UpfileTypeException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		  request.setAttribute("message", "只能上传JPG格式");
		   request.getRequestDispatcher("/WEB-INF/message.jsp").forward(request, response);
	} catch (UpfileSizeException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		  request.setAttribute("message", "只能上传大小为500K的文件");
		   request.getRequestDispatcher("/WEB-INF/message.jsp").forward(request, response);
	}   catch (Exception e) {
		// TODO Auto-generated catch block
		
		
	  	try {
	  	    //事务回滚
			JdbcUtil.rollback();
			//事务提交
			JdbcUtil.commit();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		e.printStackTrace();
		  request.setAttribute("message", "上传文件失败");
	
		  request.getRequestDispatcher("/WEB-INF/message.jsp").forward(request, response);
	}finally{
		try {
			//关闭Connection对象，并分离线程
			JdbcUtil.closeConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	}
	
	
}
