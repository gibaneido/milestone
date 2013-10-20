package br.com.projeto.dao;

import java.util.List;

import javax.persistence.NoResultException;

import br.com.projeto.entity.Cliente;

/**
 * 
 * @author gb
 *
 */
public interface ClienteDAO {
	/**
	 * Save or update the XXX entity 
	 * @param <code>Cliente</code> cliente
	 */
	public void save(Cliente cliente);
	
	public List<Cliente> findAll(Long registerNumber, Long registerAmount) throws NoResultException, Exception;
	
	public List<Cliente> findAll() throws NoResultException, Exception;
	
}
