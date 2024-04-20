package com.abs.herosofhappiness.serviceimpl;


import java.time.Period;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.abs.herosofhappiness.entity.AccountDetails;
import com.abs.herosofhappiness.entity.Admin;
import com.abs.herosofhappiness.entity.EmpLeave;
import com.abs.herosofhappiness.entity.Employee;
import com.abs.herosofhappiness.entity.EmployeeCopy;
import com.abs.herosofhappiness.exception.EmpNotFoundException;
import com.abs.herosofhappiness.repo.AdminRepo;
import com.abs.herosofhappiness.repo.EmpLeaveRepo;
import com.abs.herosofhappiness.repo.EmployeeRepo;
import com.abs.herosofhappiness.service.EmployeeService;
import com.abs.herosofhappiness.service.MailService;


@Service
public class EmployeeServiceImpl implements EmployeeService{
	
	@Autowired
	JavaMailSender mail;
	
	@Autowired
	EmployeeRepo employeeRepo;
	
	@Autowired
	MailService mailService;
	
	@Autowired
	AdminRepo adminRepo;
	
	@Autowired
	EmpLeaveRepo empLeaveRepo;
	
	BCryptPasswordEncoder b=new BCryptPasswordEncoder();
	
	public Employee getEmployee(String email) {
		Employee employee=employeeRepo.findByEmail(email);
		if(employee!=null) {
			return employee;
		}
		
			throw new EmpNotFoundException("Employee", "email", email) ;

//		Employee employee=employeeRepo.findByEmail(email)
//				.orElseThrow(()->new EmpNotFoundException("Employee","Email", email));
//		return employee;
	}
	
	public Employee editemployee(EmployeeCopy employeeCopy) {
		Employee employee=new Employee(employeeCopy);
		Employee dbEmployee=employeeRepo.findByEmail(employeeCopy.getEmail());
		if(dbEmployee!=null) {
			employee.setPassword(b.encode(employee.getPassword()));
			employee.setEmpid(dbEmployee.getEmpid());
			return employeeRepo.save(employee);
		}
		else {
			throw new EmpNotFoundException("Employee", "Mail", employee.getEmail());
		}
	}
	
	public int loginEmp(String email, String password) {
		Employee emp =  employeeRepo.findByEmail(email);
		
		if(emp != null) {
			if(b.matches(password, emp.getPassword())) {
				int otp =(int)(Math.random()*10000);
				SimpleMailMessage ms =  new SimpleMailMessage();
				ms.setTo(emp.getEmail());
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
		throw new EmpNotFoundException("Employee", "Email", email);
	}

	public String sendMailForResetPassword(String email) {
		Employee emp =  employeeRepo.findByEmail(email);
		if(emp != null) {
			mailService.sendMailForResetPassword(email,emp.getEmpname(),emp.getDesignation());
			return email;
		}
		throw new EmpNotFoundException("Employee", "Email", email) ;
	}

	public String resetPassword(String email, String password) {
		Employee emp =  employeeRepo.findByEmail(email);
		if(emp != null) {
			String pswd =  b.encode(password);
			emp.setPassword(pswd);
			employeeRepo.save(emp);
			return "reset password successfully";
		}
		return null;
	}


	@Override
	public AccountDetails getAccountDetails(String email) {
		Employee employee=employeeRepo.findByEmail(email);
		if(employee!=null) {
			AccountDetails account=employee.getAccount();
//			if(account!=null) {
//				return account;
//			}
//			return "You have not added your account details, please click on the add button"
//					+ "to add your account details";
			return account;
		}
		throw new EmpNotFoundException("Employee", "Email", email);
	}
	
	@Override
	public String createAccount(AccountDetails account,String email) {
		Employee employee=employeeRepo.findByEmail(email);
		if(employee!=null) {
			employee.setAccount(account);
			employeeRepo.save(employee);
			return "Account details saved successfully";
		}
		throw new EmpNotFoundException("Employee", "Mail", email);
	}

	@Override
	public String applyLeave(EmpLeave empLeave, String email) {
		Employee employee=employeeRepo.findByEmail(email);
		int mid=employee.getMid();
		Optional<Employee> manager=employeeRepo.findById(mid);
		
		EmpLeave dbEmpLeave=empLeaveRepo.findByEmpId(employee.getEmpid());
		if(dbEmpLeave!=null) {
			empLeave.setLeaveid(dbEmpLeave.getLeaveid());
			empLeave.setEmployee(dbEmpLeave.getEmployee());
			int duration=Period.between(empLeave.getEnddate(),empLeave.getStartdate()).getDays();
			empLeave.setRemainleave(empLeave.getTotalleave()- duration);
			empLeave.setDuration(duration);
			empLeave.setEmployee(employee);
			empLeaveRepo.save(empLeave);
			return mailService.applyLeaveMail(empLeave, manager.get().getEmail(), manager.get().getEmpname(),employee.getEmpname());
		}
		else {
			int duration=Period.between(empLeave.getEnddate(),empLeave.getStartdate()).getDays();
			empLeave.setRemainleave(empLeave.getTotalleave()- duration);
			empLeave.setDuration(duration);
			empLeave.setEmployee(employee);
			empLeaveRepo.save(empLeave);
			return mailService.applyLeaveMail(empLeave, manager.get().getEmail(), manager.get().getEmpname(),employee.getEmpname());
		}
	}
	
	@Override
	public String approveLeave(String email) {
		Employee employee=employeeRepo.findByEmail(email);
		if(employee!=null) {
		  return mailService.approveLeaveMail(email,employee.getEmpname());
		}
		return "Failed to approve leave request";
	}
	
	
	@Override
	public String rejectLeave(String email) {
		Employee employee=employeeRepo.findByEmail(email);
		if(employee!=null) {
			int id=employee.getEmpid();
			EmpLeave empLeave=empLeaveRepo.findByEmpId(id);
			empLeave.setStartdate(null);
			empLeave.setEnddate(null);
			empLeave.setReason(null);
			empLeave.setRemainleave(empLeave.getRemainleave()+empLeave.getDuration());
			empLeave.setDuration(0);
			empLeaveRepo.save(empLeave);
			return mailService.rejectLeaveMail(email,employee.getEmpname());
		}
		return "Failed to update leave status";
	}

	@Override
	public String applyResignation(String email) {
		Employee employee=employeeRepo.findByEmail(email);
		int hrid=employee.getHid();
		Employee dbEmployee=employeeRepo.findById(hrid).get();
		List<Admin> adminlist=adminRepo.findAll();
		if(employee!=null) {
			SimpleMailMessage ms =  new SimpleMailMessage();
			Iterator itr=adminlist.iterator();
			while(itr.hasNext()) {
				ms.setTo(((Admin)itr.next()).getEmail());
				ms.setCc(dbEmployee.getEmail());
				ms.setFrom("projectjspider@gmail.com");
				ms.setSubject("Applied Resignation");
				ms.setText("Hello,\r\n"
						+ "\r\n"
						+employee.getEmpname()+" applied resignation.Please look into it."
						+"\r\n\r\n"
						+"Thanks & Regards\r\n"
						+"ABS Technologies");
				mail.send(ms);
			}	
			return "Resignation applied successfully";
		}
		return null;
	}

}
