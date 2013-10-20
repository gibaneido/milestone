package br.com.projeto.dao;

import java.util.List;

import javax.persistence.NoResultException;

import br.com.projeto.entity.Projeto;

/**
 * 
 * @author gb
 *
 */
public interface ProjetoDAO {
	/**
	 * Save or update the XXX entity 
	 * @param <code>Projeto</code> projeto
	 */
	public void save(Projeto projeto);
	
	public List<Projeto> findAll(Long registerNumber, Long registerAmount) throws NoResultException, Exception;
	
	public String findLastCode() throws NoResultException, Exception;
	
	public List<Projeto> findByCliente(Long idCliente) throws NoResultException, Exception;
}
