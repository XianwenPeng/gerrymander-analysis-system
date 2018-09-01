package com.gerrymander.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gerrymander.beans.User;
import com.gerrymander.repository.UserRepository;

@Service("userService")
public class UserService {

	private UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	public User findByRole(String role) {
		return userRepository.findByRole(role);
	}
	
	public User findByStatus(String status) {
		return userRepository.findByStatus(status);
	}
	
	public void saveUser(User user) {
		userRepository.save(user);
	}
	
	public List<User> findAll(){
		return (List<User>) userRepository.findAll();
	}
	
	public void delete(User user) {
		userRepository.delete(user);
	}


}