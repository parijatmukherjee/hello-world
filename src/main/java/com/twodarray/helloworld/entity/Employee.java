package com.twodarray.helloworld.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "EMPLOYEE")
public class Employee
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "AUTO_ID")
	private Long autoId;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "BIRTH_DAY")
	@Temporal(TemporalType.TIMESTAMP)
	private Date birthday;
	
	@Column(name = "ANNIVERSARY_DAY")
	@Temporal(TemporalType.TIMESTAMP)
	private Date anniversaryDate;
}
