package com.springtask.RegistrationApplication;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AppController {
	
	@Autowired
	private UserRepository repo;
	@GetMapping("")
	public String viewHomePage() {
		return "index";
	}
	
	@GetMapping("/register")
	public String showSignUpForm(Model model) {
		model.addAttribute("user", new User());
		
		return "signup_form";
	}
	
	@PostMapping("/process_register")
	public String processRegistration(User user) {
		
		String regex = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{6,20}$";
		
		Pattern p = Pattern.compile(regex);
		
		 Matcher m = p.matcher(user.getPassword());
		 
		 if(m.matches()) {
			 BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
				String encodedPassword = encoder.encode(user.getPassword());
				user.setPassword(encodedPassword);
				repo.save(user);
				
				return "register_success";
		 }
		 else {
			 
			 return "register_fail";
		 }
		
		
	}
	
	@GetMapping("/list_users")
	public String viewUserList(Model model) {
		List<User> listUsers = repo.findAll();
		model.addAttribute("listUsers", listUsers);
		return "users";
		
	}
	
	/*
	 * @GetMapping("/users/{email}/{id}") public @ResponseBody ResponseEntity <
	 * String > getemail(@PathVariable String email,@PathVariable String id, User
	 * user) { return new ResponseEntity < String >
	 * ("Response from GET "+user.getId()+" "+ user.getEmail(), HttpStatus.OK); }
	 */
}
