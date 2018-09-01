package com.gerrymander.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gerrymander.beans.Work;
import com.gerrymander.beans.WorkItem;
import com.gerrymander.repository.*;

@Service("workService")
public class WorkService {

	private WorkRepository workRepository;
	private WorkItemRepository workItemRepository;
	
		@Autowired
		public WorkService(WorkRepository workRepository, WorkItemRepository workItemRepository) {
			this.workRepository = workRepository;
			this.workItemRepository = workItemRepository;
		}
		
		public WorkService() {
			
		}
		
		public List<Work> findByEmailAndWorkName(String email, String workName) {
			return workRepository.findByEmailAndWorkName(email, workName);
		}
	
		
		public List<Work> findByEmail(String email){
			return workRepository.findByEmail(email);
		}
		
		public List<Work> findAll(){
			return workRepository.findAll();
		}
		
		public void save(Work work) {
			workRepository.save(work);
		}
		
		public List<WorkItem> findWorkItemByEmail(String email){
			return workItemRepository.findByEmail(email);
		}
		
		public WorkItem findWorkItemById(int Id) {
			return workItemRepository.findById(Id);
		}
		
		public void saveWorkItem(WorkItem workItem) {
			workItemRepository.save(workItem);
		}
		
		public List<WorkItem> findAllWorkItem(){
			return workItemRepository.findAll();
		}
		
		public List<Work> findAllWork(){
			return workRepository.findAll();
		}
		
//		public Work findByEmailAndStatus
}
