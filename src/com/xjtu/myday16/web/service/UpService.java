package com.xjtu.myday16.web.service;

import java.sql.SQLException;
import java.util.List;

import com.xjtu.myday16.web.dao.UpDao;
import com.xjtu.myday16.web.domin.Up;

public class UpService {
	UpDao upDao = new UpDao();
	
	
  public void addUp(List<Up> upList) throws Exception{
	
	  for( Up up:upList){
		  try {
			upDao.addUp(up);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception();
		}
	  }
	  
  }
  public List<Up>  selectUp() throws Exception{
	  try {
		return upDao.selectUp();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		throw new Exception();
	}
  }
  
   /**
	  * 根据uuidFileName 查询up对象
	  * @param uuidFileName
	  * @return
	  * @throws Exception
	  */
	 public Up selectUpByUuidFileName(String uuidFileName) throws Exception {
	   try {
		return upDao.selectUpByUuidFileName(uuidFileName);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		throw new Exception();
	}
	 }
	 
}
