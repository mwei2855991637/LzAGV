package com.lc.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lc.bean.HisTransport;
import com.lc.bean.TransportPlan;

@Repository
public interface TransportPlanDao extends JpaRepository<TransportPlan, Integer> {

//	@Query("update Weibo w set w.weiboText = :text where w.user = :user")
	@Modifying
	@Query("update TransportPlan t set t.st = ?2 where t.id = ?1")
	int updateStById(int id, byte st);

	@Transactional
	@Modifying
	@Query(value = "update t_Transport_Plan t set t.product = ?1 where t.id = ?2", nativeQuery = true)
	void update(String product, int id);

	@Transactional
	@Modifying
	@Query("update TransportPlan t set t.product = ?2 where t.id = ?1")
	void update(int id, String product);
	
	@Query("select h from TransportPlan h where end_time < ?1")
	List<TransportPlan> findAllByTime(String end_time1);
	
	@Query("select carNum from TransportPlan where end_time between ?1 and ?2 group by carNum")
	List<String> findGroup(String end_time1,String end_time);
	
	@Query("select h from TransportPlan h where end_time between ?1 and ?2")
	List<TransportPlan> findAllWhereCurrTime(String end_time1,String end_time);
	
	@Query("select product from TransportPlan  where car_num = ?1 and end_time = ?2")
	Integer findByIdAndTime(String car_num,String end_time);
	
	@Query("select h from TransportPlan h where end_time between ?1 and ?2")
	List<TransportPlan> findAllLimit(String end_time1,String end_time,Pageable pageable);
	
	
	@Transactional
	@Modifying
	@Query(value = "delete from t_transport_plan where FIND_IN_SET(id,?)",nativeQuery=true)
	void delSel(String ids);
	
	@Transactional
	@Modifying
	@Query(value = "delete from t_transport_plan where end_time between ? and ?",nativeQuery=true)
	void delDay(String time1,String time2);
	
	@Transactional
	@Modifying
	@Query("delete from TransportPlan")
	void delAll();
	
}
