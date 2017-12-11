package com.tools;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.mybatis.bean.crawl.News;
import com.mybatis.bean.temp.KnowledgeTagVec;
/*
 * author:Frank Liu
 * Date:16/11/26
 */
public class KnowledgeTagVecCalculator {
	/*
	 * 方法说明:给定一条新闻和各参数,计算其各个tag的值,返回KnowledgeTagVec
	 * 参数说明:
	 * 1.news
	 * 2.time??
	 * 3.tagList
	 * 4.KeyMap
	 * 5.threshhld: tag添加到knowledgeTagVec里面的阈值,该值大小决定对噪音的屏蔽程度
	 * 6.coeff:衰减系数: 该参数大小决定每一个tag对应关键词序列重要性依次衰减的强弱程度
	 */

	public KnowledgeTagVec getVector(News news, int time, List<String> tagList, HashMap<String, List<String>> keyMap,Double threshhold,Double coeff) {	
		
		KnowledgeTagVec knowledgeTagVec = null;
		
		String content = news.getContent();
		if(time > 1000 & (content != "WAIT_UPDATE" || !content.equals(""))){
			double[] knowledgeTags = new double[tagList.size()];		
			for(int i=0;i<tagList.size();i++){
				String tag = tagList.get(i);
				List<String> keyList = keyMap.get(tag);
				double sum = 0;
				for(int j=0;j<keyList.size();j++){
					double wi = 0.0;
					if(j > 0){
						wi = Math.exp(1 - coeff*Math.log(j+1));
					}else{
						wi = 1.0;
					}	
					double count = 0.0;
					double score = 0.0;
					if(keyList.get(j).length()>0){
						count = (content.length() - getLengthOffKeyWord(keyList.get(j),content))/keyList.get(j).length();
						score = count;
					}
					sum = wi*score+sum;
				}
				
				if(sum <threshhold){
					sum = 0.0;
				}
				knowledgeTags[i] = sum;
			}	
			knowledgeTagVec = new KnowledgeTagVec(news.getId(),knowledgeTags);
			normalize(knowledgeTagVec);
		}		
		return knowledgeTagVec;
	}
	
	//计算在字符串content中，去除key的长度
	private int getLengthOffKeyWord(String key, String content) {
		return content.replaceAll(key, "").length();
	}
	
	//归一化用户向量的值
	private void normalize(KnowledgeTagVec knowledgeTagVec){
		int length = knowledgeTagVec.getTags().length;
		double[] newsValues = knowledgeTagVec.getTags();
		List list = new ArrayList();
        for(double value: newsValues){
        	if(value > 0){
            	list.add(value);        		
        	}
        }
        
        if(list.size()>0){
            double max = java.util.Collections.max(list);
            double min = java.util.Collections.min(list);
            double space = 1.0;
            if(max - min == 0){
            	space = max;
            }else{
            	space = max - min;
            }
            
            for(int i = 0; i< length; i++){
            	if(newsValues[i]>0){
                	newsValues[i] = (newsValues[i]-min)/space;	
            	}
            }
            
            knowledgeTagVec.setTags(newsValues);        	
        }

	}
	
}
