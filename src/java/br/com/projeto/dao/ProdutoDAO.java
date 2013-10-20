package br.com.projeto.dao;

import java.util.List;

import javax.persistence.NoResultException;

import br.com.projeto.entity.Produto;

/**
 * 
 * @author gb
 *
 */
public interface ProdutoDAO {
	/**
	 * Save or update the XXX entity 
	 * @param <code>Produto</code> produto
	 */
	public void save(Produto produto);
	
	public List<Produto> findAll() throws NoResultException, Exception;
	
	public List<Produto> findAll(Long registerNumber, Long registerAmount) throws NoResultException, Exception;
	
	public List<Produto> findByProjeto(Long idProjeto) throws NoResultException, Exception;
	
	public List<Produto> findByProjetoWithoutOperation(Long idProjeto) throws NoResultException, Exception;
	
	public List<Produto> findByProjetoWithoutAppointment(Long idProjeto) throws NoResultException, Exception;
	
	public int delete(Long idProduto);
	
}
