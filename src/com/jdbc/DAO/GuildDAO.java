package com.jdbc.DAO;

import com.jdbc.bean.Guild;
/*
 * author:Frank Liu
 * Date:16/11/26
 */

public interface GuildDAO {
	/*
	 * 方法说明:从数据库中的wiki_crawl的companydetail表中查询所有公司详情,生成Guild对象
	 * 该对象包含信息见Guild.java
	 */
	public Guild getGuild() throws Exception;
}
