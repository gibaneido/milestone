package br.com.projeto.web.adm;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.projeto.entity.Produto;
import br.com.projeto.entity.Projeto;
import br.com.projeto.service.ApontamentoService;
import br.com.projeto.service.ProdutoService;
import br.com.projeto.service.ProjetoService;

@Controller
public class ChartController {
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ProjetoService projetoService;
	
	@Autowired
	private ApontamentoService apontamentoService;
	
	@RequestMapping(value = "/cliente/chart", method = RequestMethod.GET)
	public ModelAndView chart(
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session,
			@RequestParam(required=false, value="idCliente") Long idCliente,
			@RequestParam(required=false, value="idProjeto") Long idProjeto
	) {

		ModelAndView model = new ModelAndView();
		model.setViewName("/cliente/chart");
		List<Projeto> projetoList = null;
		try{
			projetoList = this.projetoService.findByCliente(idCliente);
			Projeto projeto = null;
			if(idProjeto != null){
				projeto = projetoService.findById(idProjeto);
			}else{
				projeto = projetoList.get(0);
			}
			model.addObject("projetoList", projetoList);
			model.addObject("projeto", projeto);
			model.addObject("idCliente", idCliente);
			model.addObject("realizadoChartData", apontamentoService.findRealizadoToChart(projeto.getId()));
			model.addObject("previstoChartData", apontamentoService.findPrevistoToChart(projeto.getId()));
		}catch(Exception e){
			e.printStackTrace();
		}
		return model;
	}
	
	@RequestMapping(value = "/admin/projeto/ajax-data-chart", method = RequestMethod.GET)
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
}
