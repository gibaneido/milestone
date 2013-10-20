package br.com.projeto.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.projeto.dao.OperacaoDAO;
import br.com.projeto.entity.Operacao;

/**
 * Custom DAO example class
 * @author gb
 *
 */
@Repository
public class OperacaoDAOImpl implements OperacaoDAO {
	
	private EntityManager em;
	
	@PersistenceContext
	public void setEntity(EntityManager entity) {
		this.em = entity;
	}	
	
	@Transactional
	public void save(Operacao operacao)	{
		if (operacao.getId() == null)	{
			this.em.persist(operacao);
		}	else	{
			this.em.merge(operacao);
		}
	}
	
	@Transactional
	public int delete(Long idOperacao)	{
		Query q = em.createQuery("DELETE FROM Operacao o WHERE o.id = :idOperacao");
		q.setParameter("idOperacao", idOperacao);
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
	public List<Operacao> findAll() throws NoResultException, Exception {
		Query q = em.createQuery("SELECT o FROM Operacao o");
        List<Operacao> pagesList = null;
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
	public List<Operacao> findByProduto(Long idProduto) throws NoResultException, Exception {
		Query q = em.createQuery("SELECT o FROM Operacao o WHERE o.produto.id = :idProduto");
		q.setParameter("idProduto", idProduto);
        List<Operacao> pagesList = null;
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
	public List<Operacao> findByProdutoWithoutUnit(Long idProduto) throws NoResultException, Exception {
		Query q = em.createQuery("SELECT o FROM Operacao o WHERE o.produto.id = :idProduto AND o.id NOT IN (SELECT DISTINCT u.operacao.id FROM Unidade u WHERE u.operacao.id IS NOT NULL)");
		
		q.setParameter("idProduto", idProduto);
        List<Operacao> pagesList = null;
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
	public List<Operacao> findByProdutoWithoutAppointment(Long idProduto) throws NoResultException, Exception {
		Query q = em.createQuery("SELECT o FROM Operacao o WHERE o.produto.id = :idProduto AND o.id NOT IN (SELECT DISTINCT a.operacao.id FROM Apontamento a WHERE a.operacao.id IS NOT NULL)");
		
		q.setParameter("idProduto", idProduto);
        List<Operacao> pagesList = null;
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
