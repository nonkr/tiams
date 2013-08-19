package tiams.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 基础数据库操作类
 * 
 * @author nonkr
 * 
 */
public interface BaseDaoI<T> {

	public Serializable save(T o);

	public void delete(T o);

	public void update(T o);

	public void saveOrUpdate(T o);
	
	public T get(Class<T> c, Serializable id);

	public T get(String hql);

	public T get(String hql, Map<String, Object> params);

	public List<T> find(String hql);

	public List<T> find(String hql, Map<String, Object> params);

	public List<T> find(String hql, int page, int rows);

	public List<T> find(String hql, Map<String, Object> params, int page, int rows);

	public Long count(String hql);

	public Long count(String hql, Map<String, Object> params);
	
	public int execute(String hql);

    public int execute(String hql, Map<String, Object> params);

}
