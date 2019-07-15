package com.lc.util.base;

import java.util.List;

import com.lc.util.bean.Page;

public interface BaseDao<T> {

	// ----------- findAll ----------
	public List<T> findAll();

	public List<T> findAllByIds(Integer[] ids);

	// 参数要成队，如果没有参数，会返回全部数据
	// 示例参数值， findByCondition("fileInfo.name","JunAn.txt","upUser.name","admin")
	// 会对所有的，输入的参数，都进行模糊查找
	// public List<T> _findByCondition(String... args) {

	// 根据输入的属性，升序/降序排列
	//public List<T> _findAllOrderByAttribute(String attribute, String ascOrDesc);

	// 根据某个属性的值，模糊查找
	//public List<T> _findByLikeAttribute(String attr, Object value);

	// 根据输入的某个属性的值，精准查找
	//public List<T> _findByInputAttribute(String attr, Object value);

	// --------- get -----------

	public T getById(Integer id);

	public T getById(Long id);

	// 根据某个唯一约束的属性的值，精准匹配
	// T _getByInputAttribute(String attr, Object value);

	// ---------- delete ----------
	public Integer delete(Integer id);

	public Integer delete(Long id);

	public Integer deleteAll();

	public Integer deleteInIds(Integer[] ids);

	// --------- save or update-----------
	public void save(T entity);

	public void update(T entity);

	public void saveOrUpdate(T entity);

	// 批量保存
	// 遍历集合，对单个对象使用 saveOrUpdate，每隔100次就对session进行清洗
	public void saveAsList(List<T> list);

	// --------------------
	/*
	 * 初次访问，没有currPage 的信息，后台默认到 1，同时补全 总页数和总记录数 后续访问，携带 page 的相关信息进行跳转
	 * 
	 * 如果页面上，有 currPage 的信息，则进行约束
	 * 
	 * 无论如何，页面上，都需要处理好 sizeInPage 的值 页面初次访问，从cookie中抓取赋值，后续变动，同步更新到cookie，后台直接使用
	 */

	/*
	 * 预留条件查询的分页，hql 是条件部分 例， hql1 = "where name = '1'" hql2 = "order by id asc"
	 */
	// 分页，根据条件
	//public Page<T> _findByPage(String hql, Page page);

	// 不带条件的分页，相当于是对全部数据进行分页处理
	public Page<T> findAllByPage(Page page);

}
