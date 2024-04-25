package com.abs.herosofhappiness.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abs.herosofhappiness.entity.AccountDetails;
import com.abs.herosofhappiness.entity.EmpLeave;
import com.abs.herosofhappiness.entity.EmployeeCopy;
import com.abs.herosofhappiness.service.EmployeeService;


@RestController
@RequestMapping("/employee")
@CrossOrigin("http://localhost:3000")
public class EmployeeController {
	
	@Autowired
	EmployeeService employeeService;

	
	@GetMapping("/getEmployee")
	public ResponseEntity<Object> getEmployee(@RequestHeader String email) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(employeeService.getEmployee(email));
	}
	
	@PutMapping("/edit")
	public ResponseEntity<Object> editEmployee(@RequestBody EmployeeCopy employeeCopy) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(employeeService.editemployee(employeeCopy));
	}
	
	@GetMapping("/login")
	public ResponseEntity<Object> empLogin(@RequestHeader String email,@RequestHeader String password){
		return new ResponseEntity<Object>(employeeService.loginEmp(email, password),HttpStatus.OK);
	}
	
	
	@GetMapping("/resetpasswordmail")
	public ResponseEntity<String> resetpasswordForMail(@RequestHeader String email){
		return new ResponseEntity<String>(employeeService.sendMailForResetPassword(email),HttpStatus.OK);
	}
	
	
	@PutMapping("/resetpassword")
	public ResponseEntity<String> resetpassword(@RequestHeader String email,@RequestHeader String password){
		System.out.println(email);
		System.out.println(password);
		return new ResponseEntity<String>(employeeService.resetPassword(email,password),HttpStatus.OK);
	}
	
	@GetMapping("/getAccount")
	public ResponseEntity<Object> getAccountDetails(@RequestHeader String email) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(employeeService.getAccountDetails(email));
	}
	
	@PostMapping("/createAccount")
	public ResponseEntity<Object> name(@RequestBody AccountDetails account,@RequestHeader String email) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(employeeService.createAccount(account,email));
	}
	
	@GetMapping("/leave")
	public ResponseEntity<Object> leave(@RequestHeader String email) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(employeeService.leave(email));
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@PostMapping("/applyLeave")
	public ResponseEntity<Object> applyLeave(@RequestBody EmpLeave empLeave,@RequestHeader String email) {
		return ResponseEntity.status(HttpStatus.OK).body(employeeService.applyLeave(empLeave,email));
	}
	
	@GetMapping("/approveLeave")
	public ResponseEntity<String> approvLeave(@RequestParam String email) {
		return ResponseEntity.status(HttpStatus.OK).body(employeeService.approveLeave(email));
	}
	
	@GetMapping("/rejectLeave")
	public ResponseEntity<String> rejectLeave(@RequestParam String email) {
		return ResponseEntity.status(HttpStatus.OK).body(employeeService.rejectLeave(email));
	}
	
	@GetMapping("/applyResignation")
	public ResponseEntity<Object> applyResignation(@RequestHeader String email){
		return ResponseEntity.status(HttpStatus.OK).body(employeeService.applyResignation(email));
	}
	
}
