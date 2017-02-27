package com.xjtu.myday16.web.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.xjtu.myday16.web.domin.Up;
import com.xjtu.myday16.web.util.JdbcUtil;

public class UpDao {
     /**
      * ���һ���ϴ��ļ�
      * @param up
      * @throws SQLException
      */
	 public void addUp(Up up) throws SQLException{
		 QueryRunner queryRunner = new QueryRunner(JdbcUtil.getDataSource());
		 String sql = "insert into up(username,realFileName,uuidFileName) values(?,?,?)";
		 queryRunner.update(sql,
				 new Object[]{up.getUsername()
						 , up.getRealFileName()
						 , up.getUuidFileName()});
		 
	 }
	 /**
	  * ��ѯһ���ϴ��ļ�
	  * @return
	  * @throws SQLException
	  */
	 public Up selectUp() throws SQLException{
		 QueryRunner queryRunner = new QueryRunner(JdbcUtil.getDataSource());
		 String sql = "select * from up";	
		 Up up = (Up) queryRunner.query(sql, new BeanHandler(Up.class));
		 return up;
	 }
}
