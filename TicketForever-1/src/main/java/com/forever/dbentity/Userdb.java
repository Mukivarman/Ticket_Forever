package com.forever.dbentity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Userdb {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "userid")
	private Long userid;
	@Column(name = "username")
	private String username;
	@Column(name = "email")
	private String email;

	@Column(name = "password")
	private String password;
	
	@OneToMany(mappedBy ="users",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private List<History> historylist=new ArrayList<History>();
	
	public Userdb() {
	
	}

	public Userdb(Long userid, String username, String email, String password, List<History> users) {
		super();
		this.userid = userid;
		this.username = username;
		this.email = email;

		this.password = password;
		this.historylist = users;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<History> getUsers() {
		return historylist;
	}

	public void setUsers(List<History> users) {
		this.historylist = users;
	}
	
		



	
	
	
	

}
