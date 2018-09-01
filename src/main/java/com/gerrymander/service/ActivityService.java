package com.gerrymander.service;

import java.beans.Transient;
import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.auditing.AuditingHandler;
import org.springframework.data.domain.Persistable;
import org.springframework.stereotype.Service;

import com.gerrymander.beans.Activity;
import com.gerrymander.repository.ActivityRepository;

@Service("activityService")
@Transactional
public class ActivityService implements Persistable<ActivityService>{
	@Autowired
	private ActivityRepository activityRepository;
	
	private final EntityManager entityManager;
	
	public ActivityService(ActivityRepository activityRepository, EntityManager entityManager) {
		this.activityRepository = activityRepository;
		this.entityManager = entityManager;
	}
	
	public List<Activity> findAllByEmail(String email){
		return activityRepository.findAllByEmail(email);
	}
	
	public void saveActivity(Activity activity) {
		activityRepository.save(activity);
	}

	public ActivityRepository getActivityRepository() {
		return activityRepository;
	}

	public void save(Activity activity){
        //force version update even though no values have changed in the entity
		entityManager.persist(activity);
    }

	public void setActivityRepository(ActivityRepository activityRepository) {
		this.activityRepository = activityRepository;
	}

	@Override
	public ActivityService getId() {
		return null;
	}

	@Override
	@Transient
	public boolean isNew() {
		return true;
	}
	
}
