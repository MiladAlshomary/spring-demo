package com.example;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@org.springframework.stereotype.Controller
public class MainController {
	
	
	private static final Map<Integer, User> myMap;
    static {
    	myMap = new HashMap<Integer, User>();
    	myMap.put(1, new User(1, "Milad", "Alshomary"));
    	myMap.put(2, new User(2, "Milad1", "Alshomary1"));
    	myMap.put(3, new User(3, "Milad2", "Alshomary2"));
    	myMap.put(4, new User(4, "Milad3", "Alshomary3"));
    }
    
	@RequestMapping("/")
	@ResponseBody
	public String home(){
		return "home";
	}
	
	@RequestMapping("/user")
	public String user(@RequestParam(value="id", required=true) Integer userId, Model model) {
		model.addAttribute("user" , myMap.get(userId));

		return "user";
	}
	
	@RequestMapping("/users")
	public String users(Model model) {
		model.addAttribute("allUsers" , myMap.values().toArray());
		return "users";
	}
}

//TODO adding it as a model class
class User {
	public int id;
	public String firstName;
	public String lastName;
	
	public User(int id, String fName, String lName) {
		this.id = id;
		this.firstName = fName;
		this.lastName  = lName;
	}
	
	
}

