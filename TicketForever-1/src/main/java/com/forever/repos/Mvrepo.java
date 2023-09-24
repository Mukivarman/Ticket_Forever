package com.forever.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.forever.dbentity.Mventity;


public interface Mvrepo extends JpaRepository<Mventity,Integer>{
	
	boolean  existsByMvname(String name);
	
	    
}
