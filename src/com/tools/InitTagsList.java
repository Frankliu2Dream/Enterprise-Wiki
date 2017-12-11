package com.tools;

import java.util.HashMap;
import java.util.List;

public class InitTagsList {

	private static List<String> tagList = null;
	private static HashMap<String, List<String>> keyMap = null;
	
	public static List<String> getTagList(){
		if(tagList == null){
			tagList = new ReadFileLine().readFile("/config/tags/tags.txt");//维度	
		}
		//for(String tag:tagList){
		//	System.out.println(tag);
		//}
		return tagList;
	}
	
	public static HashMap<String, List<String>> getKeyMap(List<String> tagList){
		if(keyMap == null){
			keyMap = new HashMap<String, List<String>>();			
			for(int i=0;i<tagList.size();i++){
				//keyList为第i个维度对应的关键词
				//System.out.println(tagList.get(i));
				List<String> keyList = new ReadFileLine().readFile("/config/tags/record/"+tagList.get(i)+".txt");
				//System.out.println(keyList.size());
				keyMap.put(tagList.get(i), keyList);
			}			
		}
		return keyMap;
	}

}
