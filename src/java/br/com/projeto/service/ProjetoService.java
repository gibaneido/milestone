package br.com.projeto.service;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.projeto.dao.DAO;
import br.com.projeto.dao.ProjetoDAO;
import br.com.projeto.entity.Etapa;
import br.com.projeto.entity.Projeto;
import br.com.projeto.util.NivelEnum;
import br.com.projeto.util.NivelEvolucao;

/**
 * Business example class
 * @author gb
 *
 */
@Service
public class ProjetoService {
	
	/*
	 * Make IoC using constructor
	 */
	private final ProjetoDAO dao;

	 private final DAO<Projeto, Long> genericDAO;
	 
	 private static final Logger LOGGER = Logger.getLogger(ProjetoService.class);
	 
	 @Autowired
	 public ProjetoService(ProjetoDAO dao, DAO<Projeto, Long> genericDAO)	{
		 this.dao = dao;
		 this.genericDAO = genericDAO;
	 }
	 
	/**
	 * Save or update the XXX entity
	 * @param <code>Projeto</code> projeto
	 * @throws {@linkplain Exception} 
	 */
	public void save(Projeto projeto) throws Exception	{
		try {
			this.genericDAO.save(projeto);
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

	public Projeto findById(Long id) throws Exception	{
		Projeto projeto = null;
		try {
			projeto = this.genericDAO.findById(Projeto.class, id);
		} catch (SecurityException e) {
			LOGGER.error(e);
			throw new Exception(e);
		} catch (Exception e) {
			LOGGER.error(e);
			throw new Exception(e);
		}
		return projeto;
	}

	
	public List<Projeto> findAll(Long registerNumber, Long registerAmount) throws Exception{
		List<Projeto> pageList = null;
		try{
			pageList = dao.findAll(registerNumber, registerAmount);
		}catch(Exception e){
			LOGGER.error(e);
			throw e;
		}
		return pageList;
	}
	
	public String findLastCode() throws Exception{
		String code = null;
		try{
			code = dao.findLastCode();
		}catch(Exception e){
			LOGGER.error(e);
			throw e;
		}
		return code;
	}
	
	public List<Projeto> findByCliente(Long idCliente) throws Exception{
		List<Projeto> pageList = null;
		try{
			pageList = dao.findByCliente(idCliente);
		}catch(Exception e){
			LOGGER.error(e);
			throw e;
		}
		return pageList;
	}
	
	@SuppressWarnings("unchecked")
	public List<Etapa> getEtapasByNivel(Long idProjeto, NivelEnum nivel) throws Exception{
		List<Etapa> pageList = null;
		Projeto projeto;
		try{
			projeto = this.findById(idProjeto);
			List<Etapa> etapasNivel = NivelEvolucao.getEtapasByNivel(nivel);
			pageList = (List<Etapa>) CollectionUtils.intersection(projeto.getEtapas(), etapasNivel);
		}catch(Exception e){
			LOGGER.error(e);
			throw e;
		}
		return pageList;
	}

}
