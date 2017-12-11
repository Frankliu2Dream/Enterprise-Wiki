package com.jdbc.DAO;

import java.util.ArrayList;

import com.jdbc.bean.SimilarNews;
/*
 * author:Frank Liu
 * Date:16/11/26
 */

public interface SimilarNewsDAO {
	/*
	 * 方法说明:查找同公司相似新闻
	 */
	public SimilarNews getCompanySimilarNewsById(String tid) throws Exception;
	/*
	 * 方法说明:查找所有新闻中的相似新闻
	 */
	public SimilarNews getGeneralSimilarNewsById(String tid) throws Exception;
	/*
	 * 方法说明:略
	 */
	public ArrayList<String> getAllCompanySimilarNewsIds() throws Exception;
	/*
	 * 方法说明:略
	 */
	public ArrayList<String> getAllGeneralSimilarNewsIds() throws Exception;
	/*
	 * 方法说明:略
	 */
	public boolean insertCompanySimilarNews(SimilarNews similarNews) throws Exception;
	/*
	 * 方法说明:略
	 */
	public boolean insertGeneralSimilarNews(SimilarNews similarNews) throws Exception;
	
}
