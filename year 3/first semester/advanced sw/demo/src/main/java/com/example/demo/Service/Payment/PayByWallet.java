package com.example.demo.Service.Payment;

import static com.example.demo.DB.Local.ActiveUsers;

import com.example.demo.Model.User;

public class PayByWallet implements Payment {
	@Override
	public boolean pay(double amount, int id) {
		for (User user : ActiveUsers) {
			if (user.getUserId() == id) {
				if (user.getWalletBalance() >= amount) {
					double wallet = user.getWalletBalance() - amount;
					user.setWalletBalance(wallet);
					user.setLastPaidAmount(amount);
					return true;
				}
			}
		}
		return false;
	}
}
