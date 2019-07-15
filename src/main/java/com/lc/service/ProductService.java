package com.lc.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.lc.bean.Product;
@Service
public interface ProductService {
	Product findById(Integer id);
}
