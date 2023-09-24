package com.forever.dbentity;

import java.sql.Blob;
import java.util.Arrays;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class img {
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private int id;

	    @Lob
	    private byte[] data;
	    
	    public img() {
			// TODO Auto-generated constructor stub
		}
	    
	 

		@ManyToOne(fetch = FetchType.EAGER)
	    @JoinColumn(name = "mvid")
	    private Mventity mvimg;
		
		   
	    public img(int id, byte[] data, Mventity mvimg) {
			super();
			this.id = id;
			this.data = data;
			this.mvimg = mvimg;
		}


		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public byte[] getData() {
			return data;
		}

		public void setData(byte[] data) {
			this.data = data;
		}

		public Mventity getMvimg() {
			return mvimg;
		}

		public void setMvimg(Mventity mvimg) {
			this.mvimg = mvimg;
		}


		


	

}
