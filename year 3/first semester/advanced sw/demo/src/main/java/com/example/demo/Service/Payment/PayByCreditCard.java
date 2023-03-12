package com.example.demo.Service.Payment;

import static com.example.demo.DB.Local.ActiveUsers;

import com.example.demo.Model.User;

public class PayByCreditCard implements Payment {
	@Override
	public boolean pay(double amount, int id) {
		for (User user : ActiveUsers) {
			if(user.getUserId() == id) {
				user.setLastPaidAmount(amount);
				return true;
			}
		}
		return false;
	}
}
