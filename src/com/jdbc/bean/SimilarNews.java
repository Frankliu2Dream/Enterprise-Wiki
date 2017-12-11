package com.jdbc.bean;

import java.util.HashMap;
/*
 * author:Frank Liu
 * Date:16/11/26
 */

public class SimilarNews {
	/*
	 *	说明:SimilarNews 是用来记录一条新闻和其相似新闻的以及相似度的bean
	 *		包含两个属性:tid是该条新闻id, similarNewsMap用来记录相似新闻id和相似度
	 * 	加载:见SimilarNewsDAO
	 *
	 */
	
	private String tid;
	private HashMap<String,Double> similarNewsMap;
	
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public HashMap<String, Double> getSimilarNewsMap() {
		return similarNewsMap;
	}
	public void setSimilarNewsMap(HashMap<String, Double> similarNewsMap) {
		this.similarNewsMap = similarNewsMap;
	}
	
	@Override
	public String toString(){
		return "["+tid+"]"+similarNewsMap.toString();
	}
}
