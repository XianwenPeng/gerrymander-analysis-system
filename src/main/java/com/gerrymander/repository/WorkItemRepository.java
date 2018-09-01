package com.gerrymander.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gerrymander.beans.*;

@Repository("workItemRepository")
public interface WorkItemRepository extends JpaRepository<WorkItem, Long> {
		
	List<WorkItem> findByEmail(String email);
		
	WorkItem findById(int Id);
}
