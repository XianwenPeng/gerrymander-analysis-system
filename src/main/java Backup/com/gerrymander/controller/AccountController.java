package com.gerrymander.controller;

import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gerrymander.beans.User;
import com.gerrymander.constant.ControllerAttributes;
import com.gerrymander.service.EmailService;
import com.gerrymander.service.UserService;
import com.nulabinc.zxcvbn.Strength;
import com.nulabinc.zxcvbn.Zxcvbn;

@Controller
public class AccountController {
	
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	private UserService userService;
	private EmailService emailService;
	private User user;
	
	@Autowired
	public AccountController(BCryptPasswordEncoder bCryptPasswordEncoder,
			UserService userService, EmailService emailService) {
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.userService = userService;
		this.emailService = emailService;
	}
	
	@RequestMapping(value="/login", method = RequestMethod.GET)
	public ModelAndView showLoginPage(ModelAndView modelAndView, User user){
		modelAndView.addObject("user", user);
		modelAndView.setViewName("login");
		return modelAndView;
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView processLoginForm(ModelAndView modelAndView, @Valid User user, 
			BindingResult bindingResult, HttpServletRequest request) {
		// Lookup user in database by e-mail
		User userExists = userService.findByEmail(user.getEmail());		
		if (userExists != null) {
			if(userExists.getPassword().equals(user.getPassword())) {
				modelAndView.setViewName("index");
				return modelAndView;
			}
			modelAndView.addObject("confirmationMessage", "Password entered invalid");
			modelAndView.setViewName("login");
			bindingResult.reject("email");
		}
		if (bindingResult.hasErrors()) { 
			modelAndView.setViewName("login");		
		} else { 
			modelAndView.addObject("emailInvalidMessage", "Email has not been registered");
			modelAndView.setViewName("login");
		}
		return modelAndView;
	}
	
	// Return registration form template
	@RequestMapping(value="/register", method = RequestMethod.GET)
	public ModelAndView showRegistrationPage(ModelAndView modelAndView, User user){
		modelAndView.addObject("user", user);
		modelAndView.setViewName("register");
		return modelAndView;
	}
	
	// Process form input data
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView processRegistrationForm(ModelAndView modelAndView, @Valid User user, 
			BindingResult bindingResult, HttpServletRequest request) {
		// Lookup user in database by e-mail
		User userExists = userService.findByEmail(user.getEmail());
		if (userExists != null) {
			modelAndView.addObject("alreadyRegisteredMessage", "Oops!  There is already a user registered with the email provided.");
			modelAndView.setViewName("register");
			bindingResult.reject("email");
		}
		if (bindingResult.hasErrors()) { 
			modelAndView.setViewName("register");		
		} else { 
			user.setRole(ControllerAttributes.USER_REGULAR);
		    userService.saveUser(user);
		    modelAndView.setViewName("login");		
		}
		return modelAndView;
	}
	
	public boolean isAdmin(@Valid User user) {
		if(user.getRole().equals(ControllerAttributes.USER_REGULAR))
			return false;
		return true;
	}
	
}