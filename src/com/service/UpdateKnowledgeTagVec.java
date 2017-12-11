package com.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.mybatis.bean.crawl.News;
import com.mybatis.bean.temp.KnowledgeTagVec;
import com.mybatis.tools.SqlSessionConnector;
import com.tools.InitTagsList;
import com.tools.KnowledgeTagVecCalculator;
/*
 * author:Frank Liu
 * Date:16/11/26
 */

public class UpdateKnowledgeTagVec {
/*
 * 方法说明:传入参数:新闻来源网站,如baidu ,sogou. 该方法首先从数据中找出所有该来源的新闻id和所有已经存入数据库的KNowledgeTagVec
 * 的id,遍历所有新闻id,如果该新闻的knowledgeTagVec还没有写入的话,计算KnowledgeTagVec并写进数据库
 * 
 * Note:暂时仅支持sogou和baidu,如果需要添加其他公司新闻需要首先修改mybaits的Mapper再修改本方法
 */
    public int UpdateAll() throws Exception{
    	UpdateKnowledgeTagVec processor=new UpdateKnowledgeTagVec();
    	String name_baidu="baidu";
    	String name_sogou="sogou";
    	return processor.Update(name_baidu)+processor.Update(name_sogou);
    }
    public int Update(String sourceName) throws Exception {
    	//初始化statement
    	String statement_GetAllKnowledgeVecIds="com.mybatis.mapper.temp.KnowledgeTagVecMapper.getAllIds";
    	String statement_InsertVec="com.mybatis.mapper.temp.KnowledgeTagVecMapper.insert";
    	String statement_GetAllNewsIds=null;
    	String statement_GetNewsById=null;
    	
    	if(sourceName==null){
    		return 0;
    	}
    	else{
    		sourceName=sourceName.toLowerCase();
    		if(sourceName.equals("baidu")){
    			statement_GetAllNewsIds="com.mybatis.mapper.crawl.NewsMapper_baidu.findAllIds";
    			statement_GetNewsById="com.mybatis.mapper.crawl.NewsMapper_baidu.findById";
    		}
    		else if(sourceName.equals("sougou")){
    			statement_GetAllNewsIds="com.mybatis.mapper.crawl.NewsMapper_sogou.findAllIds";
    			statement_GetNewsById="com.mybatis.mapper.crawl.NewsMapper_sogou.findById";
    		}
    		else{
    			System.out.println("com.service.UpdateKnowledgeTagVec:warning:传入sourceName无法辨识");
    			return 0;
    		}
    	}
    	//初始化工具
    	List<String> tagList=InitTagsList.getTagList();
    	HashMap<String, List<String>> keyMap=InitTagsList.getKeyMap(tagList);
    	KnowledgeTagVecCalculator calculator=new KnowledgeTagVecCalculator();
    	SqlSession session=SqlSessionConnector.getSqlSession();
    	
    	
    	List<String> NewsIdList=session.selectList(statement_GetAllNewsIds);
    	//测试
    	System.out.println("Update测试:NewsIdList包含"+NewsIdList.size()+"条");
    	List<String> KnowledgeTagVecIdList=session.selectList(statement_GetAllKnowledgeVecIds);
    	//测试
    	System.out.println("Update测试:KnowledgeTagVecList包含"+KnowledgeTagVecIdList.size()+"条");
        //去重操作
    
    	List<String> UpdateList=new ArrayList<String>();
        for(String temp_id:NewsIdList){
        	
        	if(!KnowledgeTagVecIdList.contains(temp_id)){
        		UpdateList.add(temp_id);
        		
        	}
        	
        }
        //测试
        System.out.println("com.service,UpdateKnowledgeTagVecNewsIdList:需要update的个数为："+UpdateList.size());
      
        int count=0;
        //遍历idList,通过id得到news，计算KnowledgeTagVec，打印结果并存入数据库
        for(String temp_id:UpdateList){
        	News temp_news=session.selectOne(statement_GetNewsById,temp_id);
        	if(temp_news==null){
        		System.out.println("com.service,UpdateKnowledgeTagVecNewsIdList:warning:id查找新闻返回null");
        	} 	
        	
        	KnowledgeTagVec temp_vec=calculator.getVector(temp_news, 2000, tagList, keyMap,0.1,0.5);
        	
        	if(temp_vec==null){
        		System.out.println("com.service,UpdateKnowledgeTagVecNewsIdList:warning:vec为空,跳过该news");
        		continue;
        	}
        	else{
            	try{
            		session.insert(statement_InsertVec, temp_vec);
            		session.commit();
            		count++;
            		
            		
            	}catch(Exception e){
            		e.printStackTrace();
            	}
        	}
        }
        
        session.close();
        return count;
    }
}