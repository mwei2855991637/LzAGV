package com.lc.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lc.bean.HisTransport;
@Service
public interface HisTransportService {

	HisTransport save(HisTransport entity);
	
	//按车辆编号分组查询
	List<String> findGroup(String end_time);
	
	List<HisTransport> findAllWhereCurrTime(String end_time);

	//分页查询小车数据
	List<HisTransport> findAllLimit(String time,Pageable pageable);
	//删除选中数据
	void delSel(String ids);
	//删除今日数据
	void delDay(String time1,String time2);
	//删除全部数据
	void delAll();
}
