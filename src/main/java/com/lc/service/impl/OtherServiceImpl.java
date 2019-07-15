package com.lc.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lc.bean.CurrStatus;
import com.lc.dao.OtherDao;
import com.lc.service.OtherService;
@Service
public class OtherServiceImpl implements OtherService {
	@Autowired
	private OtherDao dao;
	@Override
	public List<CurrStatus> findAll() {
		List<CurrStatus> list=dao.findAll();
		for (CurrStatus currStatus : list) {
			System.out.println("业务层："+currStatus);
		}
		return list;
	}

	@Override
	public void updateStatus(String currName, Integer id) {
		dao.updateStatus(currName,id);
	}

}
