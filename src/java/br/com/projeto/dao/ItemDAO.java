package br.com.projeto.dao;

import java.util.List;

import javax.persistence.NoResultException;

import br.com.projeto.entity.Item;

/**
 * 
 * @author gb
 *
 */
public interface ItemDAO {
	/**
	 * Save or update the XXX entity 
	 * @param <code>Item</code> posicao
	 */
	public void save(Item posicao);
	
	public int delete(Long idItem);
	
	public List<Item> findAll() throws NoResultException, Exception;
	
	public List<Item> findByUnidade(Long idUnidade) throws NoResultException, Exception;
	
	public List<Item> findPosicoesConjuntoSoldado(Long idUnidade) throws NoResultException, Exception;
	
	public List<Item> findByPosicao(Long idItem) throws NoResultException, Exception;
	
	public List<Item> findPosicoesByUnidade(Long idUnidade) throws NoResultException, Exception;
	
	public List<Item> findSubPosicoesByPosicao(Long idPosicao) throws NoResultException, Exception;

}
