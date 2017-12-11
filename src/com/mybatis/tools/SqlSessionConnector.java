package com.mybatis.tools;

import java.io.Reader;
/*	author:Frank Liu
 * 	Date:16/11/26
 *  简介：SqlSessionFetcher为工具类，为调用mybatis的mapper提供初始化sqlsession
 *  使用静态方法fetchSqlSession可以获得加载完成的SqlSession，结合statement即可对mybatis的mapper进行调用
 */
public class SqlSessionConnector {
	
	public static SqlSession getSqlSession() throws Exception{
		SqlSession session=null;
		
		try{
			//加载资源文件conf.xml,con.xml为mybtis的配置文档
			String resource = "conf.xml";
			Reader reader=Resources.getResourceAsReader("conf.xml");
		
			//获得SqlSessionFactory,从而取得SqlSession
			SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
			session = sessionFactory.openSession();
		}
		catch(Exception e){
			System.out.println("com.mybaits.SqlSessionConnector:Error:加载sqlsession失败");
			e.printStackTrace();
		}
    	return session;
	}
}
