package com.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.jdbc.DAO.FeatureVecDAO;
import com.jdbc.DAO.KnowledgeTagVecDAO;
import com.jdbc.DAO.SimilarNewsDAO;
import com.jdbc.DAOimpl.FeatureVecDAOImpl;
import com.jdbc.DAOimpl.KnowledgeTagVecDAOImpl;
import com.jdbc.DAOimpl.SimilarNewsDAOImpl;
import com.jdbc.bean.FeatureVec;
import com.jdbc.bean.SimilarNews;
import com.mybatis.bean.temp.KnowledgeTagVec;
import com.tools.EmptyKnowledgeTagVecFilter;
/*
 * author:Frank Liu
 * Date:16/11/26
 */

public class UpdateGeneralSimilarNewsProcess {
	public void update() throws Exception{
		long startTime=System.currentTimeMillis();
		//加载工具类
		//GeneralSimilarNewsProcess this_processor=new GeneralSimilarNewsProcess();
		UpdateGeneralSimilarNewsProcess this_processor=new UpdateGeneralSimilarNewsProcess();
		FeatureVecDAO featureVecDao=new FeatureVecDAOImpl();
		SimilarNewsDAO similarNewsDao=new SimilarNewsDAOImpl();
		KnowledgeTagVecDAO knowVecDao=new KnowledgeTagVecDAOImpl();
		SimilarNewsProcssor processor=new SimilarNewsProcssor();
		EmptyKnowledgeTagVecFilter emptyFilter=new EmptyKnowledgeTagVecFilter();
		
		//初始化全部FeatureVec
		ArrayList<FeatureVec> allFeatureVecs=featureVecDao.findAllFeatureVec();
		
		//去重
		String tableName="wiki_crawl.generalsimilarnews";
		ArrayList<String> existingSimilarNewsId=similarNewsDao.getAllId(tableName);
		
 		//测试
		System.out.println("加载所有featureVec一共："+allFeatureVecs.size()+"条");
		
		
		//初始化featureVecMap
		HashMap<String,ArrayList<String>> featureVec_TidMap=new HashMap<String,ArrayList<String>>();
		for(FeatureVec temp_vec:allFeatureVecs){
			if(existingSimilarNewsId.contains(temp_vec.getTid())){
				continue;
			}
			if(featureVec_TidMap.containsKey(temp_vec.getfeatureVec())){
				featureVec_TidMap.get(temp_vec.getfeatureVec()).add(temp_vec.getTid());
			}
			else{
				ArrayList<String> temp_idList=new ArrayList<String>();
				temp_idList.add(temp_vec.getTid());
				featureVec_TidMap.put(temp_vec.getfeatureVec(),temp_idList );
			}
		}
		
		
		List<Map.Entry<String,ArrayList<String>>> list=new ArrayList<Map.Entry<String,ArrayList<String>>>(featureVec_TidMap.entrySet());
		  Collections.sort(list, new Comparator<Map.Entry<String, ArrayList<String>>>() {
	            //降序排序
	            @Override
	             public int compare(Entry<String, ArrayList<String>> o1, Entry<String, ArrayList<String>> o2) {
	                 //return o1.getValue().compareTo(o2.getValue());
	                 return o2.getValue().size()-o1.getValue().size();
	             }
	         });
		
		 
		//测试
		 for(int i=0;i<10;i++)
			 {
				 System.out.println(list.get(i).getKey().toString());
				 System.out.println(list.get(i).getValue().size());
			 }
		//加载所有非空knowledgeTagVec至内存
		ArrayList<KnowledgeTagVec> allKnowledgeTagVecs=knowVecDao.getAllKnowledgeVecs();
		allKnowledgeTagVecs=emptyFilter.filter(allKnowledgeTagVecs);
		HashMap<String,KnowledgeTagVec> knowledgeTagVecMap=new HashMap<String, KnowledgeTagVec>();
		for(KnowledgeTagVec temp_vec:allKnowledgeTagVecs){
			knowledgeTagVecMap.put(temp_vec.getTid(), temp_vec);
		}
	
		//对Map执行操作：对每一个Key，取出value，判断size，size大于20两两比较并存入20条相似新闻
		//对size小于20的，扩大范围进行搜索，还小于20的，生成列表并打印出来
	
		for(int i=0;i<list.size();i++){
			//测试
			System.out.println("正在处理FeatureVec为"+list.get(i).getKey());
			
			ArrayList<String> sameFeatureTids=list.get(i).getValue();
			
			//测试
			System.out.println("一共需要处理"+sameFeatureTids.size()+"条");
			
			
			if(sameFeatureTids.size()>=20){
				ArrayList<KnowledgeTagVec> sameFeatureKnowledgeTagVecList=
						new ArrayList<KnowledgeTagVec>();
				for(int j=0;j<sameFeatureTids.size();j++){
					sameFeatureKnowledgeTagVecList.add(knowVecDao.get(sameFeatureTids.get(j)));
				}
				
				for(int k=0;k<sameFeatureTids.size();k++){
					SimilarNews temp_similarNews=processor.singleKnowledgeTagVecSimilarityProcessor(sameFeatureTids.get(k), sameFeatureKnowledgeTagVecList);
					similarNewsDao.insert(temp_similarNews, "wiki_crawl.generalsimilarnews");
				}
			}
			else{
				ArrayList<KnowledgeTagVec> ObscureSameFeatureKnowledgeTagList=
					new ArrayList<KnowledgeTagVec>();
				String temp_featureVec= list.get(i).getKey();
				ArrayList<String> obscureSameFeatureVecList=this_processor.function_1(temp_featureVec);
				for(int j=0;j<obscureSameFeatureVecList.size();j++){
					ObscureSameFeatureKnowledgeTagList.add(knowledgeTagVecMap.get(obscureSameFeatureVecList.get(j)));
				}
				for(int k=0;k<sameFeatureTids.size();k++){
					SimilarNews temp_similarNews=processor.singleKnowledgeTagVecSimilarityProcessor(sameFeatureTids.get(k), ObscureSameFeatureKnowledgeTagList);
					similarNewsDao.insert(temp_similarNews, "wiki_crawl.generalsimilarnews");
				}
			}
			//测试
			long endTime= System.currentTimeMillis();
			System.out.println("已处理"+list.get(i).getKey()+"的新闻");
			System.out.println("共处理"+sameFeatureTids.size()+"条");
			System.out.println("用时"+(endTime-startTime)+"ms");
			
		}
}
	
	private ArrayList<String> function_1(String featureVec) throws Exception{
		//测试
		System.out.println(featureVec+"相似新闻较少，进行扩大搜索");
		
		
		//逻辑
		FeatureVecDAO dao=new FeatureVecDAOImpl();
		
		String[] featureTags=featureVec.split(";");
		StringBuffer buffer=new StringBuffer();
		buffer.append(featureTags[0]);
		buffer.append(";");
		buffer.append(featureTags[1]);
		buffer.append(";");
		buffer.append("%");
		
		String obscureFeatureVec=buffer.toString();
		
		ArrayList<FeatureVec> resultFeatureList=dao.obscureFindByFeatureVec(obscureFeatureVec);
		
		//测试
		System.out.println("搜索结果："+resultFeatureList.size()+"条");
		System.out.println();
		
		ArrayList<String> resultTidList=new ArrayList<String>();
		
		for(FeatureVec temp_vec:resultFeatureList){
			resultTidList.add(temp_vec.getTid());
		}
		
		return resultTidList;
		}
}
