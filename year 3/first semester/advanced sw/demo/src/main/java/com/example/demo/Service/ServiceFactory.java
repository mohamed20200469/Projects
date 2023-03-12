package com.example.demo.Service;

import com.example.demo.Model.SystemService;
import com.example.demo.Model.ServiceModel.Donation;
import com.example.demo.Model.ServiceModel.InternetPayment;
import com.example.demo.Model.ServiceModel.Landline;
import com.example.demo.Model.ServiceModel.MobileRecharge;


public class ServiceFactory {
	public static SystemService getService(String service) {
		if  (service == null) {
			return null;
		}
		else if(service.equals("internet")) {
			return InternetPayment.getSingleton();
		}
		else if(service.equals("mobile")) {
			return MobileRecharge.getSingleton();
		}
		else if(service.equals("landline")) {
			return Landline.getSingleton();
		}
		else if(service.equals("donation")) {
			return Donation.getSingleton();
		}
		return null;
	}
}
