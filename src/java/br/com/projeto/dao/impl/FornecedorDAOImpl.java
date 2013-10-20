package br.com.projeto.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.projeto.dao.FornecedorDAO;
import br.com.projeto.entity.Fornecedor;

/**
 * Custom DAO example class
 * @author gb
 *
 */
@Repository
public class FornecedorDAOImpl implements FornecedorDAO {
	
	private EntityManager em;
	
	@PersistenceContext
	public void setEntity(EntityManager entity) {
		this.em = entity;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<Fornecedor> findAll() throws NoResultException, Exception {
		Query q = em.createQuery("SELECT f FROM Fornecedor f ORDER BY f.razaoSocial");
        List<Fornecedor> fornecedorList = null;
		try{
			fornecedorList = q.getResultList();
        	
        }catch(NoResultException nre){
        	return null;
        }catch(Exception e){
        	return null;
        }
        
        return fornecedorList;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<Fornecedor> findAll(Long registerNumber, Long registerAmount) throws NoResultException, Exception {
		Query q = em.createQuery("SELECT f FROM Fornecedor f");
		q.setFirstResult(registerNumber.intValue());
		q.setMaxResults(registerAmount.intValue());
        List<Fornecedor> fornecedorList = null;
		try{
			fornecedorList = q.getResultList();
        	
        }catch(NoResultException nre){
        	return null;
        }catch(Exception e){
        	return null;
        }
        
        return fornecedorList;
	}
	
	@Transactional
	public void save(Fornecedor fornecedor)	{
		if (fornecedor.getId() == null)	{
			this.em.persist(fornecedor);
		}	else	{
			this.em.merge(fornecedor);
		}
		
	}
	
}
