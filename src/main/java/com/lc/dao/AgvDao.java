package com.lc.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lc.bean.Agv;
@Repository
public interface AgvDao extends JpaRepository<Agv, Integer> {

}
