package com.abs.herosofhappiness.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.abs.herosofhappiness.entity.Employee;
import com.abs.herosofhappiness.exception.EmpNotFoundException;
import com.abs.herosofhappiness.exception.ManagerNotFoundException;
import com.abs.herosofhappiness.repo.EmployeeRepo;
import com.abs.herosofhappiness.repo.HrRepo;
import com.abs.herosofhappiness.service.HrService;
import com.abs.herosofhappiness.service.MailService;


@Service
public class HrServiceImpl implements HrService {
	@Autowired
	JavaMailSender mail;
	
	@Autowired
	HrRepo hrRepo;
	
	@Autowired
	EmployeeRepo employeeRepo;
	
	@Autowired
	MailService mailService;
	
	BCryptPasswordEncoder b =  new BCryptPasswordEncoder();

	@Override
	public int loginHr(String email, String password) {
		Employee hr =  hrRepo.findByEmail(email);
		if(hr != null) {
//			if(b.matches(password, hr.getPassword())) {
			if(password.equals(hr.getPassword())) {
				int otp =(int)(Math.random()*10000);
				SimpleMailMessage ms =  new SimpleMailMessage();
				ms.setTo(hr.getEmail());
				ms.setFrom("projectjspider@gmail.com");
				ms.setSubject("OTP for login");
				ms.setText("Hello\r\n"
						+ "\r\n"
						+ "This is your OTP(One time password) for login.Please use this for login."
						+ "OTP : "+otp+""
						+ "\r\n"
						+ "\r\n"
						+ "Thanks & Regards\r\n"
						+ "ABS Technologies");
				mail.send(ms);
				return otp;
			}
		}
		throw new ManagerNotFoundException("Hr", "Email", email);
	}

	@Override
	public String sendMailForResetPassword(String email) {
		Employee hr =  hrRepo.findByEmail(email);
		if(hr != null) {
			mailService.sendMailForResetPassword(email,hr.getEmpname(),hr.getDesignation());
			return email;
		}
		throw new ManagerNotFoundException("Hr", "Email", email) ;
	}


	@Override
	public String resetPassword(String email, String password) {
		Employee hr =  hrRepo.findByEmail(email);
		if(hr != null) {
			String pswd =  b.encode(password);
			hr.setPassword(pswd);
			hrRepo.save(hr);
			return "reset password successfully";
		}
		return null;
	}

	@Override
	public List<Employee> findEmpByHrId(String email) {
		Employee employee=employeeRepo.findByEmail(email);
		int empid=employee.getEmpid();
		List<Employee>	empList=hrRepo.findByHr_id(empid);
		if(empList.size() >0) {
			return empList;
		}
		throw new EmpNotFoundException("Employee", "hr_id", empid+"");
		}

}
