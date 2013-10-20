package br.com.projeto.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.projeto.dao.SubEtapaDAO;
import br.com.projeto.entity.SubEtapa;

/**
 * Custom DAO example class
 * @author gb
 *
 */
@Repository
public class SubEtapaDAOImpl implements SubEtapaDAO {
	
	private EntityManager em;
	
	@PersistenceContext
	public void setEntity(EntityManager entity) {
		this.em = entity;
	}	
	
	@Transactional
	public void save(SubEtapa subEtapa)	{
		if (subEtapa.getId() == null)	{
			this.em.persist(subEtapa);
		}	else	{
			this.em.merge(subEtapa);
		}
		
	}
	
	@Transactional
	public int delete(Long idItem)	{
		Query q = em.createQuery("DELETE FROM ItemComercial i WHERE i.id = :idItem");
		q.setParameter("idItem", idItem);
        int retorno = 0;
		try{
			retorno = q.executeUpdate();
        	
        }catch(NoResultException nre){
        	return 0;
        }catch(Exception e){
        	return 0;
        }
		return retorno;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<SubEtapa> findAll() throws NoResultException, Exception {
		Query q = em.createQuery("SELECT s FROM SubEtapa s");
        List<SubEtapa> pagesList = null;
		try{
			pagesList = q.getResultList();
        	
        }catch(NoResultException nre){
        	return null;
        }catch(Exception e){
        	return null;
        }
        
        return pagesList;
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<SubEtapa> findByEtapa(Long idEtapa) throws NoResultException, Exception {
		Query q = em.createQuery("SELECT s FROM SubEtapa s WHERE s.etapa.id = :idEtapa");
		q.setParameter("idEtapa", idEtapa);
        List<SubEtapa> pagesList = null;
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
