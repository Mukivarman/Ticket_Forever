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

import org.aspectj.weaver.tools.Trace;

@Entity
public class Mventity {
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	@Column(name = "mvid")
	private int mvid;
	@Column(name = "mvname")
	private String mvname;
	@Column(name = "mvprice")
	private int mvprice;
	@Column(name = "mvseat")
	private int mvseat;
	@Column(name = "mgenre")
	private String mgenre;
	
	

    @OneToMany(mappedBy = "mvimg", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<img> images = new ArrayList<>();




	public Mventity(int mvid, String mvname, int mvprice, int mvseat, String mgenre, List<img> images) {
		super();
		this.mvid = mvid;
		this.mvname = mvname;
		this.mvprice = mvprice;
		this.mvseat = mvseat;
		this.mgenre = mgenre;
		this.images = images;
	}

	public List<img> getImages() {
		return images;
	}

	public void setImages(List<img> images) {
		this.images = images;
	}

	public String getMgenre() {
		return mgenre;
	}

	public void setMgenre(String mgenre) {
		this.mgenre = mgenre;
	}

	public Mventity() {
		System.out.println("mv create");
	}

	public int getMvid() {
		return mvid;
	}

	public void setMvid(int mvid) {
		this.mvid = mvid;
	}

	public String getMvname() {
		return mvname;
	}

	public void setMvname(String mvname) {
		this.mvname = mvname;
	}

	public int getMvprice() {
		return mvprice;
	}

	public void setMvprice(int mvprice) {
		this.mvprice = mvprice;
	}

	public int getMvseat() {
		return mvseat;
	}

	public void setMvseat(int mvseat) {
		this.mvseat = mvseat;
	}

	

	
	
	


	

	
	



}
