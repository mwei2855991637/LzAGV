package com.lc.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "car_status")
public class CarStatus {
	@Id
	@GeneratedValue
	private Integer id;
	@Column(name = "carid")
	private String carid;
	@Column(name = "status")
	private String status;
	@Column(name = "fault")
	private String fault;
	@Column(name = "road")
	private String road;
	@Column(name = "curplace")
	private String curplace;
	@Column(name = "electric")
	private String electric;
	@Column(name = "speed")
	private String speed;
	public CarStatus() {
	}
	public CarStatus(String carid, String status, String fault, String road, String curplace,
			String speed) {
		this.carid = carid;
		this.status = status;
		this.fault = fault;
		this.road = road;
		this.curplace = curplace;
		this.speed = speed;
	}
	
}
