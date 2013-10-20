package br.com.projeto.web.adm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.projeto.entity.Empreendimento;
import br.com.projeto.entity.Perfil;
import br.com.projeto.entity.Usuario;
import br.com.projeto.service.EmpreendimentoService;
import br.com.projeto.service.PerfilService;
import br.com.projeto.service.UsuarioService;
import br.com.projeto.util.CryptUtils;

@Controller
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private PerfilService perfilService;
	
	@Autowired
	private EmpreendimentoService empreendimentoService;
	
	@RequestMapping(value = "/admin/usuario/list", method = RequestMethod.GET)
	public ModelAndView listar(
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session,
			@RequestParam(required=false, value="pageScreen") Long pageScreen,
			@RequestParam(required=false, value="returnMessage") String returnMessage
	) {

		ModelAndView model = new ModelAndView();
		model.setViewName("/admin/usuario/list");
		response.setHeader("Content-Language", "pt-BR");
		if(returnMessage != null){
			model.addObject("returnMessage", returnMessage);
		}
		
		Long registerAmount = 150L;
		Long registerNumber = 0L;
		if(pageScreen != null && pageScreen.intValue() > 0){
			registerNumber = pageScreen.intValue() * registerAmount;
		}
		Long count = 0L;
		List<Usuario> usuarioList = null;
		
		try{
			
			usuarioList = this.usuarioService.findAll(registerNumber, registerAmount);
			count = new Long(usuarioList.size());
			model.addObject("totalPages", count);
			model.addObject("usuarioList", usuarioList);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return model;
	}
	
	@RequestMapping("/admin/usuario/novo")
	public ModelAndView novo(HttpServletRequest request) {
		
		ModelAndView model = new ModelAndView();
		model.setViewName("/admin/usuario/save");
		List<Perfil> perfilList = null;
		//List<Empreendimento> empreendimentoList = null;
		
		try {
			perfilList = this.perfilService.findAll();
			model.addObject("perfilList", perfilList);
			
			//empreendimentoList = this.empreendimentoService.findAll();
			//model.addObject("empreendimentoList", empreendimentoList);
			
		} catch (NoResultException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}
	
	@RequestMapping("/admin/usuario/editar")
	public ModelAndView editar(@RequestParam(required=false, value="idUsuario") Long id) {
		ModelAndView model = new ModelAndView();
		Usuario usuario = null;
		List<Perfil> perfilList = null;
		//List<Empreendimento> empreendimentoList = null;
		try {
			usuario = this.usuarioService.findById(id);
			perfilList = this.perfilService.findAll();
			model.addObject("perfilList", perfilList);
			
			//empreendimentoList = this.empreendimentoService.findAll();
			model.addObject("empreendimentoList", empreendimentoService.find(usuario.getPerfil().getId()));
			
		} catch (Exception e) {
			e.printStackTrace();
			model.setViewName("redirect:/admin/usuario/list.jhtml");
			return model;
		}
		
		model.addObject("usuario", usuario);
		model.setViewName("/admin/usuario/save");
		
		return model;
	}
	
	@RequestMapping("/admin/usuario/save")
	public ModelAndView save(
			HttpSession session,
			HttpServletRequest request,
			@RequestParam(required=true, value="idPerfil") Long idPerfil,
			@RequestParam(required=true, value="idEmpreendimento") Long idEmpreendimento,
			Usuario usuario
	) throws Exception {
		ModelAndView model = new ModelAndView();
		model.setViewName("redirect:/admin/usuario/list.jhtml");
		
		if(usuario.getId() != null && usuario.getId() > 0){
			usuario.setDataAtualizacao(new Date());
		}else{
			usuario.setDataCriacao(new Date());
			usuario.setDataAtualizacao(usuario.getDataCriacao());
		}
		String senhaCrypt = CryptUtils.md5(usuario.getSenha());
		usuario.setSenha(senhaCrypt);
		Perfil perfil = perfilService.findById(idPerfil);
		List<Perfil> perfis = new ArrayList<Perfil>();
		perfis.add(perfil);
		usuario.setPerfis(perfis);
		Empreendimento empreendimento = empreendimentoService.findById(idEmpreendimento);
		usuario.setEmpreendimento(empreendimento);
		try{
			this.usuarioService.save(usuario);	
		}catch(Exception e){
			List<Perfil> perfilList = this.perfilService.findAll();
			model.addObject("perfilList", perfilList);
			List<Empreendimento> empreendimentoList = this.empreendimentoService.findAll();
			model.addObject("empreendimentoList", empreendimentoList);
			model.addObject("usuario",usuario);
			model.setViewName("/admin/usuario/save");
		}

		return model;
	}
	
}
