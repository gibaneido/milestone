package br.com.projeto.dao;

import java.util.List;

import javax.persistence.NoResultException;

import br.com.projeto.entity.Perfil;

/**
 * 
 * @author gb
 *
 */
public interface PerfilDAO {
	/**
	 * Save or update the XXX entity 
	 * @param <code>Perfil</code> perfil
	 */
	public void save(Perfil perfil);
	
	public List<Perfil> findAll() throws NoResultException, Exception;
}
