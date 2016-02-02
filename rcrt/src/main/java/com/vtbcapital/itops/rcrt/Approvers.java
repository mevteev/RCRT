package com.vtbcapital.itops.rcrt;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name="Approvers")
public class Approvers {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private	int id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="Id_Application")
	private Applications application;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="Id_User")
	private Users user;
	
	@Column(name="Title")
	private String title;
	
	@Column(name="StartDate")
	private Date startDate;
	
	@Column(name="Order")
	private int order;

	public Applications getApplication() {
		return application;
	}

	public void setApplication(Applications application) {
		this.application = application;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}
	
	

	

}
