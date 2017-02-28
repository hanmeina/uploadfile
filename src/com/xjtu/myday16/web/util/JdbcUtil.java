package com.xjtu.myday16.web.util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import sun.util.resources.cldr.es.TimeZoneNames_es_419;

//JDBC工具类：关闭流和取得连接(使用c3p0)
public final class JdbcUtil {
	
    private static ComboPooledDataSource dataSource = null;
    private static ThreadLocal<Connection> tl = new ThreadLocal<Connection>();
	
	//静态块：注册驱动
	static{
		dataSource =  new ComboPooledDataSource();
	}
	//取得连接
		public static ComboPooledDataSource  getDataSource() {	
			
			return dataSource ;
		}
	//取得连接
	public static Connection getMySqlConnection() throws SQLException{
		Connection conn = tl.get();
		if(conn == null){
			conn = dataSource.getConnection();
			tl.set(conn);
		}
	 
		
		return conn;
	}
	//关闭连接	
	
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
	
	public static void begin() throws SQLException {
		Connection conn = getMySqlConnection();
		conn.setAutoCommit(false);
	}
	public static void commit() throws SQLException {
		Connection conn = getMySqlConnection();
		conn.commit();
	}
	public static void rollback() throws SQLException {
		Connection conn = getMySqlConnection();
		conn.rollback();
	}
	public static void closeConnection() throws SQLException {
		Connection conn = getMySqlConnection();
		close(conn);
		tl.remove();
	}
	 
	
}
