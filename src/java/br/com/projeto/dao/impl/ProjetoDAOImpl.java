package br.com.projeto.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.projeto.dao.ProjetoDAO;
import br.com.projeto.entity.Projeto;

/**
 * Custom DAO example class
 * @author gb
 *
 */
@Repository
public class ProjetoDAOImpl implements ProjetoDAO {
	
	private EntityManager em;
	
	@PersistenceContext
	public void setEntity(EntityManager entity) {
		this.em = entity;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<Projeto> findAll(Long registerNumber, Long registerAmount) throws NoResultException, Exception {
		Query q = em.createQuery("SELECT p FROM Projeto p inner join p.cliente ORDER BY p.codigo");
		q.setFirstResult(registerNumber.intValue());
		q.setMaxResults(registerAmount.intValue());
        List<Projeto> pagesList = null;
		try{
			pagesList = q.getResultList();
        	
        }catch(NoResultException nre){
        	return null;
        }catch(Exception e){
        	return null;
        }
        
        return pagesList;
	}
	
	@Transactional
	public void save(Projeto projeto)	{
		if (projeto.getId() == null)	{
			this.em.persist(projeto);
		}	else	{
			this.em.merge(projeto);
		}
		
	}
	
	@Transactional(readOnly=true)
	public String findLastCode() throws NoResultException, Exception {
		Query q = em.createQuery("SELECT p.codigo FROM Projeto p ORDER BY p.id DESC");
		q.setMaxResults(1);
        String code = null;
		try{
			code = (String)q.getSingleResult();
        	
        }catch(NoResultException nre){
        	return null;
        }catch(Exception e){
        	return null;
        }
        
        return code;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<Projeto> findByCliente(Long idCliente) throws NoResultException, Exception {
		Query q = em.createQuery("SELECT p FROM Projeto p inner join p.cliente WHERE p.cliente.id = :idCliente ORDER BY p.codigo");
		q.setParameter("idCliente", idCliente);
        List<Projeto> pagesList = null;
		try{
			pagesList = q.getResultList();
        	
        }catch(NoResultException nre){
        	return null;
        }catch(Exception e){
        	return null;
        }
        
        return pagesList;
	}

}
