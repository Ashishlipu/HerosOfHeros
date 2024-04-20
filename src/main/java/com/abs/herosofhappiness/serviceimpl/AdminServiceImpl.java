package com.abs.herosofhappiness.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.abs.herosofhappiness.entity.Admin;
import com.abs.herosofhappiness.entity.Employee;
import com.abs.herosofhappiness.exception.AdminNotFoundExcption;
import com.abs.herosofhappiness.exception.EmpNotFoundException;
import com.abs.herosofhappiness.repo.AdminRepo;
import com.abs.herosofhappiness.repo.EmployeeRepo;
import com.abs.herosofhappiness.service.AdminService;
import com.abs.herosofhappiness.service.MailService;


@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	AdminRepo adminRepo;
	
	@Autowired
	EmployeeRepo employeeRepo;
	
	@Autowired(required = true)
	JavaMailSender javaMailSender;
	
	@Autowired
	MailService mailService;
	
    BCryptPasswordEncoder b=new BCryptPasswordEncoder();
	
	@Override
	public Employee createAdmin(Employee admin) {
		Employee admin1=employeeRepo.save(admin);
		return admin1;
	}

	@Override
	public Employee createEmployee(Employee employee) {
		 String password="Abs";
		 int otp=(int)(Math.random()*100000);
		 password=password+otp;
		 employee.setPassword(password);
		 Employee dbEmployee=employeeRepo.save(employee);
		 
		 SimpleMailMessage message=new SimpleMailMessage();
		 message.setFrom("projectjspider@gmail.com");
		 message.setTo(employee.getEmail());
		 message.setSubject("Welcome to ABS Technologies - Your Credentials Inside!");
		 message.setText("Hello, \r\n"
		 		+ "\r\n"
		 		+ "Welcome aboard to ABS Technologies! We're thrilled to have you join our team and embark on this exciting journey with us."
		 		+ "\r\n"
		 		+ ""
		 		+ "Below are your login credentials for accessing our company systems:\r\n"
		 		+ "\r\n"
		 		+ "Username : "+employee.getEmail() +"\r\n"
		 		+ "Temporary Password : "+employee.getPassword()+"\r\n"
		 		+ "Access Link : [Link to Access Company Systems]"
		 		+ "\r\n"
		 		+"\r\n"
		 		+ "Please note that for security reasons, we recommend changing your password upon your first login. To do so, simply follow the prompts provided on the login page."
		 		+ ""
		 		+ "\r\n"
		 		+ "\r\n"
		 		+ "\r\n"
		 		+ "Thanks & Regards\r\n"
		 		+ "ABS Technologies");
		 
		 javaMailSender.send(message);
		 return dbEmployee;
	}
	
	
	@Override
	public int loginAdmin(String email, String password) {
		Employee admin = employeeRepo.findByEmail(email);
		if(admin != null) {
//			if(b.matches(password, ad.getPassword())) {
			if(password.equals(admin.getPassword())) {
				int otp =(int)(Math.random()*1000000);
				System.out.println(otp);
				SimpleMailMessage ms =  new SimpleMailMessage();
				ms.setTo(admin.getEmail());
				ms.setFrom("projectjspider@gmail.com");
				ms.setSubject("OTP for login");
				ms.setText("Hello,\r\n"
						+ "\r\n\r\n"
						+ "This is your OTP(One time password) for login.Please use this for login.\r\n"
						+ "OTP : "+otp+""
						+ "\r\n"
						+ "\r\n"
						+ "Thanks & Regards\r\n"
						+ "ABS Technologies");
				javaMailSender.send(ms);
				return otp;
			}
		}
		throw new AdminNotFoundExcption("Admin", "email", email);
	}


	@Override
	public String sendMailForResetPassword(String email) {
		Employee admin =  employeeRepo.findByEmail(email);
		if(admin != null) {
			mailService.sendMailForResetPassword(email,admin.getEmpname(),admin.getDesignation());
			return email;
		}
		throw new AdminNotFoundExcption("Admin", "email", email);
	}


	@Override
	public String resetpassword(String email,String password) {
		
		Employee admin =  employeeRepo.findByEmail(email);
		if(admin != null) {
			String pswd =  b.encode(password);
			admin.setPassword(pswd);
			employeeRepo.save(admin);
			return "reset password successfully";
		}
		return null;
	}

	@Override
	public Employee getAdminByEmail(String email) {
		Employee admin=employeeRepo.findByEmail(email);
		if(admin!=null) {
			return admin;
		}
		throw new AdminNotFoundExcption("Admin", "Email", email);
	}

	@Override
	public List<Employee> getAllEmployees() {
		 List<Employee> emplist=employeeRepo.findAll();
		 if(emplist!=null) {
			 return emplist;
		 }
		 throw new EmpNotFoundException("No employees are present");
	}

	@Override
	public String disableEmployee(String email) {
		Employee employee=employeeRepo.findByEmail(email);
		if(employee!=null) {
			employee.setMode("Inactive");
			employeeRepo.save(employee);
			return mailService.sendResignMailToHr(employee);
		}
		throw new EmpNotFoundException("Employee", "Email", email);
	}


}
