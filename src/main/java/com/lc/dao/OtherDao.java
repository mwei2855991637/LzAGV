package com.lc.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lc.bean.CurrStatus;

@Repository
public interface OtherDao extends JpaRepository<CurrStatus, Integer>  {
	@Query("select c from CurrStatus c where id = ?1")
	CurrStatus findByStatusId(Integer id);
	
	@Transactional
	@Modifying
	@Query("update CurrStatus set currName=?1 where id = ?2")
	void updateStatus(String currName,Integer id);
}
