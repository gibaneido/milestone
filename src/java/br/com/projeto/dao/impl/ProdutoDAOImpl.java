package br.com.projeto.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.projeto.dao.ProdutoDAO;
import br.com.projeto.entity.Produto;

/**
 * Custom DAO example class
 * @author gb
 *
 */
@Repository
public class ProdutoDAOImpl implements ProdutoDAO {
	
	private EntityManager em;
	
	@PersistenceContext
	public void setEntity(EntityManager entity) {
		this.em = entity;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<Produto> findAll() throws NoResultException, Exception {
		Query q = em.createQuery("SELECT p FROM Produto p");
        List<Produto> pagesList = null;
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
	public List<Produto> findAll(Long registerNumber, Long registerAmount) throws NoResultException, Exception {
		Query q = em.createQuery("SELECT p FROM Produto p");
		q.setFirstResult(registerNumber.intValue());
		q.setMaxResults(registerAmount.intValue());
        List<Produto> pagesList = null;
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
	public void save(Produto produto)	{
		if (produto.getId() == null)	{
			this.em.persist(produto);
		}	else	{
			this.em.merge(produto);
		}
	}
	
	@Transactional
	public int delete(Long idProduto)	{
		Query q = em.createQuery("DELETE FROM Produto p WHERE p.id = :idProduto");
		q.setParameter("idProduto", idProduto);
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
	public List<Produto> findByProjeto(Long idProjeto) throws NoResultException, Exception {
		Query q = em.createQuery("SELECT p FROM Produto p WHERE p.projeto.id = :idProjeto");
		q.setParameter("idProjeto", idProjeto);
        List<Produto> pagesList = null;
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
	public List<Produto> findByProjetoWithoutOperation(Long idProjeto) throws NoResultException, Exception {
		Query q = em.createQuery("SELECT p FROM Produto p WHERE p.projeto.id = :idProjeto AND p.id NOT IN (SELECT DISTINCT o.produto.id FROM Operacao o WHERE o.produto.id IS NOT NULL)");
		q.setParameter("idProjeto", idProjeto);
        List<Produto> pagesList = null;
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
	public List<Produto> findByProjetoWithoutAppointment(Long idProjeto) throws NoResultException, Exception {
		Query q = em.createQuery("SELECT p FROM Produto p WHERE p.projeto.id = :idProjeto AND p.id NOT IN (SELECT DISTINCT a.produto.id FROM Apontamento a WHERE a.produto.id IS NOT NULL)");
		q.setParameter("idProjeto", idProjeto);
        List<Produto> pagesList = null;
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
