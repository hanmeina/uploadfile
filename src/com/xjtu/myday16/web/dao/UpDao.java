package com.xjtu.myday16.web.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.xjtu.myday16.web.domin.Up;
import com.xjtu.myday16.web.util.JdbcUtil;

public class UpDao {
     /**
      * 添加一条上传文件
      * @param up
      * @throws SQLException
      */
	 public void addUp(Up up) throws SQLException{
		 QueryRunner queryRunner = new QueryRunner();
		 String sql = "insert into up(username,realFileName,uuidFileName) values(?,?,?)";
		 queryRunner.update(
				 JdbcUtil.getMySqlConnection(),
				 sql,
				 new Object[]{up.getUsername()
						 , up.getRealFileName()
						 , up.getUuidFileName()});
		 
	 }
	 /**
	  * 查询所有的上传文件
	  * @return
	  * @throws SQLException
	  */
	 public List<Up> selectUp() throws SQLException{
		 QueryRunner queryRunner = new QueryRunner(JdbcUtil.getDataSource());
		 String sql = "select * from up";	
		  List<Up> upList =  (List<Up>) queryRunner.query(sql, new BeanListHandler(Up.class));
		 return upList;
	 }
	 /**
	  * 根据uuidFileName 查询up对象
	  * @param uuidFileName
	  * @return
	  * @throws SQLException
	  */
	 public Up selectUpByUuidFileName(String uuidFileName) throws SQLException{
		 Up up = null;
		 QueryRunner queryRunner = new QueryRunner(JdbcUtil.getDataSource());
		 String sql = "select * from up where uuidFileName = ?";
		 up = (Up) queryRunner.query(sql, uuidFileName, new BeanHandler(Up.class));
		 return up;
	 }
}
