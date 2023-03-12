package com.example.demo.Controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;


import com.example.demo.Model.User;
import com.example.demo.Service.UserService;
import com.example.demo.Service.UserServiceImpl;

@RestController
public class UserController {
	@Autowired
	UserService userService = new UserServiceImpl();

	@GetMapping("/users/getAll")
	public ArrayList<User> getAllUsers() {
		return userService.getAllUsers();
	}

	@GetMapping("/users/active")
	public ArrayList<User> getAllActiveUsers() {
		return userService.getAllActiveUsers();
	}
	
	@GetMapping("/user/check")
	public User checkUser(String email, String password) {
		return userService.getUser(email, password);
	}

	@PostMapping("/user/refund")
	public String createRefundRequest(int userId) {
		return userService.createRefundRequest(userId);
	}

	@PostMapping("/user/signIn")
	public String signIn(String email, String password) {
		return userService.signIn(email, password);
	}

	@PutMapping("/user/service")
	public String service(String service, String provider, String paymentMethod, double amount, int userId) {
		return userService.service(service, provider, paymentMethod, amount, userId);
	}

	@PostMapping("/user/signUp")
	public String signUp(String userName, String email, String password) {
		return userService.signUp(userName, email, password);
	}

	@PutMapping("/user/addFunds")
	public String addFunds(double amount, int id) {
		return userService.addFunds(amount, id);
	}
	
	@GetMapping("/user/searchForService")
	public String search(String input) {
		return userService.searchForService(input);
	}
	@GetMapping("/user/checkForDiscounts")
	public String checkForDiscounts(int userId) {
		return userService.checkForDiscounts(userId);
	}
}
