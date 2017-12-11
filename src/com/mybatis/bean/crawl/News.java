package com.mybatis.bean.crawl;

import java.util.Date;

public class News {

	String id;
	String company_name;
	Date time;
	String title;
	String link;
	String summary;
	String content;
	int repetition;
	String from;
	String comment;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public int getRepetition() {
		return repetition;
	}
	public void setRepetition(int repetition) {
		this.repetition = repetition;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public News() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	@Override
	public String toString() {
		return "News [id=" + id + ", company_name=" + company_name + ", time="
				+ time + ", title=" + title + ", link=" + link + ", summary="
				+ summary + ", content=" + content + ", repetition="
				+ repetition + ", from=" + from + ", comment=" + comment + "]";
	}
	public News(String id, String company_name, Date time, String title,
			String link, String summary, String content, int repetition,
			String from, String comment) {
		super();
		this.id = id;
		this.company_name = company_name;
		this.time = time;
		this.title = title;
		this.link = link;
		this.summary = summary;
		this.content = content;
		this.repetition = repetition;
		this.from = from;
		this.comment = comment;
	}
	
	
}
