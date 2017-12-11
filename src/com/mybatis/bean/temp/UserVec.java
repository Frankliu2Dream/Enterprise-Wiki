package com.mybatis.bean.temp;

import java.util.Arrays;

public class UserVec {

	String id;
	double[] tags;
	
	public UserVec(String id, double[] tags) {
		super();
		this.id = id;
		this.tags = tags;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double[] getTags() {
		return tags;
	}

	public void setTags(double[] tags) {
		this.tags = tags;
	}

	@Override
	public String toString() {
		return "UserVector [id=" + id + ", tags=" + Arrays.toString(tags) + "]";
	}
	
	
}
