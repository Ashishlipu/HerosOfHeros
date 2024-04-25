package com.abs.herosofhappiness.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.abs.herosofhappiness.entity.Client;
import com.abs.herosofhappiness.service.ClientService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@CrossOrigin("http://localhost:3000")
public class ClientController {
	
//	@Autowired
//	ClientService clientService;
//
//	@PostMapping("saveClient")
//	public ResponseEntity<String> resetpassword(@RequestBody Client c ){
//		return new ResponseEntity<String>(clientService.saveClient(c),HttpStatus.OK);
//	}
}
