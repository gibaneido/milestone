package br.com.projeto.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.projeto.dao.ClienteDAO;
import br.com.projeto.dao.DAO;
import br.com.projeto.entity.Cliente;

/**
 * Business example class
 * @author gb
 *
 */
@Service
public class ClienteService {
	
	/*
	 * Make IoC using constructor
	 */
	private final ClienteDAO dao;

	 private final DAO<Cliente, Long> genericDAO;
	 
	 private static final Logger LOGGER = Logger.getLogger(ClienteService.class);
	 
	 @Autowired
	 public ClienteService(ClienteDAO dao, DAO<Cliente, Long> genericDAO)	{
		 this.dao = dao;
		 this.genericDAO = genericDAO;
	 }
	 
	/**
	 * Save or update the XXX entity
	 * @param <code>Cliente</code> cliente
	 * @throws {@linkplain Exception} 
	 */
	public void save(Cliente cliente) throws Exception	{
		try {
			this.genericDAO.save(cliente);
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
	
	public Cliente findById(Long id) throws Exception	{
		Cliente cliente = null;
		try {
			cliente = this.genericDAO.findById(Cliente.class, id);
		} catch (SecurityException e) {
			LOGGER.error(e);
			throw new Exception(e);
		} catch (Exception e) {
			LOGGER.error(e);
			throw new Exception(e);
		}
		return cliente;
	}
	
	public List<Cliente> findAll() throws Exception{
		List<Cliente> clientList = null;
		try{
			clientList = dao.findAll();
		}catch(Exception e){
			LOGGER.error(e);
			throw e;
		}
		return clientList;
	}
	
	public List<Cliente> findAll(Long registerNumber, Long registerAmount) throws Exception{
		List<Cliente> clientList = null;
		try{
			clientList = dao.findAll(registerNumber, registerAmount);
		}catch(Exception e){
			LOGGER.error(e);
			throw e;
		}
		return clientList;
	}
	
}
