package com.lc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lc.bean.HisTransport;
import com.lc.dao.HisTransportDao;
import com.lc.service.HisTransportService;

@Service
public class HisTransportServiceImpl implements HisTransportService {
	@Override
	@Transactional
	public HisTransport save(HisTransport entity) {
		return dao.save(entity);
	}

	@Autowired
	private HisTransportDao dao;
	
	//按车辆编号分组查询
	@Override
	public List<String> findGroup(String time1) {
		return dao.findGroup(time1+" 00:00:00",time1+" 23:59:59");
	}
	
	@Override
	public List<HisTransport> findAllWhereCurrTime(String time1) {
		return dao.findAllWhereCurrTime(time1+" 00:00:00",time1+" 23:59:59");
	}
	//分页查询小车数据
	@Override
	public List<HisTransport> findAllLimit(String time, Pageable pageable) {
		return dao.findAllLimit(time+" 00:00:00",time+" 23:59:59",pageable);
	}
	//删除选中数据
	@Override
	public void delSel(String ids) {
		dao.delSel(ids);
	}
	//删除今日数据
	@Override
	public void delDay(String time1, String time2) {
		dao.delDay(time1, time2);
	}
	//删除所有数据
	@Override
	public void delAll() {
		dao.delAll();
	}
	
	

}
