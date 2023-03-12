package com.example.demo.Service;

import org.springframework.stereotype.Service;

import com.example.demo.Model.OverallDiscount;
import com.example.demo.Model.Provider;
import com.example.demo.Model.SpecificDiscount;
import com.example.demo.Model.SystemService;
import com.example.demo.Model.User;
import com.example.demo.Refund.RefundRequest;
import com.example.demo.Service.Payment.PayByCash;
import com.example.demo.Service.Payment.PayByCreditCard;
import com.example.demo.Service.Payment.PayByWallet;
import com.example.demo.Service.Payment.Payment;

import static com.example.demo.DB.Local.users;
import static com.example.demo.DB.Local.ActiveUsers;
import static com.example.demo.DB.Local.refunds;
import static com.example.demo.DB.Local.specificDiscounts;
import static com.example.demo.DB.Local.overallDiscounts;
import static com.example.demo.DB.Local.log;

import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserService {
	static {
		User u1 = new User("1", "1", "1");
		User u2 = new User("Edris", "Edris@gmail", "edris123");
		users.add(u1);
		users.add(u2);
	}

	@Override
	public ArrayList<User> getAllUsers() {
		try {
			if (!users.isEmpty()) {
				return users;
			}
		} catch (Exception e) {
			System.out.println("Exception in addPerson as" + e.getMessage());
		}
		return null;
	}

	@Override
	public String signIn(String email, String password) {
		for (User user : users) {
			if (user.getEmail().equals(email) && user.getPassword().equals(password) && !user.isSignedIn()) {
				user.setSignedIn(true);
				ActiveUsers.add(user);
				return "Signed in (User) sucessfully!";
			}
		}
		return "No such user or wrong credintials!";
	}

	@Override
	public String signUp(String userName, String email, String password) {
		for (User user : users) {
			if (user.getEmail().equals(email) || user.getUserName().equals(userName))
				return "Username or email already exist!";
		}
		User user = new User(userName, email, password);
		users.add(user);
		return "Sign up sucessful!";
	}

	@Override
	public Boolean payByWallet(double amount, int id) {
		Payment p = new PayByWallet();
		return p.pay(amount, id);
	}

	@Override
	public Boolean payByCC(double amount, int id) {
		Payment p = new PayByCreditCard();
		return p.pay(amount, id);
	}

	@Override
	public Boolean payByCash(double amount, int id) {
		Payment p = new PayByCash();
		return p.pay(amount, id);
	}

	@Override
	public String createRefundRequest(int id) {
		if (getUserById(id) != null && getUserById(id).getLastPaidAmount() != 0 && !getUserById(id).isRefundCreated()) {
			refunds.add(new RefundRequest(id));
			getUserById(id).setRefundCreated(true);
			return "Refund request created sucessfully for user with id (" + id + ")";
		}
		return "Refund request failed!";
	}

	public static User getUserById(int id) {
		for (User user : users) {
			if (user.getUserId() == id) {
				return user;
			}
		}
		return null;
	}

	@Override
	public ArrayList<User> getAllActiveUsers() {
		try {
			if (!ActiveUsers.isEmpty()) {
				return ActiveUsers;
			}
		} catch (Exception e) {
			System.out.println("Exception in addPerson as" + e.getMessage());
		}
		return null;
	}

	@Override
	public String addFunds(double amount, int id) {
		if (getUserById(id) != null) {
			getUserById(id).setWalletBalance(amount + getUserById(id).getWalletBalance());
			String txt = "Funds " + amount + " added to wallet of user with id (" + id + ") sucessfully current wallet balance is " + getUserById(id).getWalletBalance();
			log += txt + "\n";
			return txt;
		}
		return "Adding funds failed";
	}

	@Override
	public String service(String service, String provider, String paymentMethod, double amount, int userId) {
		SystemService serviceInUse = ServiceFactory.getService(service);
		if (serviceInUse == null)
			return "No such service";
		Provider providerInUse = null;
		for (Provider itr : serviceInUse.getProviders()) {
			if (itr.getName().equals(provider)) {
				providerInUse = itr;
				break;
			}
		}
		if (providerInUse == null)
			return "No such provider";
		if (paymentMethod.equals("cash") && paymentMethod.equals("credit card") && paymentMethod.equals("wallet"))
			return "No such payment method";
		boolean complete;
		double discounted = 0;
		for (SpecificDiscount discount : specificDiscounts) {
			if (discount.getService() == serviceInUse) {
				discounted += ((amount * discount.getPercentage()) / 100);
			}
		}
		for (OverallDiscount discount : overallDiscounts) {
			if (discount.getUser().getUserId() == userId) {
				discounted += ((amount * discount.getPercentage()) / 100);
			}
		}
		amount -= discounted;
		switch (paymentMethod) {
		case "cash":
			complete = payByCash(amount, userId);
			break;
		case "creditcard":
			complete = payByCC(amount, userId);
			break;
		case "wallet":
			complete = payByWallet(amount, userId);
			break;
		default:
			complete = false;
			break;
		}
		if (complete) {
			getUserById(userId).setLastUsedService(service);
			String txt = "Payment sucessful for provider " + provider + " in the service " + service
					+ " by the user with id (" + userId + ")" + " payment amount is " + amount
					+ " and payment method is " + paymentMethod;
			log += txt + "\n";
			return txt;
		}
		return "Payment failed!";
	}

	@Override
	public User getUser(String email, String password) {
		for (User user : users) {
			if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
				return user;
			}
		}
		return null;
	}

	@Override
	public String searchForService(String input) {
		boolean flag = false;
		String service1 = "1- Mobile recharge services";
		String service2 = "2- Internet payment services";
		String service3 = "3- Landline services";
		String service4 = "4- Donation services";
		ArrayList<String> services = new ArrayList<String>();
		services.add(service1);
		services.add(service2);
		services.add(service3);
		services.add(service4);
		System.out.print("Enter name of service to search for: ");
		String nameOfService = input;
		String output = "";
		for (String itr : services) {
			if (itr.toLowerCase().contains(nameOfService.toLowerCase())) {
				flag = true;
				output += itr + "\n";
			}
		}
		if (flag) {
			return output;
		} else {
			return "no such service!";
		}
	}

	@Override
	public String checkForDiscounts(int userId) {
		String txt = "";
		for (OverallDiscount discount : overallDiscounts) {
			if (discount.getUser().getUserId() == userId) {
				txt += "This user has " + discount.getPercentage() + "% discount on all services!\n";
			}
		}
		for (SpecificDiscount discount : specificDiscounts) {
			txt += "There is a " + discount.getPercentage() + "% discount on the " + discount.getService().getName()
					+ " service!\n";
		}
		if (txt.equals(""))
			txt += "No discounts found!";
		return txt;
	}
}
