package com.lc.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lc.bean.Product;
import com.lc.dao.ProductDao;
import com.lc.service.ProductService;
@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductDao productDao;
	@Override
	public Product findById(Integer id) {
		return productDao.findProById(id);
	}
 
}
