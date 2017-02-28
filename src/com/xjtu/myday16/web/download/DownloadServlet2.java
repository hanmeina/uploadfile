package com.xjtu.myday16.web.download;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xjtu.myday16.web.util.UploadUtil;


/**
 * 文件下载功能
 */
public class DownloadServlet2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    //取得uuidFileName值
		String  uuidFileName = request.getParameter("uuidFileName");
		//byte[] buf = uuidFileName.getBytes("ISO8859-1");
		// uuidFileName = new String(buf,"UTF-8");
	    int index = uuidFileName.lastIndexOf("_");
	    String realFileName = uuidFileName.substring(index+1);
	    response.setHeader("content-disposition","attachment;filename="+URLEncoder.encode(realFileName,"UTF-8"));
	    String uploadPath = this.getServletContext().getRealPath(UploadUtil.uploadPath);
		String uuidFilePath = UploadUtil.UuidFilePath(uploadPath,uuidFileName);
		System.out.println("uuidFilePath:uuidFileName222"+uuidFilePath+":"+uuidFileName);      
		InputStream is = new FileInputStream(uuidFilePath+"/"+uuidFileName);
		//模式：/WEB-INF/upload/12/4/43213_cc.jpg
		OutputStream os = response.getOutputStream(); 
		byte[] buf = new byte[1024];
		int len = 0;     
		while((len=is.read(buf))>0){ 
			os.write(buf,0,len);
		}
		is.close();
		os.close();
	
	
	}
 
}
