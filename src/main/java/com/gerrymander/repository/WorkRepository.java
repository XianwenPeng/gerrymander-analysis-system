package com.gerrymander.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gerrymander.beans.*;

@Repository("workRepository")
public interface WorkRepository extends JpaRepository<Work, Long> {
		
	List<Work> findByEmail(String email);
	
	List<Work> findByEmailAndWorkName(String email, String workName);
}
