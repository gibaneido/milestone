package br.com.projeto.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.projeto.dao.ApontamentoDAO;
import br.com.projeto.dao.DAO;
import br.com.projeto.dao.ItemDAO;
import br.com.projeto.entity.Apontamento;
import br.com.projeto.entity.Item;

/**
 * Business example class
 * @author gb
 *
 */
@Service
public class ItemService {
	
	/*
	 * Make IoC using constructor
	 */
	private final ItemDAO dao;
	
	private final ApontamentoDAO apontamentoDAO;

	private final DAO<Item, Long> genericDAO;
	 
	private static final Logger LOGGER = Logger.getLogger(ItemService.class);
	 
	@Autowired
	public ItemService(ItemDAO dao, ApontamentoDAO apontamentoDAO, DAO<Item, Long> genericDAO)	{
		this.dao = dao;
		this.apontamentoDAO = apontamentoDAO;
		this.genericDAO = genericDAO;
	}
	 
	/**
	 * Save or update the XXX entity
	 * @param <code>Item</code> posicao
	 * @throws {@linkplain Exception} 
	 */
	public void save(Item posicao) throws Exception	{
		try {
			this.genericDAO.save(posicao);
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
	
	public int delete(Long idItem) throws Exception	{
		try {
			return this.dao.delete(idItem);
		} catch (SecurityException e) {
			LOGGER.error(e);
			throw new Exception(e);
		} catch (Exception e) {
			LOGGER.error(e);
			throw new Exception(e);
		}
	}
	
	public Item merge(Item item) throws Exception	{
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

	public Item findById(Long id) throws Exception	{
		Item item = null;
		try {
			item = this.genericDAO.findById(Item.class, id);
		} catch (SecurityException e) {
			LOGGER.error(e);
			throw new Exception(e);
		} catch (Exception e) {
			LOGGER.error(e);
			throw new Exception(e);
		}
		return item;
	}
	
	public List<Item> findAll() throws Exception{
		List<Item> pageList = null;
		try{
			pageList = dao.findAll();
		}catch(Exception e){
			LOGGER.error(e);
			throw e;
		}
		return pageList;
	}
	
	public List<Item> findByUnidade(Long idUnidade) throws Exception{
		List<Item> pageList = null;
		try{
			pageList = dao.findByUnidade(idUnidade);
		}catch(Exception e){
			LOGGER.error(e);
			throw e;
		}
		return pageList;
	}
	
	public List<Item> findPosicoesConjuntoSoldado(Long idUnidade) throws Exception{
		List<Item> pageList = null;
		try{
			pageList = dao.findPosicoesConjuntoSoldado(idUnidade);
		}catch(Exception e){
			LOGGER.error(e);
			throw e;
		}
		return pageList;
	}
	
	public List<Item> findByPosicao(Long idItem) throws Exception{
		List<Item> pageList = null;
		try{
			pageList = dao.findByPosicao(idItem);
		}catch(Exception e){
			LOGGER.error(e);
			throw e;
		}
		return pageList;
	}
	
	public List<Item> findByUnidadeAndSubEtapa(Long idUnidade, Long idSubEtapa) throws Exception{
		List<Item> pageList = null;
		try{
			pageList = dao.findPosicoesByUnidade(idUnidade);
			Apontamento ultimoApontamento = null;
			for(int i = 0; i < pageList.size(); i++){
				Item item = pageList.get(i);
				ultimoApontamento = apontamentoDAO.findLastByPosicao(item.getId(), idSubEtapa);
				pageList.get(i).setUltimoApontamento(ultimoApontamento);
			}
		}catch(Exception e){
			LOGGER.error(e);
			throw e;
		}
		return pageList;
	}
	
	public List<Item> findSubPosicoesByPosicaoAndSubEtapa(Long idPosicao, Long idSubEtapa) throws Exception{
		List<Item> pageList = null;
		try{
			pageList = dao.findSubPosicoesByPosicao(idPosicao);
			Apontamento ultimoApontamento = null;
			for(int i = 0; i < pageList.size(); i++){
				Item item = pageList.get(i);
				ultimoApontamento = apontamentoDAO.findLastBySubPosicao(item.getId(), idSubEtapa);
				pageList.get(i).setUltimoApontamento(ultimoApontamento);
			}
		}catch(Exception e){
			LOGGER.error(e);
			throw e;
		}
		return pageList;
	}
	
	public List<Item> findPosicoesByUnidade(Long idUnidade) throws Exception{
		List<Item> pageList = null;
		try{
			pageList = dao.findPosicoesByUnidade(idUnidade);
		}catch(Exception e){
			LOGGER.error(e);
			throw e;
		}
		return pageList;
	}
	
	public List<Item> findSubPosicoesByPosicao(Long idPosicao) throws Exception{
		List<Item> pageList = null;
		try{
			pageList = dao.findSubPosicoesByPosicao(idPosicao);
		}catch(Exception e){
			LOGGER.error(e);
			throw e;
		}
		return pageList;
	}

}
