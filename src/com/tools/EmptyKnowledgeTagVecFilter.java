package com.tools;

import java.util.ArrayList;
import java.util.List;

import com.mybatis.bean.temp.KnowledgeTagVec;
/*
 * author:Frank Liu
 * Date:16/11/26
 */

public class EmptyKnowledgeTagVecFilter {
	/*
	 * 方法简介:
	 * 作用:传入KnowledgeTagVec的列表,过滤掉tag全部为0的vec,返回不全为0
	 *		的vec
	 * 
	 */
		public ArrayList<KnowledgeTagVec> filter(List<KnowledgeTagVec> vecList) throws Exception{
			ArrayList<KnowledgeTagVec> resultList= new ArrayList<KnowledgeTagVec>();
			for(KnowledgeTagVec temp_vec: vecList){
				double[] tags= temp_vec.getTags();
				for (double tag:tags){
					if(tag!=0.0){
						resultList.add(temp_vec);
						break;
					}
				}
			}
			return resultList;
		}
}
