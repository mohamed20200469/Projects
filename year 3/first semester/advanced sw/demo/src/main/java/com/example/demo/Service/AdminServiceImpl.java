package com.example.demo.Service;

import org.springframework.stereotype.Service;

import com.example.demo.Model.Admin;
import com.example.demo.Model.OverallDiscount;
import com.example.demo.Model.Provider;
import com.example.demo.Model.SpecificDiscount;
import com.example.demo.Model.SystemService;
import com.example.demo.Model.User;
import com.example.demo.Refund.RefundRequest;

import static com.example.demo.DB.Local.admin;
import static com.example.demo.DB.Local.log;
import static com.example.demo.DB.Local.refunds;
import static com.example.demo.DB.Local.specificDiscounts;
import static com.example.demo.DB.Local.overallDiscounts;;


@Service
public class AdminServiceImpl implements AdminService {
	static boolean signedIn;

	@Override
	public String signIn(String userName, String email, String password) {
		if (admin.getEmail().equals(email) && admin.getPassword().equals(password)
				&& admin.getUserName().equals(userName)) {
			signedIn = true;
			return "Signed in (Admin) sucessfully!";
		}
		return "Wrong credintials for admin!";
	}

	@Override
	public Admin getAdmin() {
		return admin;
	}

	@Override
	public String getRefundsList() {
		if (!signedIn)
			return "Admin is not signed in";
		String txt = "";
		int i = 1;
		for (RefundRequest itr : refunds) {

			txt += i + ": " + itr.getUser().getUserName() + " requested the refund of " + itr.getAmount()
					+ " for service " + itr.getService() + " and it's state is " + itr.isState() + "\n";
			i++;
		}
		return txt;
	}

	@Override
	public String acceptRefundById(int id) {
		id--;
		if (refunds.isEmpty()) {
			return "No refunds yet!";
		}
		if (refunds.get(id).isState())
			return "No such refund!";
		refunds.get(id).getUser()
				.setWalletBalance(refunds.get(id).getUser().getWalletBalance() + refunds.get(id).getAmount());
		refunds.get(id).setState(true);
		refunds.get(id).getUser().setRefundCreated(false);
		String txt = "Refund with id (" + id+1 + ") accepted for amount " +  refunds.get(id).getAmount();
		log += txt + "\n";
		return txt;
	}

	@Override
	public String addProvider(String service, String provider) {
		if (!signedIn)
			return "Admin is not signed in!";
		SystemService serviceInUse = ServiceFactory.getService(service);
		if (serviceInUse != null) {
			serviceInUse.addProvider(new Provider(provider));
			return "Provider " + provider + " added sucessfully to service " + service + "!";
		}
		return "No such Service!";
	}

	@Override
	public String addSpecificDiscount(String service, double percentage) {
		SystemService serviceInUse = ServiceFactory.getService(service);
		if (serviceInUse == null) return "No such service!";
		SpecificDiscount discount= new SpecificDiscount(serviceInUse, percentage);
		specificDiscounts.add(discount);
		return "Discount added sucessfully";
	}

	@Override
	public String addOverallDiscount(int UserId, double percentage) {
		
		User user = UserServiceImpl.getUserById(UserId);
		if (user == null) return "No such user!";
		OverallDiscount discount= new OverallDiscount(user, percentage);
		overallDiscounts.add(discount);
		return "Discount added sucessfully";
	}
}
