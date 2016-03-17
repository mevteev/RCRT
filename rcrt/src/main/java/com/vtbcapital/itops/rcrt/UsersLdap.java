package com.vtbcapital.itops.rcrt;

import javax.persistence.Transient;

public class UsersLdap extends Users {
	private String distinguishedName;
	
	public UsersLdap(String userName, String email, String domain, String cn) {
		super(userName, email, domain);
		this.setCn(cn);
	}

	public String getCn() {
		return distinguishedName;
	}

	public void setCn(String cn) {
		this.distinguishedName = cn;
	}
}
