package br.com.projeto.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.projeto.dao.DAO;
import br.com.projeto.dao.SubEtapaDAO;
import br.com.projeto.entity.SubEtapa;

/**
 * Business example class
 * @author gb
 *
 */
@Service
public class SubEtapaService {
	
	/*
	 * Make IoC using constructor
	 */
	private final SubEtapaDAO dao;

	 private final DAO<SubEtapa, Long> genericDAO;
	 
	 private static final Logger LOGGER = Logger.getLogger(SubEtapaService.class);
	 
	 @Autowired
	 public SubEtapaService(SubEtapaDAO dao, DAO<SubEtapa, Long> genericDAO)	{
		 this.dao = dao;
		 this.genericDAO = genericDAO;
	 }
	 
	/**
	 * Save or update the XXX entity
	 * @param <code>SubEtapa</code> subEtapa
	 * @throws {@linkplain Exception} 
	 */
	public void save(SubEtapa subEtapa) throws Exception	{
		try {
			this.genericDAO.save(subEtapa);
		} catch (SecurityException e) {
			LOGGER.error(e);
			throw new Exception(e);
		} catch (NoSuchMethodException e) {
			LOGGER.error(e);
			throw new Exception(e);
		} catch (Exception e) {
			LOGGER.error(e);
			throw new Exception(e);
		}
	}

	public SubEtapa findById(Long id) throws Exception	{
		SubEtapa item = null;
		try {
			item = this.genericDAO.findById(SubEtapa.class, id);
		} catch (SecurityException e) {
			LOGGER.error(e);
			throw new Exception(e);
		} catch (Exception e) {
			LOGGER.error(e);
			throw new Exception(e);
		}
		return item;
	}
	
	public List<SubEtapa> findAll() throws Exception{
		List<SubEtapa> pageList = null;
		try{
			pageList = dao.findAll();
		}catch(Exception e){
			LOGGER.error(e);
			throw e;
		}
		return pageList;
	}
	
	public List<SubEtapa> findByEtapa(Long idEtapa) throws Exception{
		List<SubEtapa> pageList = null;
		try{
			pageList = dao.findByEtapa(idEtapa);
		}catch(Exception e){
			LOGGER.error(e);
			throw e;
		}
		return pageList;
	}
}
