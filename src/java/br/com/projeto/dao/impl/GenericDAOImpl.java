package br.com.projeto.dao.impl;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.projeto.dao.DAO;


/**
 * Basic Generic DAO
 * 
 * @author mliberato
 * @author marcio.camurati
 * 
 * @since 03/09/2009
 * @version 1.1
 * 
 */
@Repository
@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
public class GenericDAOImpl<T, ID extends Serializable> implements DAO<T, ID> {
	
	protected EntityManager em;

	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}
	
	/*
	 * (non-Javadoc)
	 * @see br.com.cliente.dao.DAO#insert(java.lang.Object)
	 */
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void insert(T entity) {
		em.persist(entity);
	}
	
	/*
	 * (non-Javadoc)
	 * @see br.com.cliente.dao.DAO#update(java.lang.Object)
	 */
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void update(T entity) {
		em.merge(entity);
	}
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public T merge(T entity) {
		return em.merge(entity);
	}
	
	/*
	 * (non-Javadoc)
	 * @see br.com.cliente.dao.DAO#save(java.lang.Object)
	 */
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void save(T entity) throws SecurityException, NoSuchMethodException, Exception {
		Field[] fields = entity.getClass().getDeclaredFields();
		
		for (Field field : fields)	{
			Annotation annotation = field.getAnnotation(Id.class);
			
			if(annotation instanceof Id){
				String tempField = field.getName().substring(0, 1).toUpperCase() 
										+ field.getName().substring(1, field.getName().length());
				
				Method method = entity.getClass().getMethod("get" + tempField, null);
				
				Long id = (Long) method.invoke(entity, null);
				
				if (id == null)	{
					this.insert(entity);
				}	else	{
					this.update(entity);
				}
				
				break;
			}
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see br.com.cliente.dao.DAO#remove(java.lang.Class, java.lang.Long)
	 */
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void remove(Class<T> entityClass, Long id) {
		T entity = findById(entityClass, id);
		em.remove(entity);
	}

	/*
	 * (non-Javadoc)
	 * @see br.com.cliente.dao.DAO#remove(java.lang.Class, java.lang.Long[])
	 */
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void remove(Class<T> entityClass, Long[] ids) {
		for(int i = 0; i < ids.length; i++) {
			Long id = ids[i];
			this.remove(entityClass, id);
		}
	}
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void remove(Class<T> entityClass, Collection<T> collection) {
		for (T obj : collection)	{
			em.remove(obj);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see br.com.cliente.dao.DAO#findAll(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	public List<T> findAll(Class<T> entityClass) {
		String queryName = this.getQueryName(entityClass, "find", "All");
		Query query = em.createNamedQuery(queryName);
		return query.getResultList();
	}
	
	/*
	 * (non-Javadoc)
	 * @see br.com.cliente.dao.DAO#findAllRelated(java.lang.Class, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	public List<T> findAllRelated(Class<T> entityClass, Integer index) {
		String queryName = this.getQueryName(entityClass, "find", "AllRelated" + (index != null ? index.toString() : ""));
		Query query = em.createNamedQuery(queryName);
		return query.getResultList();
	}

	/*
	 * (non-Javadoc)
	 * @see br.com.cliente.dao.DAO#findById(java.lang.Class, java.lang.Object)
	 */
	public T findById(Class<T> entityClass, Object id) {
		return em.find(entityClass, id);
	}
	
	/*
	 * (non-Javadoc)
	 * @see br.com.cliente.dao.DAO#findByName(java.lang.Class, java.lang.String, int, int)
	 */
	@SuppressWarnings("unchecked")
	public List<T> findByName(Class<T> entityClass, String name, int firstItem, int batchSize) {
		String queryName = this.getQueryName(entityClass, "find", "ByName");
		Query query = em.createNamedQuery(queryName);
		query.setParameter("name", name);
		query.setFirstResult(firstItem);
		
		if(batchSize != 0)
			query.setMaxResults(batchSize);
		
		return query.getResultList();
	}
	
	/*
	 * (non-Javadoc)
	 * @see br.com.cliente.dao.DAO#totalByName(java.lang.Class, java.lang.String)
	 */
	public int totalByName(Class<T> entityClass, String name) {
		String queryName = this.getQueryName(entityClass, "total", "ByName");
		Query query = em.createNamedQuery(queryName);
		query.setParameter("name", name);
		int count = ((Long) query.getSingleResult()).intValue();     
        return count;
	}
	
	/**
	 * Query name convention
	 * @param <code>Class<T> </code> entityClass
	 * @param <code>String</code> prefix
	 * @param <code>String</code> suffix
	 * @return {@linkplain String}
	 */
	protected String getQueryName(Class<T> entityClass, String prefix, String suffix) {
		return prefix + entityClass.getSimpleName() + suffix;
	}
}
