package com.lc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lc.bean.StorageAuto;
import com.lc.dao.StorageAutoDao;
import com.lc.service.StorageAutoService;
@Service
public class StorageAutoServiceImpl implements StorageAutoService {
	@Autowired
	private StorageAutoDao dao;
	//查询所有已满库位信息
	@Override
	public List<StorageAuto> findAll() {
		return dao.findAll();
	}
	//添加库位状态
	@Override
	public void sava(StorageAuto storageAuto) {
		System.out.println("执行保存操作");
		dao.save(storageAuto);	
	}
	//删除库位状态
	@Override
	public void deleteByNum(Integer id) {
		dao.deleteByNum(id);		
	}
	//更新库位状态
	@Override
	public void update(Integer count,Integer id) {
		dao.updateByNum(count, id);
	}
	//根据库位id查询
	@Override
	public List<StorageAuto> findByNum(Integer id) {
		return dao.findByNum(id);
	}

}
