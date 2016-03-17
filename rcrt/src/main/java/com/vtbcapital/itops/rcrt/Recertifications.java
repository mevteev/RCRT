package com.vtbcapital.itops.rcrt;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity(name="Recertifications")
public class Recertifications {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private	int id;
	
	@Column(name="StartDate")
	private Date startDate;
	
	@Column(name="EndDate")
	private Date endDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="Id_Application")
	private Applications application;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="Id_User")
	private Users user;
	
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "recertification")
	private Set<RecertificationDetail> recertificationDetail = new HashSet<RecertificationDetail>(0);


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

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
	
	
	

}
