package com.abs.herosofhappiness.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abs.herosofhappiness.entity.Admin;
import com.abs.herosofhappiness.entity.Client;
import com.abs.herosofhappiness.entity.Employee;
import com.abs.herosofhappiness.service.AdminService;


@RestController
@CrossOrigin("http://localhost:3000")
public class AdminController {
	
	@Autowired
	AdminService adminService;
	
	@PostMapping("/createadmin")
	public ResponseEntity<Object> createAdmin(@RequestBody Employee admin) {
		try {
		return ResponseEntity.status(HttpStatus.OK).body(adminService.createAdmin(admin));		
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@PostMapping("/createemployee")
	public ResponseEntity<Object> createEmployee(@RequestBody Employee employee) {
		try {
		return ResponseEntity.status(HttpStatus.OK).body(adminService.createEmployee(employee));
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@GetMapping("/getAdmin")
	public ResponseEntity<Object> getAdmin(@RequestHeader String email){
		return ResponseEntity.status(HttpStatus.FOUND).body(adminService.getAdminByEmail(email));
	}
	
	
	@GetMapping("login")
	public ResponseEntity<Integer> adminLogin(@RequestHeader String email,@RequestHeader String password){
		return new ResponseEntity<Integer>(adminService.loginAdmin(email, password),HttpStatus.OK);
	}
	
	
	@GetMapping("resetpasswordmail")
	public ResponseEntity<String> resetpasswordForMail(@RequestHeader String email){
		return new ResponseEntity<String>(adminService.sendMailForResetPassword(email),HttpStatus.OK);
	}
	
	
	@PutMapping("resetpassword")
	public ResponseEntity<String> resetpassword(@RequestHeader String email,@RequestHeader String password){
		return new ResponseEntity<String>(adminService.resetpassword(email,password),HttpStatus.OK);
	}
	
	
	@GetMapping("/getAllEmployees")
	public ResponseEntity<List<Employee>> getAllEmployees() {
		return ResponseEntity.status(HttpStatus.OK).body(adminService.getAllEmployees());
	}
	
	@GetMapping("/inactiveEmployee")
	public ResponseEntity<String> disableEmployee(@RequestHeader String email) {
		return ResponseEntity.status(HttpStatus.OK).body(adminService.disableEmployee(email));
	}
	
	@PostMapping("/saveClient")
	public ResponseEntity<Object> saveClient(@RequestBody Client client) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(adminService.saveClient(client));
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@GetMapping("/getAllClients")
	public ResponseEntity<List<Client>> getAllCLients() {
			return ResponseEntity.status(HttpStatus.OK).body(adminService.getAllClients());
	}
}
