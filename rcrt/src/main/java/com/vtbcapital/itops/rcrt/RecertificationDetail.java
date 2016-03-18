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
	
	@Column(name="Field1")
	private String field1;
	
	@Column(name="Field2")
	private String field2;
	
	@Column(name="Field3")
	private String field3;	
	
	@Column(name="Field4")
	private String field4;	
	
	@Column(name="Field5")
	private String field5;
	
	
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
	
	
	public String getField1() {
		return field1;
	}
	public void setField1(String field1) {
		this.field1 = field1;
	}

	public String getField2() {
		return field2;
	}
	public void setField2(String field2) {
		this.field2 = field2;
	}

	public String getField3() {
		return field3;
	}
	public void setField3(String field3) {
		this.field3 = field3;
	}

	public String getField4() {
		return field4;
	}
	public void setField4(String field4) {
		this.field4 = field4;
	}

	public String getField5() {
		return field5;
	}
	public void setField5(String field5) {
		this.field5 = field5;
	}
	
	public String getField(int index) {
		switch (index) {
			case 0: return getField1();
			case 1: return getField2();
			case 2: return getField3();
			case 3: return getField4();
			case 4: return getField5();
			default: return "";
		}
	}
	
	public void setField(int index, String val) {
		switch (index) {
			case 0: setField1(val); break;
			case 1: setField2(val); break;
			case 2: setField3(val); break;
			case 3: setField4(val); break;
			case 4: setField5(val); break;
		}
	}



}
