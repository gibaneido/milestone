package br.com.projeto.web.adm;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.projeto.entity.Apontamento;
import br.com.projeto.entity.Item;
import br.com.projeto.entity.ItemComercial;
import br.com.projeto.entity.Operacao;
import br.com.projeto.entity.Produto;
import br.com.projeto.entity.Projeto;
import br.com.projeto.entity.SubEtapa;
import br.com.projeto.entity.Unidade;
import br.com.projeto.service.ApontamentoService;
import br.com.projeto.service.ItemComercialService;
import br.com.projeto.service.ItemService;
import br.com.projeto.service.NivelEtapaSubEtapaService;
import br.com.projeto.service.OperacaoService;
import br.com.projeto.service.ProdutoService;
import br.com.projeto.service.ProjetoService;
import br.com.projeto.service.SubEtapaService;
import br.com.projeto.service.UnidadeService;
import br.com.projeto.util.NivelEnum;

@Controller
public class ApontamentoController {
	
	@Autowired
	private ProjetoService projetoService;
	
	@Autowired
	private SubEtapaService subEtapaService;

	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private OperacaoService operacaoService;
	
	@Autowired
	private UnidadeService unidadeService;
	
	@Autowired
	private ItemComercialService itemComercialService;
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private ApontamentoService apontamentoService;
	
	@Autowired
	private NivelEtapaSubEtapaService nivelEtapaSubEtapaService;

	@RequestMapping(value = "/admin/apontamento/produto-save", method = RequestMethod.POST)
	public ModelAndView produtoSave(
			HttpSession session,
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(required=false, value="idProjeto") Long idProjeto,
			@RequestParam(required=false, value="idsProdutos") Long[] idsProdutos,
			@RequestParam(required=false, value="idsApontamentos") Long[] idsApontamentos,
			@RequestParam(required=false, value="cronogramaInicio") String[] cronogramasInicio,
			@RequestParam(required=false, value="cronogramaFim") String[] cronogramasFim,
			@RequestParam(required=false, value="previstoInicio") String[] previstosInicio,
			@RequestParam(required=false, value="previstoFim") String[] previstosFim,
			@RequestParam(required=false, value="porcentagem") Long[] porcentagens,
			@RequestParam(required=false, value="porcentOld") Long[] porcentOld,
			@RequestParam(required=false, value="idSubEtapa") Long idSubEtapa,
			@RequestParam(required=false, value="na") Boolean na
	) throws Exception {
		
		boolean erros = false;
		if(idProjeto != null && idProjeto.intValue() > 0 && idSubEtapa != null && idSubEtapa.intValue() > 0){
			Projeto projeto = projetoService.findById(idProjeto);
			SubEtapa subEtapa = subEtapaService.findById(idSubEtapa);
			if(cronogramasInicio != null && cronogramasFim != null && previstosInicio != null && previstosFim != null && porcentagens != null){
				for(int i = 0; i < cronogramasInicio.length; i++){
					if(!StringUtils.isEmpty(cronogramasInicio[i]) 
							|| !StringUtils.isEmpty(cronogramasFim[i]) 
							|| !StringUtils.isEmpty(previstosInicio[i]) 
							|| !StringUtils.isEmpty(previstosFim[i]) 
							|| (porcentagens[i] != null
							&& porcentagens[i] > 0)){
						Apontamento apontamento = new Apontamento();
						apontamento.setDataCriacao(new Date());
						apontamento.setProjeto(projeto);
						apontamento.setSubEtapa(subEtapa);
						apontamento.setNivel(nivelEtapaSubEtapaService.findBySubEtapa(subEtapa.getId(), NivelEnum.PD));
						if(idsProdutos[i] != null && idsProdutos[i].intValue() > 0){
							Produto produto = produtoService.findById(idsProdutos[i]);
							apontamento.setProduto(produto);	
						}
						if(na == null){
							DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
							if(cronogramasInicio[i] != null && !cronogramasInicio[i].equals("")){
								try{
									Date cronogramaInicio = df.parse(cronogramasInicio[i]);
									apontamento.setCronogramaInicio(cronogramaInicio);
								}catch(ParseException pe){
								}
							}
							if(cronogramasFim[i] != null && !cronogramasFim[i].equals("")){
								try{
									Date cronogramaFim = df.parse(cronogramasFim[i]);
									apontamento.setCronogramaFim(cronogramaFim);
								}catch(ParseException pe){
								}
							}
							if(previstosInicio[i] != null && !previstosInicio[i].equals("")){
								try{
									Date previstoInicio = df.parse(previstosInicio[i]);
									apontamento.setPrevistoInicio(previstoInicio);
								}catch(ParseException pe){
								}
							}
							if(previstosFim[i] != null && !previstosFim[i].equals("")){
								try{
									Date previstoFim = df.parse(previstosFim[i]);
									apontamento.setPrevistoFim(previstoFim);
								}catch(ParseException pe){
								}
							}
							//TODO setar realizado com a data atual qdo a porcentagem for 100%
							/*if(realizados[i] != null && !realizados[i].equals("")){
								try{
									Date realizado = df.parse(realizados[i]);
									apontamento.setRealizado(realizado);
								}catch(ParseException pe){
								}
							}
							*/
							if(porcentagens[i] == null)
								porcentagens[i] = 0L;
							apontamento.setPorcentagem(porcentagens[i]);
							if(porcentOld[i] == porcentagens[i]){
								apontamento.setId(idsApontamentos[i]);
							}
							apontamento.setNa(false);
						}else{
							apontamento.setNa(na);
						}
						try{
							apontamentoService.save(apontamento);	
						}catch(Exception e){
							e.printStackTrace();
							erros = true;
						}
					}
				}
			}
		}else{
			erros = true;
		}
		
		try{
			if(erros){
				response.getWriter().write("{\"status\":0}");	
			}else{
				response.getWriter().write("{\"status\":1}");	
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}

		return null;
	}
	
	@RequestMapping(value = "/admin/apontamento/operacao-save", method = RequestMethod.POST)
	public ModelAndView operacaoSave(
			HttpSession session,
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(required=false, value="idProjeto") Long idProjeto,
			@RequestParam(required=false, value="idsOperacoes") Long[] idsOperacoes,
			@RequestParam(required=false, value="idsApontamentos") Long[] idsApontamentos,
			@RequestParam(required=false, value="cronogramaInicio") String[] cronogramasInicio,
			@RequestParam(required=false, value="cronogramaFim") String[] cronogramasFim,
			@RequestParam(required=false, value="previstoInicio") String[] previstosInicio,
			@RequestParam(required=false, value="previstoFim") String[] previstosFim,
			@RequestParam(required=false, value="porcentagem") Long[] porcentagens,
			@RequestParam(required=false, value="porcentOld") Long[] porcentOld,
			@RequestParam(required=false, value="idSubEtapa") Long idSubEtapa,
			@RequestParam(required=false, value="na") Boolean na
	) throws Exception {

		boolean erros = false;
		if(idProjeto != null && idProjeto.intValue() > 0 && idSubEtapa != null && idSubEtapa.intValue() > 0){
			Projeto projeto = projetoService.findById(idProjeto);
			SubEtapa subEtapa = subEtapaService.findById(idSubEtapa);
			if(cronogramasInicio != null && cronogramasFim != null && previstosInicio != null && previstosFim != null && porcentagens != null){
				for(int i = 0; i < cronogramasInicio.length; i++){
					if(!StringUtils.isEmpty(cronogramasInicio[i]) 
							|| !StringUtils.isEmpty(cronogramasFim[i]) 
							|| !StringUtils.isEmpty(previstosInicio[i]) 
							|| !StringUtils.isEmpty(previstosFim[i]) 
							|| (porcentagens[i] != null
							&& porcentagens[i] > 0)){
						Apontamento apontamento = new Apontamento();
						apontamento.setDataCriacao(new Date());
						apontamento.setProjeto(projeto);
						apontamento.setSubEtapa(subEtapa);
						apontamento.setNivel(nivelEtapaSubEtapaService.findBySubEtapa(subEtapa.getId(), NivelEnum.OP));
						if(idsOperacoes[i] != null && idsOperacoes[i].intValue() > 0){
							Operacao operacao = operacaoService.findById(idsOperacoes[i]);
							apontamento.setOperacao(operacao);	
						}
						
						if(na == null){
							DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
							if(cronogramasInicio[i] != null && !cronogramasInicio[i].equals("")){
								try{
									Date cronogramaInicio = df.parse(cronogramasInicio[i]);
									apontamento.setCronogramaInicio(cronogramaInicio);
								}catch(ParseException pe){
								}
							}
							if(cronogramasFim[i] != null && !cronogramasFim[i].equals("")){
								try{
									Date cronogramaFim = df.parse(cronogramasFim[i]);
									apontamento.setCronogramaFim(cronogramaFim);
								}catch(ParseException pe){
								}
							}
							if(previstosInicio[i] != null && !previstosInicio[i].equals("")){
								try{
									Date previstoInicio = df.parse(previstosInicio[i]);
									apontamento.setPrevistoInicio(previstoInicio);
								}catch(ParseException pe){
								}
							}
							if(previstosFim[i] != null && !previstosFim[i].equals("")){
								try{
									Date previstoFim = df.parse(previstosFim[i]);
									apontamento.setPrevistoFim(previstoFim);
								}catch(ParseException pe){
								}
							}
							//TODO setar realizado com a data atual qdo a porcentagem for 100%
							/*if(realizados[i] != null && !realizados[i].equals("")){
								try{
									Date realizado = df.parse(realizados[i]);
									apontamento.setRealizado(realizado);
								}catch(ParseException pe){
								}
							}
							*/
							if(porcentagens[i] == null)
								porcentagens[i] = 0L;
							apontamento.setPorcentagem(porcentagens[i]);
							if(porcentOld[i] == porcentagens[i]){
								apontamento.setId(idsApontamentos[i]);
							}
							apontamento.setNa(false);
						}else{
							apontamento.setNa(na);
						}
						
						try{
							apontamentoService.save(apontamento);	
						}catch(Exception e){
							e.printStackTrace();
							erros = true;
						}						
					}
				}
			}
		}else{
			erros = true;
		}
		
		
		try{
			if(erros){
				response.getWriter().write("{\"status\":0}");	
			}else{
				response.getWriter().write("{\"status\":1}");	
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}

		return null;
	}
	
	@RequestMapping(value = "/admin/apontamento/unidade-save", method = RequestMethod.POST)
	public ModelAndView unidadeSave(
			HttpSession session,
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(required=false, value="idProjeto") Long idProjeto,
			@RequestParam(required=false, value="idsUnidades") Long[] idsUnidades,
			@RequestParam(required=false, value="idsApontamentos") Long[] idsApontamentos,
			@RequestParam(required=false, value="cronogramaInicio") String[] cronogramasInicio,
			@RequestParam(required=false, value="cronogramaFim") String[] cronogramasFim,
			@RequestParam(required=false, value="previstoInicio") String[] previstosInicio,
			@RequestParam(required=false, value="previstoFim") String[] previstosFim,
			@RequestParam(required=false, value="porcentagem") Long[] porcentagens,
			@RequestParam(required=false, value="porcentOld") Long[] porcentOld,
			@RequestParam(required=false, value="idSubEtapa") Long idSubEtapa,
			@RequestParam(required=false, value="na") Boolean na
	) throws Exception {
		
		boolean erros = false;
		if(idProjeto != null && idProjeto.intValue() > 0 && idSubEtapa != null && idSubEtapa.intValue() > 0){
			Projeto projeto = projetoService.findById(idProjeto);
			SubEtapa subEtapa = subEtapaService.findById(idSubEtapa);
			if(cronogramasInicio != null){
				for(int i = 0; i < cronogramasInicio.length; i++){
					if(!StringUtils.isEmpty(cronogramasInicio[i]) 
							|| !StringUtils.isEmpty(cronogramasFim[i]) 
							|| !StringUtils.isEmpty(previstosInicio[i]) 
							|| !StringUtils.isEmpty(previstosFim[i]) 
							|| (porcentagens[i] != null
							&& porcentagens[i] > 0)){
						Apontamento apontamento = new Apontamento();
						apontamento.setDataCriacao(new Date());
						apontamento.setProjeto(projeto);
						apontamento.setSubEtapa(subEtapa);
						apontamento.setNivel(nivelEtapaSubEtapaService.findBySubEtapa(subEtapa.getId(), NivelEnum.UN));
						
						if(idsUnidades[i] != null && idsUnidades[i].intValue() > 0){
							Unidade unidade = unidadeService.findById(idsUnidades[i]);
							apontamento.setUnidade(unidade);	
						}
						if(na == null){
							DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
							if(cronogramasInicio[i] != null && !cronogramasInicio[i].equals("")){
								try{
									Date cronogramaInicio = df.parse(cronogramasInicio[i]);
									apontamento.setCronogramaInicio(cronogramaInicio);
								}catch(ParseException pe){
								}
							}
							if(cronogramasFim[i] != null && !cronogramasFim[i].equals("")){
								try{
									Date cronogramaFim = df.parse(cronogramasFim[i]);
									apontamento.setCronogramaFim(cronogramaFim);
								}catch(ParseException pe){
								}
							}
							if(previstosInicio[i] != null && !previstosInicio[i].equals("")){
								try{
									Date previstoInicio = df.parse(previstosInicio[i]);
									apontamento.setPrevistoInicio(previstoInicio);
								}catch(ParseException pe){
								}
							}
							if(previstosFim[i] != null && !previstosFim[i].equals("")){
								try{
									Date previstoFim = df.parse(previstosFim[i]);
									apontamento.setPrevistoFim(previstoFim);
								}catch(ParseException pe){
								}
							}
							//TODO setar realizado com a data atual qdo a porcentagem for 100%
							/*if(realizados[i] != null && !realizados[i].equals("")){
								try{
									Date realizado = df.parse(realizados[i]);
									apontamento.setRealizado(realizado);
								}catch(ParseException pe){
								}
							}
							*/
							if(porcentagens[i] == null)
								porcentagens[i] = 0L;
							apontamento.setPorcentagem(porcentagens[i]);
							if(porcentOld[i] == porcentagens[i]){
								apontamento.setId(idsApontamentos[i]);
							}
							apontamento.setNa(false);
						}else{
							apontamento.setNa(na);
						}
						
						try{
							apontamentoService.save(apontamento);	
						}catch(Exception e){
							e.printStackTrace();
							erros = true;
						}						
					}
				}
			}
			
		}else{
			erros = true;
		}
		try{
			if(erros){
				response.getWriter().write("{\"status\":0}");	
			}else{
				response.getWriter().write("{\"status\":1}");	
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/admin/apontamento/item-comercial-save", method = RequestMethod.POST)
	public ModelAndView itemComercialSave(
			HttpSession session,
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(required=false, value="idProjeto") Long idProjeto,
			@RequestParam(required=false, value="idsItensComerciais") Long[] idsItensComerciais,
			@RequestParam(required=false, value="idsApontamentos") Long[] idsApontamentos,
			@RequestParam(required=false, value="cronogramaInicio") String[] cronogramasInicio,
			@RequestParam(required=false, value="cronogramaFim") String[] cronogramasFim,
			@RequestParam(required=false, value="previstoInicio") String[] previstosInicio,
			@RequestParam(required=false, value="previstoFim") String[] previstosFim,
			@RequestParam(required=false, value="realizado") String[] realizados,
			@RequestParam(required=false, value="porcentagem") Long[] porcentagens,
			@RequestParam(required=false, value="porcentOld") Long[] porcentOld,
			@RequestParam(required=false, value="idSubEtapa") Long idSubEtapa,
			@RequestParam(required=false, value="na") Boolean na
	) throws Exception {
		
		boolean erros = false;
		if(idProjeto != null && idProjeto.intValue() > 0 && idSubEtapa != null && idSubEtapa.intValue() > 0){
			Projeto projeto = projetoService.findById(idProjeto);
			SubEtapa subEtapa = subEtapaService.findById(idSubEtapa);
			if(cronogramasInicio != null){
				for(int i = 0; i < cronogramasInicio.length; i++){
					if(!StringUtils.isEmpty(cronogramasInicio[i]) 
							|| !StringUtils.isEmpty(cronogramasFim[i]) 
							|| !StringUtils.isEmpty(previstosInicio[i]) 
							|| !StringUtils.isEmpty(previstosFim[i]) 
							|| (porcentagens[i] != null
							&& porcentagens[i] > 0)){
						Apontamento apontamento = new Apontamento();
						apontamento.setDataCriacao(new Date());
						apontamento.setProjeto(projeto);
						apontamento.setSubEtapa(subEtapa);
						apontamento.setNivel(nivelEtapaSubEtapaService.findBySubEtapa(subEtapa.getId(), NivelEnum.IC));
						
						if(idsItensComerciais[i] != null && idsItensComerciais[i].intValue() > 0){
							ItemComercial itemComercial = itemComercialService.findById(idsItensComerciais[i]);
							apontamento.setItemComercial(itemComercial);	
						}
						if(na == null){
							DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
							if(cronogramasInicio[i] != null && !cronogramasInicio[i].equals("")){
								try{
									Date cronogramaInicio = df.parse(cronogramasInicio[i]);
									apontamento.setCronogramaInicio(cronogramaInicio);
								}catch(ParseException pe){
								}
							}
							if(cronogramasFim[i] != null && !cronogramasFim[i].equals("")){
								try{
									Date cronogramaFim = df.parse(cronogramasFim[i]);
									apontamento.setCronogramaFim(cronogramaFim);
								}catch(ParseException pe){
								}
							}
							if(previstosInicio[i] != null && !previstosInicio[i].equals("")){
								try{
									Date previstoInicio = df.parse(previstosInicio[i]);
									apontamento.setPrevistoInicio(previstoInicio);
								}catch(ParseException pe){
								}
							}
							if(previstosFim[i] != null && !previstosFim[i].equals("")){
								try{
									Date previstoFim = df.parse(previstosFim[i]);
									apontamento.setPrevistoFim(previstoFim);
								}catch(ParseException pe){
								}
							}
							//TODO setar realizado com a data atual qdo a porcentagem for 100%
							/*if(realizados[i] != null && !realizados[i].equals("")){
								try{
									Date realizado = df.parse(realizados[i]);
									apontamento.setRealizado(realizado);
								}catch(ParseException pe){
								}
							}
							*/
							if(porcentagens[i] == null)
								porcentagens[i] = 0L;
							apontamento.setPorcentagem(porcentagens[i]);
							if(porcentOld[i] == porcentagens[i]){
								apontamento.setId(idsApontamentos[i]);
							}
							apontamento.setNa(false);
						}else{
							apontamento.setNa(na);
						}
						
						try{
							apontamentoService.save(apontamento);	
						}catch(Exception e){
							e.printStackTrace();
							erros = true;
						}
					}
				}	
			}
		}else{
			erros = true;
		}
		try{
			if(erros){
				response.getWriter().write("{\"status\":0}");	
			}else{
				response.getWriter().write("{\"status\":1}");	
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}

		return null;
	}
	
	@RequestMapping(value = "/admin/apontamento/posicao-save", method = RequestMethod.POST)
	public ModelAndView posicaoSave(
			HttpSession session,
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(required=false, value="idProjeto") Long idProjeto,
			@RequestParam(required=false, value="idsPosicoes") Long[] idsPosicoes,
			@RequestParam(required=false, value="idsApontamentos") Long[] idsApontamentos,
			@RequestParam(required=false, value="cronogramaInicio") String[] cronogramasInicio,
			@RequestParam(required=false, value="cronogramaFim") String[] cronogramasFim,
			@RequestParam(required=false, value="previstoInicio") String[] previstosInicio,
			@RequestParam(required=false, value="previstoFim") String[] previstosFim,
			@RequestParam(required=false, value="realizado") String[] realizados,
			@RequestParam(required=false, value="porcentagem") Long[] porcentagens,
			@RequestParam(required=false, value="porcentOld") Long[] porcentOld,
			@RequestParam(required=false, value="idSubEtapa") Long idSubEtapa,
			@RequestParam(required=false, value="na") Boolean na
	) throws Exception {
		
		boolean erros = false;
		if(idProjeto != null && idProjeto.intValue() > 0 && idSubEtapa != null && idSubEtapa.intValue() > 0){
			Projeto projeto = projetoService.findById(idProjeto);
			SubEtapa subEtapa = subEtapaService.findById(idSubEtapa);
			if(cronogramasInicio != null){
				for(int i = 0; i < cronogramasInicio.length; i++){
					if(!StringUtils.isEmpty(cronogramasInicio[i]) 
							|| !StringUtils.isEmpty(cronogramasFim[i]) 
							|| !StringUtils.isEmpty(previstosInicio[i]) 
							|| !StringUtils.isEmpty(previstosFim[i]) 
							|| (porcentagens[i] != null
							&& porcentagens[i] > 0)){
						int any = 0;
						Apontamento apontamento = new Apontamento();
						apontamento.setDataCriacao(new Date());
						apontamento.setProjeto(projeto);
						apontamento.setSubEtapa(subEtapa);
						apontamento.setNivel(nivelEtapaSubEtapaService.findBySubEtapa(subEtapa.getId(), NivelEnum.PO));
						
						if(idsPosicoes[i] != null && idsPosicoes[i].intValue() > 0){
							Item item = itemService.findById(idsPosicoes[i]);
							apontamento.setItem(item);	
						}
						
						if(na == null){
							DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
							if(cronogramasInicio[i] != null && !cronogramasInicio[i].equals("")){
								try{
									Date cronogramaInicio = df.parse(cronogramasInicio[i]);
									apontamento.setCronogramaInicio(cronogramaInicio);
								}catch(ParseException pe){
								}
								any++;
							}
							if(cronogramasFim[i] != null && !cronogramasFim[i].equals("")){
								try{
									Date cronogramaFim = df.parse(cronogramasFim[i]);
									apontamento.setCronogramaFim(cronogramaFim);
								}catch(ParseException pe){
								}
								any++;
							}
							if(previstosInicio[i] != null && !previstosInicio[i].equals("")){
								try{
									Date previstoInicio = df.parse(previstosInicio[i]);
									apontamento.setPrevistoInicio(previstoInicio);
								}catch(ParseException pe){
								}
								any++;
							}
							if(previstosFim[i] != null && !previstosFim[i].equals("")){
								try{
									Date previstoFim = df.parse(previstosFim[i]);
									apontamento.setPrevistoFim(previstoFim);
								}catch(ParseException pe){
								}
								any++;
							}
							//TODO setar realizado com a data atual qdo a porcentagem for 100%
							/*if(realizados[i] != null && !realizados[i].equals("")){
								try{
									Date realizado = df.parse(realizados[i]);
									apontamento.setRealizado(realizado);
								}catch(ParseException pe){
								}
								any++;
							}*/
							if(porcentagens[i] == null)
								porcentagens[i] = 0L;
							apontamento.setPorcentagem(porcentagens[i]);
							if(porcentOld[i] == porcentagens[i]){
								apontamento.setId(idsApontamentos[i]);
							}
							any++;
							apontamento.setNa(false);
						}else{
							apontamento.setNa(na);
							any++;
						}
						
						if(any > 0){
							try{
								apontamentoService.save(apontamento);	
							}catch(Exception e){
								e.printStackTrace();
								erros = true;
							}	
						}
					}
				}	
			}
		}else{
			erros = true;
		}
		try{
			if(erros){
				response.getWriter().write("{\"status\":0}");	
			}else{
				response.getWriter().write("{\"status\":1}");	
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}

		return null;
	}
	
	@RequestMapping(value = "/admin/apontamento/sub-posicao-save", method = RequestMethod.POST)
	public ModelAndView subPosicaoSave(
			HttpSession session,
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(required=false, value="idProjeto") Long idProjeto,
			@RequestParam(required=false, value="idsSubPosicoes") Long[] idsSubPosicoes,
			@RequestParam(required=false, value="idsApontamentos") Long[] idsApontamentos,
			@RequestParam(required=false, value="cronogramaInicio") String[] cronogramasInicio,
			@RequestParam(required=false, value="cronogramaFim") String[] cronogramasFim,
			@RequestParam(required=false, value="previstoInicio") String[] previstosInicio,
			@RequestParam(required=false, value="previstoFim") String[] previstosFim,
			@RequestParam(required=false, value="realizado") String[] realizados,
			@RequestParam(required=false, value="porcentagem") Long[] porcentagens,
			@RequestParam(required=false, value="porcentOld") Long[] porcentOld,
			@RequestParam(required=false, value="idSubEtapa") Long idSubEtapa,
			@RequestParam(required=false, value="na") Boolean na
	) throws Exception {
		
		boolean erros = false;
		if(idProjeto != null && idProjeto.intValue() > 0 && idSubEtapa != null && idSubEtapa.intValue() > 0){
			Projeto projeto = projetoService.findById(idProjeto);
			SubEtapa subEtapa = subEtapaService.findById(idSubEtapa);
			if(cronogramasInicio != null){
				for(int i = 0; i < cronogramasInicio.length; i++){
					if(!StringUtils.isEmpty(cronogramasInicio[i]) 
							|| !StringUtils.isEmpty(cronogramasFim[i]) 
							|| !StringUtils.isEmpty(previstosInicio[i]) 
							|| !StringUtils.isEmpty(previstosFim[i]) 
							|| (porcentagens[i] != null
							&& porcentagens[i] > 0)){
						int any = 0;
						Apontamento apontamento = new Apontamento();
						apontamento.setDataCriacao(new Date());
						apontamento.setProjeto(projeto);
						apontamento.setSubEtapa(subEtapa);
						apontamento.setNivel(nivelEtapaSubEtapaService.findBySubEtapa(subEtapa.getId(), NivelEnum.SP));						
						
						if(idsSubPosicoes[i] != null && idsSubPosicoes[i].intValue() > 0){
							Item item = itemService.findById(idsSubPosicoes[i]);
							apontamento.setItem(item);	
						}
						
						if(na == null){
							DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
							if(cronogramasInicio[i] != null && !cronogramasInicio[i].equals("")){
								try{
									Date cronogramaInicio = df.parse(cronogramasInicio[i]);
									apontamento.setCronogramaInicio(cronogramaInicio);
								}catch(ParseException pe){
								}
								any++;
							}
							if(cronogramasFim[i] != null && !cronogramasFim[i].equals("")){
								try{
									Date cronogramaFim = df.parse(cronogramasFim[i]);
									apontamento.setCronogramaFim(cronogramaFim);
								}catch(ParseException pe){
								}
								any++;
							}
							if(previstosInicio[i] != null && !previstosInicio[i].equals("")){
								try{
									Date previstoInicio = df.parse(previstosInicio[i]);
									apontamento.setPrevistoInicio(previstoInicio);
								}catch(ParseException pe){
								}
								any++;
							}
							if(previstosFim[i] != null && !previstosFim[i].equals("")){
								try{
									Date previstoFim = df.parse(previstosFim[i]);
									apontamento.setPrevistoFim(previstoFim);
								}catch(ParseException pe){
								}
								any++;
							}
							//TODO setar realizado com a data atual qdo a porcentagem for 100%
							/*if(realizados[i] != null && !realizados[i].equals("")){
								try{
									Date realizado = df.parse(realizados[i]);
									apontamento.setRealizado(realizado);
								}catch(ParseException pe){
								}
								any++;
							}
							*/
								if(porcentagens[i] == null)
									porcentagens[i] = 0L;
								apontamento.setPorcentagem(porcentagens[i]);
								if(porcentOld[i] == porcentagens[i]){
									apontamento.setId(idsApontamentos[i]);
								}
								any++;
							apontamento.setNa(false);
						}else{
							apontamento.setNa(na);
							any++;
						}
						if(any > 0){
							try{
								apontamentoService.save(apontamento);	
							}catch(Exception e){
								e.printStackTrace();
								erros = true;
							}	
						}
					}
				}	
			}
		}else{
			erros = true;
		}
		try{
			if(erros){
				response.getWriter().write("{\"status\":0}");	
			}else{
				response.getWriter().write("{\"status\":1}");	
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	@InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
    	DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    	df.setLenient(false);
    	CustomDateEditor editor = new CustomDateEditor(df, false);
    	binder.registerCustomEditor(Date.class, editor);
    }
	
}
