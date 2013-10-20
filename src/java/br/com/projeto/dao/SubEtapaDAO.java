package br.com.projeto.dao;

import java.util.List;

import javax.persistence.NoResultException;

import br.com.projeto.entity.SubEtapa;

/**
 * 
 * @author gb
 *
 */
public interface SubEtapaDAO {
	/**
	 * Save or update the XXX entity 
	 * @param <code>SubEtapa</code> subEtapa
	 */
	public void save(SubEtapa subEtapa);
	
	public int delete(Long idSubEtapa);
	
	public List<SubEtapa> findAll() throws NoResultException, Exception;
	
	public List<SubEtapa> findByEtapa(Long idEtapa) throws NoResultException, Exception;

}
