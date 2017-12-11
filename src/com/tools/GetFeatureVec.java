package com.tools;

import java.util.ArrayList;
import java.util.Map;

import com.jdbc.bean.FeatureVec;
import com.mybatis.bean.temp.KnowledgeTagVec;
/*
 * author:Frank Liu
 * Date:16/11/26
 */

public class GetFeatureVec {
	/*
	 * 方法简介:传入一个新闻的knowledgeTagVec,对该新闻的各个tag大小进行排序,选出最大的3个,记录其位置,生成字符串,返回新的FeatureVec
	 * 
	 */
	public static FeatureVec getFeatureVec(KnowledgeTagVec knowledgeTagVec){

		//获得当前Vec的id
		String tid=knowledgeTagVec.getTid();
		if(tid==null||"".equals(tid)){
			System.out.println("com.tools.GetFeatureVec:warning:出现KnowledgeTagVec的id为空");
			return null;
		}
		
		//将当前Vec的Tags进行排序
		double[] tags=knowledgeTagVec.getTags();
		if(tags.length==0||tags==null){
			System.out.println("com.tools.GetFeatureVec:warning:出现tags为空或长度为0");
			return null;
		}
		SortTags sortTool=new SortTags();
		ArrayList<Map.Entry<Integer, Double>> sortedTagList=new ArrayList<Map.Entry<Integer,Double>>();
		try {
			sortedTagList = sortTool.sortByValue(tags);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//取得数值最大的三个tag的位置，并生成featureVec字段表示这三个tag的位置
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<3;i++){
			sb.append(sortedTagList.get(i).getKey().toString()+";");
		}
		
		String featureVec=sb.toString();
		
		FeatureVec resultFeatureVec= new FeatureVec();
		resultFeatureVec.setTid(tid);
		resultFeatureVec.setfeatureVec(featureVec);
		
		return resultFeatureVec;
	}
}
