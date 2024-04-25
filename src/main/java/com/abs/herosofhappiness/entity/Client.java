package com.abs.herosofhappiness.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Entity
@Data
public class Client {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int c_id;
	
	@Column(name = "name")
	private String c_name;
	
	@Column(name ="loc")
	private String c_loc;
	
	@Column(name = "pocemail")
	@Pattern(regexp ="^[A-Za-z0-9+_.-]+@(.+)$")
	private String poc_email;
	
	private long poc_phone;
	
//	@OneToMany(mappedBy = "client")
//	private List<Project> project;
}
