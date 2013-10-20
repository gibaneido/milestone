package br.com.projeto.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;

import br.com.projeto.entity.Apontamento;
import br.com.projeto.entity.Item;
import br.com.projeto.entity.Unidade;
import br.com.projeto.vo.ChartEtapaVO;

/**
 * 
 * @author gb
 *
 */
public interface ApontamentoDAO {
	/**
	 * Save or update the XXX entity 
	 * @param <code>Apontamento</code> apontamento
	 */
	public void save(Apontamento apontamento);
	
	public int delete(Long idApontamento);
	
	public List<Apontamento> findAll() throws NoResultException, Exception;
	
	public Apontamento findLastByProduto(Long idProduto, Long idSubEtapa) throws NoResultException, Exception;
	
	public Apontamento findLastByOperacao(Long idOperacao, Long idSubEtapa) throws NoResultException, Exception;
	
	public Apontamento findLastByUnidade(Long idUnidade, Long idSubEtapa) throws NoResultException, Exception;
	
	public Apontamento findLastByItemComercial(Long idItemComercial, Long idSubEtapa) throws NoResultException, Exception;
	
	public Apontamento findLastByPosicao(Long idItem, Long idSubEtapa) throws NoResultException, Exception;
	
	public Apontamento findLastBySubPosicao(Long idItem, Long idSubEtapa) throws NoResultException, Exception;
	
	public List<Long> findLastsByPosicao(Item item) throws NoResultException, Exception;
	
	public List<Long> findLastsBySubPosicao(Item item) throws NoResultException, Exception;
	
	public List<Long> findLastsByUnidade(Unidade unidade) throws NoResultException, Exception;
	
	public List<ChartEtapaVO> findRealizadoToChart(Long idEtapa, Long idProjeto, Date date) throws NoResultException, Exception;
	
	public List<ChartEtapaVO> findPrevistoToChart(Long idEtapa, Long idProjeto, Date date) throws NoResultException, Exception;
}
