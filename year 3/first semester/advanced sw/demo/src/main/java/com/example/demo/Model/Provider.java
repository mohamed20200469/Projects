package com.example.demo.Model;

import java.util.ArrayList;

import com.example.demo.Service.Payment.Payment;
import com.example.demo.Service.Payment.PayByCash;
import com.example.demo.Service.Payment.PayByCreditCard;
import com.example.demo.Service.Payment.PayByWallet;

public class Provider {
	private String name;
	ArrayList<Payment> paymentMethods;
	boolean cash, wallet, cc;
	
	// provider constructed with all 3 payment methods by default
	public Provider(String name) {
		this.setName(name);
		paymentMethods = new ArrayList<Payment>();
		cash = addCashMethod();
		wallet = addWalletMethod();
		cc = addCCMethod();
	}
	
	// add cash method to provider
	public boolean addCashMethod() {
		if(!cash) {
			paymentMethods.add(new PayByCash());
			return true;
		}
		return false;
	}
	
	// add wallet method to provider
	public boolean addWalletMethod() {
		if(!wallet) {
			paymentMethods.add(new PayByWallet());
			return true;
		}
		return false;
	}
	
	// add credit card method to provider
	public boolean addCCMethod() {
		if(!cc) {
			paymentMethods.add(new PayByCreditCard());;
			return true;
		}
		return false;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
