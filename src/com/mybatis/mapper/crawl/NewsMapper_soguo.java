package com.mybatis.mapper.crawl;

import java.util.List;

import com.mybatis.bean.crawl.News;

public interface NewsMapper_soguo extends NewsMapper{

	List<String> findAbbr(String COMAPNY_ABBR);
	
	News findById(String id);
	
	List<News> findByIdArray(String[] ids); 
	
	List<News> findByIdList(List<String> ids);
	
	List<String> findRandNews(int limit_num);
}
