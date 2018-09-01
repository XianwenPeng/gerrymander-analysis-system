package com.gerrymander.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.gerrymander.beans.Activity;
import com.gerrymander.beans.User;
import com.gerrymander.constant.ControllerAttributes;
import com.gerrymander.htmlObject.ManageUserObj;
import com.gerrymander.htmlObject.SelectBar;
import com.gerrymander.service.EmailService;
import com.gerrymander.service.*;


@Controller
public class AccountController {
	
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	private UserService userService;
	private ActivityService activityService;
	
	@Autowired
	public AccountController(BCryptPasswordEncoder bCryptPasswordEncoder,
			UserService userService, EmailService emailService, ActivityService activityService) {
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.userService = userService;
		this.activityService = activityService;
	}
	
	@RequestMapping(value="/gerrymander/account", method = RequestMethod.GET)
	public ModelAndView showAccountPage(ModelAndView modelAndView, Model model, User user){		
		User loggedIn = userService.findByStatus("online");
//		System.out.println("*************************************");
//		System.out.println(loggedIn.getFirstName());
//		System.out.println("*************************************");
		modelAndView.addObject("userAdmin", loggedIn);
		modelAndView.addObject("user", loggedIn);
		model.addAttribute("email", loggedIn.getEmail());
		model.addAttribute("firstName", loggedIn.getFirstName());
		model.addAttribute("lastName", loggedIn.getLastName());
		modelAndView.setViewName("regularAccountSetting");
		
		return modelAndView;
	}
	
	@RequestMapping(value="/gerrymander/account", method = RequestMethod.POST)
	public String AccountPage(ModelAndView modelAndView, Model model, User user){	
		User loggedIn = userService.findByStatus("online");
		User userExists = userService.findByEmail(loggedIn.getEmail());	
//		System.out.println("*************************************");
//		System.out.println(user.getEmail());
//		System.out.println(userExists.getFirstName());
//		System.out.println("*************************************");
//		
		boolean changed = false;
		if(!userExists.getFirstName().equals(user.getFirstName())) {
			userExists.setFirstName(user.getFirstName());
			changed = true;
		}
		if(!userExists.getLastName().equals(user.getLastName())) {
			userExists.setLastName(user.getLastName());
			changed = true;
		}
		if(user.getPassword() != "" && !bCryptPasswordEncoder.matches(user.getPassword(), userExists.getPassword())) {
			userExists.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			changed = true;
		}
		userService.saveUser(userExists);
		if(changed) {
			Activity activity = new Activity(userExists.getEmail(), "Changed	Account Info");
		    activity.setTime(getCurrentTimeUsingDate());
		    activityService.saveActivity(activity);
		}
//		User saveUser = new User(user.getId(), user.getEmail(), user.getPassword(), user.getFirstName(),
//				user.getLastName(), user.getRole(), user.getStatus());
		
//		SelectBar selectBar = new SelectBar();
//		modelAndView.getModel().put("selectBar", selectBar);
//		modelAndView.setViewName("gerrymander");
		
		return "redirect:/gerrymander";
	}
	
	@RequestMapping(value="/gerrymander/login", method = RequestMethod.GET)
	public ModelAndView showLoginPage(ModelAndView modelAndView, User user){
		modelAndView.addObject("userAdmin", user);
		modelAndView.setViewName("login");
		return modelAndView;
	}
	
	@RequestMapping(value = "/gerrymander/login", method = RequestMethod.POST)
	public ModelAndView processLoginForm(ModelAndView modelAndView, @Valid User user, 
			BindingResult bindingResult, HttpServletRequest request) {
		// Lookup user in database by e-mail
		User userExists = userService.findByEmail(user.getEmail());		
		if (userExists != null) {
			if(bCryptPasswordEncoder.matches(user.getPassword(), userExists.getPassword())) {
				SelectBar selectBar = new SelectBar();
				User loggedIn = new User(userExists.getId(), userExists.getEmail(), userExists.getPassword(), 
						userExists.getFirstName(), userExists.getLastName(), userExists.getRole(), "online");
//				System.out.println("\n*************************************");
//				System.out.println(loggedIn.getEmail());
//				System.out.println("*************************************\n");
				
				userService.saveUser(loggedIn);
				Activity activity = new Activity(loggedIn.getEmail(), "Log in");
			    activity.setTime(getCurrentTimeUsingDate());
			    activityService.saveActivity(activity);
				
				modelAndView.getModel().put("selectBar", selectBar);
				modelAndView.setViewName("redirect:/gerrymander");
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
	
	@RequestMapping(value="/gerrymander/register", method = RequestMethod.GET)
	public ModelAndView showRegistrationPage(ModelAndView modelAndView, User user){
		modelAndView.addObject("userAdmin", user);
		modelAndView.setViewName("register");
		return modelAndView;
	}
	
	@RequestMapping(value = "/gerrymander/register", method = RequestMethod.POST)
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
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			user.setStatus("offline");
		    userService.saveUser(user);
		    Activity activity = new Activity(user.getEmail(), "Register");
		    activity.setTime(getCurrentTimeUsingDate());
		    activityService.saveActivity(activity);
		    modelAndView.setViewName("login");		
		}
		return modelAndView;
	}
	
	@RequestMapping(value="/gerrymander/manage/addUser", method = RequestMethod.GET)
	public ModelAndView showAddUserPage(ModelAndView modelAndView){
		modelAndView.setViewName("addUserPage");
		User loggedIn = userService.findByStatus("online");
		modelAndView.getModel().put("userAdmin", loggedIn);
		User user = new User();
		modelAndView.getModel().put("user", user);
		return modelAndView;
	}
	
	@RequestMapping(value = "/gerrymander/manage/addUser", method = RequestMethod.POST)
	public ModelAndView processAddUserForm(ModelAndView modelAndView, @ModelAttribute("user") User user, 
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
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			user.setStatus("offline");
		    userService.saveUser(user);
		    Activity activity = new Activity(user.getEmail(), "Register");
		    activity.setTime(getCurrentTimeUsingDate());
		    activityService.saveActivity(activity);
		    modelAndView.setViewName("redirect:/gerrymander/manage/addUser");		
		}
		return modelAndView;
	}
	
	@RequestMapping(value="/gerrymander/manage/userActivity", method = RequestMethod.GET)
	public ModelAndView showUserActivityPage(ModelAndView modelAndView, Model model, User user){		
		modelAndView.setViewName("findUserActivityPage");
		User loggedIn = userService.findByStatus("online");
		modelAndView.getModel().put("userAdmin", loggedIn);
		modelAndView.getModel().put("totalUsers", userService.findAll().size());
//		modelAndView.getModel().put("activityList", activityService.findAllByEmail(user.getEmail()));
//		modelAndView.getModel().put("usersList", userService.findAll());
		return modelAndView;
	}
	
	@RequestMapping(value="/gerrymander/manage/userActivity", method = RequestMethod.POST)
	public ModelAndView postUserActivityPage(ModelAndView modelAndView, Model model, User user){		
		User loggedIn = userService.findByStatus("online");
		modelAndView.getModel().put("userAdmin", loggedIn);
		User existUser = userService.findByEmail(user.getEmail());
		if(existUser == null)
			modelAndView.setViewName("redirect:/gerrymander/manage/userActivity");
		else {
			List<Activity> activities = activityService.findAllByEmail(existUser.getEmail());
			modelAndView.setViewName("showActivityPage");
			modelAndView.getModel().put("activityList", activities);
		}
//		modelAndView.getModel().put("activityList", activityService.findAllByEmail(user.getEmail()));
//		modelAndView.getModel().put("usersList", userService.findAll());
		return modelAndView;
	}
	
//	@RequestMapping(value="/gerrymander/manage/userActivity", method = RequestMethod.GET)
//	public ModelAndView showUserActivityPage(ModelAndView modelAndView, Model model, User user){		
//		modelAndView.setViewName("findUserActivity");
//		User loggedIn = userService.findByStatus("online");
//		modelAndView.getModel().put("userAdmin", loggedIn);
////		modelAndView.getModel().put("activityList", activityService.findAllByEmail(user.getEmail()));
////		modelAndView.getModel().put("usersList", userService.findAll());
//		return modelAndView;
//	}
	
	public String getCurrentTimeUsingDate() {
        Date date = new Date();
        String strDateFormat = ("yyyy/MM/dd HH:mm:ss");
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        String formattedDate= dateFormat.format(date);
        	return formattedDate;
    }
	
	@RequestMapping(value="/gerrymander/manage/manageUser", method = RequestMethod.GET)
	public ModelAndView showManageUserPage(ModelAndView modelAndView, Model model, User user){		
		modelAndView.setViewName("manageUser");
		User loggedIn = userService.findByStatus("online");
		modelAndView.getModel().put("userAdmin", loggedIn);
		ManageUserObj mu = new ManageUserObj();
		modelAndView.getModel().put("manageUserObj", mu);
		List<String> actions = new ArrayList<>();
		actions.add("Delete");
		actions.add("Upgrade");
		actions.add("Downgrade");
		modelAndView.getModel().put("actionList", actions);
		return modelAndView;
	}
	
	@RequestMapping(value="/gerrymander/manage/manageUser", method = RequestMethod.POST)
	public ModelAndView postManageUserPage(ModelAndView modelAndView, Model model, @ModelAttribute("manageUserObj") ManageUserObj manageUserObj){		
		modelAndView.setViewName("redirect:/gerrymander/manage");
		User loggedIn = userService.findByStatus("online");
		modelAndView.getModel().put("userAdmin", loggedIn);
		User existUser = userService.findByEmail(manageUserObj.getEmail());
		if(existUser == null)
			modelAndView.setViewName("redirect:/gerrymander/manage");
		else {
			if(manageUserObj.getAction().equals("Delete"))
				userService.delete(existUser);
			else if(manageUserObj.getAction().equals("Upgrade")) {
				existUser.setRole("admin");
				userService.saveUser(existUser);
				Activity activity = new Activity(manageUserObj.getEmail(), "Upgraded");
			    activity.setTime(getCurrentTimeUsingDate());
			    activityService.saveActivity(activity);
				modelAndView.setViewName("redirect:/gerrymander/manage");
			}
			else if(manageUserObj.getAction().equals("Downgrade")) {
				existUser.setRole(ControllerAttributes.USER_REGULAR);
				userService.saveUser(existUser);
				Activity activity = new Activity(manageUserObj.getEmail(), "Downgraded");
			    activity.setTime(getCurrentTimeUsingDate());
			    activityService.saveActivity(activity);
				modelAndView.setViewName("redirect:/gerrymander/manage");
			}
		}
//		modelAndView.getModel().put("usersList", userService.findAll());
		return modelAndView;
	}
}