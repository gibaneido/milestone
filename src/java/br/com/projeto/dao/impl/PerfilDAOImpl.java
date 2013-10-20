package br.com.projeto.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.projeto.dao.PerfilDAO;
import br.com.projeto.entity.Perfil;

/**
 * Custom DAO example class
 * @author gb
 *
 */
@Repository
public class PerfilDAOImpl implements PerfilDAO {
	
	private EntityManager em;
	
	@PersistenceContext
	public void setEntity(EntityManager entity) {
		this.em = entity;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<Perfil> findAll() throws NoResultException, Exception {
		Query q = em.createQuery("SELECT p FROM Perfil p");
        List<Perfil> perfilList = null;
		try{
			perfilList = q.getResultList();
        	
        }catch(NoResultException nre){
        	return null;
        }catch(Exception e){
        	return null;
        }
        
        return perfilList;
	}
	
	@Transactional
	public void save(Perfil perfil)	{
		if (perfil.getId() == null)	{
			this.em.persist(perfil);
		}	else	{
			this.em.merge(perfil);
		}
		
	}

}
