package com.rays.pro4.Bean;

import java.util.Date;

public class LessonBean extends BaseBean {

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	private String name;
	private String author;
	private Date dob ;
	private String subject;
	@Override
	public String getkey() {
		// TODO Auto-generated method stub
		return subject + " ";
	}
	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return subject;
	}
}
