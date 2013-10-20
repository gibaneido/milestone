package br.com.projeto.web.adm;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.projeto.entity.Fornecedor;
import br.com.projeto.entity.ItemComercial;
import br.com.projeto.entity.Operacao;
import br.com.projeto.entity.Produto;
import br.com.projeto.entity.Projeto;
import br.com.projeto.entity.Unidade;
import br.com.projeto.entity.Usuario;
import br.com.projeto.service.FornecedorService;
import br.com.projeto.service.ItemComercialService;
import br.com.projeto.service.OperacaoService;
import br.com.projeto.service.ProdutoService;
import br.com.projeto.service.ProjetoService;
import br.com.projeto.service.UnidadeService;
import br.com.projeto.service.UsuarioService;
import br.com.projeto.util.Constants;

@Controller
public class ItemComercialController {
	
	@Autowired
	private ProjetoService projetoService;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private OperacaoService operacaoService;

	@Autowired
	private UnidadeService unidadeService;
	
	@Autowired
	private ItemComercialService itemComercialService;
	
	@Autowired
	private FornecedorService fornecedorService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@RequestMapping(value = "/admin/item-comercial/list")
	public ModelAndView listar(
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session,
			@RequestParam(required=false, value="pageScreen") Long pageScreen,
			@RequestParam(required=false, value="returnMessage") String returnMessage,
			@RequestParam(required=false, value="idProjeto") Long idProjeto,
			@RequestParam(required=false, value="idProduto") Long idProduto,
			@RequestParam(required=false, value="idOperacao") Long idOperacao,
			@RequestParam(required=false, value="idUnidade") Long idUnidade
	) {
		ModelAndView model = new ModelAndView();
		model.setViewName("/admin/item-comercial/list");
		if(returnMessage != null){
			model.addObject("returnMessage", returnMessage);
		}
		List<Projeto> projetoList = null;
		List<Produto> produtoList = null;
		List<Operacao> operacaoList = null;
		List<Unidade> unidadeList = null;
		List<Fornecedor> fornecedorList = null;
		try{
			fornecedorList = this.fornecedorService.findAll();
			projetoList = this.projetoService.findAll(0L,15L);
			model.addObject("idProjeto", idProjeto);
			model.addObject("idProduto", idProduto);
			model.addObject("idOperacao", idOperacao);
			model.addObject("idUnidade", idUnidade);
			if(idProjeto != null && idProjeto.intValue() > 0){
				produtoList = this.produtoService.findByProjeto(idProjeto);
			}
			if(idProduto != null && idProduto.intValue() > 0){
				operacaoList = this.operacaoService.findByProduto(idProduto);
			}
			if(idOperacao != null && idOperacao.intValue() > 0){
				unidadeList = this.unidadeService.findByOperacao(idOperacao);
			}
			model.addObject("fornecedorList", fornecedorList);
			model.addObject("projetoList", projetoList);
			model.addObject("produtoList", produtoList);
			model.addObject("operacaoList", operacaoList);
			model.addObject("unidadeList", unidadeList);
		}catch(Exception e){
			e.printStackTrace();
		}
		return model;
	}
	
	@RequestMapping(value = "/admin/item-comercial/ajax-list-by-produto", method = RequestMethod.GET)
	public ModelAndView listarByProdutoAjax(
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session,
			@RequestParam(required=true, value="idProduto") Long idProduto
	) {

		try{
			
			List<ItemComercial> itemList = null;
			itemList = itemComercialService.findByProduto(idProduto);
			if(itemList != null && itemList.size() > 0){
				Usuario usuarioLogado = (Usuario) session.getAttribute(Usuario.USUARIO_LOGADO);
				PrintWriter pw = response.getWriter();
				pw.write("{");
				pw.write("	\"itens\":[");
				int i = 1;
				for(Iterator<ItemComercial> it = itemList.iterator(); it.hasNext(); i++){
					ItemComercial item = it.next();
					pw.write("		{");
					pw.write("		\"item\":{");
					pw.write("			\"id\":" + item.getId() + ",");
					pw.write("			\"descricao\":\"" + item.getDescricao() + "\",");
					pw.write("			\"qtdEsquerda\":\"" + item.getQtdEsquerda() + "\",");
					pw.write("			\"qtdDireita\":\"" + item.getQtdDireita() + "\",");
					pw.write("			\"porcentagem\":\"" + item.getPorcentagem() + "\"");
					if(item.getFornecedor() != null){
						pw.write(",");
						pw.write("			\"fornecedor\":\"" + (item.getFornecedor() != null ? item.getFornecedor().getRazaoSocial() : "") + "\"");	
					}
					
					if(item.getUltimoApontamento() != null){
						DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
						pw.write(",");
						pw.write("			\"apontamento\":{");
						pw.write("				\"id\":" + item.getUltimoApontamento().getId() + ",");
						if(item.getUltimoApontamento().getCronogramaInicio() != null){
							pw.write("				\"cronogramaInicio\":\"" + df.format(item.getUltimoApontamento().getCronogramaInicio()) + "\",");	
						}else{
							pw.write("				\"cronogramaInicio\":\"\",");
						}
						if(item.getUltimoApontamento().getCronogramaFim() != null){
							pw.write("				\"cronogramaFim\":\"" + df.format(item.getUltimoApontamento().getCronogramaFim()) + "\",");	
						}else{
							pw.write("				\"cronogramaFim\":\"\",");
						}
						if(item.getUltimoApontamento().getPrevistoInicio() != null){
							pw.write("				\"previstoInicio\":\"" + df.format(item.getUltimoApontamento().getPrevistoInicio()) + "\",");
						}else{
							pw.write("				\"previstoInicio\":\"\",");
						}
						if(item.getUltimoApontamento().getPrevistoFim() != null){
							pw.write("				\"previstoFim\":\"" + df.format(item.getUltimoApontamento().getPrevistoFim()) + "\",");
						}else{
							pw.write("				\"previstoFim\":\"\",");
						}
						if(item.getUltimoApontamento().getRealizado() != null){
							pw.write("				\"realizado\":\"" + df.format(item.getUltimoApontamento().getRealizado()) + "\",");	
						}else{
							pw.write("				\"realizado\":\"\",");
						}
						if(item.getUltimoApontamento().getPorcentagem() != null){
							pw.write("				\"porcentagem\":" + item.getUltimoApontamento().getPorcentagem() + "");
						}else{
							pw.write("				\"porcentagem\":\"\",");
						}
						
						pw.write("			}");
					}else{
						pw.write(",");
						pw.write("			\"apontamento\":{");
						pw.write("				\"id\":\"\",");
						pw.write("				\"na\":false,");
						pw.write("				\"cronogramaInicio\":\"\",");
						pw.write("				\"cronogramaFim\":\"\",");
						pw.write("				\"previstoInicio\":\"\",");
						pw.write("				\"previstoFim\":\"\",");
						pw.write("				\"realizado\":\"\",");	
						pw.write("				\"porcentagem\":\"\"");
						pw.write("			}");
					}
					pw.write("		}");
					if(i == itemList.size()){
						pw.write("		}");
					}else{
						pw.write("		},");
					}
					
				}
				pw.write("	],");
				pw.write("	\"edit\":" + (usuarioLogado.getPerfil().getId().intValue() != Constants.PERFIL_CLIENTE.intValue() && usuarioLogado.getPerfil().getId().intValue() != Constants.PERFIL_LIDER.intValue()));
				pw.write("}");
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	@RequestMapping(value = "/admin/item-comercial/ajax-list-by-produto-and-sub-etapa", method = RequestMethod.GET)
	public ModelAndView listarByProdutoAndSubEtapaAjax(
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session,
			@RequestParam(required=true, value="idProduto") Long idProduto,
			@RequestParam(required=true, value="idSubEtapa") Long idSubEtapa
	) {

		try{
			
			List<ItemComercial> itemList = null;
			itemList = itemComercialService.findByProdutoAndSubEtapa(idProduto, idSubEtapa);
			if(itemList != null && itemList.size() > 0){
				PrintWriter pw = response.getWriter();
				pw.write("{");
				pw.write("	\"itens\":[");
				int i = 1;
				for(Iterator<ItemComercial> it = itemList.iterator(); it.hasNext(); i++){
					ItemComercial item = it.next();
					pw.write("		{");
					pw.write("		\"item\":{");
					pw.write("			\"id\":" + item.getId() + ",");
					pw.write("			\"descricao\":\"" + item.getDescricao() + "\",");
					pw.write("			\"qtdEsquerda\":\"" + item.getQtdEsquerda() + "\",");
					pw.write("			\"qtdDireita\":\"" + item.getQtdDireita() + "\"");
					if(item.getUltimoApontamento() != null){
						DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
						pw.write(",");
						pw.write("			\"apontamento\":{");
						pw.write("				\"id\":" + item.getUltimoApontamento().getId() + ",");
						pw.write("				\"na\":" + item.getUltimoApontamento().getNa() + ",");
						if(item.getUltimoApontamento().getCronogramaInicio() != null){
							pw.write("				\"cronogramaInicio\":\"" + df.format(item.getUltimoApontamento().getCronogramaInicio()) + "\",");	
						}else{
							pw.write("				\"cronogramaInicio\":\"\",");
						}
						if(item.getUltimoApontamento().getCronogramaFim() != null){
							pw.write("				\"cronogramaFim\":\"" + df.format(item.getUltimoApontamento().getCronogramaFim()) + "\",");	
						}else{
							pw.write("				\"cronogramaFim\":\"\",");
						}
						if(item.getUltimoApontamento().getPrevistoInicio() != null){
							pw.write("				\"previstoInicio\":\"" + df.format(item.getUltimoApontamento().getPrevistoInicio()) + "\",");
						}else{
							pw.write("				\"previstoInicio\":\"\",");
						}
						if(item.getUltimoApontamento().getPrevistoFim() != null){
							pw.write("				\"previstoFim\":\"" + df.format(item.getUltimoApontamento().getPrevistoFim()) + "\",");
						}else{
							pw.write("				\"previstoFim\":\"\",");
						}
						if(item.getUltimoApontamento().getRealizado() != null){
							pw.write("				\"realizado\":\"" + df.format(item.getUltimoApontamento().getRealizado()) + "\",");	
						}else{
							pw.write("				\"realizado\":\"\",");
						}
						if(item.getUltimoApontamento().getPorcentagem() != null){
							pw.write("				\"porcentagem\":" + item.getUltimoApontamento().getPorcentagem() + "");
						}else{
							pw.write("				\"porcentagem\":\"\"");
						}
						pw.write("			}");
					}else{
						pw.write(",");
						pw.write("			\"apontamento\":{");
						pw.write("				\"id\":\"\",");
						pw.write("				\"na\":false,");
						pw.write("				\"cronogramaInicio\":\"\",");
						pw.write("				\"cronogramaFim\":\"\",");
						pw.write("				\"previstoInicio\":\"\",");
						pw.write("				\"previstoFim\":\"\",");
						pw.write("				\"realizado\":\"\",");	
						pw.write("				\"porcentagem\":\"\"");
						pw.write("			}");
					}
					
					pw.write("		}");
					if(i == itemList.size()){
						pw.write("		}");
					}else{
						pw.write("		},");
					}
					
				}
				pw.write("	]");
				pw.write("}");
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	@RequestMapping(value = "/admin/item-comercial/ajax-list-by-unidade", method = RequestMethod.GET)
	public ModelAndView listarByUnidadeAjax(
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session,
			@RequestParam(required=true, value="idUnidade") Long idUnidade
	) {

		try{
			
			List<ItemComercial> itemList = null;
			itemList = itemComercialService.findByUnidade(idUnidade);
			if(itemList != null && itemList.size() > 0){
				Usuario usuarioLogado = (Usuario) session.getAttribute(Usuario.USUARIO_LOGADO);
				PrintWriter pw = response.getWriter();
				pw.write("{");
				pw.write("	\"itens\":[");
				int i = 1;
				for(Iterator<ItemComercial> it = itemList.iterator(); it.hasNext(); i++){
					ItemComercial item = it.next();
					pw.write("		{");
					pw.write("		\"item\":{");
					pw.write("			\"id\":" + item.getId() + ",");
					pw.write("			\"descricao\":\"" + item.getDescricao() + "\",");
					pw.write("			\"qtdEsquerda\":\"" + item.getQtdEsquerda() + "\",");
					pw.write("			\"qtdDireita\":\"" + item.getQtdDireita() + "\",");
					pw.write("			\"fornecedor\":\"" + (item.getFornecedor() != null ? item.getFornecedor().getRazaoSocial() : "") + "\",");
					pw.write("			\"porcentagem\":\"" + item.getPorcentagem() + "\"");
					if(item.getUltimoApontamento() != null){
						DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
						pw.write(",");
						pw.write("			\"apontamento\":{");
						pw.write("				\"id\":" + item.getUltimoApontamento().getId() + ",");
						pw.write("				\"na\":" + item.getUltimoApontamento().getNa() + ",");
						if(item.getUltimoApontamento().getCronogramaInicio() != null){
							pw.write("				\"cronogramaInicio\":\"" + df.format(item.getUltimoApontamento().getCronogramaInicio()) + "\",");	
						}else{
							pw.write("				\"cronogramaInicio\":\"\",");
						}
						if(item.getUltimoApontamento().getCronogramaFim() != null){
							pw.write("				\"cronogramaFim\":\"" + df.format(item.getUltimoApontamento().getCronogramaFim()) + "\",");	
						}else{
							pw.write("				\"cronogramaFim\":\"\",");
						}
						if(item.getUltimoApontamento().getPrevistoInicio() != null){
							pw.write("				\"previstoInicio\":\"" + df.format(item.getUltimoApontamento().getPrevistoInicio()) + "\",");
						}else{
							pw.write("				\"previstoInicio\":\"\",");
						}
						if(item.getUltimoApontamento().getPrevistoFim() != null){
							pw.write("				\"previstoFim\":\"" + df.format(item.getUltimoApontamento().getPrevistoFim()) + "\",");
						}else{
							pw.write("				\"previstoFim\":\"\",");
						}
						if(item.getUltimoApontamento().getRealizado() != null){
							pw.write("				\"realizado\":\"" + df.format(item.getUltimoApontamento().getRealizado()) + "\",");	
						}else{
							pw.write("				\"realizado\":\"\",");
						}
						if(item.getUltimoApontamento().getPorcentagem() != null){
							pw.write("				\"porcentagem\":" + item.getUltimoApontamento().getPorcentagem() + "");
						}else{
							pw.write("				\"porcentagem\":\"\"");
						}
						pw.write("			}");
					}else{
						pw.write(",");
						pw.write("			\"apontamento\":{");
						pw.write("				\"id\":\"\",");
						pw.write("				\"na\":false,");
						pw.write("				\"cronogramaInicio\":\"\",");
						pw.write("				\"cronogramaFim\":\"\",");
						pw.write("				\"previstoInicio\":\"\",");
						pw.write("				\"previstoFim\":\"\",");
						pw.write("				\"realizado\":\"\",");	
						pw.write("				\"porcentagem\":\"\"");
						pw.write("			}");
					}
					pw.write("		}");
					if(i == itemList.size()){
						pw.write("		}");
					}else{
						pw.write("		},");
					}
					
				}
				pw.write("	],");
				pw.write("	\"edit\":" + (usuarioLogado.getPerfil().getId().intValue() != Constants.PERFIL_CLIENTE.intValue() && usuarioLogado.getPerfil().getId().intValue() != Constants.PERFIL_LIDER.intValue()));
				pw.write("}");
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	@RequestMapping(value = "/admin/item-comercial/ajax-list-by-unidade-and-sub-etapa", method = RequestMethod.GET)
	public ModelAndView listarByUnidadeAndSubEtapaAjax(
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session,
			@RequestParam(required=true, value="idUnidade") Long idUnidade,
			@RequestParam(required=true, value="idSubEtapa") Long idSubEtapa
	) {

		try{
			
			List<ItemComercial> itemList = null;
			itemList = itemComercialService.findByUnidadeAndSubEtapa(idUnidade, idSubEtapa);
			if(itemList != null && itemList.size() > 0){
				PrintWriter pw = response.getWriter();
				pw.write("{");
				pw.write("	\"itens\":[");
				int i = 1;
				for(Iterator<ItemComercial> it = itemList.iterator(); it.hasNext(); i++){
					ItemComercial item = it.next();
					pw.write("		{");
					pw.write("		\"item\":{");
					pw.write("			\"id\":" + item.getId() + ",");
					pw.write("			\"descricao\":\"" + item.getDescricao() + "\",");
					pw.write("			\"qtdEsquerda\":\"" + item.getQtdEsquerda() + "\",");
					pw.write("			\"qtdDireita\":\"" + item.getQtdDireita() + "\"");
					if(item.getUltimoApontamento() != null){
						DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
						pw.write(",");
						pw.write("			\"apontamento\":{");
						pw.write("				\"id\":" + item.getUltimoApontamento().getId() + ",");
						pw.write("				\"na\":" + item.getUltimoApontamento().getNa() + ",");
						if(item.getUltimoApontamento().getCronogramaInicio() != null){
							pw.write("				\"cronogramaInicio\":\"" + df.format(item.getUltimoApontamento().getCronogramaInicio()) + "\",");	
						}else{
							pw.write("				\"cronogramaInicio\":\"\",");
						}
						if(item.getUltimoApontamento().getCronogramaFim() != null){
							pw.write("				\"cronogramaFim\":\"" + df.format(item.getUltimoApontamento().getCronogramaFim()) + "\",");	
						}else{
							pw.write("				\"cronogramaFim\":\"\",");
						}
						if(item.getUltimoApontamento().getPrevistoInicio() != null){
							pw.write("				\"previstoInicio\":\"" + df.format(item.getUltimoApontamento().getPrevistoInicio()) + "\",");
						}else{
							pw.write("				\"previstoInicio\":\"\",");
						}
						if(item.getUltimoApontamento().getPrevistoFim() != null){
							pw.write("				\"previstoFim\":\"" + df.format(item.getUltimoApontamento().getPrevistoFim()) + "\",");
						}else{
							pw.write("				\"previstoFim\":\"\",");
						}
						if(item.getUltimoApontamento().getRealizado() != null){
							pw.write("				\"realizado\":\"" + df.format(item.getUltimoApontamento().getRealizado()) + "\",");	
						}else{
							pw.write("				\"realizado\":\"\",");
						}
						if(item.getUltimoApontamento().getPorcentagem() != null){
							pw.write("				\"porcentagem\":" + item.getUltimoApontamento().getPorcentagem() + "");
						}else{
							pw.write("				\"porcentagem\":\"\"");
						}
						pw.write("			}");
					}else{
						pw.write(",");
						pw.write("			\"apontamento\":{");
						pw.write("				\"id\":\"\",");
						pw.write("				\"na\":false,");
						pw.write("				\"cronogramaInicio\":\"\",");
						pw.write("				\"cronogramaFim\":\"\",");
						pw.write("				\"previstoInicio\":\"\",");
						pw.write("				\"previstoFim\":\"\",");
						pw.write("				\"realizado\":\"\",");	
						pw.write("				\"porcentagem\":\"\"");
						pw.write("			}");
					}
					
					pw.write("		}");
					if(i == itemList.size()){
						pw.write("		}");
					}else{
						pw.write("		},");
					}
					
				}
				pw.write("	]");
				pw.write("}");
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}

	
	@RequestMapping("/admin/item-comercial/editar")
	public ModelAndView editar(
			HttpServletResponse response,
			@RequestParam(required=true, value="idItemComercial") Long id
	){
		ItemComercial item = null;
		try {
			item = this.itemComercialService.findById(id);
			PrintWriter pw = response.getWriter();
			pw.write("{");
			pw.write("	\"item\":{");
			pw.write("		\"id\":\"" + item.getId() + "\",");
			pw.write("		\"descricao\":\"" + item.getDescricao() + "\",");
			pw.write("		\"qtdEsquerda\":\"" + item.getQtdEsquerda() + "\",");
			pw.write("		\"qtdDireita\":\"" + item.getQtdDireita() + "\",");
			pw.write("		\"codigoNorma\":\"" + item.getCodigoNorma() + "\",");
			if(item.getFornecedor() != null){
				pw.write("		\"idFornecedor\":\"" + (item.getFornecedor() != null ? item.getFornecedor().getId() : 0) + "\"");				
			}
			pw.write("	}");
			pw.write("}");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
	
	@RequestMapping("/admin/item-comercial/save")
	public ModelAndView save(
			HttpSession session,
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(required=false, value="idProduto") Long idProduto,
			@RequestParam(required=false, value="idUnidade") Long idUnidade,
			@RequestParam(required=true, value="idFornecedor") Long idFornecedor,
			ItemComercial itemComercial
	) throws Exception {
		
		if(itemComercial.getId() != null && itemComercial.getId() > 0){
			itemComercial.setDataAtualizacao(new Date());
		}else{
			itemComercial.setDataCriacao(new Date());
			itemComercial.setDataAtualizacao(itemComercial.getDataCriacao());
		}
		Usuario usuarioLogado = (Usuario)session.getAttribute(Usuario.USUARIO_LOGADO);
		Usuario usuario = usuarioService.findById(usuarioLogado.getId());
		itemComercial.setUsuario(usuario);
		Fornecedor fornecedor = fornecedorService.findById(idFornecedor);
		itemComercial.setFornecedor(fornecedor);
		
		if(idUnidade != null && idUnidade.intValue() > 0){
			Unidade unidade = unidadeService.findById(idUnidade);
			itemComercial.setUnidade(unidade);
			try{
				itemComercial = this.itemComercialService.merge(itemComercial);
				response.getWriter().write("{\"itemComercial\":{\"id\":" + itemComercial.getId() + ",\"fornecedor\":\"" + (itemComercial.getFornecedor() != null ? itemComercial.getFornecedor().getRazaoSocial() : "") + "\"}}");
				
			}catch(Exception e){
				response.getWriter().write("{\"itemComercial\":{\"id\":0}}");
			}
		}else if(idProduto != null && idProduto.intValue() > 0){
			Produto produto = produtoService.findById(idProduto);
			itemComercial.setProduto(produto);
			try{
				itemComercial = this.itemComercialService.merge(itemComercial);
				response.getWriter().write("{\"itemComercial\":{\"id\":" + itemComercial.getId() + ",\"fornecedor\":\"" + (itemComercial.getFornecedor() != null ? itemComercial.getFornecedor().getRazaoSocial() : "") + "\"}}");
				
			}catch(Exception e){
				response.getWriter().write("{\"itemComercial\":{\"id\":0}}");
			}
		}
		return null;
	}
	
	@RequestMapping("/admin/item-comercial/delete")
	public ModelAndView delete(
			HttpSession session,
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(required=true, value="idItemComercial") Long idItemComercial
	) throws Exception {
		
		if(idItemComercial != null && idItemComercial.intValue() > 0){
			try{
				if(this.itemComercialService.delete(idItemComercial) > 0){
					response.getWriter().write("{\"status\":1}");
				}else{
					response.getWriter().write("{\"status\":0}}");
				}
			}catch(Exception e){
				response.getWriter().write("{\"status\":0}}");
			}
		}else{
			response.getWriter().write("{\"status\":0}}");
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
