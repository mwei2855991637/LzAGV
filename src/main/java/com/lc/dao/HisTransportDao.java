package com.lc.dao;


import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lc.bean.HisTransport;

@Repository
public interface HisTransportDao extends JpaRepository<HisTransport, Integer> {
	
	@Query("select carNum from HisTransport where end_time between ?1 and ?2 group by carNum")
	List<String> findGroup(String end_time1,String end_time);
	
	@Query("select h from HisTransport h where end_time between ?1 and ?2")
	List<HisTransport> findAllWhereCurrTime(String end_time1,String end_time);
	
	@Query("select h from HisTransport h where end_time between ?1 and ?2")
	List<HisTransport> findAllLimit(String end_time1,String end_time,Pageable pageable);
}
