package com.lc.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lc.bean.Customer;

@Repository
public interface CustomerDao extends JpaRepository<Customer, Integer> {
	//根据用户名密码查找信息
	@Query("select c from Customer c where username=?1 and password=?2")
	Customer login(String username,String password);
	//根据用户名查找信息
	Customer findByUsername(String username);
	@Transactional
	@Modifying
	@Query("update Customer set password=?1 where username=?2")
	void updatePassWord(String username,String password);
	
}
