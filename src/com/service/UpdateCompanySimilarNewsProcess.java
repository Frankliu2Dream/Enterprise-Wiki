package com.service;

import java.util.ArrayList;
import java.util.List;

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

public class UpdateCompanySimilarNewsProcess {
public void update()throws Exception{
		
		//加载工具类
		SimilarNewsProcssor processor=new SimilarNewsProcssor();
		KnowledgeTagVecDAO knowVecDao=new KnowledgeTagVecDAOImpl();
		SimilarNewsDAO similarNewsDao=new SimilarNewsDAOImpl();
		SqlSession session=SqlSessionConnector.getSqlSession();
		String statement_findAllIds="com.mybatis.mapper.crawl.NewsMapper_before.findAllIds";
		String statement_findCompanyName="com.mybatis.mapper.crawl.NewsMapper_before.findCompanyName";
		String statement_findByCompanyName="com.mybatis.mapper.crawl.NewsMapper_before.findByCompanyName";
		String tableName="wiki_crawl.companysimilarnews";
		
		
		List<String> processedCompanyName=new ArrayList<String>();
		List<String> idList=session.selectList(statement_findAllIds);
		 
		//去重
		ArrayList<String> existingSimilarNewsId=similarNewsDao.getAllId(tableName);
		
		
		//遍历idList，对每一条新闻执行以下操作：
		//1.检查该新闻的company_name是否在company_similarnewsMap中，如果在，跳过
		//2.company_name没有出现过，从数据库中提取出所有相同companyname的新闻，对这些新闻两两比较，对每一条新闻
		//生成其similarNews，写入数据库
		
		for(int i=0;i<idList.size();i++){
			
			String temp_tid=idList.get(i);
			String temp_companyName=session.selectOne(statement_findCompanyName,temp_tid);
			
			if(existingSimilarNewsId.contains(temp_tid)){
				continue;
			}
			
			if(processedCompanyName.contains(temp_companyName)){
				continue;
			}
			
			
			else{
				//测试
				processedCompanyName.add(temp_companyName);
				System.out.println("正在处理"+temp_companyName+"的相似新闻");
				long startTime=System.currentTimeMillis();
				
				
				List<String> sameCompanyNewsTids=
						session.selectList(statement_findByCompanyName,temp_companyName);
				
				ArrayList<KnowledgeTagVec> sameCompanyKnowledgeTagVecList=
							new ArrayList<KnowledgeTagVec>();
				
				for(int j=0;j<sameCompanyNewsTids.size();j++){
					sameCompanyKnowledgeTagVecList.add(knowVecDao.get(sameCompanyNewsTids.get(j)));
				}
				
				ArrayList<SimilarNews> sameCompanySimilarNews=
						processor.batchTagVecListSimilarityProcessor(sameCompanyKnowledgeTagVecList);
				
				
				int emptyCount=0;
				for(SimilarNews temp_similarNews:sameCompanySimilarNews){
					
					if(existingSimilarNewsId.contains(temp_similarNews.getTid())){
						continue;
					}
					if(temp_similarNews.getSimilarNewsMap().size()==0){
						emptyCount+=1;
					}
					similarNewsDao.insert(temp_similarNews, tableName);
				}
				
				//测试
				long endTime=System.currentTimeMillis();
				System.out.println("处理完成,共用"+(endTime-startTime)+"ms");
				System.out.println("该公司共处理"+sameCompanyNewsTids.size()+"条新闻");
				System.out.println("其中similarNews为空的有"+emptyCount+"条");
			}
			
			
		}
		
		
		
		
		
		
	}
	
}
