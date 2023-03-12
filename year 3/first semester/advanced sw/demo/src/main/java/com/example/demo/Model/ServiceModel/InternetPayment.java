package com.example.demo.Model.ServiceModel;

import java.util.ArrayList;

import com.example.demo.Model.Provider;
import com.example.demo.Model.SystemService;

public class InternetPayment extends SystemService{
	
	private static InternetPayment singleton;
	
	private InternetPayment(){
		setName("Internet payment");
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
			singleton = new InternetPayment();
		}
		return singleton;
	} 
}