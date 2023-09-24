package com.forever.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.forever.dbentity.Userdb;
import java.util.List;


public interface UserRepo extends JpaRepository<Userdb, Long>{
	
      boolean existsByEmail(String email);
     boolean existsByEmailAndPassword(String email, String password);
       Userdb findByEmail(String email);
       
}
