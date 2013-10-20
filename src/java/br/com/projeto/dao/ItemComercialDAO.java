package br.com.projeto.dao;

import java.util.List;

import javax.persistence.NoResultException;

import br.com.projeto.entity.ItemComercial;

/**
 * 
 * @author gb
 *
 */
public interface ItemComercialDAO {
	/**
	 * Save or update the XXX entity 
	 * @param <code>ItemComercial</code> itemComercial
	 */
	public void save(ItemComercial itemComercial);
	
	public int delete(Long idItemComercial);
	
	public List<ItemComercial> findAll() throws NoResultException, Exception;
	
	public List<ItemComercial> findByProduto(Long idProduto) throws NoResultException, Exception;
	
	public List<ItemComercial> findByUnidade(Long idUnidade) throws NoResultException, Exception;
	
}
