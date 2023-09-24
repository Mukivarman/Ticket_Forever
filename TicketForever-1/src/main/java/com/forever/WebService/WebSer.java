package com.forever.WebService;

import java.awt.Image;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Date;
import java.util.Base64;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.Properties;

import org.hibernate.tuple.InMemoryValueGenerationStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.forever.dbentity.Admin;
import com.forever.dbentity.History;
import com.forever.dbentity.Mventity;
import com.forever.dbentity.Userdb;
import com.forever.dbentity.img;
import com.forever.repos.Adminrepo;
import com.forever.repos.Historyrepo;
import com.forever.repos.Images;
import com.forever.repos.Mvrepo;
import com.forever.repos.UserRepo;


@Service
public class WebSer {
	
	@Autowired
	private Adminrepo adminrepo;
	@Autowired
	private Mvrepo mvrepo;
	@Autowired
	private UserRepo userrepo;
	@Autowired
	private Images images;
	@Autowired
	private Historyrepo his;
	@Autowired
	private JavaMailSender mail;
	
		

	//add admin
	
	public int SaveAdmin(Admin admin) {
		String name=admin.getAdminName();
		
		boolean mn=adminrepo.existsByAdminname(name);
		System.out.println(mn);
		
		if(admin.getAdminName()!=null&&admin.getPassword()!=null&&!mn) {
			
		adminrepo.save(admin);
		return 1;
		}
		else {
			return 0;
		}
	}
	

		//fetch all admin
	
		public List<Admin> alladmins(){
			List<Admin> n=adminrepo.findAll();
			
			return n;

		}
		
		//check admin
		public int checkadmin(Admin a) {
 boolean b=adminrepo.existsByAdminnameAndPassword(a.getAdminName(), a.getPassword());
		if(b) {
			return 1;
		}
			else {
				return 0;
			}
		
		}
		
		//delete  admin
		public int removeAdmin(int adminId) {
		    adminrepo.deleteById(adminId);
		    return 1;
		}
		
		
		
		//save movies
		  
		public int savemovie(Mventity mv,byte[] bt) {
		 String m=mv.getMvname();
		 System.out.println("gu");
		 System.out.println(m);
		 boolean b=mvrepo.existsByMvname(m);
		 if(!b) {
			 System.out.println("ytdty");
			 img i = new img(); 
			 i.setData(bt);
			 mv.getImages().add(i);
			 
			  i.setMvimg(mv);
	
			 mvrepo.save(mv);
			  return 1; 
		 }
		   		return 0;  
			  
		
		  }

			
		 
		  
		  //delete movie
		  public int removemovie(int Id) {
			  System.out.println(Id);
			  images.deleteById(Id);
			  mvrepo.deleteById(Id);
			    return 1;
			}
		  
		 
		
		//fetch all movies
		public List<Mventity> getmvs() {
		
			List<Mventity> n=mvrepo.findAll();
		
			return n;
			
		}
		
		//get images from database
		 public img viewById(int id) {
		        return images.findById(id).get();
		    }
		

		// Save new User
		public int SaveUser(Userdb user) {
			String n=user.getEmail();
			boolean b=userrepo.existsByEmail(n);
			if(b) {
				return 0;
			}else {
			if(user.getEmail()!=null&&user.getPassword()!=null&&user.getUsername()!=null) {
				userrepo.save(user);
				return 1;
			}
			
			else {
				return -1;
			}
			}
		}
		//check

		public int checklogin(Userdb u) {
			
			if(u.getEmail()!=null&&u.getPassword()!=null) {
			boolean n=userrepo.existsByEmailAndPassword(u.getEmail(),u.getPassword());
		
			if(n){
				return 1;
			}
			}
			return 0;
		}
		//get user
		@Cacheable(cacheNames = "login",key = "'Userdb'+#Email+#password")
		public Userdb user(String em) {
			Userdb l= userrepo.findByEmail(em);
			return l;
		}

		// get one movie
		public Optional<Mventity> getOneMv(int mvid) {
		return	mvrepo.findById(mvid);
		}

		

	
		//save history
		
		public int savehistory(History hs) {
			if(hs!=null) {
				his.save(hs);
				return 1;
			}
			
			return 0;
		}


//send mail
		public void sendmail(String mv,String st,String sdate ,Userdb u, String p, String seat) {
			
		SimpleMailMessage m=new SimpleMailMessage();
		m.setFrom("mugivarmans@gmail.com");
		m.setTo(u.getEmail());
		m.setSubject("Ticket Confirmation");
		m.setText("Hi"+" "+u.getUsername()+"\n \n This is From TicketForever.com"
				+"\t Thank you For using Booking Tickets From My Website"
				+"The Bookig Process Will Be Completed. please"
				+"Confirm Once Again \n \n"
				+"Movie       :"+mv+"\n \n"
				+"Date        :"+sdate+"\n \n"
				+"ShowTime    :"+st+"\n\n"
				+"SeatNumber  :"+seat+"\n \n"
				+"Total Price :Rs."+p+"\n \n"
				+"Thank you For Using Our Service And Visit Again. \n "
				+"Enjoy Yuor Movie...\n \n"
				+"note:This Mail Is AutoGenrated. Do Not Reply");
		mail.send(m);
		System.out.println("mail send");
		}
		
		//user check history
		public List<History> history(Long id) {
			
			List<History> l=his.findAllById(id);
	
			
			return l;
			
		}
		  
		
//check seats
		public List<Integer>  getseats(String mv, String date, String stime) {
			Date d=Date.valueOf(date);
			String st=null;
			if(stime.contains("9.00")){
				 st="Show-1 9:00Am";
				}
				else if(stime.contains("12.00")){
					st="Show-2 12:00pm";
				}
				else if(stime.contains("16.00")){
					st="show-3 04.pm";
				}
				else if(stime.contains("20.00")) {
					st="Show-4 08:pm";
				}
			
List<Integer> l=his.findSeatnumByMovieNameAndDateTime(mv, d, st);
         		System.out.println(l);
         		for(Integer i:l) {
         			System.out.println(i);
         		}

               return l ;
			
		}


		public int updateuser(Userdb l,String name,String email,String pass) {
			Optional<Userdb> user=userrepo.findById(l.getUserid());
				Userdb db=user.get();
				db.setEmail(email);
				db.setPassword(pass);
				db.setUsername(name);
				userrepo.save(db);
				return 1;
		
			
		}

//get all history
		public List<History> getallhistory() {
			List<History> l=his.findAllHistory();
			return l;	
		}
		
		//get all user
		public List<Userdb> alluser() {
			List<Userdb> l=userrepo.findAll();
					return l;
		}
				
	
		}

