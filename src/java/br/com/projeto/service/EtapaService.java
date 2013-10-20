package br.com.projeto.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.projeto.dao.DAO;
import br.com.projeto.dao.EtapaDAO;
import br.com.projeto.entity.Etapa;

/**
 * Business example class
 * @author gb
 *
 */
@Service
public class EtapaService {
	
	/*
	 * Make IoC using constructor
	 */
	private final EtapaDAO dao;

	 private final DAO<Etapa, Long> genericDAO;
	 
	 private static final Logger LOGGER = Logger.getLogger(EtapaService.class);
	 
	 @Autowired
	 public EtapaService(EtapaDAO dao, DAO<Etapa, Long> genericDAO)	{
		 this.dao = dao;
		 this.genericDAO = genericDAO;
	 }
	 
	/**
	 * Save or update the XXX entity
	 * @param <code>Etapa</code> etapa
	 * @throws {@linkplain Exception} 
	 */
	public void save(Etapa etapa) throws Exception	{
		try {
			this.genericDAO.save(etapa);
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

	public int delete(Long idEtapa) throws Exception	{
		try {
			return this.dao.delete(idEtapa);
		} catch (SecurityException e) {
			LOGGER.error(e);
			throw new Exception(e);
		} catch (Exception e) {
			LOGGER.error(e);
			throw new Exception(e);
		}
	}
	
	public Etapa merge(Etapa etapa) throws Exception	{
		try {
			return this.genericDAO.merge(etapa);
		} catch (SecurityException e) {
			LOGGER.error(e);
			throw new Exception(e);
		} catch (Exception e) {
			LOGGER.error(e);
			throw new Exception(e);
		}
	}

	public Etapa findById(Long id) throws Exception	{
		Etapa etapa = null;
		try {
			etapa = this.genericDAO.findById(Etapa.class, id);
		} catch (SecurityException e) {
			LOGGER.error(e);
			throw new Exception(e);
		} catch (Exception e) {
			LOGGER.error(e);
			throw new Exception(e);
		}
		return etapa;
	}
	
	public List<Etapa> findAll() throws Exception{
		List<Etapa> pageList = null;
		try{
			pageList = dao.findAll();
		}catch(Exception e){
			LOGGER.error(e);
			throw e;
		}
		return pageList;
	}
	
}
