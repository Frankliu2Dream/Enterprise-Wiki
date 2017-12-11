package com.jdbc.bean;

import java.util.ArrayList;
import java.util.HashMap;
/*
 * author:Frank Liu
 * Date:16/11/26
 */

public class Guild {
	/*
	 * 说明:Guild 是将所有公司按照行列分类的bean
	 * 		只含有一个属性guildMap, key值为行业名称,如"制造业","金融业",value值为该行业所有公司 的Id
	 * 	加载方法:见GuildDAO
	 */
	
	private HashMap<String,ArrayList<String>> guildMap;

	public HashMap<String, ArrayList<String>> getGuildMap() {
		return guildMap;
	}


	public void setGuildMap(HashMap<String, ArrayList<String>> guildMap) {
		this.guildMap = guildMap;
	}


	@Override
	public String toString() {
		return "Guild [guildMap=" + guildMap + "]";
	}

	
	
}