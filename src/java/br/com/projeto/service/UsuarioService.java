package br.com.projeto.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.projeto.dao.DAO;
import br.com.projeto.dao.UsuarioDAO;
import br.com.projeto.entity.Usuario;
import br.com.projeto.util.CryptUtils;

/**
 * Business example class
 * @author gb
 *
 */
@Service
public class UsuarioService {
	
	/*
	 * Make IoC using constructor
	 */
	private final UsuarioDAO dao;

	 private final DAO<Usuario, Long> genericDAO;
	 
	 private static final Logger LOGGER = Logger.getLogger(UsuarioService.class);
	 
	 @Autowired
	 public UsuarioService(UsuarioDAO dao, DAO<Usuario, Long> genericDAO)	{
		 this.dao = dao;
		 this.genericDAO = genericDAO;
	 }
	 
	/**
	 * Save or update the XXX entity
	 * @param <code>Usuario</code> usuario
	 * @throws {@linkplain Exception} 
	 */
	public void save(Usuario usuario) throws Exception	{
		try {
			this.genericDAO.save(usuario);
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
	
	public List<Usuario> findAll(Long registerNumber, Long registerAmount) throws Exception{
		List<Usuario> pageList = null;
		try{
			pageList = dao.findAll(registerNumber, registerAmount);
		}catch(Exception e){
			LOGGER.error(e);
			throw e;
		}
		return pageList;
	}
	
	public Usuario findById(Long id) throws Exception	{
		Usuario usuario = null;
		try {
			usuario = this.genericDAO.findById(Usuario.class, id);
		} catch (SecurityException e) {
			LOGGER.error(e);
			throw new Exception(e);
		} catch (Exception e) {
			LOGGER.error(e);
			throw new Exception(e);
		}
		return usuario;
	}
	
	/**
	 * Login of users
	 * @param <code>String</code> email
	 * @param <code>String</code> senha
	 * @throws {@linkplain Exception} 
	 */
	public Usuario checkLogin(String email, String senhaDigitada) throws Exception	{
		Usuario usuario = null;
		try {
			
			Usuario usuarioBD = this.dao.getByEmail(email);
			if(usuarioBD != null && usuarioBD.getSenha().equals(CryptUtils.md5(senhaDigitada)) ){
				return usuarioBD;
			}
			
		} catch (SecurityException e) {
			LOGGER.error(e);
			throw new Exception(e);
		} catch (Exception e) {
			LOGGER.error(e);
			throw new Exception(e);
		}
		return usuario;
	}

}
