package br.com.projeto.dao;

import java.util.List;

import javax.persistence.NoResultException;

import br.com.projeto.entity.Usuario;

/**
 * 
 * @author gb
 *
 */
public interface UsuarioDAO {
	/**
	 * Save or update the XXX entity 
	 * @param <code>Usuario</code> usuario
	 */
	public void save(Usuario usuario);
	
	/**
	 * Get Usuario by email 
	 * @param <code>String</code> email
	 */
	public Usuario getByEmail(String email);
	
	public List<Usuario> findAll(Long registerNumber, Long registerAmount) throws NoResultException, Exception;

}
