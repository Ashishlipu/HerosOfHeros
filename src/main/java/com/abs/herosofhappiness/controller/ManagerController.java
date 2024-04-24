package com.abs.herosofhappiness.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abs.herosofhappiness.entity.Employee;
import com.abs.herosofhappiness.service.ManagerService;


@RestController
@RequestMapping("manager")
@CrossOrigin("http://localhost:3000")
public class ManagerController {
	@Autowired
	ManagerService managerService;
	
	
	@GetMapping("login")
	public ResponseEntity<Object> managerLogin(@RequestHeader String email,@RequestHeader String password){
		return new ResponseEntity<Object>(managerService.loginManager(email, password),HttpStatus.FOUND);
	}
	
	
	@GetMapping("resetpasswordmail")
	public ResponseEntity<String> resetpasswordForMail(@RequestHeader String email){
		return new ResponseEntity<String>(managerService.sendMailForResetPassword(email),HttpStatus.OK);
	}
	
	
	@PutMapping("resetpassword")
	public ResponseEntity<String> resetpassword(@RequestHeader String email,@RequestHeader String password){
		return new ResponseEntity<String>(managerService.resetPassword(email,password),HttpStatus.OK);
	}
	
	
	@GetMapping("getemployeebymgr")
	public ResponseEntity<List<Employee>> findEmpByHr_id(@RequestHeader String email){
		return new ResponseEntity<List<Employee>>(managerService.findEmpByMgrId(email),HttpStatus.FOUND);
	}
}
