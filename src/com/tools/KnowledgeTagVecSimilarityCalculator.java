package com.tools;

import com.mybatis.bean.temp.KnowledgeTagVec;
/*
 * author:Frank Liu
 * Date:16/11/26
 */

public class KnowledgeTagVecSimilarityCalculator {
	/*
	 * 方法说明:使用余弦相似度计算两KnowledgeTagVec相似度
	 */
	public Double calculate(KnowledgeTagVec vec1,KnowledgeTagVec vec2) throws Exception{
		
		KnowledgeTagVecSimilarityCalculator calculator= new KnowledgeTagVecSimilarityCalculator();
		
		//初始化vec1,vec2的模长以及向量积
		double vec1Modulo=calculateModulo(vec1.getTags());
		double vec2Modulo=calculateModulo(vec2.getTags());
		if(vec1Modulo==0.0||vec2Modulo==0.0){
			return 0.0;
		}

		double vectorProduct=0.0;
		double cosSimilarity=0.0;
		
		vectorProduct=calculator.calculateVectorProduct(vec1.getTags(), vec2.getTags());
		cosSimilarity=vectorProduct/(vec1Modulo*vec2Modulo);
		
		if(cosSimilarity<0.0){
			System.out.println("com.tools.KnowledgetTagVecsSimilarityCalculator.java:warning:相似度出现负值，请检查输入参数");
			return 0.0;
		}
		return cosSimilarity;
	
		
	}
	private double calculateModulo(double[] vec) throws Exception{
		
		double result=0.0;
		for(double i:vec){
			result+= i*i;
		}
		return Math.sqrt(result);
	}
	
	private double calculateVectorProduct(double[] vec1,double[] vec2){
		//检查两向量是否相等
		if(vec1.length!=vec2.length){
			System.out.println("error：两向量长度不等！");
			return 0;
		}
		double result=0.0;
		for(int i=0;i<vec1.length;i++){
			result+=vec1[i]*vec2[i];
		}
		return result;
		
	}
}
