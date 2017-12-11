package com.jdbc.DAOimpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.jdbc.DAO.FeatureVecDAO;
import com.jdbc.bean.FeatureVec;
import com.jdbc.tool.DBConnector;
/*
 * author:Frank Liu
 * Date:16/11/26
 */
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class FeatureVecDAOImpl implements FeatureVecDAO {
		
	@Override
	public int insertAll(ArrayList<FeatureVec> featureVecList) throws SQLException{
		//加载JDBC
		Connection conn=DBConnector.getConnection();
		PreparedStatement pstmt=null;
		//开始执行主要功能
		int count=0;
		for(FeatureVec temp_vec: featureVecList){
			
			//获得当前Vec的id
			String tid=temp_vec.getTid();
			if(tid==null||"".equals(tid)){
				System.out.println("com.jdbc.DAOImpl:warning:出现KnowledgeTagVec的id为空");
				break;
			}
			String featureVec=temp_vec.getfeatureVec();
			if(featureVec==null||"".equals(featureVec)){
				System.out.println("com.jdbc.DAOImpl:warning:出现KnowledgeTagVec的id为空");
				break;
			}
			
			String sql="insert into wiki_temp.featureVec (tid,featurevec) values(?,?);";
			
			try {
				pstmt=(PreparedStatement)conn.prepareStatement(sql);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				pstmt.setString(1, tid);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				pstmt.setString(2, featureVec);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				int flag=pstmt.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			count+=1;
		}
		
		
		pstmt.close();
		conn.close();
		return count;
	}
	
	
	
	@Override
	public FeatureVec findById(String tid) throws Exception{
		
		
		//long startTime=System.currentTimeMillis();
		
		//加载JDBC
		Connection conn=DBConnector.getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		FeatureVec resultVec=new FeatureVec();
		resultVec.setTid(tid);
		String sql="select featurevec from wiki_temp.featurevec where tid=\""+tid+"\";";
		pstmt=(PreparedStatement)conn.prepareStatement(sql);
		
		rs=pstmt.executeQuery();
		while(rs.next()){
			resultVec.setfeatureVec(rs.getString(1));
		}
		pstmt.close();
		conn.close();
		return resultVec;	
	}
	
	public ArrayList<FeatureVec> findByFeatureVec(String featureVec) throws Exception{
		//加载JDBC
		Connection conn=DBConnector.getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		//加载结果list
		ArrayList<FeatureVec> resultList= new ArrayList<FeatureVec>();
		
		String sql="select * from wiki_temp.featurevec where featurevec=\""+featureVec+"\";";
		pstmt=(PreparedStatement) conn.prepareStatement(sql);
		rs=pstmt.executeQuery();
		
		while(rs.next()){
			FeatureVec temp_featurevec=new FeatureVec();
			temp_featurevec.setTid(rs.getString(1));
			temp_featurevec.setfeatureVec(rs.getString(2));
			resultList.add(temp_featurevec);
		}
				
		pstmt.close();
		conn.close();
		return resultList;	
		
		
	}
	@Override
	public ArrayList<FeatureVec> findAllFeatureVec() throws Exception{
		//加载JDBC
		Connection conn=DBConnector.getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
				
		//加载结果list
		ArrayList<FeatureVec> resultList= new ArrayList<FeatureVec>();
				
		String sql="select * from wiki_temp.featurevec;";
		pstmt=(PreparedStatement) conn.prepareStatement(sql);
		rs=pstmt.executeQuery();
				
		while(rs.next()){
			FeatureVec temp_featurevec=new FeatureVec();
			temp_featurevec.setTid(rs.getString(1));
			temp_featurevec.setfeatureVec(rs.getString(2));
			resultList.add(temp_featurevec);
		}
		pstmt.close();
		conn.close();
		return resultList;	
				
	}
	
	@Override
	public ArrayList<FeatureVec> obscureFindByFeatureVec(String featureVec) throws Exception{
		//加载JDBC
		Connection conn=DBConnector.getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		//加载结果list
		ArrayList<FeatureVec> resultList= new ArrayList<FeatureVec>();
		
		String sql="select * from wiki_temp.featurevec where featurevec like \'"+featureVec+"\';";
		pstmt=(PreparedStatement) conn.prepareStatement(sql);
		rs=pstmt.executeQuery();
		
		while(rs.next()){
			FeatureVec temp_featurevec=new FeatureVec();
			temp_featurevec.setTid(rs.getString(1));
			temp_featurevec.setfeatureVec(rs.getString(2));
			resultList.add(temp_featurevec);
		}	
		pstmt.close();
		conn.close();
		return resultList;	
	}
	
	@Override
	public ArrayList<String> findAllIds() throws Exception{
		//加载JDBC
		Connection conn=DBConnector.getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
				
		//加载结果list
		ArrayList<String> resultList= new ArrayList<String>();
				
		String sql="select tid from wiki_temp.featurevec;";
		pstmt=(PreparedStatement) conn.prepareStatement(sql);
		rs=pstmt.executeQuery();
				
		while(rs.next()){
			resultList.add(rs.getString(1));
		}
		//测试
		System.out.println("com.jdbc.DAOimpl.FeatureVecDAOImpl测试：findAllIds结果：查找到"+resultList.size()+"条");				
		pstmt.close();
		conn.close();
		return resultList;	
			
	}
	
}
