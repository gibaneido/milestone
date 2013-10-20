package br.com.projeto.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.projeto.dao.ItemDAO;
import br.com.projeto.entity.Item;

/**
 * Custom DAO example class
 * @author gb
 *
 */
@Repository
public class ItemDAOImpl implements ItemDAO {
	
	private EntityManager em;
	
	@PersistenceContext
	public void setEntity(EntityManager entity) {
		this.em = entity;
	}	
	
	@Transactional
	public void save(Item posicao)	{
		if (posicao.getId() == null)	{
			this.em.persist(posicao);
		}	else	{
			this.em.merge(posicao);
		}
		
	}
	
	@Transactional
	public int delete(Long idItem)	{
		Query q = em.createQuery("DELETE FROM Item i WHERE i.id = :idItem");
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
	public List<Item> findAll() throws NoResultException, Exception {
		Query q = em.createQuery("SELECT i FROM Item i");
        List<Item> pagesList = null;
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
	public List<Item> findByUnidade(Long idUnidade) throws NoResultException, Exception {
		Query q = em.createQuery("SELECT i FROM Item i WHERE i.unidade.id = :idUnidade");
		q.setParameter("idUnidade", idUnidade);
        List<Item> pagesList = null;
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
	public List<Item> findPosicoesConjuntoSoldado(Long idUnidade) throws NoResultException, Exception {
		Query q = em.createQuery("SELECT i FROM Item i WHERE i.unidade.id = :idUnidade AND i.conjuntoSoldado = :cjSoldado");
		q.setParameter("idUnidade", idUnidade);
		q.setParameter("cjSoldado", Item.CONJUNTO_SOLDADO);
        List<Item> pagesList = null;
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
	public List<Item> findByPosicao(Long idItem) throws NoResultException, Exception {
		Query q = em.createQuery("SELECT i FROM Item i WHERE i.itemPosicao.id = :idItem");
		q.setParameter("idItem", idItem);
        List<Item> pagesList = null;
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
	public List<Item> findPosicoesByUnidade(Long idUnidade) throws NoResultException, Exception {
		Query q = em.createQuery("SELECT i FROM Item i WHERE i.unidade.id = :idUnidade AND conjuntoSoldado = :normal");
		q.setParameter("idUnidade", idUnidade);
		q.setParameter("normal", Item.NORMAL);
        List<Item> pagesList = null;
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
	//@Transactional(readOnly=true)
	public List<Item> findSubPosicoesByPosicao(Long idPosicao) throws NoResultException, Exception {
		Query q = em.createQuery("SELECT i FROM Item i WHERE i.itemPosicao.id = :idPosicao AND conjuntoSoldado = :conjuntoSoldado");
		q.setParameter("idPosicao", idPosicao);
		q.setParameter("conjuntoSoldado", Item.CONJUNTO_SOLDADO);
        List<Item> pagesList = null;
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
