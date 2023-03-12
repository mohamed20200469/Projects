package com.example.demo.Model;

public abstract class Discount {
	double percentage;
	public Discount(double percentage) {
		this.percentage = percentage;
	}
	public double getPercentage() {
		return percentage;
	}
	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}
}
