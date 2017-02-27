package com.xjtu.myday16.web.util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

//JDBC�����ࣺ�ر�����ȡ������(ʹ��c3p0)
public final class JdbcUtil {
	
    private static ComboPooledDataSource dataSource = null;
	
	
	//��̬�飺ע������
	static{
		dataSource =  new ComboPooledDataSource();
	}
	//ȡ������
		public static ComboPooledDataSource  getDataSource() {		
			return dataSource ;
		}
	//ȡ������
	public static Connection getMySqlConnection() throws SQLException{
	
	   Connection conn = dataSource.getConnection();
		
		return conn;
	}
	//�ر�����	
	
	public static void close(Connection conn){
		if(conn!=null){
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void close(PreparedStatement psmt) {
		// TODO Auto-generated method stub
		if(psmt!=null){
			try {
				psmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void close(ResultSet rs) {
		// TODO Auto-generated method stub
		if(rs!=null){
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	 
	
}
