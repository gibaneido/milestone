package br.com.projeto.dao;

import java.util.Collection;
import java.util.List;

/**
 * Basic Generic DAO Interface
 * 
 * @author marcio.camurati
 * @author mliberato
 * @since 03/09/2009
 * @version 1.1
 * 
 */
public interface DAO<T, ID> {
	
	/**
	 * Make an insert of the entity at the database, call this method when you 
	 * have a new object to save
	 * @param <code>T</code> entity
	 */
	public void insert(T entity);
	
	/**
	 * Make an update of the entity values at the database, call this method when you 
	 * have to update values of the object in the database
	 * @param <code>T</code> entity
	 */
	public void update(T entity);
	
	public T merge(T entity);
	
	/**
	 * Use reflection to verify if the annotated id have a value and 
	 * with this, choice if make a insert or a merge call
	 * @param <code>T</code> entity
	 * @throws {@linkplain SecurityException}
	 * @throws {@linkplain NoSuchMethodException}
	 * @throws {@linkplain Exception}
	 * 
	 * TODO @author marcio.camurati - review this reflection
	 */
	public void save(T entity) throws SecurityException, NoSuchMethodException, Exception;
	
	/**
	 * Remove the entity that corresponding at the id value
	 * @param <code>Class<T></code> entityClass
	 * @param <code>Long</code> id
	 */
	public void remove(Class<T> entityClass, Long id);
	
	/**
	 * Remove all entity that corresponding at the id's values
	 * @param <code>Class<T></code> entityClass
	 * @param <code>Long</code> ids
	 */
	public void remove(Class<T> entityClass, Long[] ids);
	
	/**
	 * Remove all entity that corresponding at the collection entities
	 * @param <code>Class<T></code> entityClass
	 * @param <code>Collection<T></code> collection
	 */
	public void remove(Class<T> entityClass, Collection<T> collection);
	
	/**
	 * Find the entity that have the id value
	 * @param <code>Class<T></code> entityClass
	 * @param id
	 * @return {@linkplain T}
	 */
	public T findById(Class<T> entityClass, Object id);
	
	/**
	 * Using the convention query name find#EntityName#All, get all entities objects
	 * @param <code>Class<T></code> entityClass
	 * @return {@linkplain List<T>}
	 */
	public List<T> findAll(Class<T> entityClass);
	
	/**
	 * Using the convention query name find#EntityName#AllRelated<Index>, get all related objects
	 * @param <code>Class<T></code> entityClass
	 * @param <code>Integer</code> index, convention to mount the query name like find#EntityName#AllRelated1
	 * @return {@linkplain List<T>}
	 */
	public List<T> findAllRelated(Class<T> entityClass, Integer index);
	
	/**
	 * Using the convention query name find#EntityName#ByName, find by name
	 * @param <code>Class<T></code> entityClass
	 * @param <code>name</code>, text to filter
	 * @param <code>int</code> firstItem, cursor first position
	 * @param <code>int</code> batchSize, search limit
	 * @return {@linkplain List<T>}
	 */
	public List<T> findByName(Class<T> entityClass, String name, int firstItem, int batchSize);
	
	/**
	 * Using the convention query name total#EntityName#ByName, count the entities filter by name
	 * @param <code>Class<T></code> entityClass
	 * @param <code>String</code> name, text to filter
	 * @return {@linkplain int}
	 */
	public int totalByName(Class<T> entityClass, String name);

}
