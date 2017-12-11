package com.tools;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
/*
 * author:Frank Liu
 * Date:16/11/26
 */
public class NewsTimelinessFilter {
	/*
	 * 方法说明:过滤陈旧新闻
	 * 传入id的list 和允许的最早新闻日期
	 * 传出过滤后的id集合
	 */
	public ArrayList<String> filter(ArrayList<String> tidList,Date limitDate) throws Exception{
		ArrayList<String> resultList=new ArrayList<String>();
		if(limitDate==null){
			System.out.println("日期期限不能为空");
			return null;
		}
		else{
			System.out.println(limitDate.toString());
		}
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
		for(String tid:tidList){
			String dateString=tid.substring(0, 8);
			Date newsDate=sdf.parse(dateString);
			if(newsDate.after(limitDate)){
				resultList.add(tid);
			}
		}
		return resultList;
	 }
}
