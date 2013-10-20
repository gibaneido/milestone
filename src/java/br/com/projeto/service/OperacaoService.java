package br.com.projeto.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.projeto.dao.ApontamentoDAO;
import br.com.projeto.dao.DAO;
import br.com.projeto.dao.OperacaoDAO;
import br.com.projeto.entity.Apontamento;
import br.com.projeto.entity.Operacao;

/**
 * Business example class
 * @author gb
 *
 */
@Service
public class OperacaoService {
	
	/*
	 * Make IoC using constructor
	 */
	private final OperacaoDAO dao;
	
	private final ApontamentoDAO apontamentoDAO;

	 private final DAO<Operacao, Long> genericDAO;
	 
	 private static final Logger LOGGER = Logger.getLogger(OperacaoService.class);
	 
	 @Autowired
	 public OperacaoService(OperacaoDAO dao, ApontamentoDAO apontamentoDAO, DAO<Operacao, Long> genericDAO)	{
		 this.dao = dao;
		 this.apontamentoDAO = apontamentoDAO;
		 this.genericDAO = genericDAO;
	 }
	 
	/**
	 * Save or update the XXX entity
	 * @param <code>Operacao</code> operacao
	 * @throws {@linkplain Exception} 
	 */
	public void save(Operacao operacao) throws Exception	{
		try {
			this.genericDAO.save(operacao);
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

	public int delete(Long idOperacao) throws Exception	{
		try {
			return this.dao.delete(idOperacao);
		} catch (SecurityException e) {
			LOGGER.error(e);
			throw new Exception(e);
		} catch (Exception e) {
			LOGGER.error(e);
			throw new Exception(e);
		}
	}
	
	public Operacao merge(Operacao operacao) throws Exception	{
		try {
			return this.genericDAO.merge(operacao);
		} catch (SecurityException e) {
			LOGGER.error(e);
			throw new Exception(e);
		} catch (Exception e) {
			LOGGER.error(e);
			throw new Exception(e);
		}
	}

	public Operacao findById(Long id) throws Exception	{
		Operacao operacao = null;
		try {
			operacao = this.genericDAO.findById(Operacao.class, id);
		} catch (SecurityException e) {
			LOGGER.error(e);
			throw new Exception(e);
		} catch (Exception e) {
			LOGGER.error(e);
			throw new Exception(e);
		}
		return operacao;
	}
	
	public List<Operacao> findAll() throws Exception{
		List<Operacao> pageList = null;
		try{
			pageList = dao.findAll();
		}catch(Exception e){
			LOGGER.error(e);
			throw e;
		}
		return pageList;
	}
	
	public List<Operacao> findByProduto(Long idProduto) throws Exception{
		List<Operacao> pageList = null;
		try{
			pageList = dao.findByProduto(idProduto);
		}catch(Exception e){
			LOGGER.error(e);
			throw e;
		}
		return pageList;
	}
	
	public List<Operacao> findByProdutoWithoutAppointment(Long idProduto) throws Exception{
		List<Operacao> pageList = null;
		try{
			pageList = dao.findByProdutoWithoutAppointment(idProduto);
		}catch(Exception e){
			LOGGER.error(e);
			throw e;
		}
		return pageList;
	}
	
	public List<Operacao> findOperacoesByProdutoAndSubEtapa(Long idProduto, Long idSubEtapa) throws Exception{
		List<Operacao> pageList = null;
		try{
			pageList = dao.findByProdutoWithoutUnit(idProduto);
			Apontamento ultimoApontamento = null;
			for(int i = 0; i < pageList.size(); i++){
				Operacao operacao = pageList.get(i);
				ultimoApontamento = apontamentoDAO.findLastByOperacao(operacao.getId(), idSubEtapa);
				pageList.get(i).setUltimoApontamento(ultimoApontamento);
			}
		}catch(Exception e){
			LOGGER.error(e);
			throw e;
		}
		return pageList;
	}

}
