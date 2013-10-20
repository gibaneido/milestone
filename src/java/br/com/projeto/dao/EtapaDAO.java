package br.com.projeto.dao;

import java.util.List;

import javax.persistence.NoResultException;

import br.com.projeto.entity.Etapa;

/**
 * 
 * @author gb
 *
 */
public interface EtapaDAO {
	/**
	 * Save or update the XXX entity 
	 * @param <code>Etapa</code> etapa
	 */
	public void save(Etapa etapa);
	
	public int delete(Long idEtapa);
	
	public List<Etapa> findAll() throws NoResultException, Exception;
	
}
