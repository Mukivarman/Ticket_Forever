package com.forever;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.forever.WebService.WebSer;
import com.forever.dbentity.Admin;
import com.forever.dbentity.History;
import com.forever.dbentity.Mventity;
import com.forever.dbentity.Userdb;
import com.forever.dbentity.img;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;





@Controller
public class WebController {
	public WebController() {
		System.out.println("web controller reached");
		
	}
	
	@Autowired
	private WebSer webser;
	
	
	// home page controll
	
	  @GetMapping("/") public String homepage(HttpSession s,Model m) { 
		  Userdb u=(Userdb) s.getAttribute("user");
		  if(u!=null) {
			  return "redirect:/Home";
		  }else {
			  List<Mventity> mv= webser.getmvs();
		       m.addAttribute("mv",mv);
		  return "home";
		  }
	  
	  }
	  
		//Login page
		@GetMapping("/UserLogin")
		public String LoGinPage() {
			return "Login";
		}
		
		//Register page
		@GetMapping("/UserReg")
		public String userregister() {
			return "Register";
		}
		
		//after register
		@GetMapping("/Usersave")
		public String usrreg(Userdb user,Model m) {
			
			
		int	i=webser.SaveUser(user);
		if(i==1) {
			return "Login";
		}
		else if(i==0) {
			m.addAttribute("er", "User Already Exit");
			return "Register";
		}
		else {
			return "error";
		}
			
			
			
		}
		
		

		//check user LoginPage
		
		@PostMapping("/CheckLogin")
		public String Login(Userdb u,HttpSession s,Model m) {
			
			 Userdb l=(Userdb)s.getAttribute("user");
			if(l!=null) {
				return "redirect:/Home";
			}
			else {
			int i=webser.checklogin(u);
			  if(i==1) {
			Userdb udb=webser.user(u.getEmail());	
			
				s.setAttribute("user", udb);	
				return "redirect:/Home";
			}
			else {
				m.addAttribute("fail","Invalid Login");
				return "Login";
			}
		 }
		}
		
		
		//user Index After LOgin
		@GetMapping("/Home")
		public String index(Model m,HttpSession s) {
			Userdb u=(Userdb)s.getAttribute("user"); 

			if(u!=null) {
				m.addAttribute("u", u.getUsername());
				m.addAttribute("mv",webser.getmvs());
				
				return"index";  
			}
				return "redirect:/UserLogin";	
					
		}
	 
	
	//book process
	@GetMapping("/book")
	public String book(HttpSession s ,Model m,@RequestParam("id")int id) {
	Userdb u=	(Userdb) s.getAttribute("user");
	if(u==null) {
		
		return "redirect:/UserLogin";
	}
	else {
		s.setAttribute("mvid", id);
		return "redirect:/SelectShow";
	}	
 }
	
	//select date show process
		@GetMapping("/SelectShow")
		public String show(HttpSession s,Model m) {
			Userdb u=(Userdb) s.getAttribute("user");
			
			if(u!=null) {
				int mvid=(Integer) s.getAttribute("mvid");
				if(mvid!=0) {
				 Optional<Mventity> mv=webser.getOneMv(mvid);
				 if(!mv.isEmpty()) {
					 String d=(String) s.getAttribute("date");
					 String show=(String) s.getAttribute("show");
					 if(d!=null&&show!=null) {
						 s.removeAttribute("date");
						 s.removeAttribute("show");
						 m.addAttribute("max","Error :Max 5 Tickets Only");
					 }
					Mventity mve= mv.get();
					m.addAttribute("name",u.getUsername());
					m.addAttribute("mv",mve);
					s.setAttribute("mv", mve);
					LocalDate Ld=LocalDate.now();
					m.addAttribute("max",Ld.plusMonths(1));				
					m.addAttribute("mindate",Ld);
					String er=(String) s.getAttribute("er");
					System.out.println(er);
					if(er!=null){
						m.addAttribute("er",er);
						s.removeAttribute("er");
					}
					return "SelectShow";
			    }
				 return "redirect:/";
				}
					return "redirect:/";
				}
				
			return "redirect:/UserLogin";	
			}
		

		//Select Seat
		@PostMapping("/SelectSeat")
		public String selectSeat(@RequestParam("date")String date,
				@RequestParam("show")String show,HttpSession s,Model m)
		{
            Userdb u=(Userdb) s.getAttribute("user");
			Mventity mov=(Mventity) s.getAttribute("mv");
			
			if(u!=null) {
				
				if(mov!=null&&date!=null&&!show.isBlank()) {
					m.addAttribute("name",u.getUsername());
					m.addAttribute("mv",mov);
					s.setAttribute("date", date);
					s.setAttribute("show", show);
					m.addAttribute("date", date);
					m.addAttribute("show", show);
					m.addAttribute("seat",mov.getMvseat());
					List<Integer> l=webser.getseats(mov.getMvname(),date,show);
					m.addAttribute("bookedseat", l);
					return "TicketList";
			  
				
				}
					return "redirect:/";
				}
				
			return "redirect:/UserLogin";	
			}
		

		//completed page
		@PostMapping("/Completed")
		public String completwepage(@RequestParam("seatnum")String seat,
				@RequestParam("ticketcount")String ticketcount,
				@RequestParam("totalprice")String totalprice,
				HttpSession s,Model m)
				 {
			  Userdb u=(Userdb) s.getAttribute("user");
				Mventity mov=(Mventity) s.getAttribute("mv");
				
				if(u!=null) {
					
					if(mov!=null&&!seat.isEmpty()&&!ticketcount.isBlank()&&!totalprice.isBlank()) {
					String[] s1=seat.split(",");
					System.out.println(s1.length);
					int f=0;
					if(s1.length<=5) {
						String sdate=(String) s.getAttribute("date") ;
						String stime =(String) s.getAttribute("show");
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
						else {
							return "redirect:/";
						}
			for(int i=0;i<s1.length;i++) {
					 String mvname=mov.getMvname();
					 int  price=mov.getMvprice();
					   LocalDate cdate=LocalDate.now();
						LocalTime ctime=LocalTime.now();
						
						System.out.println(stime);
						LocalDate sd=LocalDate.parse(sdate);
						
						int seatnum=Integer.parseInt(s1[i]);
						
						History hs =new History();
						hs.setBookingdate(cdate);
						hs.setBookingtime(ctime);
						hs.setMoviename(mvname);
						hs.setPrice(price);
						hs.setSeatnum(seatnum);
						hs.setShowdate(sd);
						hs.setShowtime(st);
						u.getUsers().add(hs);
						hs.setUsers(u);
					    f=	webser.savehistory(hs);					
											
						}
			//send mail
			            webser.sendmail(mov.getMvname(),st,sdate,u, totalprice,seat);	
						m.addAttribute("mv",mov);
						m.addAttribute("date",sdate);
						m.addAttribute("show",st);
						m.addAttribute("seat",seat);
						m.addAttribute("total",totalprice);
						
			return   f==1? "/Completed":"/"	 ;
					}
					else {
						s.setAttribute("er", "Only 5 Bookings Allowed");
						return "redirect:/SelectShow";
					}
						
						
					}
						return "redirect:/";
					}
					
				return "redirect:/UserLogin";	
				}
			
				
		
		//order history
		
		@GetMapping("/OrderHistory")
		public String orderhistory( HttpSession s,Model m) {
			Userdb u=(Userdb)s.getAttribute("user");
			if(u!=null) {
			List<History> h=	webser.history(u.getUserid());
			System.out.println(h);
			m.addAttribute("his", h);
			return "OrderHistory";
			}
			return "redirect:/UserLogin";
		}

//movie add		
		
		//addmv
		
		 @PostMapping("/addmv")
		    public String addMovie(Mventity mv,@RequestParam("img") MultipartFile mf,Model m) throws IOException, SerialException, SQLException   {
			 			byte[]b=mf.getBytes();
			 		
				int i =   webser.savemovie(mv,b);	      
		            
				return i==1? "redirect:/viewadmin":"error";
		
		 }
		 
		 //remove movie
		 
		 @PostMapping("/deletemv")
		 public String removemv(@RequestParam("Mvid") int id,Model m) {
			 int i=0;
			 i=webser.removemovie(id);
			
			return i==1?"redirect:/viewadmin":"error";
			 
			 
		 }	
		
		  //fetch images
		  @GetMapping("/display")
		    public ResponseEntity<byte[]> displayImage(@RequestParam("id") int id) throws IOException, SQLException
		    {
		    	 img i = webser.viewById(id);
		    	  if (i == null) {
		    		  return ResponseEntity.notFound().build();
		    	    }
		      	       
		       byte[] ib= i.getData();
		        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(ib);
		    }
		    
		    
		
		
	//admin	
		
		//user page
		@GetMapping("/UserPage")
		public String userpage(HttpSession s, Model m) {
			Userdb u=	(Userdb) s.getAttribute("user");
			String up=(String) s.getAttribute("update");
			if(u!=null) {
				Userdb usr=webser.user(u.getEmail());
				
				m.addAttribute("usr",usr);
				if(up!=null) {
					m.addAttribute("update",up);
					s.removeAttribute("update");
				}
		
				
				return "UserPage";
			}
			else {
				
				return "redirect:/UserLogin";
			}	
			
			
		}
		
		
		
		//edit user
	@PostMapping("/EditUser")
	public String edituser(HttpSession s ,Model m,@RequestParam("uname")String name,
		@RequestParam("uemail")String email,@RequestParam("upass")String pass) 
	{
			int i=0;
			 Userdb l=(Userdb)s.getAttribute("user");
			 if(l!=null) {
			 i=	webser.updateuser(l, name,email,pass);
			 s.setAttribute("update", "Succcesfully Update Id");
			 return "redirect:/UserPage";
		}else {
			return i==1 ?"redirect:/UserLogin":"error";
		}
	}
				
				
	// user log out 
	@GetMapping("/Logout")
	public String logout(HttpSession s) {
		s.removeAttribute("user");
		s.removeAttribute("mv");
		s.removeAttribute("date");
		s.removeAttribute("show");
		s.removeAttribute("mvid");
				
		return "redirect:/";
	}
	
	
	//get all user
	@GetMapping("/GetAllUser")
	public String getalluser(HttpSession s,Model m) {
		Admin a=(Admin)s.getAttribute("admin");
		if(a!=null) {
			List<Userdb> l=webser.alluser();
			
			m.addAttribute("usr",l);
			
			return "Allusers";
		}
		else {
			 return "redirect:/AdminLogin";
		}
		
		
	}
		
		//admin register
				
	@PostMapping("/admin_register")
	public String regadmin(Admin admin,Model m) {
		if(admin!=null) {
		int i=webser.SaveAdmin(admin);
		return i==1? "redirect:/viewadmin":"error";
	}
		return "error";
	}
	
	//delete admin
	@PostMapping("/deleteadmin")
	public String deleteAdmin(@RequestParam("Aid") int adminId ,Model m) {
	   int i = webser.removeAdmin(adminId);		 
		return i==1?"redirect:/viewadmin":"error";
	}
	
				
	//Admin login
	@GetMapping("/AdminLogin")
	public String adminlogin( HttpSession s,Model m) {
	String msg=	(String) s.getAttribute("eradmin");
	Admin admin=(Admin)s.getAttribute("admin");
	if(admin!=null) {
		return "redirect:/viewadmin";
	}
	else {
		if(msg!=null) {
			m.addAttribute("er",msg);
			s.removeAttribute("eradmin");
		}
		return "AdminLogin";
	}
	}
	
	//admin page
	@GetMapping("/viewadmin")
	public String afterAdminpages(HttpSession s,Model m) {
		Admin a=(Admin)s.getAttribute("admin");
		if(a!=null) {
			 m.addAttribute("admins",webser.alladmins());
			  m.addAttribute("mv",webser.getmvs());
			return "Admin";
		}
		else {
			 return "redirect:/AdminLogin";
		}
	}

	//check admin

	@PostMapping("/admin")
	public String Admipage(Model m,HttpSession s,Admin a) {
		Userdb u=(Userdb)s.getAttribute("user");
		Admin admin=(Admin) s.getAttribute("admin");
		if(u!=null) {
			System.out.println("inside come");
			return "/";
		}
		else {
			if(admin!=null) {
									
					return "redirect:/viewadmin";
					
				}
			
		if(a!=null) {
			 int i =webser.checkadmin(a);
			 if(i==1) {
				 s.setAttribute("admin", a);
									
				 return "redirect:/viewadmin";
			 }
			 else {
				 s.setAttribute("eradmin", "invalid login");
				 return "redirect:/AdminLogin";
			 }
		}
		else {
			return " redirect:/AdminLogin";
		
		}
		}
	}
	
	
	//history for admin
	@GetMapping("/AllHistory")
	public String AllHistory(HttpSession s,Model m) {
		Admin a=(Admin)s.getAttribute("admin");
		if(a!=null) {
			List<History> l=webser.getallhistory();
			
			m.addAttribute("his",l);
			
			return "TotalOrderHistory";
		}
		else {
			 return "redirect:/AdminLogin";
		}
		
		
	}
	
	//admin log out
	@GetMapping("/LogoutAdmin")
	public String AdminLogout(HttpSession s)
	{
		Admin a=(Admin)s.getAttribute("admin");
		if(a!=null) {
			s.removeAttribute("admin");
			return "redirect:/";
		}
		else {
			return "redirect:/AdminLogin";
		}
	}
	
}
