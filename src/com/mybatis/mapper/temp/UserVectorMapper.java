package com.mybatis.mapper.temp;



public interface UserVectorMapper {

    void update(@Param("uid")String uid, @Param("tags")double[] tags);
    
	
}
