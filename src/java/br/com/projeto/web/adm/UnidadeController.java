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

import br.com.projeto.entity.Operacao;
import br.com.projeto.entity.Produto;
import br.com.projeto.entity.Projeto;
import br.com.projeto.entity.Unidade;
import br.com.projeto.entity.Usuario;
import br.com.projeto.service.ApontamentoService;
import br.com.projeto.service.OperacaoService;
import br.com.projeto.service.ProdutoService;
import br.com.projeto.service.ProjetoService;
import br.com.projeto.service.UnidadeService;
import br.com.projeto.service.UsuarioService;
import br.com.projeto.util.Constants;

@Controller
public class UnidadeController {
	
	@Autowired
	private ProjetoService projetoService;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private OperacaoService operacaoService;

	@Autowired
	private UnidadeService unidadeService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private ApontamentoService apontamentoService;
	
	@RequestMapping(value = "/admin/unidade/list", method = RequestMethod.GET)
	public ModelAndView listar(
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session,
			@RequestParam(required=false, value="pageScreen") Long pageScreen,
			@RequestParam(required=false, value="returnMessage") String returnMessage,
			@RequestParam(required=false, value="idProjeto") Long idProjeto,
			@RequestParam(required=false, value="idProduto") Long idProduto
	) {
		ModelAndView model = new ModelAndView();
		model.setViewName("/admin/unidade/list");
		if(returnMessage != null){
			model.addObject("returnMessage", returnMessage);
		}
		Projeto projeto = null;
		Produto produto = null;
		List<Produto> produtoList = null;
		List<Projeto> projetoList = null;
		List<Operacao> operacaoList = null;
		try{
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
				model.addObject("idProduto", idProduto);
			}else{
				if(produtoList!= null && produtoList.size() > 0){
					produto = produtoList.get(0);
					model.addObject("idProduto", produto.getId());
				}
			}
			operacaoList = operacaoService.findByProdutoWithoutAppointment(produto.getId());
			model.addObject("projetoList", projetoList);
			model.addObject("produtoList", produtoList);
			model.addObject("operacaoList", operacaoList);
		}catch(Exception e){
			e.printStackTrace();
		}
		return model;
	}
	
	@RequestMapping(value = "/admin/unidade/ajax-list", method = RequestMethod.GET)
	public ModelAndView listarAjax(
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session,
			@RequestParam(required=true, value="idOperacao") Long idOperacao
	) {

		try{
			
			List<Unidade> unidadeList = null;
			unidadeList = unidadeService.findByOperacao(idOperacao);
			if(unidadeList != null && unidadeList.size() > 0){
				Usuario usuarioLogado = (Usuario) session.getAttribute(Usuario.USUARIO_LOGADO);
				PrintWriter pw = response.getWriter();
				pw.write("{");
				pw.write("	\"unidades\":[");
				int i = 1;
				for(Iterator<Unidade> it = unidadeList.iterator(); it.hasNext(); i++){
					Unidade unidade = it.next();
					pw.write("		{");
					pw.write("		\"unidade\":{");
					pw.write("			\"id\":" + unidade.getId() + ",");
					pw.write("			\"numero\":\"" + unidade.getNumero() + "\",");
					pw.write("			\"qtdEsquerda\":\"" + unidade.getQtdEsquerda() + "\",");
					pw.write("			\"qtdDireita\":\"" + unidade.getQtdDireita() + "\",");
					pw.write("			\"porcentagem\":\"" + this.apontamentoService.porcentOfUnidade(unidade) + "\"");
					pw.write("		}");
					if(i == unidadeList.size()){
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
	
	@RequestMapping(value = "/admin/unidade/ajax-list-by-operacao-and-sub-etapa", method = RequestMethod.GET)
	public ModelAndView listarByProdutoAndSubEtapaAjax(
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session,
			@RequestParam(required=true, value="idOperacao") Long idOperacao,
			@RequestParam(required=true, value="idSubEtapa") Long idSubEtapa
	) {

		try{
			
			List<Unidade> unidadeList = null;
			unidadeList = unidadeService.findUnidadesByOperacaoAndSubEtapa(idOperacao, idSubEtapa);
			if(unidadeList != null && unidadeList.size() > 0){
				PrintWriter pw = response.getWriter();
				pw.write("{");
				pw.write("	\"unidades\":[");
				int i = 1;
				for(Iterator<Unidade> it = unidadeList.iterator(); it.hasNext(); i++){
					Unidade unidade = it.next();
					pw.write("		{");
					pw.write("		\"unidade\":{");
					pw.write("			\"id\":" + unidade.getId() + ",");
					pw.write("			\"numero\":\"" + unidade.getNumero() + "\",");
					pw.write("			\"descricao\":\"" + unidade.getDescricao() + "\"");
					if(unidade.getUltimoApontamento() != null){
						DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
						pw.write(",");
						pw.write("			\"apontamento\":{");
						pw.write("				\"id\":" + unidade.getUltimoApontamento().getId() + ",");
						pw.write("				\"na\":" + unidade.getUltimoApontamento().getNa() + ",");
						if(unidade.getUltimoApontamento().getCronogramaInicio() != null){
							pw.write("				\"cronogramaInicio\":\"" + df.format(unidade.getUltimoApontamento().getCronogramaInicio()) + "\",");	
						}else{
							pw.write("				\"cronogramaInicio\":\"\",");
						}
						if(unidade.getUltimoApontamento().getCronogramaFim() != null){
							pw.write("				\"cronogramaFim\":\"" + df.format(unidade.getUltimoApontamento().getCronogramaFim()) + "\",");	
						}else{
							pw.write("				\"cronogramaFim\":\"\",");
						}
						if(unidade.getUltimoApontamento().getPrevistoInicio() != null){
							pw.write("				\"previstoInicio\":\"" + df.format(unidade.getUltimoApontamento().getPrevistoInicio()) + "\",");
						}else{
							pw.write("				\"previstoInicio\":\"\",");
						}
						if(unidade.getUltimoApontamento().getPrevistoFim() != null){
							pw.write("				\"previstoFim\":\"" + df.format(unidade.getUltimoApontamento().getPrevistoFim()) + "\",");
						}else{
							pw.write("				\"previstoFim\":\"\",");
						}
						if(unidade.getUltimoApontamento().getRealizado() != null){
							pw.write("				\"realizado\":\"" + df.format(unidade.getUltimoApontamento().getRealizado()) + "\",");	
						}else{
							pw.write("				\"realizado\":\"\",");
						}
						if(unidade.getUltimoApontamento().getPorcentagem() != null){
							pw.write("				\"porcentagem\":" + unidade.getUltimoApontamento().getPorcentagem() + "");	
						}else{
							pw.write("				\"porcentagem\":\"0\"");
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
					if(i == unidadeList.size()){
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
	
	@RequestMapping("/admin/unidade/editar")
	public ModelAndView editar(
			HttpServletResponse response,
			@RequestParam(required=true, value="idUnidade") Long id
	){
		Unidade unidade = null;
		try {
			unidade = this.unidadeService.findById(id);
			PrintWriter pw = response.getWriter();
			pw.write("{");
			pw.write("	\"unidade\":{");
			pw.write("		\"id\":\"" + unidade.getId() + "\",");
			pw.write("		\"numero\":\"" + unidade.getNumero() + "\",");
			pw.write("		\"descricao\":\"" + unidade.getDescricao() + "\",");
			pw.write("		\"qtdEsquerda\":\"" + unidade.getQtdEsquerda() + "\",");
			pw.write("		\"qtdDireita\":\"" + unidade.getQtdDireita() + "\"");
			pw.write("	}");
			pw.write("}");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
	
	@RequestMapping("/admin/unidade/save")
	public ModelAndView save(
			HttpSession session,
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(required=true, value="idOperacao") Long idOperacao,
			Unidade unidade
	) throws Exception {
		
		if(unidade.getId() != null && unidade.getId() > 0){
			unidade.setDataAtualizacao(new Date());
		}else{
			unidade.setDataCriacao(new Date());
			unidade.setDataAtualizacao(unidade.getDataCriacao());
		}
		Usuario usuarioLogado = (Usuario)session.getAttribute(Usuario.USUARIO_LOGADO);
		Usuario usuario = usuarioService.findById(usuarioLogado.getId());
		unidade.setUsuario(usuario);
		Operacao operacao = operacaoService.findById(idOperacao);
		unidade.setOperacao(operacao);
		try{
			unidade = this.unidadeService.merge(unidade);
			response.getWriter().write("{\"unidade\":{\"id\":" + unidade.getId() + "}}");
			
		}catch(Exception e){
			response.getWriter().write("{\"unidade\":{\"id\":0}}");
		}

		return null;
	}
	
	@RequestMapping("/admin/unidade/delete")
	public ModelAndView delete(
			HttpSession session,
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(required=true, value="idUnidade") Long idUnidade
	) throws Exception {
		
		if(idUnidade != null && idUnidade.intValue() > 0){
			try{
				if(this.unidadeService.delete(idUnidade) > 0){
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
