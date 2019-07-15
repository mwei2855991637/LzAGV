package com.lc.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lc.bean.Agv;
@Service
public interface AgvService {
	List<Agv> findAll();
}
