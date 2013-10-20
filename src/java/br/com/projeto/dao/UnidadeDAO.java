package br.com.projeto.dao;

import java.util.List;

import javax.persistence.NoResultException;

import br.com.projeto.entity.Unidade;

/**
 * 
 * @author gb
 *
 */
public interface UnidadeDAO {
	/**
	 * Save or update the XXX entity 
	 * @param <code>Unidade</code> unidade
	 */
	public void save(Unidade unidade);
	
	public int delete(Long idUnidade);
	
	public List<Unidade> findAll() throws NoResultException, Exception;
	
	public List<Unidade> findByOperacao(Long idOperacao) throws NoResultException, Exception;
	
	public List<Unidade> findByOperacaoWithoutPosition(Long idOperacao) throws NoResultException, Exception;
	
	public List<Unidade> findByOperacaoWithoutAppointment(Long idOperacao) throws NoResultException, Exception;

}
