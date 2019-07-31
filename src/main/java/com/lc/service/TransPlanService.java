package com.lc.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lc.bean.HisTransport;
import com.lc.bean.TransportPlan;
@Service
public interface TransPlanService {

	// 保存记录，需要通知到网页上（需要网页上显示对应的数据）
	TransportPlan save(TransportPlan entity);

	// 更新指定记录的状态，成功后，也需要通知到网页
	void updateStToTrue(int id);
	
	//查询TransportPlan表里所有数据
	List<TransportPlan> findAllTPlan(String today);
	
	//按车辆编号分组查询
	List<String> findGroup(String end_time);
	//查询HisTransportService表里今日所有数据
	List<TransportPlan> findAllWhereCurrTime(String end_time);
	
	//根据小车id查询未到达小车的货物编号
	Integer findByIdAndTime(String carNum);
	
	//分页查询小车数据
	List<TransportPlan> findAllLimit(String time,Pageable pageable);
	
	//删除选中数据
	void delSel(String ids);
	//删除今日数据
	void delDay(String time1,String time2);
	//删除全部数据
	void delAll();
	
}
