package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Model.Admin;
import com.example.demo.Service.AdminService;
import com.example.demo.Service.AdminServiceImpl;
import static com.example.demo.DB.Local.log;


@RestController
public class AdminController {
	@Autowired
    AdminService adminService = new AdminServiceImpl();
	@PostMapping("/signInAdmin")
	public String signIn(String userName , String email, String password) {
		return adminService.signIn(userName, email, password);
	}
	@GetMapping("/admin/get")
	public Admin getAdmin() {
		return adminService.getAdmin();
	}
	@GetMapping("/admin/getRefunds")
	public String getRefunds() {
		return adminService.getRefundsList();
	}
	@PutMapping("/admin/acceptRefund")
	public String acceptRefund(int refundId) {
		return adminService.acceptRefundById(refundId);
	}
	@PostMapping("/admin/addProvider")
	public String addProvider(String service, String provider) {
		return adminService.addProvider(service, provider);
	}
	@GetMapping("/admin/getLog")
	public String getLog() {
		return log;
	}
	@PostMapping("/admin/discount/specific")
	public String addSpecificDiscount(String service, double percentage) {
		return adminService.addSpecificDiscount(service, percentage);
	}
	@PostMapping("/admin/discount/overall")
	public String addOverallDiscount(int userId, double percentage) {
		return adminService.addOverallDiscount(userId, percentage);
	}
}
