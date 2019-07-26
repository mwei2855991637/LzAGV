package com.lc.bean;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "customer")
public class Customer implements Serializable {
	@Id
	@GeneratedValue
	private Integer id;
	private String username;
	private String password;
	private String company;
	private String address;
	private String email;
	private String phone;
	private Integer power;
	public Customer() {
	}
	public Customer(String username, String password, String company, String address, String email,
			String phone, Integer power) {
		super();
		this.username = username;
		this.password = password;
		this.company = company;
		this.address = address;
		this.email = email;
		this.phone = phone;
		this.power = power;
	}
}
