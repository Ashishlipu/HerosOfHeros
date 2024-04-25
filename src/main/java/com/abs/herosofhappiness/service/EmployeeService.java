package com.abs.herosofhappiness.service;


import com.abs.herosofhappiness.entity.AccountDetails;
import com.abs.herosofhappiness.entity.EmpLeave;
import com.abs.herosofhappiness.entity.Employee;
import com.abs.herosofhappiness.entity.EmployeeCopy;


public interface EmployeeService {
	public Employee getEmployee(String email);
	public Employee editemployee(EmployeeCopy employeeCopy);
	public int loginEmp(String email,String password);
	public String sendMailForResetPassword(String email);
	public String resetPassword(String email,String password);
	public AccountDetails getAccountDetails(String email);
	public String createAccount(AccountDetails account, String email);
	public String applyLeave(EmpLeave empLeave, String email);
	public String applyResignation(String email);
	public String rejectLeave(String email);
	public String approveLeave(String email);
	public Object leave(String email);
	
}
