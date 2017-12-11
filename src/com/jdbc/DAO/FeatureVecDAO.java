package com.jdbc.DAO;

import java.util.ArrayList;

import com.jdbc.bean.FeatureVec;
/*
 * author:Frank Liu
 * Date:16/11/26
 */

public interface FeatureVecDAO {
	/*
	 * 方法说明:略
	 */
	public int insertAll(ArrayList<FeatureVec> featureVecList) throws Exception;
	/*
	 * 方法说明:略
	 */
	public FeatureVec findById(String tid) throws Exception;
	/*
	 * 方法说明:略
	 */
	public ArrayList<FeatureVec> findByFeatureVec(String featureVec) throws Exception;
	/*
	 * 方法说明:略
	 */
	public ArrayList<FeatureVec> findAllFeatureVec() throws Exception;
	/*
	 * 方法说明:模糊匹配,可以匹配诸如"%35;", "47;%"等字符串,返回符合该模糊查询的集合
	 */
	public ArrayList<FeatureVec> obscureFindByFeatureVec(String featureVec) throws Exception;
	/*
	 * 方法说明:略
	 */
	public ArrayList<String> findAllIds() throws Exception;
}
