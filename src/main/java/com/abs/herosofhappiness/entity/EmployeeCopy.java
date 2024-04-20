package com.abs.herosofhappiness.entity;

import java.sql.Clob;
import java.time.LocalDate;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class EmployeeCopy {

    private String empname;
	
	@Pattern(regexp ="^[A-Za-z0-9+_.-]+@(.+)$")
	private String email;
	
	private String password;
	
	private String designation;
	
	private String pname;
	
	@Min(value = 6000000000l)
	@Max(value = 9999999999l)
	private long phone;
	
	private double salary;
	
	private String gender;
	
	private LocalDate hiredate;
			
	private String mgrname;
		
	private int hid;
		
	private int mid;	
	
	private String address;
	
	private Clob profileimage;
	
	private String status;
	
	private String mode;
	
//	@OneToMany(mappedBy = "employee")
//	private List<Project> project;
}
