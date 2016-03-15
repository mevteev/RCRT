package com.vtbcapital.itops.rcrt;

import java.util.HashSet;
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
	
	
	
	

}
