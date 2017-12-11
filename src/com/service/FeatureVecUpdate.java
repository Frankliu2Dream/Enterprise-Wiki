package com.service;

import java.util.ArrayList;

import com.jdbc.DAO.FeatureVecDAO;
import com.jdbc.DAO.KnowledgeTagVecDAO;
import com.jdbc.DAOimpl.FeatureVecDAOImpl;
import com.jdbc.DAOimpl.KnowledgeTagVecDAOImpl;
import com.jdbc.bean.FeatureVec;
import com.mybatis.bean.temp.KnowledgeTagVec;
import com.tools.GetFeatureVec;

public class FeatureVecUpdate {
	/*
	 * 方法说明:更新FeatureVec
	 * 1.加载全部已有FeatureVec的id
	 * 2.加载全部KnolwdgeTagVec
	 * 3.对未计算FeatureVec的KnowledgeTagVec进行计算,得到FeatureVec存入updateList
	 * 4.向数据库相关表写入updateList
	 */
	public int featureVecUpdate() throws Exception{
		FeatureVecDAO featureDao=new FeatureVecDAOImpl();
		KnowledgeTagVecDAO knowledgeDao= new KnowledgeTagVecDAOImpl();
		
		
		ArrayList<String> existingFeatureVecIds=featureDao.findAllIds();
		//测试
		System.out.println("featureVecUpdate测试:读入现有featureVec的id:"+existingFeatureVecIds.size()+"条");
		ArrayList<KnowledgeTagVec> allKnowledgeTagVecs=knowledgeDao.getAllKnowledgeVecs();
		//测试
		System.out.println("featureVecUpdate测试:读入现有KnowledgeTagVec:"+allKnowledgeTagVecs.size()+"条");
		ArrayList<FeatureVec> updateList=new ArrayList<FeatureVec>();
		
		//测试
		int count=0;
		for(KnowledgeTagVec temp_vec:allKnowledgeTagVecs){
			String id=temp_vec.getTid();
			if(existingFeatureVecIds.contains(id)){
				continue;
			}
			else{
				FeatureVec temp_featureVec=GetFeatureVec.getFeatureVec(temp_vec);
				updateList.add(temp_featureVec);
				//测试
				count++;
				System.out.println(count);
			}
		}
		return featureDao.insertAll(updateList);
	}
}
