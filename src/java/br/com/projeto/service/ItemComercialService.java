package br.com.projeto.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.projeto.dao.ApontamentoDAO;
import br.com.projeto.dao.DAO;
import br.com.projeto.dao.ItemComercialDAO;
import br.com.projeto.entity.Apontamento;
import br.com.projeto.entity.ItemComercial;

/**
 * Business example class
 * @author gb
 *
 */
@Service
public class ItemComercialService {
	
	/*
	 * Make IoC using constructor
	 */
	private final ItemComercialDAO dao;
	
	private final ApontamentoDAO apontamentoDAO;

	 private final DAO<ItemComercial, Long> genericDAO;
	 
	 private static final Logger LOGGER = Logger.getLogger(ItemComercialService.class);
	 
	 @Autowired
	 public ItemComercialService(ItemComercialDAO dao, ApontamentoDAO apontamentoDAO, DAO<ItemComercial, Long> genericDAO)	{
		 this.dao = dao;
		 this.apontamentoDAO = apontamentoDAO;
		 this.genericDAO = genericDAO;
	 }
	 
	/**
	 * Save or update the XXX entity
	 * @param <code>ItemComercial</code> itemComercial
	 * @throws {@linkplain Exception} 
	 */
	public void save(ItemComercial itemComercial) throws Exception	{
		try {
			this.genericDAO.save(itemComercial);
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
	
	public int delete(Long idItemComercial) throws Exception	{
		try {
			return this.dao.delete(idItemComercial);
		} catch (SecurityException e) {
			LOGGER.error(e);
			throw new Exception(e);
		} catch (Exception e) {
			LOGGER.error(e);
			throw new Exception(e);
		}
	}
	
	public ItemComercial merge(ItemComercial item) throws Exception	{
		try {
			return this.genericDAO.merge(item);
		} catch (SecurityException e) {
			LOGGER.error(e);
			throw new Exception(e);
		} catch (Exception e) {
			LOGGER.error(e);
			throw new Exception(e);
		}
	}

	public ItemComercial findById(Long id) throws Exception	{
		ItemComercial item = null;
		try {
			item = this.genericDAO.findById(ItemComercial.class, id);
		} catch (SecurityException e) {
			LOGGER.error(e);
			throw new Exception(e);
		} catch (Exception e) {
			LOGGER.error(e);
			throw new Exception(e);
		}
		return item;
	}
	
	public List<ItemComercial> findAll() throws Exception{
		List<ItemComercial> pageList = null;
		try{
			pageList = dao.findAll();
		}catch(Exception e){
			LOGGER.error(e);
			throw e;
		}
		return pageList;
	}
	
	public List<ItemComercial> findByProduto(Long idProduto) throws Exception{
		List<ItemComercial> pageList = null;
		try{
			pageList = dao.findByProduto(idProduto);
		}catch(Exception e){
			LOGGER.error(e);
			throw e;
		}
		return pageList;
	}
	
	public List<ItemComercial> findByProdutoAndSubEtapa(Long idProduto, Long idSubEtapa) throws Exception{
		List<ItemComercial> pageList = null;
		try{
			pageList = dao.findByProduto(idProduto);
			Apontamento ultimoApontamento = null;
			for(int i = 0; i < pageList.size(); i++){
				ItemComercial item = pageList.get(i);
				ultimoApontamento = apontamentoDAO.findLastByItemComercial(item.getId(), idSubEtapa);
				pageList.get(i).setUltimoApontamento(ultimoApontamento);
			}
		}catch(Exception e){
			LOGGER.error(e);
			throw e;
		}
		return pageList;
	}
	
	public List<ItemComercial> findByUnidadeAndSubEtapa(Long idUnidade, Long idSubEtapa) throws Exception{
		List<ItemComercial> pageList = null;
		try{
			pageList = dao.findByUnidade(idUnidade);
			Apontamento ultimoApontamento = null;
			for(int i = 0; i < pageList.size(); i++){
				ItemComercial item = pageList.get(i);
				ultimoApontamento = apontamentoDAO.findLastByItemComercial(item.getId(), idSubEtapa);
				pageList.get(i).setUltimoApontamento(ultimoApontamento);
			}
		}catch(Exception e){
			LOGGER.error(e);
			throw e;
		}
		return pageList;
	}
	
	public List<ItemComercial> findByUnidade(Long idUnidade) throws Exception{
		List<ItemComercial> pageList = null;
		try{
			pageList = dao.findByUnidade(idUnidade);
		}catch(Exception e){
			LOGGER.error(e);
			throw e;
		}
		return pageList;
	}

}
