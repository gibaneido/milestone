package br.com.projeto.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.projeto.dao.ApontamentoDAO;
import br.com.projeto.dao.DAO;
import br.com.projeto.dao.ProdutoDAO;
import br.com.projeto.entity.Apontamento;
import br.com.projeto.entity.Produto;

/**
 * Business example class
 * @author gb
 *
 */
@Service
public class ProdutoService {
	
	/*
	 * Make IoC using constructor
	 */
	private final ProdutoDAO dao;

	private final ApontamentoDAO apontamentoDAO;
	
	private final DAO<Produto, Long> genericDAO;
	 
	private static final Logger LOGGER = Logger.getLogger(ProdutoService.class);
	 
	@Autowired
	public ProdutoService(ProdutoDAO dao, ApontamentoDAO apontamentoDAO, DAO<Produto, Long> genericDAO)	{
		this.dao = dao;
		this.apontamentoDAO = apontamentoDAO;
		this.genericDAO = genericDAO;
	}
	 
	/**
	 * Save or update the XXX entity
	 * @param <code>Produto</code> produto
	 * @throws {@linkplain Exception} 
	 */
	public void save(Produto produto) throws Exception	{
		try {
			this.genericDAO.save(produto);
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
	
	public int delete(Long idProduto) throws Exception	{
		try {
			return this.dao.delete(idProduto);
		} catch (SecurityException e) {
			LOGGER.error(e);
			throw new Exception(e);
		} catch (Exception e) {
			LOGGER.error(e);
			throw new Exception(e);
		}
	}
	
	public Produto merge(Produto produto) throws Exception	{
		try {
			return this.genericDAO.merge(produto);
		} catch (SecurityException e) {
			LOGGER.error(e);
			throw new Exception(e);
		} catch (Exception e) {
			LOGGER.error(e);
			throw new Exception(e);
		}
	}

	public Produto findById(Long id) throws Exception	{
		Produto produto = null;
		try {
			produto = this.genericDAO.findById(Produto.class, id);
		} catch (SecurityException e) {
			LOGGER.error(e);
			throw new Exception(e);
		} catch (Exception e) {
			LOGGER.error(e);
			throw new Exception(e);
		}
		return produto;
	}
	
	public List<Produto> findAll(Long registerNumber, Long registerAmount) throws Exception{
		List<Produto> pageList = null;
		try{
			pageList = dao.findAll(registerNumber, registerAmount);
		}catch(Exception e){
			LOGGER.error(e);
			throw e;
		}
		return pageList;
	}
	
	public List<Produto> findAll() throws Exception{
		List<Produto> pageList = null;
		try{
			pageList = dao.findAll();
		}catch(Exception e){
			LOGGER.error(e);
			throw e;
		}
		return pageList;
	}
	
	public List<Produto> findByProjeto(Long idProjeto) throws Exception{
		List<Produto> pageList = null;
		try{
			pageList = dao.findByProjeto(idProjeto);
		}catch(Exception e){
			LOGGER.error(e);
			throw e;
		}
		return pageList;
	}
	
	public List<Produto> findByProjetoWithoutAppointment(Long idProjeto) throws Exception{
		List<Produto> pageList = null;
		try{
			pageList = dao.findByProjetoWithoutAppointment(idProjeto);
		}catch(Exception e){
			LOGGER.error(e);
			throw e;
		}
		return pageList;
	}
	
	public List<Produto> findProdutosByProjetoAndSubEtapa(Long idProjeto, Long idSubEtapa) throws Exception{
		List<Produto> pageList = null;
		try{
			pageList = dao.findByProjetoWithoutOperation(idProjeto);
			Apontamento ultimoApontamento = null;
			for(int i = 0; i < pageList.size(); i++){
				Produto produto = pageList.get(i);
				ultimoApontamento = apontamentoDAO.findLastByProduto(produto.getId(), idSubEtapa);
				pageList.get(i).setUltimoApontamento(ultimoApontamento);
			}
		}catch(Exception e){
			LOGGER.error(e);
			throw e;
		}
		return pageList;
	}

}
