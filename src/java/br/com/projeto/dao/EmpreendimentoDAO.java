package br.com.projeto.dao;

import java.util.List;

import javax.persistence.NoResultException;

import br.com.projeto.entity.Empreendimento;

/**
 * 
 * @author gb
 *
 */
public interface EmpreendimentoDAO {
	
	public List<Empreendimento> findAll() throws NoResultException, Exception;
	
	/**
	 * Save or update the XXX entity 
	 * @param <code>Empreendimento</code> empreendimento
	 */
	public void save(Empreendimento empreendimento);
	
	/**
	 * Count at how much XXXX rows exist, with the reference id
	 * @param <code>Long</code> id, object XXX reference id
	 * @return {@link Integer}
	 */
	public Integer queryExample(Long id);
	
	public List<Empreendimento> find(Long idPerfil) throws Exception;
}
