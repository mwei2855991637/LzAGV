package com.lc.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lc.bean.StorageAuto;
@Service
public interface StorageAutoService {
	//查询所有已满库位信息
	List<StorageAuto> findAll();
	//添加库位状态
	void sava(StorageAuto storageAuto);
	//删除库位状态
	void deleteByNum(Integer id);
	//更新库位状态
	void update(Integer count,Integer id);
	//根据库位id查询
	List<StorageAuto> findByNum(Integer id);
}
