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
 * ��ʾ�����б�
 */
public class ListFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	UpDao upDao = new UpDao();
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    //Map<UuidFileName,��ʵ�ļ���>����
		Map<String,String> map =  new HashMap<>();
		String uploadPath = this.getServletContext().getRealPath(UploadUtil.uploadPath);
		getFiles(uploadPath,map);
		request.setAttribute("map", map);
		request.getRequestDispatcher("/WEB-INF/list.jsp").forward(request, response);
	}
 
	
	/**
    * �ݹ��ѯ���пɹ����ص��ļ�
    * @param uploadPath
    * @param map
    */
		private void getFiles(String uploadPath, Map<String, String> map) {
		// TODO Auto-generated method stub
	File file = new File(uploadPath);
    //���file���ļ�
	if(file.isFile()){
		//ȡ���ļ�����UUID�ļ���
		String uuidFileName = file.getName();
		int index = uuidFileName.lastIndexOf("_");
		//ȡ����ʵ�ļ���
		String realFileName = uuidFileName.substring(index+1);
		
		map.put(uuidFileName,realFileName );
	}else{
		//�ض���Ŀ¼
		//ȡ�ø�Ŀ¼�µ���������
    File[] files = file.listFiles();
    for(File f: files){
    	//�ݹ�����Լ�
	    getFiles(f.getPath(), map);
      }
	}
	}     
 
}
