package com.mybatis.mapper.temp;

/*
 * author:Frank Liu
 * Date:16/11/26
 */

public interface KnowledgeTagVecMapper {

    int insert(@Param("tid")String tid, @Param("tags")double[] tags);
    
	
}
