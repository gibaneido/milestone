package br.com.projeto.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.projeto.dao.ApontamentoDAO;
import br.com.projeto.dao.DAO;
import br.com.projeto.entity.Apontamento;
import br.com.projeto.entity.Etapa;
import br.com.projeto.entity.Item;
import br.com.projeto.entity.Projeto;
import br.com.projeto.entity.Unidade;
import br.com.projeto.util.NivelEnum;
import br.com.projeto.util.NivelEvolucao;
import br.com.projeto.vo.ChartEtapaVO;
import br.com.projeto.vo.ChartProjetoVO;

/**
 * Business example class
 * @author gb
 *
 */
@Service
public class ApontamentoService {
	
	/*
	 * Make IoC using constructor
	 */
	private final ApontamentoDAO dao;

	 private final DAO<Apontamento, Long> genericDAO;
	 
	 private final ProjetoService projetoService;
	 
	 private static final Logger LOGGER = Logger.getLogger(ApontamentoService.class);
	 
	 @Autowired
	 public ApontamentoService(ApontamentoDAO dao, DAO<Apontamento, Long> genericDAO, ProjetoService projetoService)	{
		 this.dao = dao;
		 this.genericDAO = genericDAO;
		 this.projetoService = projetoService;
	 }
	 
	/**
	 * Save or update the XXX entity
	 * @param <code>Apontamento</code> apontamento
	 * @throws {@linkplain Exception} 
	 */
	public void save(Apontamento apontamento) throws Exception	{
		try {
			this.genericDAO.save(apontamento);
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
	
	public Long porcentOfPosicao(Item item) throws Exception {
		Long total = 0L;
		long points = 0;
		for (Long porcent : dao.findLastsByPosicao(item)){
			total += porcent;
			points++;
		}
		if (points == 0) return points;
		return total/NivelEvolucao.count(NivelEnum.PO);
	}
	
	public Long porcentOfSubPosicao(Item item) throws Exception {
		Long total = 0L;
		long points = 0;
		for (Long porcent : dao.findLastsBySubPosicao(item)){
			total += porcent;
			points++;
		}
		if (points == 0) return points;
		return total/NivelEvolucao.count(NivelEnum.SP);
	}
	
	public Long porcentOfUnidade(Unidade unidade) throws Exception {
		Long total = 0L;
		long points = 0;
		for (Long porcent : dao.findLastsByUnidade(unidade)){
			total += porcent;
			points++;
		}
		if (points == 0) return points;
		return total/points;
	}
	
	public List<ChartProjetoVO> findRealizadoToChart(Long idProjeto) throws Exception	{
		List<ChartProjetoVO> retorno = new ArrayList<ChartProjetoVO>();
		try {
			Projeto projeto = this.projetoService.findById(idProjeto);
			Calendar dataInicio = Calendar.getInstance();
			dataInicio.setTime(projeto.getDataInicio());
			dataInicio.add(Calendar.DAY_OF_MONTH, 1);
			Calendar now = Calendar.getInstance();
			ChartProjetoVO chartProjeto = new ChartProjetoVO();
			while(dataInicio.before(now)){
				chartProjeto = new ChartProjetoVO();
				Float porcentagemProjeto = 0F;
				for(Etapa etapa : projeto.getEtapas()){
					Float porcentagemEtapa = 0F;
					for(ChartEtapaVO chartEtapa : this.dao.findRealizadoToChart(etapa.getId(), idProjeto, dataInicio.getTime())){
						porcentagemEtapa = (float) (porcentagemEtapa + chartEtapa.getPorcentagem());
					}
					porcentagemProjeto = porcentagemProjeto + porcentagemEtapa;
				}
				chartProjeto.setPorcentagem(porcentagemProjeto.longValue()/100);
				chartProjeto.setPoint(dataInicio.getTime());
				retorno.add(chartProjeto);
				dataInicio.add(Calendar.WEEK_OF_YEAR, 1);
			}
			Calendar dataFim = Calendar.getInstance();
			dataFim.setTime(projeto.getDataFim());
			dataFim.add(Calendar.DAY_OF_MONTH, 1);
			Long porcentagemFinal = chartProjeto.getPorcentagem();
			while(dataInicio.before(dataFim)){
				chartProjeto = new ChartProjetoVO();
				chartProjeto.setPorcentagem(porcentagemFinal);
				chartProjeto.setPoint(dataInicio.getTime());
				retorno.add(chartProjeto);
				dataInicio.add(Calendar.WEEK_OF_YEAR, 1);
			}
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
		return retorno;
	}
	
	public List<ChartProjetoVO> findPrevistoToChart(Long idProjeto) throws Exception	{
		List<ChartProjetoVO> retorno = new ArrayList<ChartProjetoVO>();
		try {
			Projeto projeto = this.projetoService.findById(idProjeto);
			Calendar dataInicio = Calendar.getInstance();
			dataInicio.setTime(projeto.getDataInicio());
			//dataInicio.add(Calendar.DAY_OF_MONTH, 1);
			Calendar dataFim = Calendar.getInstance();
			dataFim.setTime(projeto.getDataFim());
			//dataFim.add(Calendar.DAY_OF_MONTH, 3);
			ChartProjetoVO chartProjeto = new ChartProjetoVO();
			while(dataInicio.before(dataFim)){
				chartProjeto = new ChartProjetoVO();
				Float porcentagemProjeto = 0F;
				for(Etapa etapa : projeto.getEtapas()){
					Float porcentagemEtapa = 0F;
					for(ChartEtapaVO chartEtapa : this.dao.findPrevistoToChart(etapa.getId(), idProjeto, dataInicio.getTime())){
						porcentagemEtapa = (float) (porcentagemEtapa + chartEtapa.getPorcentagem());
					}
					porcentagemProjeto = porcentagemProjeto + porcentagemEtapa;
				}
				chartProjeto.setPorcentagem(porcentagemProjeto.longValue()/100);
				chartProjeto.setPoint(dataInicio.getTime());
				retorno.add(chartProjeto);
				dataInicio.add(Calendar.WEEK_OF_YEAR, 1);
			}
			
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
		return retorno;
	}
}
