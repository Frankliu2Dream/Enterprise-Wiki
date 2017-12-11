package com.tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
/*
 * author:Frank Liu
 * Date:16/11/26
 */

public class SortTags {
	/*
	 * 方法说明:传入Double 数组tags,按照tags的值进行排序,并按照该顺序将tag的下标和值存进Map中
	 */
	public ArrayList<Map.Entry<Integer,Double>> sortByValue(double[] tags) throws Exception{
		HashMap<Integer,Double> position_valueMap=new HashMap<Integer,Double>();
		for(int i=1;i<=tags.length;i++){
			position_valueMap.put((Integer)i,tags[i-1]);
		}
		ArrayList<Map.Entry<Integer,Double>> resultList=new ArrayList<Map.Entry<Integer,Double>>(position_valueMap.entrySet());
		Collections.sort(resultList,new Comparator<Map.Entry<Integer,Double>>(){
			@Override
            public int compare(Entry<Integer, Double> o1, Entry<Integer, Double> o2) {
                //return o1.getValue().compareTo(o2.getValue());
                return o2.getValue().compareTo(o1.getValue());
            }
		});
		
		return resultList;
	}
	/*
	 * 方法说明:略
	 * 
	 */
	public ArrayList<Map.Entry<Integer,Double>> sortByKey(double[] tags) throws Exception{
		HashMap<Integer,Double> position_valueMap=new HashMap<Integer,Double>();
		for(int i=1;i<=tags.length;i++){
			position_valueMap.put((Integer)i,tags[i-1]);
		}
		ArrayList<Map.Entry<Integer,Double>> resultList=new ArrayList<Map.Entry<Integer,Double>>(position_valueMap.entrySet());
		Collections.sort(resultList,new Comparator<Map.Entry<Integer,Double>>(){
			@Override
            public int compare(Entry<Integer, Double> o1, Entry<Integer, Double> o2) {
                //return o1.getValue().compareTo(o2.getValue());
                return o1.getKey().compareTo(o2.getKey());
            }
		});
		
		return resultList;
	}
}
