package com.forever.dbentity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Admin")
public class Admin {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "aid")
	private int aid;
	
	@Column(name = "adminname")
	private String adminname;
	
	@Column(name = "password")
	private String password;
	
	public Admin() {
		System.out.println("admin entity");
	}

	public Admin(int aid, String adminName, String password) {
		super();
		this.aid = aid;
		adminname = adminName;
		this.password = password;
	}

	public int getAid() {
		return aid;
	}

	public void setAid(int aid) {
		this.aid = aid;
	}

	public String getAdminName() {
		return adminname;
	}

	public void setAdminName(String adminName) {
		adminname = adminName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Admin [aid=" + aid + ", AdminName=" + adminname + ", password=" + password + "]";
	}
	
	
	
	
	
	
}
