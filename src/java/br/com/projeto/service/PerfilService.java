package br.com.projeto.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.projeto.dao.DAO;
import br.com.projeto.dao.PerfilDAO;
import br.com.projeto.entity.Perfil;

/**
 * Business example class
 * @author gb
 *
 */
@Service
public class PerfilService {
	
	/*
	 * Make IoC using constructor
	 */
	private final PerfilDAO dao;

	 private final DAO<Perfil, Long> genericDAO;
	 
	 private static final Logger LOGGER = Logger.getLogger(PerfilService.class);
	 
	 @Autowired
	 public PerfilService(PerfilDAO dao, DAO<Perfil, Long> genericDAO)	{
		 this.dao = dao;
		 this.genericDAO = genericDAO;
	 }
	 
	/**
	 * Save or update the XXX entity
	 * @param <code>Perfil</code> perfil
	 * @throws {@linkplain Exception} 
	 */
	public void save(Perfil perfil) throws Exception	{
		try {
			this.genericDAO.save(perfil);
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
	
	public List<Perfil> findAll() throws Exception{
		List<Perfil> perfilList = null;
		try{
			perfilList = dao.findAll();
		}catch(Exception e){
			LOGGER.error(e);
			throw e;
		}
		return perfilList;
	}
	
	public Perfil findById(Long id) throws Exception{
		Perfil perfil = null;
		try{
			perfil = genericDAO.findById(Perfil.class, id);
		}catch(Exception e){
			LOGGER.error(e);
			throw e;
		}
		return perfil;
	}

}
