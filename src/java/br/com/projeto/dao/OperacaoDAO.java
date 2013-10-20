package br.com.projeto.dao;

import java.util.List;

import javax.persistence.NoResultException;

import br.com.projeto.entity.Operacao;

/**
 * 
 * @author gb
 *
 */
public interface OperacaoDAO {
	/**
	 * Save or update the XXX entity 
	 * @param <code>Produto</code> operacao
	 */
	public void save(Operacao operacao);
	
	public int delete(Long idOperacao);
	
	public List<Operacao> findAll() throws NoResultException, Exception;
	
	public List<Operacao> findByProduto(Long idProduto) throws NoResultException, Exception;
	
	public List<Operacao> findByProdutoWithoutUnit(Long idProduto) throws NoResultException, Exception;
	
	public List<Operacao> findByProdutoWithoutAppointment(Long idProduto) throws NoResultException, Exception;
	
}
