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
import javax.persistence.OneToMany;

@Entity(name="Applications")
public class Applications {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private
	int id;
	
	@Column(name="Name")
	private
	String name;
	
	@Column(name="Date")
	private
	Date date;
	
	@Column(name="Interval")
	private
	int interval;
	
	@Column(name="Notes")
	private
	String notes;
	
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "application")
	private Set<Approvers> approvers = new HashSet<Approvers>(0);

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Set<Approvers> getApprovers() {
		return approvers;
	}

	public void setApprovers(Set<Approvers> approvers) {
		this.approvers = approvers;
	}
	
	

}
