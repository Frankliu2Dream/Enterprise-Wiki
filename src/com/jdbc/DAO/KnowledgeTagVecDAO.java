package com.jdbc.DAO;

import java.util.ArrayList;

import com.mybatis.bean.temp.KnowledgeTagVec;
/*
 * author:Frank Liu
 * Date:16/11/26
 */

public interface KnowledgeTagVecDAO {
	/*
	 * 方法说明:略
	 */
	public KnowledgeTagVec getByTid(String tid) throws Exception;
	/*
	 * 方法说明:略
	 */
	public ArrayList<KnowledgeTagVec> getAllKnowledgeVecs() throws Exception;
	/*
	 * 方法说明:如查询所有百度新闻的KnowledgeTagVec,只需输入参数字符串"baidu",返回所有百度新闻的KnowledgeTagVec
	 * 暂时仅支持"baidu"和"sogou",如果其他公司的新闻计算Tag并更新到数据库,应在KnowledgeTagVecDAOImpl.java文件更新该方法
	 */
	public ArrayList<KnowledgeTagVec> getAllByWebsiteName(String websiteName) throws Exception;
}
