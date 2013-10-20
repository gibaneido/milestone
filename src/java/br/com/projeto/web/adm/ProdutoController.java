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

import br.com.projeto.entity.Produto;
import br.com.projeto.entity.Projeto;
import br.com.projeto.entity.Usuario;
import br.com.projeto.service.ProdutoService;
import br.com.projeto.service.ProjetoService;
import br.com.projeto.service.UsuarioService;
import br.com.projeto.util.Constants;

@Controller
public class ProdutoController {
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ProjetoService projetoService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@RequestMapping(value = "/admin/produto/list", method = RequestMethod.GET)
	public ModelAndView listar(
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session,
			@RequestParam(required=false, value="pageScreen") Long pageScreen,
			@RequestParam(required=false, value="returnMessage") String returnMessage
	) {

		ModelAndView model = new ModelAndView();
		model.setViewName("/admin/produto/list");

		if(returnMessage != null){
			model.addObject("returnMessage", returnMessage);
		}
		List<Projeto> projetoList = null;
		try{
			projetoList = this.projetoService.findAll(0L,15L);
			model.addObject("projetoList", projetoList);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return model;
	}
	
	@RequestMapping(value = "/admin/produto/ajax-list", method = RequestMethod.GET)
	public ModelAndView listarAjax(
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session,
			@RequestParam(required=true, value="idProjeto") Long idProjeto
	) {

		try{
			
			List<Produto> produtoList = null;
			produtoList = produtoService.findByProjeto(idProjeto);
			if(produtoList != null && produtoList.size() > 0){
				Usuario usuarioLogado = (Usuario) session.getAttribute(Usuario.USUARIO_LOGADO);
				PrintWriter pw = response.getWriter();
				pw.write("{");
				pw.write("	\"produtos\":[");
				int i = 1;
				for(Iterator<Produto> it = produtoList.iterator(); it.hasNext(); i++){
					Produto produto = it.next();
					pw.write("		{");
					pw.write("		\"produto\":{");
					pw.write("			\"id\":" + produto.getId() + ",");
					pw.write("			\"descricao\":\"" + produto.getDescricao() + "\",");
					pw.write("			\"numero\":\"" + produto.getNumero() + "\",");
					pw.write("			\"porcentagem\":\"" + produto.getPorcentagem() + "\"");
					pw.write("		}");
					if(i == produtoList.size()){
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
	
	@RequestMapping(value = "/admin/produto/ajax-list-by-projeto-and-sub-etapa", method = RequestMethod.GET)
	public ModelAndView listarByProdutoAndSubEtapaAjax(
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session,
			@RequestParam(required=true, value="idProjeto") Long idProjeto,
			@RequestParam(required=true, value="idSubEtapa") Long idSubEtapa
	) {

		try{
			
			List<Produto> produtoList = null;
			produtoList = produtoService.findProdutosByProjetoAndSubEtapa(idProjeto, idSubEtapa);
			if(produtoList != null && produtoList.size() > 0){
				PrintWriter pw = response.getWriter();
				pw.write("{");
				pw.write("	\"produtos\":[");
				int i = 1;
				for(Iterator<Produto> it = produtoList.iterator(); it.hasNext(); i++){
					Produto produto = it.next();
					pw.write("		{");
					pw.write("		\"produto\":{");
					pw.write("			\"id\":" + produto.getId() + ",");
					pw.write("			\"descricao\":\"" + produto.getDescricao() + "\",");
					pw.write("			\"numero\":\"" + produto.getNumero() + "\"");
					if(produto.getUltimoApontamento() != null){
						DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
						pw.write(",");
						pw.write("			\"apontamento\":{");
						pw.write("				\"id\":" + produto.getUltimoApontamento().getId() + ",");
						pw.write("				\"na\":" + produto.getUltimoApontamento().getNa() + ",");
						if(produto.getUltimoApontamento().getCronogramaInicio() != null){
							pw.write("				\"cronogramaInicio\":\"" + df.format(produto.getUltimoApontamento().getCronogramaInicio()) + "\",");	
						}else{
							pw.write("				\"cronogramaInicio\":\"\",");
						}
						if(produto.getUltimoApontamento().getCronogramaFim() != null){
							pw.write("				\"cronogramaFim\":\"" + df.format(produto.getUltimoApontamento().getCronogramaFim()) + "\",");	
						}else{
							pw.write("				\"cronogramaFim\":\"\",");
						}
						if(produto.getUltimoApontamento().getPrevistoInicio() != null){
							pw.write("				\"previstoInicio\":\"" + df.format(produto.getUltimoApontamento().getPrevistoInicio()) + "\",");
						}else{
							pw.write("				\"previstoInicio\":\"\",");
						}
						if(produto.getUltimoApontamento().getPrevistoFim() != null){
							pw.write("				\"previstoFim\":\"" + df.format(produto.getUltimoApontamento().getPrevistoFim()) + "\",");
						}else{
							pw.write("				\"previstoFim\":\"\",");
						}
						if(produto.getUltimoApontamento().getRealizado() != null){
							pw.write("				\"realizado\":\"" + df.format(produto.getUltimoApontamento().getRealizado()) + "\",");	
						}else{
							pw.write("				\"realizado\":\"\",");
						}
						if(produto.getUltimoApontamento().getPorcentagem() != null){
							pw.write("				\"porcentagem\":" + produto.getUltimoApontamento().getPorcentagem() + "");	
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
					if(i == produtoList.size()){
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
	
	@RequestMapping("/admin/produto/editar")
	public ModelAndView editar(
			HttpServletResponse response,
			@RequestParam(required=true, value="idProduto") Long id
	){
		Produto produto = null;
		try {
			produto = this.produtoService.findById(id);
			PrintWriter pw = response.getWriter();
			pw.write("{");
			pw.write("	\"produto\":{");
			pw.write("		\"id\":\"" + produto.getId() + "\",");
			pw.write("		\"descricao\":\"" + produto.getDescricao() + "\",");
			pw.write("		\"numero\":\"" + produto.getNumero() + "\"");
			pw.write("	}");
			pw.write("}");
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
	
	@RequestMapping("/admin/produto/save")
	public ModelAndView save(
			HttpSession session,
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(required=true, value="idProjeto") Long idProjeto,
			Produto produto
	) throws Exception {
		
		if(produto.getId() != null && produto.getId() > 0){
			produto.setDataAtualizacao(new Date());
		}else{
			produto.setDataCriacao(new Date());
			produto.setDataAtualizacao(produto.getDataCriacao());
		}
		Usuario usuarioLogado = (Usuario)session.getAttribute(Usuario.USUARIO_LOGADO);
		Usuario usuario = usuarioService.findById(usuarioLogado.getId());
		produto.setUsuario(usuario);
		Projeto projeto = projetoService.findById(idProjeto);
		produto.setProjeto(projeto);
		try{
			produto = this.produtoService.merge(produto);
			response.getWriter().write("{\"produto\":{\"id\":" + produto.getId() + "}}");
			
		}catch(Exception e){
			response.getWriter().write("{\"produto\":{\"id\":0}}");
		}

		return null;
	}
	
	@RequestMapping("/admin/produto/delete")
	public ModelAndView delete(
			HttpSession session,
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(required=true, value="idProduto") Long idProduto
	) throws Exception {
		
		if(idProduto != null && idProduto.intValue() > 0){
			try{
				if(this.produtoService.delete(idProduto) > 0){
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
