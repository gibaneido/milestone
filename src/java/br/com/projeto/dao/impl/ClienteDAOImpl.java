package br.com.projeto.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.projeto.dao.ClienteDAO;
import br.com.projeto.entity.Cliente;

/**
 * Custom DAO example class
 * @author gb
 *
 */
@Repository
public class ClienteDAOImpl implements ClienteDAO {
	
	private EntityManager em;
	
	@PersistenceContext
	public void setEntity(EntityManager entity) {
		this.em = entity;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<Cliente> findAll() throws NoResultException, Exception {
		Query q = em.createQuery("SELECT c FROM Cliente c ORDER BY c.razaoSocial");
        List<Cliente> clientList = null;
		try{
			clientList = q.getResultList();
        	
        }catch(NoResultException nre){
        	return null;
        }catch(Exception e){
        	return null;
        }
        
        return clientList;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<Cliente> findAll(Long registerNumber, Long registerAmount) throws NoResultException, Exception {
		Query q = em.createQuery("SELECT c FROM Cliente c");
		q.setFirstResult(registerNumber.intValue());
		q.setMaxResults(registerAmount.intValue());
        List<Cliente> clientList = null;
		try{
			clientList = q.getResultList();
        	
        }catch(NoResultException nre){
        	return null;
        }catch(Exception e){
        	return null;
        }
        
        return clientList;
	}
	
	@Transactional
	public void save(Cliente cliente)	{
		if (cliente.getId() == null)	{
			this.em.persist(cliente);
		}	else	{
			this.em.merge(cliente);
		}
		
	}
	
}
