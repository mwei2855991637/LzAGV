package com.lc.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lc.bean.StorageAuto;
@Repository
public interface StorageAutoDao extends JpaRepository<StorageAuto, Integer>  {
	@Transactional
	@Modifying
	@Query(value = "update StorageAuto set count_num = ?1 where storage_num = ?2")
	void updateByNum(Integer count,Integer storage_num);
	
	@Transactional
	@Modifying
	@Query(value = "delete from StorageAuto where storage_num = ?1")
	void deleteByNum(Integer storage_num);
	
	@Query(value = "select s from StorageAuto s where storage_num = ?1")
	List<StorageAuto> findByNum(Integer storage_num);
}
