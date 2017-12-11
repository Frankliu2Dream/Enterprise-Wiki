package com.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.mybatis.bean.crawl.News;
import com.mybatis.bean.temp.KnowledgeTagVec;
import com.mybatis.tools.SqlSessionConnector;
import com.tools.InitTagsList;
import com.tools.KnowledgeTagVecCalculator;

public class TestThreshhold {
	public static void main(String args[]) throws Exception{
		//初始化工具
    	List<String> tagList=InitTagsList.getTagList();
    	HashMap<String, List<String>> keyMap=InitTagsList.getKeyMap(tagList);
    	KnowledgeTagVecCalculator calculator=new KnowledgeTagVecCalculator();
    	SqlSession session=SqlSessionConnector.getSqlSession();
    	
    	//初始化statement
    	String statement_GetAllNewsIds = "com.mybatis.mapper.crawl.NewsMapper_before.findAllIds";
    	//String statementGetAllKnowledgeVecIds="com.mybatis.mapper.temp.KnowledgeTagVecMapper.getAllIds";
    	String statement_GetNewsById="com.mybatis.mapper.crawl.NewsMapper_before.findById";
    	//String statementInsertVec="com.mybatis.mapper.temp.KnowledgeTagVecMapper.insert";
    	
    	
    	List<String> allNewsIds=session.selectList(statement_GetAllNewsIds);
    	System.out.println("加载所有新闻id,一共:"+allNewsIds.size()+"条");
    	
    	allNewsIds=allNewsIds.subList(0, 20);
    	System.out.println("选择子集:"+"一共"+allNewsIds.size()+"条");
    	
    	List<News> allNews=new ArrayList<News>();
    	int count=0;
    	for(String id:allNewsIds){
    		News temp_news=session.selectOne(statement_GetNewsById,id);
    		allNews.add(temp_news);
    		count++;
    		System.out.println(count);
    	}
		System.out.println("加载新闻:"+allNews.size()+"条");
		
		//for(int i=15;i>=1;i--){
			Double threshhold=0.1;
			Double coeff=0.1;
			int temp_count=0;
			//ArrayList<KnowledgeTagVec> temp_KnowledgeTagVecList=new ArrayList<KnowledgeTagVec>();
			for(News temp_news:allNews){
				System.out.println(temp_news.getContent());
				KnowledgeTagVec temp_vec=calculator.getVector(temp_news, 2000, tagList, keyMap, threshhold,coeff);
				System.out.println(temp_vec.toString());
				double[] tags=temp_vec.getTags();
				for(double tag:tags){
					if(tag!=0.0){
						temp_count++;
						break;
					}
				}
			//}
			//System.out.println("阈值为"+threshhold+"时,衰减参数为："+coeff+"时，不为空的TagVec有"+temp_count+"条");
				System.out.println();
			}
	}
}
