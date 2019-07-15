package com.lc.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "t_his_transport")
public class HisTransport {
	@Id
	@GeneratedValue
	private Integer id;
	private String product;
	private Integer quantity;
	@Column(name = "start_Place")
	private String startPlace;
	@Column(name = "end_Place")
	private String endPlace;
	@Column(name = "car_Num")
	private String carNum;
	@Column(name = "start_Time")
	private String startTime;
	@Column(name = "end_Time")
	private String endTime;

	public HisTransport(TransportPlan plan) {
		id = plan.getId();
		product = plan.getProduct();
		quantity = plan.getQuantity();
		startPlace = plan.getStartPlace();
		endPlace = plan.getEndPlace();
		carNum = plan.getCarNum();
		startTime = plan.getStartTime();
		endTime = plan.getEndTime();
	}

	@Override
	public String toString() {
		return String.format("HisTransport [id=%s, product=%s, quantity=%s, startPlace=%s, endPlace=%s, carNum=%s, startTime=%s, endTime=%s]", id, product, quantity, startPlace,
				endPlace, carNum, startTime, endTime);
	}

	public HisTransport() {
	}

}
