package com.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jdbc.DAO.KnowledgeTagVecDAO;
import com.jdbc.DAO.SimilarNewsDAO;
import com.jdbc.DAOimpl.KnowledgeTagVecDAOImpl;
import com.jdbc.DAOimpl.SimilarNewsDAOImpl;
import com.jdbc.bean.SimilarNews;
import com.mybatis.bean.temp.KnowledgeTagVec;
import com.mybatis.tools.SqlSessionConnector;
/*
 * author:Frank Liu
 * Date:16/11/26
 */

public class CompanySimilarNewsProcess {
	/*
	 * 方法说明:
	 * 功能:找出同一公司的所有新闻,计算两两之间的相似度,生成similarNews,存入数据库
	 * 备注:处理范围:百度新闻和搜狗新闻,增加其他新闻需要更新本方法的代码
	 */
	
	public void process()throws Exception{	
	    //初始化statement
	    String statement_GetAllNewsIds_baidu="com.mybatis.mapper.crawl.NewsMapper_baidu.findAllIds";
	    String statement_GetAllNewsIds_sogou="com.mybatis.mapper.crawl.NewsMapper_sogou.findAllIds";
	    String statement_findCompanyName_baidu="com.mybaits.mapper.crawl.NewsMapper_baidu.findCompanyName";
	    String statement_findCompanyName_sogou="com.mybaits.mapper.crawl.NewsMapper_sogou.findCompanyName";
	 
		//加载工具类
		SimilarNewsProcssor processor=new SimilarNewsProcssor();
		KnowledgeTagVecDAO knowVecDao=new KnowledgeTagVecDAOImpl();
		SimilarNewsDAO similarNewsDao=new SimilarNewsDAOImpl();
		SqlSession session=SqlSessionConnector.getSqlSession();
		
		HashMap<String,ArrayList<String>> companyName_newsIdMap=new HashMap<String,ArrayList<String>>();
		//加载全部baidu新闻id,在Map中按照公司名聚类
		List<String> idList=session.selectList(statement_GetAllNewsIds_baidu);
		//测试
		System.out.println("CompanySimilarNews的process方法测试:加载百度新闻"+idList.size()+"条");
		for(String temp_id:idList){
			String companyName=session.selectOne(statement_findCompanyName_baidu);
			if(companyName_newsIdMap.containsKey(companyName)){
			companyName_newsIdMap.get(companyName).add(temp_id);
			}
			else{
				ArrayList<String> temp_idList=new ArrayList<String>();
				temp_idList.add(temp_id);
				companyName_newsIdMap.put(companyName, temp_idList);
			}
		}
		//加载全部sogou新闻id,在Map中按照公司名聚类
		idList=session.selectList(statement_GetAllNewsIds_sogou);
		//测试
		System.out.println("CompanySimilarNews的process方法测试:加载sogou新闻"+idList.size()+"条");
		for(String temp_id:idList){
			String companyName=session.selectOne(statement_findCompanyName_sogou);
			if(companyName_newsIdMap.containsKey(companyName)){
			companyName_newsIdMap.get(companyName).add(temp_id);
			}
			else{
				ArrayList<String> temp_idList=new ArrayList<String>();
				temp_idList.add(temp_id);
				companyName_newsIdMap.put(companyName, temp_idList);
			}
		}
		//测试
		System.out.println("CompanySimilarNews的process方法测试:companyName_newsIdMap的key共"+companyName_newsIdMap.size()+"条");
		//对Map中每一个公司进行遍历,计算公司内similarNews,写入数据库
		List<Map.Entry<String, ArrayList<String>>> company_newsEntry=new ArrayList<Map.Entry<String, ArrayList<String>>>(companyName_newsIdMap.entrySet());
		for(Map.Entry<String, ArrayList<String>> temp_entry:company_newsEntry){
			long startTime=System.currentTimeMillis();
			String companyName=temp_entry.getKey();
			ArrayList<String> companyNewsIds=temp_entry.getValue();
			ArrayList<KnowledgeTagVec> temp_knowledgeTagVecList=
					new ArrayList<KnowledgeTagVec>();
		
			for(int j=0;j<companyNewsIds.size();j++){
				temp_knowledgeTagVecList.add(knowVecDao.getByTid(companyNewsIds.get(j)));
			}
		
			ArrayList<SimilarNews> sameCompanySimilarNews=
				processor.batchTagVecListSimilarityProcessor(temp_knowledgeTagVecList);
		
		
			int emptyCount=0;
			for(SimilarNews temp_similarNews:sameCompanySimilarNews){
				if(temp_similarNews.getSimilarNewsMap().size()==0){
					emptyCount+=1;
					similarNewsDao.insertCompanySimilarNews(temp_similarNews);
				}
			
			}
			//测试
			long endTime=System.currentTimeMillis();
			System.out.println("正在处理:"+companyName+"的新闻");
			System.out.println("处理完成,共用"+(endTime-startTime)+"ms");
			System.out.println("该公司共处理"+companyNewsIds.size()+"条新闻");
			System.out.println("其中similarNews为空的有"+emptyCount+"条");
		}
	}	
	}
