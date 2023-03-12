package com.example.demo.Model;

public class SpecificDiscount extends Discount{
	SystemService service;
	public SpecificDiscount(SystemService service, double percentage) {
		super(percentage);
		this.service = service;
	}
	public SystemService getService() {
		return service;
	}
	public void setService(SystemService service) {
		this.service = service;
	}
}