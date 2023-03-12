package com.example.demo.Model.ServiceModel;

import java.util.ArrayList;

import com.example.demo.Model.Provider;
import com.example.demo.Model.SystemService;

public class MobileRecharge extends SystemService{
	
	private static MobileRecharge singleton;
	
	private MobileRecharge() {
		setName("Mobile recharge");
		setDiscount(false);
		setProviders(new ArrayList<Provider>());
		getProviders().add(new Provider("Vodafone"));
		getProviders().add(new Provider("Etisalat"));
		getProviders().add(new Provider("Orange"));
		getProviders().add(new Provider("We"));
	}
	
	//singleton design pattern for the class
	public static SystemService getSingleton() {
		if (singleton == null) {
			singleton = new MobileRecharge();
		}
		return singleton;
	}
}
