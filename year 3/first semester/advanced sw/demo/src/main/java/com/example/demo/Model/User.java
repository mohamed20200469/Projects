package com.example.demo.Model;

public class User extends Account{
	static int userCounter = 0;
	int userId;

	private String lastUsedService;
	Double wallet;
	double lastPaidAmount;
	boolean refundCreated;
	boolean signedIn;

	public User(String userName, String email, String password) {
		super(userName, email, password);
		userCounter++;
		this.setUserId(userCounter);
		this.wallet = 100d;
		this.lastPaidAmount = 0d;
		this.refundCreated = false;
		this.signedIn = false;
	}

	public Double getWalletBalance() {
		return this.wallet;
	}

	public void setWalletBalance(double amount) {
		this.wallet = amount;
	}

	@Override
	public String toString() {
		return email + "::" + userName + "::";
	}

	public double getLastPaidAmount() {
		return lastPaidAmount;
	}

	public void setLastPaidAmount(double lastPaidAmount) {
		this.lastPaidAmount = lastPaidAmount;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public boolean isRefundCreated() {
		return refundCreated;
	}

	public void setRefundCreated(boolean refundCreated) {
		this.refundCreated = refundCreated;
	}

	public boolean isSignedIn() {
		return signedIn;
	}

	public void setSignedIn(boolean signedIn) {
		this.signedIn = signedIn;
	}

	public String getLastUsedService() {
		return lastUsedService;
	}

	public void setLastUsedService(String lastUsedService) {
		this.lastUsedService = lastUsedService;
	}
}
