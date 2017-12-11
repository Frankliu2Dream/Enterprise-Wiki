package com.tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.jdbc.bean.SimilarNews;
/*
 * author:Frank Liu
 * Date:16/11/26
 */
public class SortSimilarNews {
/*
 * 方法说明:对SimilarNews 中的similarNews的HashMap按照value,即Double类型的相似度进行排序，返回List<Map.Entry<String,Double>>,即排序后结果
 * 
 */
	public static List<Map.Entry<String, Double>> sortSimilarNewsMap(SimilarNews similarNews) throws Exception{
		HashMap<String,Double> similarNewsMap= similarNews.getSimilarNewsMap();
	    //将map.entrySet()转换成list
        List<Map.Entry<String, Double>> list = new ArrayList<Map.Entry<String, Double>>(similarNewsMap.entrySet());
         Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
            //降序排序
            @Override
             public int compare(Entry<String, Double> o1, Entry<String, Double> o2) {
                 //return o1.getValue().compareTo(o2.getValue());
                 return o2.getValue().compareTo(o1.getValue());
             }
         });

         return list;
	}
}
