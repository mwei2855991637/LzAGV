package com.lc.util.base;

import java.util.List;

public interface BaseService<T> {

	public List<T> findAll();

	public T getById(Integer id);

	public String delete(Integer id);

	public void update(T t);

	public void save(T t);

}
