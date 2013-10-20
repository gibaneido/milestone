package br.com.projeto.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.projeto.dao.EtapaDAO;
import br.com.projeto.entity.Etapa;

/**
 * Custom DAO example class
 * @author gb
 *
 */
@Repository
public class EtapaDAOImpl implements EtapaDAO {
	
	private EntityManager em;
	
	@PersistenceContext
	public void setEntity(EntityManager entity) {
		this.em = entity;
	}	
	
	@Transactional
	public void save(Etapa etapa)	{
		if (etapa.getId() == null)	{
			this.em.persist(etapa);
		}	else	{
			this.em.merge(etapa);
		}
		
	}
	
	@Transactional
	public int delete(Long idEtapa)	{
		Query q = em.createQuery("DELETE FROM Etapa e WHERE e.id = :idEtapa");
		q.setParameter("idEtapa", idEtapa);
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
	public List<Etapa> findAll() throws NoResultException, Exception {
		Query q = em.createQuery("SELECT e FROM Etapa e");
        List<Etapa> pagesList = null;
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
