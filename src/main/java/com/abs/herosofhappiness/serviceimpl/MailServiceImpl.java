package com.abs.herosofhappiness.serviceimpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.abs.herosofhappiness.entity.EmpLeave;
import com.abs.herosofhappiness.entity.Employee;
import com.abs.herosofhappiness.repo.EmployeeRepo;
import com.abs.herosofhappiness.service.MailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class MailServiceImpl implements MailService {

	@Autowired
	JavaMailSender mail;
	
	@Autowired
	EmployeeRepo employeeRepo;
	
	//for resetpassword link mail
		@Override
		public String sendMailForResetPassword(String email,String name,String designation) {
			MimeMessage message=mail.createMimeMessage();
			String m1="Hello "+name+",\n"
					+ "\r\n"
					+ "We have received a request to Reset your password.\r\n\r\n"
					+ "Please click on the following link, (or paste this in your browser) to complete the process\r\n";
			MimeMessageHelper helper=new MimeMessageHelper(message);
			try {
				helper.setTo(email);
			
			helper.setSubject("Password reset link !!");
			
			helper.setText(m1+"""
					<div>
	                    <a href="http://localhost:3000/resetPassword?email=%s&designation=%s" target="_blank">Click this link to reset password</a>
	                </div>
					""".formatted(email,designation),true);
			mail.send(message);
			return email;
			
			} catch (MessagingException e) {
				return "Failed to send mail";
			}
		}
	
	@Override
	public String applyLeaveMail(EmpLeave empLeave, String email,String mgrname,String empname) {
		MimeMessage message=mail.createMimeMessage();
		String m1="Hello "+mgrname+",\n"
				+ "\r\n"
				+ "We have received a leave request of "+empname+".As you are the manager of that employee,"
				+ "please click on the below link to approve or reject.\r\n\r\n";
		MimeMessageHelper helper=new MimeMessageHelper(message);
		try {
			helper.setTo(email);
		
		helper.setSubject("Leave Request !!");
		
		helper.setText(m1+"""
				<div>
                    <a href="http://localhost:3000/resetPassword?email=%s" target="_blank">Click this link to reset password</a>
                </div>
				""".formatted(email),true);
		mail.send(message);
		return "Leave applied successfully";
		}
		catch(MessagingException e) {
			return "Failed to apply leave";
		}
	}
	
	@Override
	public String approveLeaveMail(String email,String name) {
		SimpleMailMessage message=new SimpleMailMessage();
		message.setTo(email);
		message.setSubject("Leave request approved !!");
		message.setText("Hello "+name+",\r\n"
				+ "\r\n"
				+"Your leave request has been approved by your manager.\r\n\r\n"
				+"Thanks & Regards\r\n"
				+"ABS Technologies");
		mail.send(message);
		return "Leave request has been approved";
	}
	
	@Override
	public String rejectLeaveMail(String email,String name) {
		SimpleMailMessage message=new SimpleMailMessage();
		message.setTo(email);
		message.setSubject("Leave request Denied !!");
		message.setText("Hello "+name+",\r\n"
				+ "\r\n"
				+"Your leave request has been denied by your manager.\r\n\r\n"
				+"Thanks & Regards\r\n"
				+"ABS Technologies");
		mail.send(message);
		return "Leave request has been denied";
	}

	@Override
	public String sendResignMailToHr(Employee employee) {
		int hrid=employee.getHid();
		Employee dbEmployee=employeeRepo.findById(hrid).get();
		SimpleMailMessage message=new SimpleMailMessage();
		message.setFrom("projectjspider@gmail.com");
		message.setTo(dbEmployee.getEmail());
		message.setSubject("Final settlement !! of "+employee.getEmpname());
		message.setText("Hello,\r\n"
				+ "\r\n"+
				employee.getEmpname()+" has resigned from company.Please do the final settlement "
						+ "of the employee.Below are the details of the employee.\r\n\r\nEmail:"+employee.getEmail()
				        +"\r\nEmp Name:"+employee.getEmpname()
				        +"\r\nEmp Id:"+employee.getEmpid()
				+"\r\n\r\n\r\n"
				+"Thanks & Regards\r\n"
				+"ABS Technologies");
		mail.send(message);
		return "Disabled successfully";
	}
}
