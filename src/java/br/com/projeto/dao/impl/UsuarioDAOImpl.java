package br.com.projeto.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.projeto.dao.UsuarioDAO;
import br.com.projeto.entity.Usuario;

/**
 * Custom DAO example class
 * @author gb
 *
 */
@Repository
public class UsuarioDAOImpl implements UsuarioDAO {
	
	private EntityManager em;
	
	@PersistenceContext
	public void setEntity(EntityManager entity) {
		this.em = entity;
	}	
	
	@Transactional
	public void save(Usuario usuario)	{
		if (usuario.getId() == null)	{
			this.em.persist(usuario);
		}	else	{
			this.em.merge(usuario);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<Usuario> findAll(Long registerNumber, Long registerAmount) throws NoResultException, Exception {
		Query q = em.createQuery("SELECT u FROM Usuario u INNER JOIN FETCH u.perfis p");
		q.setFirstResult(registerNumber.intValue());
		q.setMaxResults(registerAmount.intValue());
        List<Usuario> pagesList = null;
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
	public Usuario getByEmail(String email)	{

		Query q = em.createQuery("SELECT u FROM Usuario u inner join fetch u.perfis WHERE u.email = :email");
		q.setParameter("email", email);
        Usuario usuario = null;
		try{
			
			usuario = (Usuario)q.getSingleResult();
			
        }catch(NoResultException nre){
        	return null;
        }catch(Exception e){
        	return null;
        }
        
        return usuario;
		
	}

}
