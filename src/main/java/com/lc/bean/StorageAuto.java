package com.lc.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "storage_auto")
public class StorageAuto {
	@Id
	@GeneratedValue
	private Integer id;
	@Column(name = "storage_num")
	private String storageNum;
	@Column(name = "end_time")
	private String endTime;
	private Integer priority;
	@Column(name = "count_num")
	private Integer countNum;
	
	public StorageAuto() {
	}
	public StorageAuto(String storageNum, String endTime, Integer priority,Integer countNum) {
		this.storageNum = storageNum;
		this.endTime = endTime;
		this.priority = priority;
		this.countNum = countNum;
	}
	
}
