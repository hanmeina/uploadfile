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

import com.xjtu.myday16.web.domin.Up;
import com.xjtu.myday16.web.service.UpService;
import com.xjtu.myday16.web.util.UploadUtil;


/**
 * 文件下载功能,DB版
 */
public class DownloadServlet extends HttpServlet {
	UpService upService = new UpService();
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    //取得uuidFileName值
		String  uuidFileName = request.getParameter("uuidFileName");
		//byte[] buf = uuidFileName.getBytes("ISO8859-1");
		//uuidFileName = new String(buf,"UTF-8");
	   System.out.println("uuidFileName:"+uuidFileName);
		try { 
			   //根据UUID文件名查询Up对象
		    	 Up up = upService.selectUpByUuidFileName(uuidFileName);
		    	 System.out.println("up.getUUidFileName:" + up.getUuidFileName());
			     String realFileName = up.getRealFileName();
			    response.setHeader("content-disposition","attachment;filename="+URLEncoder.encode(realFileName,"UTF-8"));
			    String uploadPath = this.getServletContext().getRealPath(UploadUtil.uploadPath);
				String uuidFilePath = UploadUtil.UuidFilePath(uploadPath,uuidFileName);
				System.out.println("uuidFilePath:uuidFileName222"+uuidFilePath+":"+uuidFileName);      
				InputStream is = new FileInputStream(uuidFilePath+"/"+uuidFileName);
				//模式：/WEB-INF/upload/12/4/43213_cc.jpg
				//真正下载
				OutputStream os = response.getOutputStream(); 
				byte[] buf = new byte[1024];   
				int len = 0;     
				while((len=is.read(buf))>0){ 
					os.write(buf,0,len);
				}
				is.close();
				os.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("message","下载文件失败");
			request.getRequestDispatcher("/WEB-INF/message.jsp").forward(request,response);
		}
	   
	
	
	}
 
}
