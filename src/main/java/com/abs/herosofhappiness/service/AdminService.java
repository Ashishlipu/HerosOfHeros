package com.abs.herosofhappiness.service;


import java.util.List;

import com.abs.herosofhappiness.entity.Admin;
import com.abs.herosofhappiness.entity.Client;
import com.abs.herosofhappiness.entity.Employee;


public interface AdminService {
	public Employee createAdmin(Employee admin); 
	
	public Employee createEmployee(Employee employee);
	
	public int loginAdmin(String email,String password);
	public String sendMailForResetPassword(String email);
	public String resetpassword(String email,String password);

	public Employee getAdminByEmail(String email);

	public List<Employee> getAllEmployees();

	public String disableEmployee(String email);
	
	public Client saveClient(Client client);
	
	public List<Client> getAllClients();
}
