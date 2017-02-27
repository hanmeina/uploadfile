package com.xjtu.myday16.web.download;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Field;
import com.xjtu.myday16.web.dao.UpDao;
import com.xjtu.myday16.web.util.UploadUtil;



/**
 * 显示下载列表
 */
public class ListFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	UpDao upDao = new UpDao();
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    //Map<UuidFileName,真实文件名>集合
		Map<String,String> map =  new HashMap<>();
		String uploadPath = this.getServletContext().getRealPath(UploadUtil.uploadPath);
		getFiles(uploadPath,map);
		request.setAttribute("map", map);
		request.getRequestDispatcher("/WEB-INF/list.jsp").forward(request, response);
	}
 
	
	/**
    * 递归查询所有可供下载的文件
    * @param uploadPath
    * @param map
    */
		private void getFiles(String uploadPath, Map<String, String> map) {
		// TODO Auto-generated method stub
	File file = new File(uploadPath);
    //如果file是文件
	if(file.isFile()){
		//取得文件名，UUID文件名
		String uuidFileName = file.getName();
		int index = uuidFileName.lastIndexOf("_");
		//取得真实文件名
		String realFileName = uuidFileName.substring(index+1);
		
		map.put(uuidFileName,realFileName );
	}else{
		//必定是目录
		//取得该目录下的所有内容
    File[] files = file.listFiles();
    for(File f: files){
    	//递归调用自己
	    getFiles(f.getPath(), map);
      }
	}
	}     
 
}
