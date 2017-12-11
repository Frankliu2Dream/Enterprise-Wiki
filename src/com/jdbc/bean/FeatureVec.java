package com.jdbc.bean;
/*
 * author:Frank Liu
 * Date:16/11/26
 */

public class FeatureVec {
	/*
	 * 说明：FeatureVec 用来表征新闻最具代表性的tag位置,即表征
	 * 		包含两个属性，tid是对应新闻的id号
	 * 		featureVec 是形如："35;26;47;"的字符串,"35"是指该新闻TagList中第35个的数值最大,最具代表性,后面依次类推
	 * 		featureVec暂时只记录3个最大值的位置
	 *	加载:见FeatureVecDAO
	 */
	private String tid;
	
	private String featureVec;
	
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String getfeatureVec() {
		return featureVec;
	}
	public void setfeatureVec(String featureVec) {
		this.featureVec = featureVec;
	}
	@Override
	public String toString() {
		return "featureVec [tid=" + tid + ", featureVec=" + featureVec
				+ "]";
	}	
}
