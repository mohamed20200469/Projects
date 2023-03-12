package com.example.demo.DB;

import java.util.ArrayList;

import com.example.demo.Model.Admin;
import com.example.demo.Model.User;
import com.example.demo.Model.OverallDiscount;
import com.example.demo.Model.SpecificDiscount;
import com.example.demo.Refund.RefundRequest;

public class Local {
	public static ArrayList<OverallDiscount> overallDiscounts = new ArrayList<OverallDiscount>();
	public static ArrayList<SpecificDiscount> specificDiscounts = new ArrayList<SpecificDiscount>();
	public static String log = "";
	public static ArrayList<User> users = new ArrayList<User>();
	public static ArrayList<User> ActiveUsers = new ArrayList<User>();
	public static Admin admin = new Admin("m", "m", "m");
	public static ArrayList<RefundRequest> refunds = new ArrayList<RefundRequest>();
}
