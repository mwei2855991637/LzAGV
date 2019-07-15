package com.lc.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "t_transport_plan")
public class TransportPlan {

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
	
//	@CreationTimestamp
	private String startTime;
	
	@Column(name = "end_Time")
//	@UpdateTimestamp
	private String endTime;
	
	private Byte st = 0;

	public TransportPlan(String product, Integer quantity, String startPlace, String endPlace, String carNum,String startTime,String endTime) {
		this.product = product;
		this.quantity = quantity;
		this.startPlace = startPlace;
		this.endPlace = endPlace;
		this.carNum = carNum;
		this.startTime=startTime;
		this.endTime=endTime;
	}

	public TransportPlan() {
	}

	@Override
	public String toString() {
		return String.format("TransportPlan [id=%s, product=%s, quantity=%s, startPlace=%s, endPlace=%s, carNum=%s, startTime=%s, endTime=%s, st=%s]", id, product, quantity,
				startPlace, endPlace, carNum, startTime, endTime, st);
	}

}
