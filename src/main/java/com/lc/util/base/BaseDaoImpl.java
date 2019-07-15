package com.lc.util.base;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.lc.util.bean.Page;

// 可以被继承,开启事物，交给spring管理
// @Transactional
@SuppressWarnings("unchecked")
public abstract class BaseDaoImpl<T> implements BaseDao<T> {
	@Resource
	protected SessionFactory sessionFactory; // (该属性需要在子类中注入xml)
	protected List<Object> objects;
	private String className;

	private Class<T> clazz;

	public BaseDaoImpl() {
		ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();//返回当前对象所表示的类的超类，包括其泛型
		this.clazz = (Class<T>) pt.getActualTypeArguments()[0];//如：user.class
		this.className = clazz.getSimpleName();//获取类的简写名称
	}

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	// -------------------- findAll --------------------
	/**
	 * @param attr1     查询参数
	 * @param value1    查询参数的值
	 * @param attr2     排序参数
	 * @param ascOrDesc 升/降序
	 * @return getSession() .createQuery("from " + className + " where " + attr1 + " = ? order by " + attr2 + " " + ascOrDesc) .setParameter(0, value1).list();
	 */
//	protected List<T> _findByInputAttr1AndOrderByAttr2(String attr1, Object value1, String attr2, String ascOrDesc) {
//		return getSession()
//				.createQuery("from " + className + " where " + attr1 + " = ? order by " + attr2 + " " + ascOrDesc)
//				.setParameter(0, value1).list();
//	}

	/**
	 * @param sql
	 * @return getSession().createQuery("FROM " + className + " " + sql).list();
	 */
//	protected List<T> _findAllWithConditions(String sql) {
//		return getSession().createQuery("FROM " + className + " " + sql).list();
//	}

	public List<T> findAll() {
		return getSession().createQuery("FROM " + className).list();
	}

	/**
	 * @param attr
	 * @param value
	 * @return "from " + className + " where " + attr + " like ?"
	 */
	protected List<T> _findByLikeAttr(String attr, Object attrVal) {
		// return getSession().createQuery("from " + className + " where " + attr + " like ?") .setParameter(0, "%" + value.toString() + "%").list();
		return _findByLikeConditions(null, attr, attrVal);
	}

	/**
	 * @param attr
	 * @param value
	 * @return "from " + className + " where " + attr + " = ?"
	 */
	protected List<T> _findByAttr(String attr, Object attrVal) {
		// return getSession().createQuery("from " + className + " where " + attr + " = ?").setParameter(0, value).list();
		return _findByConditions(null, attr, attrVal);
	}

	public List<T> findAllByIds(Integer[] ids) {
		if (ids != null && ids.length > 0) {
			return getSession().createQuery("from " + className + " where id in (:ids)").setParameterList("ids", ids).list();
		}
		return null;
	}

	/**
	 * @param attribute
	 * @param ascOrDesc
	 * @return "from " + className + " order by " + attribute + " " + ascOrDesc
	 */
	protected List<T> _findOrderByAttr(String attr, String ascOrDesc) {
		return _findByConditions("order by " + attr + " " + ascOrDesc);
	}

	/**
	 * attr = ?
	 * 
	 * @param endSql "order by xx desc"
	 * @param args   key1,value1,key2,value2,....
	 * @return
	 */
	protected List<T> _findByConditions(String endSql, Object... args) {
		if (endSql == null) {
			endSql = "";
		}
		if (args.length > 0) {
			String sql = "from " + className + " where ";
			for (int i = 0; i < args.length / 2; i++) {
				sql += args[i * 2] + " = ? ";
				if (i < args.length / 2 - 1) {
					sql += "and ";
				}
			}
			Query createQuery = getSession().createQuery(sql + endSql);
			for (int i = 0; i < args.length / 2; i++) {
				createQuery.setParameter(i, args[i * 2 + 1]);
			}
			return createQuery.list();
		} else {
			return getSession().createQuery("from " + className + " " + endSql).list();
		}
	}

	/**
	 * attr like ?
	 * 
	 * @param endSql "order by xx desc"
	 * @param args   key1,value1,key2,value2,....
	 * @return
	 */
	protected List<T> _findByLikeConditions(String endSql, Object... args) {
		if (endSql == null) {
			endSql = "";
		}
		if (args.length > 0) {
			String sql = "from " + className + " where ";
			for (int i = 0; i < args.length / 2; i++) {
				sql += args[i * 2] + " like ? ";
				if (i < args.length / 2 - 1) {
					sql += "and ";
				}
			}
			Query createQuery = getSession().createQuery(sql + endSql);
			for (int i = 0; i < args.length / 2; i++) {
				createQuery.setParameter(i, "%" + args[i * 2 + 1] + "%");
			}
			return createQuery.list();
		} else {
			return getSession().createQuery("from " + className + " " + endSql).list();
		}
	}

	// -------------------- getOne --------------------
	/**
	 * @param attr
	 * @param value
	 * @return (T) getSession().createQuery("from " + className + " where " + attr + " = ?").setParameter(0, value) .uniqueResult();
	 */
	protected T _getByAttr(String attr, Object attrVal) {
		return (T) getSession().createQuery("from " + className + " where " + attr + " = ?").setParameter(0, attrVal).uniqueResult();
	}

	/**
	 * @param attr
	 * @param value
	 * @param hql
	 * @return (T) getSession().createQuery("from " + className + " where " + hql).setParameter(0, value) .uniqueResult();
	 */
	protected T _getByHql(String hql, Object value) {
		return (T) getSession().createQuery("from " + className + " where " + hql).setParameter(0, value).uniqueResult();
	}

	public T getById(Integer id) {
		if (id == null) {
			return null;
		} else {
			return (T) getSession().get(clazz, id);
		}
	}

	public T getById(Long id) {
		if (id == null) {
			return null;
		} else {
			return (T) getSession().get(clazz, id);
		}
	}

	// -------------------- save/update/saveOrUpdate/saveAsList --------------------
	public void save(T entity) {
		getSession().save(entity);
	}

	public void update(T entity) {
		getSession().update(entity);
	}

	public void saveOrUpdate(T entity) {
		getSession().saveOrUpdate(entity);
	}

	public void saveAsList(List<T> list) {
		int i = 0;
		for (T p : list) {
			// System.out.println(p);
			// save(p);
			saveOrUpdate(p);
			if (i++ > 100) {
				i = 0;
				getSession().flush();
				getSession().clear();
			}
		}
	}

	// -------------------- delete --------------------
	public Integer delete(Integer id) {
		return getSession().createQuery("delete " + className + " where id =?").setParameter(0, id).executeUpdate();
	}

	public Integer delete(Long id) {
		return getSession().createQuery("delete " + className + " where id =?").setParameter(0, id).executeUpdate();
	}

	public Integer deleteInIds(Integer[] ids) {
		if (ids.length > 0) {
			return getSession().createQuery("delete " + className + " where id in (:ids)").setParameterList("ids", ids).executeUpdate();
		}
		return null;
	}

	/**
	 * getSession().createQuery("delete " + className + " where " + hql).executeUpdate();
	 * 
	 * @param hql
	 * @param value
	 * @return
	 */
	public Integer _deleteByHql(String hql, Object value) {
		return getSession().createQuery("delete " + className + " where " + hql).executeUpdate();
	}

	public Integer _deleteByAttr(String attr, Object value) {
		return getSession().createQuery("delete " + className + " where " + attr + "=?").setParameter(0, value).executeUpdate();
	}

	public Integer deleteAll() {
		return getSession().createQuery("delete " + className).executeUpdate();
	}

	/*
	 * public void delete(Integer id) { Object obj = getById(id); if (obj != null) {
	 * getSession().delete(obj); } }
	 * 
	 * public void delete(Long id) { Object obj = getById(id); if (obj != null) {
	 * getSession().delete(obj); } }
	 */

	// -------------------- findByPage --------------------
	public Page<T> findAllByPage(Page page) {
		return _findByPage(null, page);
	}

	/**
	 * hql = hql == null ? "" : " " + hql; <br/>
	 * hql = "from " + className + hql;
	 * 
	 * @param hql
	 * @param page
	 * @return
	 */
	protected Page<T> _findByPage(String hql, Page page) {
		if (page != null) {
			// 输入参数
			Integer currPage = page.getCurrPage();
			int sizeInPage = page.getSizeInPage();

			hql = hql == null ? "" : " " + hql;
			hql = "from " + className + hql;

			Query query = getSession().createQuery(hql);

			ScrollableResults sroll = query.scroll();
			sroll.last();

			int count = sroll.getRowNumber() + 1;
			int countPage = count / sizeInPage;
			countPage = count % sizeInPage == 0 ? countPage : countPage + 1; // 处理总页数
			// currPage = currPage == null ? 1 : currPage; // 处理当前页

			// 已经获得 总记录数，当前页码，每页数量，开始封装分页显示的记录
			query.setFirstResult((currPage - 1) * sizeInPage).setMaxResults(sizeInPage);

			// 处理page的所有属性
			page.setTotalRecord(count);
			page.setTotalPage(countPage);
			page.setResult(query.list());
			return page;
		} else {
			return new Page<T>();
		}
	}
}
