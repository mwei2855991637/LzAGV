package com.lc.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lc.bean.CarStatus;
@Repository
public interface CarDao  extends JpaRepository<CarStatus, Integer>  {
	@Query("select c from CarStatus c where carid = ?1")
	CarStatus findByCarId(String id);
	
	@Transactional
	@Modifying
	@Query("update CarStatus set status=?1,fault=?2 where carid = ?3")
	void updateStatus(String status,String fault,String id);
	
}
