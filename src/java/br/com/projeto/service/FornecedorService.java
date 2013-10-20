package br.com.projeto.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.projeto.dao.FornecedorDAO;
import br.com.projeto.dao.DAO;
import br.com.projeto.entity.Fornecedor;

/**
 * Business example class
 * @author gb
 *
 */
@Service
public class FornecedorService {
	
	/*
	 * Make IoC using constructor
	 */
	private final FornecedorDAO dao;

	 private final DAO<Fornecedor, Long> genericDAO;
	 
	 private static final Logger LOGGER = Logger.getLogger(FornecedorService.class);
	 
	 @Autowired
	 public FornecedorService(FornecedorDAO dao, DAO<Fornecedor, Long> genericDAO)	{
		 this.dao = dao;
		 this.genericDAO = genericDAO;
	 }
	 
	/**
	 * Save or update the XXX entity
	 * @param <code>Fornecedor</code> fornecedor
	 * @throws {@linkplain Exception} 
	 */
	public void save(Fornecedor fornecedor) throws Exception	{
		try {
			this.genericDAO.save(fornecedor);
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
	
	public Fornecedor findById(Long id) throws Exception	{
		Fornecedor fornecedor = null;
		try {
			fornecedor = this.genericDAO.findById(Fornecedor.class, id);
		} catch (SecurityException e) {
			LOGGER.error(e);
			throw new Exception(e);
		} catch (Exception e) {
			LOGGER.error(e);
			throw new Exception(e);
		}
		return fornecedor;
	}
	
	public List<Fornecedor> findAll() throws Exception{
		List<Fornecedor> clientList = null;
		try{
			clientList = dao.findAll();
		}catch(Exception e){
			LOGGER.error(e);
			throw e;
		}
		return clientList;
	}
	
	public List<Fornecedor> findAll(Long registerNumber, Long registerAmount) throws Exception{
		List<Fornecedor> clientList = null;
		try{
			clientList = dao.findAll(registerNumber, registerAmount);
		}catch(Exception e){
			LOGGER.error(e);
			throw e;
		}
		return clientList;
	}
	
}
