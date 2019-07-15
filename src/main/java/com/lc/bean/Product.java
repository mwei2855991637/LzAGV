package com.lc.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "p_product")
public class Product {
	@Id
	@GeneratedValue
	private Integer id;
	@Column(name = "p_name")
	private String name;
	@Column(name = "p_num")
	private Integer num;
	@Column(name = "p_desc")
	private String desc;
	@Column(name = "p_status")
	private String status;
	public Product() {
	}
	public Product(String name, Integer num, String desc, String status) {
		this.name = name;
		this.num = num;
		this.desc = desc;
		this.status = status;
	}
	
}
