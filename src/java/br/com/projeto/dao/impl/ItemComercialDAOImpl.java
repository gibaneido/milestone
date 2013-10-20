package br.com.projeto.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.projeto.dao.ItemComercialDAO;
import br.com.projeto.entity.ItemComercial;

/**
 * Custom DAO example class
 * @author gb
 *
 */
@Repository
public class ItemComercialDAOImpl implements ItemComercialDAO {
	
	private EntityManager em;
	
	@PersistenceContext
	public void setEntity(EntityManager entity) {
		this.em = entity;
	}	
	
	@Transactional
	public void save(ItemComercial itemComercial)	{
		if (itemComercial.getId() == null)	{
			this.em.persist(itemComercial);
		}	else	{
			this.em.merge(itemComercial);
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
	public List<ItemComercial> findAll() throws NoResultException, Exception {
		Query q = em.createQuery("SELECT i FROM ItemComercial i");
        List<ItemComercial> pagesList = null;
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
	public List<ItemComercial> findByProduto(Long idProduto) throws NoResultException, Exception {
		Query q = em.createQuery("SELECT i FROM ItemComercial i WHERE i.produto.id = :idProduto");

		q.setParameter("idProduto", idProduto);
		
        List<ItemComercial> pagesList = null;
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
	public List<ItemComercial> findByUnidade(Long idUnidade) throws NoResultException, Exception {
		Query q = em.createQuery("SELECT i FROM ItemComercial i WHERE i.unidade.id = :idUnidade");
		q.setParameter("idUnidade", idUnidade);
        List<ItemComercial> pagesList = null;
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
