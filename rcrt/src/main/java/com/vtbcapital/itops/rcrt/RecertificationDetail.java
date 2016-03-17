package com.vtbcapital.itops.rcrt;

import java.util.List;
import java.util.Vector;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity(name="RecertificationDetails")
public class RecertificationDetail {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private	int id;
	
	@Column(name="Login")
	private String login;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="Id_User")
	private Users user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="Id_Manager")
	private Users lineManager;
	

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="Id_Recertification")
	private Recertifications recertification;
	
	@Transient
	private String userName;
	
	@Transient
	private String email;
	
	@Transient
	private Users prevLineManager;
	
	@Transient
	private Vector<Users> proposedLineManagers;
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}
	public Users getLineManager() {
		return lineManager;
	}
	public void setLineManager(Users lineManager) {
		this.lineManager = lineManager;
	}
	public Users getPrevLineManager() {
		return prevLineManager;
	}
	public void setPrevLineManager(Users prevLineManager) {
		this.prevLineManager = prevLineManager;
	}
	
	public void findUserByEmail() {
		
	}
	
	
	public Vector<Users> getProposedLineManagers() {
		return proposedLineManagers;
	}
	public void setProposedLineManagers(Vector<Users> proposedLineManagers) {
		this.proposedLineManagers = proposedLineManagers;
	}
	public Recertifications getRecertification() {
		return recertification;
	}
	public void setRecertification(Recertifications recertification) {
		this.recertification = recertification;
	}


}
