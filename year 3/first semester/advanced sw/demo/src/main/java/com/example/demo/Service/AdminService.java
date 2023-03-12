package com.example.demo.Service;
import com.example.demo.Model.Admin;
public interface AdminService {
	public String signIn(String userName , String email, String password);
	public Admin getAdmin();
	public String getRefundsList();
	public String acceptRefundById(int id);
	public String addProvider(String service, String provider);
	public String addSpecificDiscount(String service, double percentage);
	public String addOverallDiscount(int userId, double percentage);
}
