package br.com.projeto.web.adm;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.NoResultException;
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

import br.com.projeto.entity.Cliente;
import br.com.projeto.entity.Etapa;
import br.com.projeto.entity.Projeto;
import br.com.projeto.entity.Usuario;
import br.com.projeto.service.ClienteService;
import br.com.projeto.service.EtapaService;
import br.com.projeto.service.ProjetoService;
import br.com.projeto.service.UsuarioService;
import br.com.projeto.util.NivelEnum;

@Controller
public class ProjetoController {
	
	@Autowired
	private ProjetoService projetoService;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private EtapaService etapaService;
	
	@RequestMapping(value = "/admin/projeto/list", method = RequestMethod.GET)
	public ModelAndView listarGTs(
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session,
			@RequestParam(required=false, value="pageScreen") Long pageScreen,
			@RequestParam(required=false, value="returnMessage") String returnMessage
	) {

		ModelAndView model = new ModelAndView();
		model.setViewName("/admin/projeto/list");

		if(returnMessage != null){
			model.addObject("returnMessage", returnMessage);
		}
		
		Long registerAmount = 15L;
		Long registerNumber = 0L;
		if(pageScreen != null && pageScreen.intValue() > 0){
			registerNumber = pageScreen.intValue() * registerAmount;
		}
		Long count = 0L;
		List<Projeto> projetoList = null;
		
		try{
			
			projetoList = this.projetoService.findAll(registerNumber, registerAmount);
			count = new Long(projetoList.size());
			model.addObject("totalPages", count);
			model.addObject("projetoList", projetoList);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return model;
	}
	
	@RequestMapping("/admin/projeto/novo")
	public ModelAndView novo(HttpServletRequest request) {
		
		ModelAndView model = new ModelAndView();
		model.setViewName("/admin/projeto/save");
		List<Cliente> clientList = null;
		
		try {
			clientList = this.clienteService.findAll();
			model.addObject("clientList", clientList);
			model.addObject("etapaList", this.etapaService.findAll());
		} catch (NoResultException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}
	
	@RequestMapping("/admin/projeto/editar")
	public ModelAndView editar(@RequestParam(required=true, value="idProjeto") Long id) {
		ModelAndView model = new ModelAndView();
		try {
			model.addObject("projeto", this.projetoService.findById(id));
			model.addObject("clientList", this.clienteService.findAll());
			model.addObject("etapaList", this.etapaService.findAll());
		} catch (Exception e) {
			e.printStackTrace();
			model.setViewName("redirect:/admin/projeto/list.jhtml");
			return model;
		}
		model.setViewName("/admin/projeto/save");
		return model;
	}
	
	@RequestMapping("/admin/projeto/save")
	public ModelAndView save(
			HttpSession session,
			HttpServletRequest request,
			@RequestParam(required=true, value="idCliente") Long idCliente,
			@RequestParam(required=true, value="idClienteFinal") Long idClienteFinal,
			@RequestParam(required=false, value="etapaIds") List<Long> etapaIds,
			Projeto projeto
	) throws Exception {
		ModelAndView model = new ModelAndView();
		model.setViewName("redirect:/admin/projeto/list.jhtml");
		
		if(projeto.getId() != null && projeto.getId() > 0){
			projeto.setDataAtualizacao(new Date());
		}else{
			String code = null;
			try{
				code = this.getNewCode(this.projetoService.findLastCode());
			}catch(Exception e){
				code = "ERR-0001";
			}
			projeto.setCodigo(code);
			projeto.setDataCriacao(new Date());
			projeto.setDataAtualizacao(projeto.getDataCriacao());
		}
		Usuario usuarioLogado = (Usuario)session.getAttribute(Usuario.USUARIO_LOGADO);
		Usuario usuario = usuarioService.findById(usuarioLogado.getId());
		projeto.setUsuario(usuario);
		Cliente cliente = clienteService.findById(idCliente);
		projeto.setCliente(cliente);
		Cliente clienteFinal = clienteService.findById(idClienteFinal);
		projeto.setClienteFinal(clienteFinal);
		List<Etapa> etapasList = new ArrayList<Etapa>();
		try{
			if(etapaIds != null){
				for(Long idEtapa : etapaIds){
					etapasList.add(this.etapaService.findById(idEtapa));
				}
				projeto.setEtapas(etapasList);				
			}
			this.projetoService.save(projeto);
		}catch(Exception e){
			List<Cliente> clientList = this.clienteService.findAll();
			model.addObject("clientList", clientList);
			model.addObject("projeto",projeto);
			model.setViewName("/admin/projeto/save");
		}

		return model;
	}
	
	@RequestMapping(value = "/admin/projeto/etapas/ajax-list", method = RequestMethod.GET)
	public ModelAndView listarAjax(
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session,
			@RequestParam(required=true, value="idProjeto") Long idProjeto,
			@RequestParam(required=true, value="nivel") NivelEnum nivel
	) {

		try{
			
			List<Etapa> etapaList = null;
			etapaList = projetoService.getEtapasByNivel(idProjeto, nivel);
			PrintWriter pw = response.getWriter();
			pw.write("{");
			pw.write("	\"etapas\":[");
			if(etapaList != null && etapaList.size() > 0){
				int i = 1;
				for(Iterator<Etapa> it = etapaList.iterator(); it.hasNext(); i++){
					Etapa etapa = it.next();
					pw.write("		{");
					pw.write("		\"etapa\":{");
					pw.write("			\"id\":" + etapa.getId() + ",");
					pw.write("			\"descricao\":\"" + etapa.getDescricao() + "\"");
					pw.write("		}");
					if(i == etapaList.size()){
						pw.write("		}");
					}else{
						pw.write("		},");
					}
				}
			}
			pw.write("	]");
			pw.write("}");			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	private String getNewCode(String lastCode){
		String newCode = null;
		if(lastCode != null && StringUtils.isNotEmpty(lastCode)){
			Integer codigo = new Integer(lastCode.substring(lastCode.lastIndexOf("-") + 1, lastCode.length()));
			newCode = new Integer(codigo.intValue() + 1).toString();
			while(newCode.length() < Projeto.CODE_SIZE){
				newCode = "0" + newCode;
			}
		}else{
			newCode = "0001";
		}
		return "GT-" + newCode;
	}
	
	@InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
    	DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    	df.setLenient(false);
    	CustomDateEditor editor = new CustomDateEditor(df, true);
    	binder.registerCustomEditor(Date.class, editor);
    }
}
