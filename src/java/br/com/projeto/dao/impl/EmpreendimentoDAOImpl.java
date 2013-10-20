package br.com.projeto.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.projeto.dao.EmpreendimentoDAO;
import br.com.projeto.entity.Empreendimento;

/**
 * Custom DAO example class
 * @author gb
 *
 */
@Repository
public class EmpreendimentoDAOImpl implements EmpreendimentoDAO {
	
	private EntityManager em;
	
	@PersistenceContext
	public void setEntity(EntityManager entity) {
		this.em = entity;
	}	
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<Empreendimento> findAll() throws NoResultException, Exception {
		Query q = em.createQuery("SELECT e FROM Empreendimento e");
        List<Empreendimento> pagesList = null;
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
	public void save(Empreendimento empreendimento)	{
		if (empreendimento.getId() == null)	{
			this.em.persist(empreendimento);
		}	else	{
			this.em.merge(empreendimento);
		}
		
	}

	/*
	 * (non-Javadoc)
	 * @see br.com.ea.dao.DepartamentoDAO#queryExample(java.lang.Long)
	 */
	@Transactional(readOnly=true)
	public Integer queryExample(Long id) {
		Query q = em.createQuery("SELECT COUNT(id) FROM empreendimento");
//		q.setParameter("X", id);
		
        int count = ((Long)q.getSingleResult()).intValue();
        
        return count;
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<Empreendimento> find(Long idPerfil) throws Exception{
		Query q = em.createQuery("FROM Empreendimento e INNER JOIN FETCH e.perfis p where p.id = :idPerfil");
		q.setParameter("idPerfil", idPerfil);
        return q.getResultList();
	}

}
