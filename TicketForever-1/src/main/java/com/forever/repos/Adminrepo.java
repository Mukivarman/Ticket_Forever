package com.forever.repos;

import java.util.List;

import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import com.forever.dbentity.Admin;


public interface Adminrepo extends JpaRepository<Admin,Integer> {
	
	boolean existsByAdminname(String name);
	boolean existsByAdminnameAndPassword(String adminName, String password);
	
}
