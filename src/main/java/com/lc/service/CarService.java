package com.lc.service;

import org.springframework.stereotype.Service;

import com.lc.bean.CarStatus;
@Service
public interface CarService {
	//根据车辆id查询车辆信息
	CarStatus findByCarId(String id);
	//更改状态
	void updateStatus(String status,String fault,String id);
}
