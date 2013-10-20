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
import br.com.projeto.entity.Usuario;
import br.com.projeto.service.OperacaoService;
import br.com.projeto.service.ProdutoService;
import br.com.projeto.service.ProjetoService;
import br.com.projeto.service.UsuarioService;
import br.com.projeto.util.Constants;

@Controller
public class OperacaoController {
	
	@Autowired
	private ProjetoService projetoService;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private OperacaoService operacaoService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@RequestMapping(value = "/admin/operacao/list", method = RequestMethod.GET)
	public ModelAndView listar(
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session,
			@RequestParam(required=false, value="pageScreen") Long pageScreen,
			@RequestParam(required=false, value="returnMessage") String returnMessage,
			@RequestParam(required=false, value="idProjeto") Long idProjeto
	) {
		ModelAndView model = new ModelAndView();
		model.setViewName("/admin/operacao/list");
		if(returnMessage != null){
			model.addObject("returnMessage", returnMessage);
		}
		Projeto projeto = null;
		List<Produto> produtoList = null;
		List<Projeto> projetoList = null;
		try{
			projetoList = this.projetoService.findAll(0L,15L);
			if(idProjeto != null && idProjeto.intValue() > 0){
				projeto = this.projetoService.findById(idProjeto);
				model.addObject("idProjeto", idProjeto);
			}else{
				if(projetoList!= null && projetoList.size() > 0){
					projeto = projetoList.get(0);
					model.addObject("idProjeto", projeto.getId());
				}
			}
			produtoList = this.produtoService.findByProjetoWithoutAppointment(projeto.getId());
			model.addObject("projetoList", projetoList);
			model.addObject("produtoList", produtoList);
		}catch(Exception e){
			e.printStackTrace();
		}
		return model;
	}
	
	@RequestMapping(value = "/admin/operacao/ajax-list", method = RequestMethod.GET)
	public ModelAndView listarAjax(
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session,
			@RequestParam(required=true, value="idProduto") Long idProduto
	) {

		try{
			
			List<Operacao> operacaoList = null;
			operacaoList = operacaoService.findByProduto(idProduto);
			if(operacaoList != null && operacaoList.size() > 0){
				Usuario usuarioLogado = (Usuario) session.getAttribute(Usuario.USUARIO_LOGADO);
				PrintWriter pw = response.getWriter();
				pw.write("{");
				pw.write("	\"operacoes\":[");
				int i = 1;
				for(Iterator<Operacao> it = operacaoList.iterator(); it.hasNext(); i++){
					Operacao operacao = it.next();
					pw.write("		{");
					pw.write("		\"operacao\":{");
					pw.write("			\"id\":" + operacao.getId() + ",");
					pw.write("			\"op\":\"" + operacao.getOp() + "\",");
					pw.write("			\"descricao\":\"" + operacao.getDescricao() + "\",");
					pw.write("			\"numeroDesenho\":\"" + operacao.getNumeroDesenho() + "\",");
					pw.write("			\"qtdEsquerda\":\"" + operacao.getQtdEsquerda() + "\",");
					pw.write("			\"qtdDireita\":\"" + operacao.getQtdDireita() + "\",");
					pw.write("			\"porcentagem\":\"" + operacao.getPorcentagem() + "\"");
					pw.write("		}");
					if(i == operacaoList.size()){
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
	
	@RequestMapping(value = "/admin/operacao/ajax-list-by-produto-and-sub-etapa", method = RequestMethod.GET)
	public ModelAndView listarByProdutoAndSubEtapaAjax(
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session,
			@RequestParam(required=true, value="idProduto") Long idProduto,
			@RequestParam(required=true, value="idSubEtapa") Long idSubEtapa
	) {

		try{
			
			List<Operacao> operacaoList = null;
			operacaoList = operacaoService.findOperacoesByProdutoAndSubEtapa(idProduto, idSubEtapa);
			if(operacaoList != null && operacaoList.size() > 0){
				PrintWriter pw = response.getWriter();
				pw.write("{");
				pw.write("	\"operacoes\":[");
				int i = 1;
				for(Iterator<Operacao> it = operacaoList.iterator(); it.hasNext(); i++){
					Operacao operacao = it.next();
					pw.write("		{");
					pw.write("		\"operacao\":{");
					pw.write("			\"id\":" + operacao.getId() + ",");
					pw.write("			\"op\":\"" + operacao.getOp() + "\",");
					pw.write("			\"descricao\":\"" + operacao.getDescricao() + "\"");
					if(operacao.getUltimoApontamento() != null){
						DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
						pw.write(",");
						pw.write("			\"apontamento\":{");
						pw.write("				\"id\":" + operacao.getUltimoApontamento().getId() + ",");
						pw.write("				\"na\":" + operacao.getUltimoApontamento().getNa() + ",");
						if(operacao.getUltimoApontamento().getCronogramaInicio() != null){
							pw.write("				\"cronogramaInicio\":\"" + df.format(operacao.getUltimoApontamento().getCronogramaInicio()) + "\",");	
						}else{
							pw.write("				\"cronogramaInicio\":\"\",");
						}
						if(operacao.getUltimoApontamento().getCronogramaFim() != null){
							pw.write("				\"cronogramaFim\":\"" + df.format(operacao.getUltimoApontamento().getCronogramaFim()) + "\",");	
						}else{
							pw.write("				\"cronogramaFim\":\"\",");
						}
						if(operacao.getUltimoApontamento().getPrevistoInicio() != null){
							pw.write("				\"previstoInicio\":\"" + df.format(operacao.getUltimoApontamento().getPrevistoInicio()) + "\",");
						}else{
							pw.write("				\"previstoInicio\":\"\",");
						}
						if(operacao.getUltimoApontamento().getPrevistoFim() != null){
							pw.write("				\"previstoFim\":\"" + df.format(operacao.getUltimoApontamento().getPrevistoFim()) + "\",");
						}else{
							pw.write("				\"previstoFim\":\"\",");
						}
						if(operacao.getUltimoApontamento().getRealizado() != null){
							pw.write("				\"realizado\":\"" + df.format(operacao.getUltimoApontamento().getRealizado()) + "\",");	
						}else{
							pw.write("				\"realizado\":\"\",");
						}
						if(operacao.getUltimoApontamento().getPorcentagem() != null){
							pw.write("				\"porcentagem\":" + operacao.getUltimoApontamento().getPorcentagem() + "");	
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
					if(i == operacaoList.size()){
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
	
	@RequestMapping("/admin/operacao/editar")
	public ModelAndView editar(
			HttpServletResponse response,
			@RequestParam(required=true, value="idOperacao") Long id
	) {
		Operacao operacao = null;
		try {
			operacao = this.operacaoService.findById(id);
			PrintWriter pw = response.getWriter();
			pw.write("{");
			pw.write("	\"operacao\":{");
			pw.write("		\"id\":\"" + operacao.getId() + "\",");
			pw.write("		\"op\":\"" + operacao.getOp() + "\",");
			pw.write("		\"descricao\":\"" + operacao.getDescricao() + "\",");
			pw.write("		\"numeroDesenho\":\"" + operacao.getNumeroDesenho() + "\",");
			pw.write("		\"qtdEsquerda\":\"" + operacao.getQtdEsquerda() + "\",");
			pw.write("		\"qtdDireita\":\"" + operacao.getQtdDireita() + "\"");
			pw.write("	}");
			pw.write("}");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return null;
	}
	
	@RequestMapping("/admin/operacao/save")
	public ModelAndView save(
			HttpSession session,
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(required=true, value="idProduto") Long idProduto,
			Operacao operacao
	) throws Exception {
		
		if(operacao.getId() != null && operacao.getId() > 0){
			operacao.setDataAtualizacao(new Date());
		}else{
			operacao.setDataCriacao(new Date());
			operacao.setDataAtualizacao(operacao.getDataCriacao());
		}
		Usuario usuarioLogado = (Usuario)session.getAttribute(Usuario.USUARIO_LOGADO);
		Usuario usuario = usuarioService.findById(usuarioLogado.getId());
		operacao.setUsuario(usuario);
		Produto produto = produtoService.findById(idProduto);
		operacao.setProduto(produto);
		try{
			operacao = this.operacaoService.merge(operacao);
			response.getWriter().write("{\"operacao\":{\"id\":" + operacao.getId() + "}}");
			
		}catch(Exception e){
			response.getWriter().write("{\"operacao\":{\"id\":0}}");
		}

		return null;
	}
	
	@RequestMapping("/admin/operacao/delete")
	public ModelAndView delete(
			HttpSession session,
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(required=true, value="idOperacao") Long idOperacao
	) throws Exception {
		
		if(idOperacao != null && idOperacao.intValue() > 0){
			try{
				if(this.operacaoService.delete(idOperacao) > 0){
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
