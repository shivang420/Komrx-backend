//package com.komrz.trackxbackend.service;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//@Controller
//@RequestMapping("/contract")
//public class SampleService {
//	
//	@GetMapping
//	@ResponseBody
//	
//	public String welcome() {
//		return "Welcome Komrz, the era of Smart Contracts";
//	}
//	
//	@GetMapping("/login/guest")
//	@ResponseBody
//	public String loginGuest(@RequestParam("user") String username,
//			@RequestParam("pass") String password) {
//		
//		return "Welcome to Contract World, Guest" + username + " !";
//	}
//	
//	@PostMapping("/login/auth")
//	@ResponseBody
//	public String loginUser(@RequestParam String username,
//			@RequestParam String password ) {
//		
//		return "Welcome to Contract World, Member" + username + " !";
//		
//	}
//	
//	
//	@GetMapping("/login/role/{user}")
//	@ResponseBody
//	
//	public String validateRole(@PathVariable("user") String username) {
//		return "User: " + username + " is an Admin!";
//	}
//	
//	@PostMapping("/login/role/{user}/{pass:[A-Za-z0-0@$_]{3,20}}")
//	@ResponseBody
//	
//	public String validateRole(@PathVariable("user") String username, 
//			@PathVariable("pass") String password) {
//		return new StringBuilder(String.join("", username, password)).reverse().toString();
//	}
//	
//	
//}
