package com.mybatis.bean.temp;

import java.util.List;

import com.tools.InitTagsList;
/*
 * author:Frank Liu
 * Date:16/11/26
 */

public class KnowledgeTagVec {
	
	String tid;
	
	double[] tags;
	
	public KnowledgeTagVec(String tid, double[] tags){
		super();
		this.tid=tid;
		this.tags=tags;
	}
	
	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public double[] getTags() {
		return tags;
	}

	public void setTags(double[] tags) {
		this.tags = tags;
	}

	@Override
	public String toString() {
		StringBuffer sb=new StringBuffer();
		sb.append("tid:"+tid);
		List<String> tagList=InitTagsList.getTagList();
		for(int i=0;i<tagList.size();i++){
			sb.append(tagList.get(i)+":"+tags[i]+";");
		}
		return sb.toString();
	}

}
