package com.lc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lc.bean.Agv;
import com.lc.dao.AgvDao;
import com.lc.service.AgvService;
@Service
public class AgvServiceImpl implements AgvService {
	@Autowired
	private AgvDao dao;
	@Override
	public List<Agv> findAll() {
		return dao.findAll();
	}

}
