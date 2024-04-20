package com.abs.herosofhappiness.service;

import com.abs.herosofhappiness.entity.EmpLeave;
import com.abs.herosofhappiness.entity.Employee;

public interface MailService {
	public String applyLeaveMail(EmpLeave leave,String email,String mgrname,String empname);

	public String sendResignMailToHr(Employee employee);
	
	public String sendMailForResetPassword(String email,String mgrname,String designatio);
	
	public String approveLeaveMail(String email,String name);
	
	public String rejectLeaveMail(String email,String name);
}
