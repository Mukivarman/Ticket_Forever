package com.forever.dbentity;



  

import java.time.LocalDate;
import java.time.LocalTime;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class History {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long hid;
	
	private String moviename;
	private LocalDate bookingdate;
	
	private LocalTime bookingtime;

	private LocalDate showdate;
	private String showtime;
	private int Seatnum;
	private int price;
	@ManyToOne(fetch = FetchType.EAGER)
	private Userdb users;
	
public History() {
	
}

public History(Long hid, String moviename, LocalDate bookingdate, LocalTime bookingtime, LocalDate showdate, String showtime,
		int seatnum, int price, Userdb users) {
	super();
	this.hid = hid;
	this.moviename = moviename;
	this.bookingdate = bookingdate;
	this.bookingtime = bookingtime;
	this.showdate = showdate;
	this.showtime = showtime;
	Seatnum = seatnum;
	this.price = price;
	this.users = users;
}

public Long getHid() {
	return hid;
}

public void setHid(Long hid) {
	this.hid = hid;
}

public String getMoviename() {
	return moviename;
}

public void setMoviename(String moviename) {
	this.moviename = moviename;
}

public LocalDate getBookingdate() {
	return bookingdate;
}

public void setBookingdate(LocalDate bookingdate) {
	this.bookingdate = bookingdate;
}

public LocalTime getBookingtime() {
	return bookingtime;
}

public void setBookingtime(LocalTime bookingtime) {
	this.bookingtime = bookingtime;
}

public LocalDate getShowdate() {
	return showdate;
}

public void setShowdate(LocalDate showdate) {
	this.showdate = showdate;
}

public String getShowtime() {
	return showtime;
}

public void setShowtime(String showtime) {
	this.showtime = showtime;
}

public int getSeatnum() {
	return Seatnum;
}

public void setSeatnum(int seatnum) {
	Seatnum = seatnum;
}

public int getPrice() {
	return price;
}

public void setPrice(int price) {
	this.price = price;
}

public Userdb getUsers() {
	return users;
}

public void setUsers(Userdb users) {
	this.users = users;
}

@Override
public String toString() {
	return "History [hid=" + hid + ", moviename=" + moviename + ", bookingdate=" + bookingdate + ", bookingtime="
			+ bookingtime + ", showdate=" + showdate + ", showtime=" + showtime + ", Seatnum=" + Seatnum + ", price="
			+ price + ", users=" + users + "]";
}
	
	
	
}
