package com.example.demo.Model.ServiceModel;

import java.util.ArrayList;

import com.example.demo.Model.Provider;
import com.example.demo.Model.SystemService;

public class Landline extends SystemService{
	
	private static Landline singleton;
	
	private Landline() {
		setName("Landline");
		setDiscount(false);
		setProviders(new ArrayList<Provider>());
		getProviders().add(new Provider("Monthly receipt"));
		getProviders().add(new Provider("Quarterly receipt"));
	}
	
	//singleton design pattern for the class
	public static SystemService getSingleton() {
		if (singleton == null) {
			singleton = new Landline();
		}
		return singleton;
	}
}
