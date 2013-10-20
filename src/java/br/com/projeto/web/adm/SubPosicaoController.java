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
import br.com.projeto.entity.Item;
import br.com.projeto.entity.Operacao;
import br.com.projeto.entity.Produto;
import br.com.projeto.entity.Projeto;
import br.com.projeto.entity.Unidade;
import br.com.projeto.entity.Usuario;
import br.com.projeto.service.ApontamentoService;
import br.com.projeto.service.FornecedorService;
import br.com.projeto.service.ItemService;
import br.com.projeto.service.OperacaoService;
import br.com.projeto.service.ProdutoService;
import br.com.projeto.service.ProjetoService;
import br.com.projeto.service.UnidadeService;
import br.com.projeto.service.UsuarioService;
import br.com.projeto.util.Constants;
import br.com.projeto.util.NivelEnum;
import br.com.projeto.util.NivelEvolucao;

@Controller
public class SubPosicaoController {
	
	@Autowired
	private ProjetoService projetoService;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private OperacaoService operacaoService;

	@Autowired
	private UnidadeService unidadeService;
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private FornecedorService fornecedorService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private ApontamentoService apontamentoService;
	
	@RequestMapping(value = "/admin/sub-posicao/list", method = RequestMethod.GET)
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
		model.setViewName("/admin/sub-posicao/list");

		if(returnMessage != null){
			model.addObject("returnMessage", returnMessage);
		}
		Projeto projeto = null;
		Produto produto = null;
		Operacao operacao = null;
		Unidade unidade = null;
		List<Projeto> projetoList = null;
		List<Produto> produtoList = null;
		List<Operacao> operacaoList = null;
		List<Unidade> unidadeList = null;
		List<Item> posicaoList = null;
		List<Fornecedor> fornecedorList = null;
		try{
			fornecedorList = this.fornecedorService.findAll();
			projetoList = this.projetoService.findAll(0L,15L);
			if(idProjeto != null && idProjeto.intValue() > 0){
				projeto = this.projetoService.findById(idProjeto);
			}else{
				if(projetoList!= null && projetoList.size() > 0){
					projeto = projetoList.get(0);
				}
			}
			model.addObject("idProjeto", projeto.getId());
			produtoList = this.produtoService.findByProjeto(projeto.getId());
			if(idProduto != null && idProduto.intValue() > 0){
				produto = this.produtoService.findById(idProduto);
			}else{
				if(produtoList!= null && produtoList.size() > 0){
					produto = produtoList.get(0);
					model.addObject("idProduto", produto.getId());
				}
			}
			operacaoList = this.operacaoService.findByProduto(produto.getId());
			if(idOperacao != null && idOperacao.intValue() > 0){
				operacao = this.operacaoService.findById(idOperacao);
			}else{
				if(operacaoList!= null && operacaoList.size() > 0){
					operacao = operacaoList.get(0);
					model.addObject("idOperacao", operacao.getId());
				}
			}
			unidadeList = this.unidadeService.findByOperacao(operacao.getId());
			if(idUnidade != null && idUnidade.intValue() > 0){
				unidade = this.unidadeService.findById(idUnidade);
			}else{
				if(unidadeList!= null && unidadeList.size() > 0){
					unidade = unidadeList.get(0);
					model.addObject("idUnidade", unidade.getId());
				}
			}
			posicaoList = itemService.findPosicoesConjuntoSoldado(unidade.getId());
			model.addObject("projetoList", projetoList);
			model.addObject("produtoList", produtoList);
			model.addObject("operacaoList", operacaoList);
			model.addObject("unidadeList", unidadeList);
			model.addObject("posicaoList", posicaoList);
			model.addObject("fornecedorList", fornecedorList);
		}catch(Exception e){
			e.printStackTrace();
		}
		return model;
	}
	
	@RequestMapping(value = "/admin/sub-posicao/ajax-list", method = RequestMethod.GET)
	public ModelAndView listarAjax(
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session,
			@RequestParam(required=true, value="idPosicao") Long idPosicao
	) {

		try{
			
			List<Item> itemList = null;
			itemList = itemService.findByPosicao(idPosicao);
			if(itemList != null && itemList.size() > 0){
				Usuario usuarioLogado = (Usuario) session.getAttribute(Usuario.USUARIO_LOGADO);
				PrintWriter pw = response.getWriter();
				pw.write("{");
				pw.write("	\"itens\":[");
				int i = 1;
				for(Iterator<Item> it = itemList.iterator(); it.hasNext(); i++){
					Item item = it.next();
					pw.write("		{");
					pw.write("		\"item\":{");
					pw.write("			\"id\":" + item.getId() + ",");
					pw.write("			\"subPosicao\":\"" + item.getPosicao() + "\",");
					pw.write("			\"descricao\":\"" + item.getDescricao() + "\",");
					pw.write("			\"qtdEsquerda\":\"" + item.getQtdEsquerda() + "\",");
					pw.write("			\"qtdDireita\":\"" + item.getQtdDireita() + "\",");
					pw.write("			\"porcentagem\":\"" + this.apontamentoService.porcentOfSubPosicao(item) + "\"");
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
	
	@RequestMapping(value = "/admin/sub-posicao/ajax-list-by-posicao", method = RequestMethod.GET)
	public ModelAndView listarByPosicaoAjax(
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session,
			@RequestParam(required=true, value="idPosicao") Long idPosicao
	) {

		try{
			
			List<Item> itemList = null;
			itemList = itemService.findSubPosicoesByPosicao(idPosicao);
			if(itemList != null && itemList.size() > 0){
				PrintWriter pw = response.getWriter();
				pw.write("{");
				pw.write("	\"itens\":[");
				int i = 1;
				for(Iterator<Item> it = itemList.iterator(); it.hasNext(); i++){
					Item item = it.next();
					pw.write("		{");
					pw.write("		\"item\":{");
					pw.write("			\"id\":" + item.getId() + ",");
					pw.write("			\"posicao\":\"" + item.getPosicao() + "\",");
					pw.write("			\"descricao\":\"" + item.getDescricao() + "\",");
					pw.write("			\"qtdEsquerda\":\"" + item.getQtdEsquerda() + "\",");
					pw.write("			\"qtdDireita\":\"" + item.getQtdDireita() + "\"");
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
						pw.write("				\"porcentagem\":" + item.getUltimoApontamento().getPorcentagem() + "");
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
	
	@RequestMapping(value = "/admin/sub-posicao/ajax-list-by-posicao-and-sub-etapa", method = RequestMethod.GET)
	public ModelAndView listarByProdutoAndSubEtapaAjax(
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session,
			@RequestParam(required=true, value="idPosicao") Long idPosicao,
			@RequestParam(required=true, value="idSubEtapa") Long idSubEtapa
	) {

		try{
			
			List<Item> itemList = null;
			itemList = itemService.findSubPosicoesByPosicaoAndSubEtapa(idPosicao, idSubEtapa);
			if(itemList != null && itemList.size() > 0){
				PrintWriter pw = response.getWriter();
				pw.write("{");
				pw.write("	\"itens\":[");
				int i = 1;
				for(Iterator<Item> it = itemList.iterator(); it.hasNext(); i++){
					Item item = it.next();
					pw.write("		{");
					pw.write("		\"item\":{");
					pw.write("			\"id\":" + item.getId() + ",");
					pw.write("			\"subPosicao\":\"" + item.getPosicao() + "\",");
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
						pw.write("				\"porcentagem\":" + item.getUltimoApontamento().getPorcentagem() + "");
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
	
	@RequestMapping("/admin/sub-posicao/editar")
	public ModelAndView editar(
			HttpServletResponse response,
			@RequestParam(required=true, value="idItem") Long id
	){
		Item item = null;
		try {
			item = this.itemService.findById(id);
			PrintWriter pw = response.getWriter();
			pw.write("{");
			pw.write("	\"item\":{");
			pw.write("		\"id\":\"" + item.getId() + "\",");
			pw.write("		\"subPosicao\":\"" + item.getPosicao() + "\",");
			pw.write("		\"descricao\":\"" + item.getDescricao() + "\",");
			pw.write("		\"qtdEsquerda\":\"" + item.getQtdEsquerda() + "\",");
			pw.write("		\"qtdDireita\":\"" + item.getQtdDireita() + "\",");
			pw.write("		\"codigoNorma\":\"" + item.getCodigoNorma() + "\",");
			pw.write("		\"material\":\"" + item.getMaterial() + "\",");
			pw.write("		\"larguraDiametro\":\"" + item.getLarguraDiametro() + "\",");
			pw.write("		\"comprimento\":\"" + item.getComprimento() + "\",");
			pw.write("		\"espessuraAlma\":\"" + item.getEspessuraAlma() + "\",");
			pw.write("		\"altura\":\"" + item.getAltura() + "\",");
			if(item.getFornecedor() != null){
				pw.write("		\"idFornecedor\":\"" + item.getFornecedor().getId() + "\",");	
			}
			pw.write("		\"observacao\":\"" + item.getObservacao() + "\"");
			pw.write("	}");
			pw.write("}");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
	
	@RequestMapping("/admin/sub-posicao/save")
	public ModelAndView save(
			HttpSession session,
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(required=true, value="idPosicao") Long idPosicao,
			@RequestParam(required=true, value="idFornecedor") Long idFornecedor,
			Item item
	) throws Exception {
		
		if(item.getId() != null && item.getId() > 0){
			item.setDataAtualizacao(new Date());
		}else{
			item.setDataCriacao(new Date());
			item.setDataAtualizacao(item.getDataCriacao());
		}
		Usuario usuarioLogado = (Usuario)session.getAttribute(Usuario.USUARIO_LOGADO);
		Usuario usuario = usuarioService.findById(usuarioLogado.getId());
		item.setUsuario(usuario);
		item.setUnidade(null);
		Fornecedor fornecedor = fornecedorService.findById(idFornecedor);
		item.setFornecedor(fornecedor);
		Item posicao = itemService.findById(idPosicao);
		item.setItemPosicao(posicao);
		item.setConjuntoSoldado(Item.CONJUNTO_SOLDADO);
		try{
			item = this.itemService.merge(item);
			response.getWriter().write("{\"item\":{\"id\":" + item.getId() + "}}");
			
		}catch(Exception e){
			response.getWriter().write("{\"item\":{\"id\":0}}");
		}

		return null;
	}
	
	@RequestMapping("/admin/sub-posicao/delete")
	public ModelAndView delete(
			HttpSession session,
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(required=true, value="idItem") Long idItem
	) throws Exception {
		
		if(idItem != null && idItem.intValue() > 0){
			try{
				if(this.itemService.delete(idItem) > 0){
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
