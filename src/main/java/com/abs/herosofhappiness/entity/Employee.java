package com.abs.herosofhappiness.entity;

import java.sql.Clob;
import java.time.LocalDate;


import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int empid;
	
	@Column(name = "empname")
	private String empname;
	
	@Column(name = "email")
	@Pattern(regexp ="^[A-Za-z0-9+_.-]+@(.+)$")
	private String email;
	
	private String password;
	
	@Column(name = "designation")
	private String designation;
	
	private String pname;
	
	@Column(name = "phone")
	private long phone;
	
	@Column(name = "sal")
	private double salary;
	
	@Column(name = "gender")
	private String gender;
	
	@Column(name = "startdate")
	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate hiredate;
		
	private String mgrname;
	
	@Column(name = "hr_id")
	private int hid;
		
	@Column(name = "mgr_id")
	private int mid;	
	
	private String address;
	
	private Clob profileimage;
	
	private String status;
	
	private String mode;
	
	@OneToOne(cascade = CascadeType.ALL)
	AccountDetails account;
	
//	@OneToMany(mappedBy = "employee")
//	private List<Project> project;

	public Employee(EmployeeCopy employeeCopy) {
		this.empname=employeeCopy.getEmpname();
		this.email=employeeCopy.getEmail();
		this.password=employeeCopy.getPassword();
		this.designation=employeeCopy.getDesignation();
		this.pname=employeeCopy.getPname();
		this.phone=employeeCopy.getPhone();
		this.salary=employeeCopy.getSalary();
		this.gender=employeeCopy.getGender();
		this.hiredate=employeeCopy.getHiredate();
		this.mgrname=employeeCopy.getMgrname();
		this.hid=employeeCopy.getHid();
		this.mid=employeeCopy.getMid();
		this.address=employeeCopy.getAddress();
		this.status=employeeCopy.getStatus();
		this.mode=employeeCopy.getMode();
		this.profileimage=employeeCopy.getProfileimage();
//		this.project=employeeCopy.getProject();
	}
}
