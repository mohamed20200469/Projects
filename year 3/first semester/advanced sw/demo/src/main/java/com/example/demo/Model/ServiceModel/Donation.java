package com.example.demo.Model.ServiceModel;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.example.demo.Model.Provider;
import com.example.demo.Model.SystemService;

@Service
public class Donation extends SystemService{
	private static Donation singleton;
	
	private Donation() {
		setName("Donation");
		setDiscount(false);
		setProviders(new ArrayList<Provider>());
		getProviders().add(new Provider("Cancer hospital"));
		getProviders().add(new Provider("Schools"));
		getProviders().add(new Provider("Non profitable organizations"));
	}
	
	//singleton design pattern for the class
	public static SystemService getSingleton() {
		if (singleton == null) {
			singleton = new Donation();
		}
		return singleton;
	}
}