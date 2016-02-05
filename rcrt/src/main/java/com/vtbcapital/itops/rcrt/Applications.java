package com.vtbcapital.itops.rcrt;

import java.util.ArrayList;
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


import org.hibernate.Query;
import org.hibernate.Session;



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
	
	@Column(name="Server")
	private
	String server;
	
	@Column(name="DatabaseName")
	private 
	String databaseName;
	
	@Column(name="UserName")
	private 
	String userName;
	
	@Column(name="Password")
	private
	String password;
	
	@Column(name="Statement")
	private String statement; 
	
	
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
	
	
	public String toString() {
		return getName();
	}
	
	public static ArrayList<Applications> getApplications() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		Query q = session.createQuery("from Applications");
		
		@SuppressWarnings("unchecked")
		ArrayList<Applications> result = new ArrayList<Applications>(q.list());
		
		session.close();

		return result;
		
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStatement() {
		return statement;
	}

	public void setStatement(String statement) {
		this.statement = statement;
	}
	
	

}
