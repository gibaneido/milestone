package br.com.projeto.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.projeto.dao.ApontamentoDAO;
import br.com.projeto.dao.DAO;
import br.com.projeto.dao.UnidadeDAO;
import br.com.projeto.entity.Apontamento;
import br.com.projeto.entity.Unidade;

/**
 * Business example class
 * @author gb
 *
 */
@Service
public class UnidadeService {
	
	/*
	 * Make IoC using constructor
	 */
	private final UnidadeDAO dao;
	
	private final ApontamentoDAO apontamentoDAO;

	 private final DAO<Unidade, Long> genericDAO;
	 
	 private static final Logger LOGGER = Logger.getLogger(UnidadeService.class);
	 
	 @Autowired
	 public UnidadeService(UnidadeDAO dao, ApontamentoDAO apontamentoDAO, DAO<Unidade, Long> genericDAO)	{
		 this.dao = dao;
		 this.apontamentoDAO = apontamentoDAO;
		 this.genericDAO = genericDAO;
	 }
	 
	/**
	 * Save or update the XXX entity
	 * @param <code>Unidade</code> unidade
	 * @throws {@linkplain Exception} 
	 */
	public void save(Unidade unidade) throws Exception	{
		try {
			this.genericDAO.save(unidade);
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
	
	public int delete(Long idUnidade) throws Exception	{
		try {
			return this.dao.delete(idUnidade);
		} catch (SecurityException e) {
			LOGGER.error(e);
			throw new Exception(e);
		} catch (Exception e) {
			LOGGER.error(e);
			throw new Exception(e);
		}
	}
	
	public Unidade merge(Unidade unidade) throws Exception	{
		try {
			return this.genericDAO.merge(unidade);
		} catch (SecurityException e) {
			LOGGER.error(e);
			throw new Exception(e);
		} catch (Exception e) {
			LOGGER.error(e);
			throw new Exception(e);
		}
	}

	public Unidade findById(Long id) throws Exception	{
		Unidade unidade = null;
		try {
			unidade = this.genericDAO.findById(Unidade.class, id);
		} catch (SecurityException e) {
			LOGGER.error(e);
			throw new Exception(e);
		} catch (Exception e) {
			LOGGER.error(e);
			throw new Exception(e);
		}
		return unidade;
	}
	
	public List<Unidade> findAll() throws Exception{
		List<Unidade> pageList = null;
		try{
			pageList = dao.findAll();
		}catch(Exception e){
			LOGGER.error(e);
			throw e;
		}
		return pageList;
	}
	
	public List<Unidade> findByOperacao(Long idOperacao) throws Exception{
		List<Unidade> pageList = null;
		try{
			pageList = dao.findByOperacao(idOperacao);
		}catch(Exception e){
			LOGGER.error(e);
			throw e;
		}
		return pageList;
	}
	
	public List<Unidade> findByOperacaoWithoutAppointment(Long idOperacao) throws Exception{
		List<Unidade> pageList = null;
		try{
			pageList = dao.findByOperacaoWithoutAppointment(idOperacao);
		}catch(Exception e){
			LOGGER.error(e);
			throw e;
		}
		return pageList;
	}
	
	public List<Unidade> findUnidadesByOperacaoAndSubEtapa(Long idOperacao, Long idSubEtapa) throws Exception{
		List<Unidade> pageList = null;
		try{
			pageList = dao.findByOperacaoWithoutPosition(idOperacao);
			Apontamento ultimoApontamento = null;
			for(int i = 0; i < pageList.size(); i++){
				Unidade unidade = pageList.get(i);
				ultimoApontamento = apontamentoDAO.findLastByUnidade(unidade.getId(), idSubEtapa);
				pageList.get(i).setUltimoApontamento(ultimoApontamento);
			}
		}catch(Exception e){
			LOGGER.error(e);
			throw e;
		}
		return pageList;
	}

}
