package com.example.demo.Model;

import java.util.ArrayList;

public abstract class SystemService {
	private String name;
	int currentProviderIndex;
	private ArrayList<Provider> providers;
	private boolean discount;
	public boolean isDiscount() {
		return discount;
	}
	public void setDiscount(boolean discount) {
		this.discount = discount;
	}
	public ArrayList<Provider> getProviders() {
		return providers;
	}
	public void setProviders(ArrayList<Provider> providers) {
		this.providers = providers;
	}
	public void addProvider(Provider provider) {
		providers.add(provider);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
