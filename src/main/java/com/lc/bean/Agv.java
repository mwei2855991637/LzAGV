package com.lc.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "agv")
public class Agv {
	@Id
	@GeneratedValue
	private Integer id;
	private String carNo;
	private String carName;
	private String carType;
	
	public Agv() {
	
	}
	public Agv(Integer id, String carNo, String carName, String carType) {
		this.id = id;
		this.carNo = carNo;
		this.carName = carName;
		this.carType = carType;
	}
	
}
