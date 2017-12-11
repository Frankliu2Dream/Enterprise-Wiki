package com.jdbc.tool;

import java.sql.DriverManager;

import com.mysql.jdbc.Connection;

/*
 * author:Frank Liu
 * Date:16/11/26
 */

public class DBConnector {
	public static Connection getConnection(){
		Connection conn=null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
		
			conn=(Connection)DriverManager.getConnection("jdbc:mysql://127.0.0.1/wiki_crawl?user=root&password=1@375%62&useUnicode=true&characterEncoding=utf-8");
		}
		catch(Exception e){
			System.out.println("jdbc:加载Connection失败");
			e.printStackTrace();
		}
		
		return conn;
	}
	
	
}
