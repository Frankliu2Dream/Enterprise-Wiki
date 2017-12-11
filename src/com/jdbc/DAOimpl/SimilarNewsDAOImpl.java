    package com.jdbc.DAOimpl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jdbc.DAO.SimilarNewsDAO;
import com.jdbc.bean.SimilarNews;
import com.jdbc.tool.DBConnector;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.tools.SortSimilarNews;
/*
 * author:Frank Liu
 * Date:16/11/26
 */

public class SimilarNewsDAOImpl implements SimilarNewsDAO{
	

	@Override
	public SimilarNews getCompanySimilarNewsById(String tid) throws Exception {
		Connection conn=DBConnector.getConnection();
		
		//从数据库中获取similarnews
		String sql="select * from wiki_crawl.companysimilarnews where tid="+tid+";";
		PreparedStatement pstmt=(PreparedStatement)conn.prepareStatement(sql);
		String temp_tid=null;
		String temp_message=null;//取得similarNews的字符串
		ResultSet rs=pstmt.executeQuery();
		
		//初始化返回对象
		SimilarNews similarNews=new SimilarNews();
		
		if(rs==null){
			System.out.println("com.jdbc.DAOImpl.SimilarNewsDAOImpl.java:warning:GetCompanySimilarNewsById方法rs返回为空");
			return null;
		}
		
		
		while(rs.next()){
			temp_tid=rs.getString(1);
			temp_message=rs.getString(2);
		}
		pstmt.close();
		conn.close();
		
		similarNews.setTid(temp_tid);
		//解析SimilarNews字符串,得到单个相似新闻的id的相似度
		String[] singleSimilarNewsStrings=temp_message.split(";");
		for(String temp_str:singleSimilarNewsStrings){
			String[] fields=temp_str.split(":");
			similarNews.getSimilarNewsMap().put(fields[0], Double.parseDouble(fields[1]));
		}
		return similarNews;
		
	}



	@Override
	public SimilarNews getGeneralSimilarNewsById(String tid) throws Exception {
		Connection conn=DBConnector.getConnection();
		
		//从数据库中获取similarnews
		String sql="select * from wiki_crawl.generalsimilarnews where tid="+tid+";";
		PreparedStatement pstmt=(PreparedStatement)conn.prepareStatement(sql);
		String temp_tid=null;
		String temp_message=null;//取得similarNews的字符串
		ResultSet rs=pstmt.executeQuery();
		
		//初始化返回对象
		SimilarNews similarNews=new SimilarNews();
		
		if(rs==null){
			System.out.println("com.jdbc.DAOImpl.SimilarNewsDAOImpl.java:warning:GetGeneralSimilarNewsById方法rs返回为空");
			return null;
		}
		
		
		while(rs.next()){
			temp_tid=rs.getString(1);
			temp_message=rs.getString(2);
		}
		pstmt.close();
		conn.close();
		
		similarNews.setTid(temp_tid);
		//解析SimilarNews字符串,得到单个相似新闻的id的相似度
		String[] singleSimilarNewsStrings=temp_message.split(";");
		for(String temp_str:singleSimilarNewsStrings){
			String[] fields=temp_str.split(":");
			similarNews.getSimilarNewsMap().put(fields[0], Double.parseDouble(fields[1]));
		}
		return similarNews;
		
	}



	@Override
	public ArrayList<String> getAllCompanySimilarNewsIds() throws Exception {
		// TODO Auto-generated method stub
		Connection conn=DBConnector.getConnection();
		//从数据库中获取similarnews
				
		ArrayList<String> resultList=new ArrayList<String>();
		String sql="select tid from wiki_crawl.companysimilarnews where 1=1;";
		PreparedStatement pstmt=(PreparedStatement)conn.prepareStatement(sql);
		ResultSet rs=pstmt.executeQuery();
		
		if(rs==null){
			System.out.println("com.jdbc.DAOImpl.SimilarNewsDAOImpl.java:warning:getAllCompanySimilarNewsIds方法rs返回为空");
			return null;
		}
		
		while(rs.next()){
			resultList.add(rs.getString(1));
		}
		pstmt.close();
		conn.close();
		return resultList;
	}



	@Override
	public ArrayList<String> getAllGeneralSimilarNewsIds() throws Exception {
		// TODO Auto-generated method stub
		Connection conn=DBConnector.getConnection();
		//从数据库中获取similarnews
				
		ArrayList<String> resultList=new ArrayList<String>();
		String sql="select tid from wiki_crawl.generalsimilarnews where 1=1;";
		PreparedStatement pstmt=(PreparedStatement)conn.prepareStatement(sql);
		ResultSet rs=pstmt.executeQuery();
		
		if(rs==null){
			System.out.println("com.jdbc.DAOImpl.SimilarNewsDAOImpl.java:warning:getGeneralSimilarNewsIds方法rs返回为空");
			return null;
		}
		
		while(rs.next()){
			resultList.add(rs.getString(1));
		}
		pstmt.close();
		conn.close();
		return resultList;
	}



	@Override
	public boolean insertCompanySimilarNews(SimilarNews similarNews) throws Exception {
		Connection conn=DBConnector.getConnection();
		String tid=similarNews.getTid();
		HashMap<String,Double> similarNewsMap= similarNews.getSimilarNewsMap();
		//将similarNewsMap属性进行排序，返回List...
		List<Map.Entry<String,Double>> similarNewsOrderedList= SortSimilarNews.sortSimilarNewsMap(similarNews);
		if(similarNewsOrderedList.size()>=100){
			//条数过多，删去100之后的
			List<Map.Entry<String,Double>> truncatedList=new  ArrayList<Map.Entry<String,Double>>();
			for(int i=0;i<100;i++){
				truncatedList.add(similarNewsOrderedList.get(i));
			}
			similarNewsOrderedList=truncatedList;
		}
		//构造StringBuffer，生成向数据库写入的字符串
		StringBuffer strBuffer=new StringBuffer();
		for(Map.Entry<String, Double> entry:similarNewsOrderedList){
			strBuffer.append(entry.getKey());
			strBuffer.append(":");
			strBuffer.append(entry.getValue().toString());
			strBuffer.append(";");
		}
		//转化为String,写入数据库
		String similarNewsString=strBuffer.toString();
		
		String sql="insert  into wiki_crawl.companysimilarnews (tid,similarnews) values(?,?); ";
		
		PreparedStatement pstmt= (PreparedStatement)conn.prepareStatement(sql);
		pstmt.setString(1, tid);
		pstmt.setString(2, similarNewsString);
		
		int flag=pstmt.executeUpdate();
		
		if(flag==0){
			System.out.println("com.jdbc.DAOImpl.SimilarNewsDAOImpl.java:warning:insertCompanySimilarNews方法insert失败，插入0条记录");
			pstmt.close();
			conn.close();
			return false;
		}
		else
		{
			pstmt.close();
			conn.close();
			return true;
		}
	}



	@Override
	public boolean insertGeneralSimilarNews(SimilarNews similarNews) throws Exception {
		Connection conn=DBConnector.getConnection();
		String tid=similarNews.getTid();
		HashMap<String,Double> similarNewsMap= similarNews.getSimilarNewsMap();
		//将similarNewsMap属性进行排序，返回List...
		List<Map.Entry<String,Double>> similarNewsOrderedList= SortSimilarNews.sortSimilarNewsMap(similarNews);
		if(similarNewsOrderedList.size()>=100){
			//条数过多，删去100之后的
			List<Map.Entry<String,Double>> truncatedList=new  ArrayList<Map.Entry<String,Double>>();
			for(int i=0;i<100;i++){
				truncatedList.add(similarNewsOrderedList.get(i));
			}
			similarNewsOrderedList=truncatedList;
		}
		//构造StringBuffer，生成向数据库写入的字符串
		StringBuffer strBuffer=new StringBuffer();
		for(Map.Entry<String, Double> entry:similarNewsOrderedList){
			strBuffer.append(entry.getKey());
			strBuffer.append(":");
			strBuffer.append(entry.getValue().toString());
			strBuffer.append(";");
		}
		//转化为String,写入数据库
		String similarNewsString=strBuffer.toString();
		
		String sql="insert  into wiki_crawl.generalsimilarnews (tid,similarnews) values(?,?); ";
		
		PreparedStatement pstmt= (PreparedStatement)conn.prepareStatement(sql);
		pstmt.setString(1, tid);
		pstmt.setString(2, similarNewsString);
		
		int flag=pstmt.executeUpdate();
		
		if(flag==0){
			System.out.println("com.jdbc.DAOImpl.SimilarNewsDAOImpl.java:warning:insertGeneralSimilarNews方法insert失败，插入0条记录");
			pstmt.close();
			conn.close();
			return false;
		}
		else
		{
			pstmt.close();
			conn.close();
			return true;
		}
	}}

