package com.lc.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
//当前状态（手动，自动）
@Data
@Entity
@Table(name = "curr_status")
public class CurrStatus {
	@Id
	@GeneratedValue
	private Integer id;
	@Column(name = "curr_name")
	private String currName;
	
	public CurrStatus() {
	}
	
	public CurrStatus(Integer id, String currName) {
		this.id = id;
		this.currName = currName;
	}

	@Override
	public String toString() {
		return "CurrStatus [id=" + id + ", currName=" + currName + "]";
	}
	
	
}
