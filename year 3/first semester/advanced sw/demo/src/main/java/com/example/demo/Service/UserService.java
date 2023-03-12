package com.example.demo.Service;

import java.util.ArrayList;

import com.example.demo.Model.User;

public interface UserService {
	public ArrayList<User> getAllUsers();
	public User getUser(String email, String password);
	public ArrayList<User> getAllActiveUsers();
	public String service(String service, String provider, String paymentMethod, double amount, int userId);
	public String signIn(String email, String password);
	public String signUp(String userName , String email, String password);
	public Boolean payByWallet(double amount, int id);
	public Boolean payByCC(double amount, int id);
	public Boolean payByCash(double amount, int id);
	public String createRefundRequest(int id);
	public String addFunds(double amount, int id);
	public String searchForService(String input);
	public String checkForDiscounts(int userId);
}
