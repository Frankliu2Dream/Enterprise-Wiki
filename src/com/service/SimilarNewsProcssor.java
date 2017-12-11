package com.service;

import java.util.ArrayList;
import java.util.HashMap;

import com.jdbc.DAO.KnowledgeTagVecDAO;
import com.jdbc.DAOimpl.KnowledgeTagVecDAOImpl;
import com.jdbc.bean.SimilarNews;
import com.mybatis.bean.temp.KnowledgeTagVec;
import com.tools.KnowledgeTagVecSimilarityCalculator;
/*
 * author:Frank Liu
 * Date:16/11/26
 */

public class SimilarNewsProcssor {
	/*
	 * 方法说明:传入KnowledgeTagVec的List,将每一条与所有vec两两计算得到similarity然后生成similarNews,返回similarNews的list
	 * 
	 */
	public ArrayList<SimilarNews> batchTagVecListSimilarityProcessor(ArrayList<KnowledgeTagVec> knowledgeTagVecList) throws Exception{
		
		KnowledgeTagVecSimilarityCalculator calculator=new KnowledgeTagVecSimilarityCalculator();
	
		ArrayList<SimilarNews> resultList=new ArrayList<SimilarNews>();
				
		for(int i=0;i<knowledgeTagVecList.size();i++){
			
			SimilarNews temp_similarNews=new SimilarNews();
			
			HashMap<String,Double> temp_map=new HashMap<String,Double>();
			
			String tid=knowledgeTagVecList.get(i).getTid();
			
			temp_similarNews.setTid(tid);
			
			for(int j=0;j<knowledgeTagVecList.size();j++){
				if(j==i){
					continue;
				}
				else{
				String temp_tid=knowledgeTagVecList.get(j).getTid();
				Double similarity=calculator.calculate(knowledgeTagVecList.get(i), knowledgeTagVecList.get(j));
				if(similarity>0.0){
					temp_map.put(temp_tid, similarity);
					}
				}
			}
			
			temp_similarNews.setSimilarNewsMap(temp_map);
			
			resultList.add(temp_similarNews);
		}
		return resultList;
	}
	/*
	 * 方法说明:传入一条特定新闻的tid,传入所有新闻的knowledgeTagVec,将该新闻的KnowledgeTagVec与传入的所有vec进行两两比较,生成该新闻的similarNews
	 * 
	 * 
	 */
	public SimilarNews singleKnowledgeTagVecSimilarityProcessor(String tid,ArrayList<KnowledgeTagVec> knowledgeTagVecList) throws Exception{
		
		
		KnowledgeTagVecSimilarityCalculator calculator=new KnowledgeTagVecSimilarityCalculator();
		KnowledgeTagVecDAO knowledgeDao=new KnowledgeTagVecDAOImpl();
		SimilarNews resultSimilarNews=new SimilarNews();
		
		KnowledgeTagVec vec=knowledgeDao.getByTid(tid);
		HashMap<String,Double> map=new HashMap<String,Double>();
		for(int j=0;j<knowledgeTagVecList.size();j++){
			if(tid.equals(knowledgeTagVecList.get(j).getTid())){
				continue;
			}
			else{
				String temp_id=knowledgeTagVecList.get(j).getTid();
				Double temp_similarity=calculator.calculate(vec, knowledgeTagVecList.get(j));
				map.put(temp_id, temp_similarity);
			}
		}
		
		resultSimilarNews.setSimilarNewsMap(map);
		
		return resultSimilarNews;
	}
	
	
}
