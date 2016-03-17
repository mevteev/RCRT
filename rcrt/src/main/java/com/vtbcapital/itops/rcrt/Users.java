package com.vtbcapital.itops.rcrt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity(name="Users")
public class Users {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private	int id;
	
	@Column(name="Name")
	private String name;
	
	@Column(name="EMail")
	private String email;
	
	@Column(name="DomainAccount")
	private String domainAccount;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	private Set<Approvers> approvers = new HashSet<Approvers>(0);
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	private Set<Recertifications> recertifications = new HashSet<Recertifications>(0);
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	private Set<RecertificationDetail> recertificationDetail = new HashSet<RecertificationDetail>(0);
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "lineManager")
	private Set<RecertificationDetail> recertificationDetailLM = new HashSet<RecertificationDetail>(0);

	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name.trim();
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email.trim();
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDomainAccount() {
		return domainAccount.trim();
	}

	public void setDomainAccount(String domainAccount) {
		this.domainAccount = domainAccount;
	}
	
	public Set<Approvers> getApprovers() {
		return approvers;
	}

	public void setApprovers(Set<Approvers> approvers) {
		this.approvers = approvers;
	}
	
	
	public Users() {
		
	}
	
	public Users(String userName, String email, String domain) {
		this.setName(userName);
		this.setEmail(email);
		this.setDomainAccount(domain);
	}

	public String toString() {
		return getName();
	}
	
	public boolean equals(Object that) {
		if (this == that)
			return true;
		if (that == null)
			return false;
		if (!(that instanceof Users))
			return false;
		return ((Users) that).getEmail().equals(this.getEmail()) && ((Users) that).getName().equals(this.getName());
	}
	
	public List<Users> getPreviousLineManagers(Applications app) {
		/*
		select u.name,m.name, r.startdate, a.name from [RCRT].Users u
		 inner join [RCRT].RecertificationDetails rd on rd.id_user = u.id
		 inner join [RCRT].Users m on rd.id_manager = m.id
		 inner join [RCRT].Recertifications r on rd.id_recertification = r.id
		 inner join [RCRT].Applications a on r.id_application = a.id
		 */
		String sqlQuery = "select u, rd.lineManager, r.startDate, a.name from Users u " + 
				"inner join u.recertificationDetail rd " +
				"inner join rd.recertification r " +
				"inner join r.application a " +
				"where u.id = " + this.getId() +
				"and a.id = " + app.getId() + 
				"order by r.startDate desc";
		
		
		/*+
				 "inner join u.recertificationDetail.lineManager m  " +
				 "inner join u.recertificationDetail.recertification " +
				 "inner join u.recertificationDetail.recertification.application " +
				 "where a.id = " + app.getId() +
				 "and u.id = " + this.getId();*/
		
		
		List<Object[]> res = HibernateUtil.select(sqlQuery);
		List<Users> approvers = new ArrayList<Users>();
		
		for (Object[] arr : res) {
			approvers.add((Users)arr[1]);
		}
		
		return approvers;
	}
	
	public Users getPreviousLineManager(Applications app) {
		List<Users> approvers = getPreviousLineManagers(app);
		if (!approvers.isEmpty()) {
			return approvers.get(0);
		} else {
			return null;
		}
	}
}
