package com.example.demo.Refund;

import static com.example.demo.Service.UserServiceImpl.getUserById;
import com.example.demo.Model.User;

public class RefundRequest {
	private double amount;
	private User user;
	private boolean state;
	private String service;
	public RefundRequest(int id) {
		this.setUser(getUserById(id));
		this.setAmount(getUserById(id).getLastPaidAmount());
		this.setService(getUserById(id).getLastUsedService());
		setState(false);
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public boolean isState() {
		return state;
	}
	public void setState(boolean state) {
		this.state = state;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
}
