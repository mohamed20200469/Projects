package com.example.demo.Model;

public class OverallDiscount extends Discount{
	User user;
	public OverallDiscount(User user, double percentage) {
		super(percentage);
		this.user = user;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
