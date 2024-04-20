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
import com.abs.herosofhappiness.repo.ManagerRepo;
import com.abs.herosofhappiness.service.MailService;
import com.abs.herosofhappiness.service.ManagerService;


@Service
public class ManagerServiceImpl implements ManagerService {
	@Autowired
	JavaMailSender mail;
	
	@Autowired
	ManagerRepo managerRepo;
	
	@Autowired
	EmployeeRepo employeeRepo;
	
	@Autowired
	MailService mailService;
	
	BCryptPasswordEncoder b =  new BCryptPasswordEncoder();
	
	@Override
	public int loginManager(String email, String password) {
		Employee mgr =  managerRepo.findByEmail(email);
		if(mgr != null) {
			if(b.matches(password, mgr.getPassword())) {
				int otp =(int)(Math.random()*10000);
				SimpleMailMessage ms =  new SimpleMailMessage();
				ms.setTo(mgr.getEmail());
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
		throw new ManagerNotFoundException("Manager", "Email", email);
	}
	
	@Override
	public String sendMailForResetPassword(String email) {
		Employee mgr =  managerRepo.findByEmail(email);
		if(mgr != null) {
			mailService.sendMailForResetPassword(email,mgr.getEmpname(),mgr.getDesignation());
			return email;
		}
		throw new ManagerNotFoundException("Manager", "Email", email) ;
	}


	@Override
	public String resetPassword(String email, String password) {
		Employee mgr =  managerRepo.findByEmail(email);
		if(mgr != null) {
			String pswd =  b.encode(password);
			mgr.setPassword(pswd);
			managerRepo.save(mgr);
			return "reset password successfully";
		}
		return null;
	}

	@Override
	public List<Employee> findEmpByMgrId(String email) {
		Employee employee=employeeRepo.findByEmail(email);
		int mgrid=employee.getEmpid();
		List<Employee>	empList=managerRepo.findByMgrId(mgrid);
		if(empList.size() >0) {
			return empList;
		}
		throw new EmpNotFoundException("Employee", "hr_id", mgrid+"");
		}
	}

