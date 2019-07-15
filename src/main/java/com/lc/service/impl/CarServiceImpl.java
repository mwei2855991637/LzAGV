package com.lc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lc.bean.CarStatus;
import com.lc.dao.CarDao;
import com.lc.service.CarService;
@Service
public class CarServiceImpl implements CarService {
	@Autowired
	private CarDao dao;
	@Override
	public CarStatus findByCarId(String id) {
		return dao.findByCarId(id);
	}
	@Override
	public void updateStatus(String status, String fault, String id) {
		dao.updateStatus(status, fault, id);
	}

}
