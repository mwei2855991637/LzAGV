package com.lc.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lc.bean.CurrStatus;

@Service
public interface OtherService {
	//根据id查询当前状态
	List<CurrStatus> findAll();
	//更改状态
	void updateStatus(String currName,Integer id);
}
