package com.xjtu.myday16.web.download;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Field;
import com.xjtu.myday16.web.dao.UpDao;
import com.xjtu.myday16.web.domin.Up;
import com.xjtu.myday16.web.service.UpService;
import com.xjtu.myday16.web.util.UploadUtil;



/**
 * ��ʾ�����б� �����ݿ��
 */
public class ListFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	UpService  upService  = new UpService();
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    //Map<UuidFileName,��ʵ�ļ���>����
		List<Up> upList;
		try {
			upList = upService.selectUp();
			request.setAttribute("upList",upList);
			request.getRequestDispatcher("/WEB-INF/list.jsp").forward(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("message","��ѯ�ļ��б�ʧ��");
			request.getRequestDispatcher("/WEB-INF/message.jsp").forward(request, response);
		}		
	
	
	}
 
	
	
 
}
