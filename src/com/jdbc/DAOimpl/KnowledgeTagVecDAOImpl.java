package com.jdbc.DAOimpl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.jdbc.DAO.KnowledgeTagVecDAO;
import com.jdbc.tool.DBConnector;
import com.mybatis.bean.temp.KnowledgeTagVec;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.tools.InitTagsList;
/*
 * author:Frank Liu
 * Date:16/11/26
 */
public class KnowledgeTagVecDAOImpl implements KnowledgeTagVecDAO {
	
	@Override
	public ArrayList<KnowledgeTagVec> getAllKnowledgeVecs() throws Exception{
		//初始化TagList
		List<String> tagList=InitTagsList.getTagList();
    	HashMap<String, List<String>> keyMap=InitTagsList.getKeyMap(tagList);
    	
		Connection  conn=DBConnector.getConnection();
		String sql="select * from wiki_crawl.knowledgetagvec;";
		PreparedStatement pstmt=(PreparedStatement) conn.prepareStatement(sql);
		ResultSet rs=pstmt.executeQuery();
		ArrayList<KnowledgeTagVec> resultList= new ArrayList<KnowledgeTagVec>();
		while(rs.next()){
			KnowledgeTagVec temp_vec= new KnowledgeTagVec(null, null);
			temp_vec.setTid(rs.getString(1));
			double[] tags=new double[70];
			for(int i=0;i<tagList.size();i++){
				tags[i]=rs.getDouble(i+2);
			}
			temp_vec.setTags(tags);
			resultList.add(temp_vec);
		}
		pstmt.close();
		conn.close();
		return resultList;
	}
	
	@Override
	public ArrayList<KnowledgeTagVec> getAllByWebsiteName(String websitename) throws Exception{
		//初始化TagList
		List<String> tagList=InitTagsList.getTagList();
    	HashMap<String, List<String>> keyMap=InitTagsList.getKeyMap(tagList);
    	
		String sql=null;
		if(websitename==null){
			System.out.println("websitename不合法不能为空");
			return null;
		}
		if(websitename.equals("baidu")){
			System.out.println("开始处理百度新闻的knowledgVecTag");
			sql="select * from wiki_crawl.knowledgetagvec where tid like \"%-6\";";
			
		}
		else if(websitename.equals("sogou")){
			System.out.println("开始搜索搜狗新闻的knowledgeTagVec");
			sql="select * from wiki_crawl.knowledgetagvec where tid like \"%-4\";";
		}
		else{
			System.out.println("输入的网站名不合法");
			return null;
		}
		
		Connection  conn=DBConnector.getConnection();
		PreparedStatement pstmt=(PreparedStatement) conn.prepareStatement(sql);
		ResultSet rs=pstmt.executeQuery();
		ArrayList<KnowledgeTagVec> resultList= new ArrayList<KnowledgeTagVec>();
		
		while(rs.next()){
			KnowledgeTagVec temp_vec= new KnowledgeTagVec(null, null);
			temp_vec.setTid(rs.getString(1));
			double[] tags=new double[70];
			for(int i=0;i<tagList.size();i++){
				tags[i]=rs.getDouble(i+2);
			}
			temp_vec.setTags(tags);
			resultList.add(temp_vec);
		}
		pstmt.close();
		conn.close();
		
		
		return resultList;
	}

	@Override
	public KnowledgeTagVec getByTid(String tid) throws Exception {
		//初始化TagList和KeyMap
		List<String> tagList=InitTagsList.getTagList();
    	HashMap<String, List<String>> keyMap=InitTagsList.getKeyMap(tagList);
        
		Connection conn=DBConnector.getConnection();
		String sql="select * from knowledgetagvec where tid = '"+tid+"';";
		PreparedStatement pstmt=(PreparedStatement)conn.prepareStatement(sql);
		ResultSet rs=pstmt.executeQuery();
		double[] tags=new double[70];
		KnowledgeTagVec vec= new KnowledgeTagVec(tid, tags);
		while(rs.next()){
			for(int i=0;i<tagList.size();i++){
				tags[i]=rs.getDouble(i+2);
			}
		}
		
		vec.setTags(tags);
		pstmt.close();
		conn.close();
		return vec;	
	}
	
	
	
}
