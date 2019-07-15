package com.lc.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lc.bean.HisTransport;
import com.lc.bean.TransportPlan;
import com.lc.dao.TransportPlanDao;
import com.lc.service.HisTransportService;
import com.lc.service.TransPlanService;

@Service
public class TransPlanServiceImpl implements TransPlanService {
	@Autowired
	private TransportPlanDao dao;
	@Autowired
	private HisTransportService hisTransportService;
	
	@Override
	@Transactional
	public TransportPlan save(TransportPlan entity) {
		return dao.save(entity);
	}
	@Override
	@Transactional
	public void updateStToTrue(int id) {
		// 备份
		//System.out.println("backUpId："+id);
		hisTransportService.save(new HisTransport(dao.getOne(id)));
		// 删除原数据
		dao.deleteById(id);
	}
	//查询24点前所有的数据
	@Override
	public List<TransportPlan> findAllTPlan(String today) {
		return dao.findAllByTime(today+" 00:00:00");
	}
	//按车辆编号分组查询
	@Override
	public List<String> findGroup(String time1) {
		return dao.findGroup(time1+" 00:00:00",time1+" 23:59:59");
	}
	//查询今天所有的数据
	@Override
	public List<TransportPlan> findAllWhereCurrTime(String time1) {
		return dao.findAllWhereCurrTime(time1+" 00:00:00",time1+" 23:59:59");
	}
	//根据小车id查询未到达小车的货物编号
	@Override
	public Integer findByIdAndTime(String carNum) {
		return dao.findByIdAndTime(carNum,"-1");
	}
	//分页查询小车数据
	@Override
	public List<TransportPlan> findAllLimit(String time, Pageable pageable) {
		return dao.findAllLimit(time+" 00:00:00",time+" 23:59:59",pageable);
	}
	
}
