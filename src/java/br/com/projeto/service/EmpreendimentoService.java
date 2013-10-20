package br.com.projeto.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.projeto.dao.DAO;
import br.com.projeto.dao.EmpreendimentoDAO;
import br.com.projeto.entity.Empreendimento;

/**
 * Business example class
 * @author gb
 *
 */
@Service
public class EmpreendimentoService {
	
	/*
	 * Make IoC using constructor
	 */
	private final EmpreendimentoDAO dao;

	 private final DAO<Empreendimento, Long> genericDAO;
	 
	 private static final Logger LOGGER = Logger.getLogger(EmpreendimentoService.class);
	 
	 @Autowired
	 public EmpreendimentoService(EmpreendimentoDAO dao, DAO<Empreendimento, Long> genericDAO)	{
		 this.dao = dao;
		 this.genericDAO = genericDAO;
	 }
	 
	 /* Make IoC using setter
	  *  private EmpreendimentoDAO dao;
	  *   private DAO<Empreendimento, Long> genericDAO;
	  *   
	  *   @Autowired
	  *   public void setArticleCommentDAO(EmpreendimentoDAO dao) {
	  *   	this.dao = dao;
	  *   }
	  *   
	  *   @Autowired
	  *   public void setGenericDao(DAO genericDao) {
	  *   	this.genericDAO = genericDao;
	  *   }	   
	  */

	/**
	 * Count at how much XXXX rows exist, with the reference id
	 * @param <code>Long</code> id, object XXX reference id
	 * @return {@link Integer}
	 * @throws {@link ExampleCustomException} 
	 */
	/*public Integer serviceExample(Long id) throws ExampleCustomException	{
		if (id == null)
			throw new ExampleCustomException("Error ...");
		
		Integer total = dao.queryExample(id);
		
		return total;
	}*/
	
	/**
	 * Save or update the XXX entity
	 * @param <code>Empreendimento</code> entityExample
	 * @throws {@linkplain Exception} 
	 */
	public void save(Empreendimento empreendimento) throws Exception	{
		try {
			this.genericDAO.save(empreendimento);
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
	
	public List<Empreendimento> findAll() throws Exception{
		List<Empreendimento> pageList = null;
		try{
			pageList = dao.findAll();
		}catch(Exception e){
			LOGGER.error(e);
			throw e;
		}
		return pageList;
	}
	
	public Empreendimento findById(Long id) throws Exception{
		Empreendimento empreendimento = null;
		try{
			empreendimento = genericDAO.findById(Empreendimento.class, id);
		}catch(Exception e){
			LOGGER.error(e);
			throw e;
		}
		return empreendimento;
	}

	public List<Empreendimento> find(Long idPerfil) throws Exception{
		List<Empreendimento> pageList = null;
		try{
			pageList = dao.find(idPerfil);
		}catch(Exception e){
			LOGGER.error(e);
			throw e;
		}
		return pageList;
	}
}
