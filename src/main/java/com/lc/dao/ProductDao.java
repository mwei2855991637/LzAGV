package com.lc.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lc.bean.Product;
import com.lc.bean.TransportPlan;
@Repository
public interface ProductDao extends JpaRepository<Product, Integer> {
	@Query("select h from Product h where id = ?1")
	Product findProById(Integer id);
}
