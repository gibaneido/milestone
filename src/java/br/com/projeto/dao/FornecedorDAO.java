package br.com.projeto.dao;

import java.util.List;

import javax.persistence.NoResultException;

import br.com.projeto.entity.Fornecedor;

/**
 * 
 * @author gb
 *
 */
public interface FornecedorDAO {
	/**
	 * Save or update the XXX entity 
	 * @param <code>Fornecedor</code> fornecedor
	 */
	public void save(Fornecedor fornecedor);
	
	public List<Fornecedor> findAll(Long registerNumber, Long registerAmount) throws NoResultException, Exception;
	
	public List<Fornecedor> findAll() throws NoResultException, Exception;
	
}
