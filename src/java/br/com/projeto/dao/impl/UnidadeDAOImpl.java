package br.com.projeto.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.projeto.dao.UnidadeDAO;
import br.com.projeto.entity.Unidade;

/**
 * Custom DAO example class
 * @author gb
 *
 */
@Repository
public class UnidadeDAOImpl implements UnidadeDAO {
	
	private EntityManager em;
	
	@PersistenceContext
	public void setEntity(EntityManager entity) {
		this.em = entity;
	}	
	
	@Transactional
	public void save(Unidade unidade)	{
		if (unidade.getId() == null)	{
			this.em.persist(unidade);
		}	else	{
			this.em.merge(unidade);
		}
		
	}
	
	@Transactional
	public int delete(Long idUnidade)	{
		Query q = em.createQuery("DELETE FROM Unidade u WHERE u.id = :idUnidade");
		q.setParameter("idUnidade", idUnidade);
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
	public List<Unidade> findAll() throws NoResultException, Exception {
		Query q = em.createQuery("SELECT u FROM Unidade u");
        List<Unidade> pagesList = null;
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
	public List<Unidade> findByOperacao(Long idOperacao) throws NoResultException, Exception {
		Query q = em.createQuery("SELECT u FROM Unidade u WHERE u.operacao.id = :idOperacao");
		q.setParameter("idOperacao", idOperacao);
        List<Unidade> pagesList = null;
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
	public List<Unidade> findByOperacaoWithoutPosition(Long idOperacao) throws NoResultException, Exception {
		Query q = em.createQuery("SELECT u FROM Unidade u WHERE u.operacao.id = :idOperacao AND u.id NOT IN (SELECT DISTINCT i.unidade.id FROM Item i WHERE i.unidade.id IS NOT NULL)");
		q.setParameter("idOperacao", idOperacao);
        List<Unidade> pagesList = null;
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
	public List<Unidade> findByOperacaoWithoutAppointment(Long idOperacao) throws NoResultException, Exception {
		Query q = em.createQuery("SELECT u FROM Unidade u WHERE u.operacao.id = :idOperacao AND u.id NOT IN (SELECT DISTINCT a.unidade.id FROM Apontamento a WHERE a.unidade.id IS NOT NULL)");
		q.setParameter("idOperacao", idOperacao);
        List<Unidade> pagesList = null;
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
