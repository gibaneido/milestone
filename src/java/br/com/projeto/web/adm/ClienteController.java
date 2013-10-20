package br.com.projeto.web.adm;

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

import br.com.projeto.entity.Cliente;
import br.com.projeto.service.ClienteService;

@Controller
public class ClienteController {
	
	@Autowired
	private ClienteService clienteService;
	
	@RequestMapping(value = "/admin/cliente/list", method = RequestMethod.GET)
	public ModelAndView listar(
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session,
			@RequestParam(required=false, value="pageScreen") Long pageScreen,
			@RequestParam(required=false, value="returnMessage") String returnMessage
	) {

		ModelAndView model = new ModelAndView();
		model.setViewName("/admin/cliente/list");

		if(returnMessage != null){
			model.addObject("returnMessage", returnMessage);
		}
		
		Long registerAmount = 15L;
		Long registerNumber = 0L;
		if(pageScreen != null && pageScreen.intValue() > 0){
			registerNumber = pageScreen.intValue() * registerAmount;
		}
		Long count = 0L;
		List<Cliente> clienteList = null;
		
		try{
			
			clienteList = this.clienteService.findAll(registerNumber, registerAmount);
			count = new Long(clienteList.size());
			model.addObject("totalPages", count);
			model.addObject("clienteList", clienteList);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return model;
	}
	
	@RequestMapping("/admin/cliente/novo")
	public ModelAndView novo(HttpServletRequest request) {
		
		ModelAndView model = new ModelAndView();
		model.setViewName("/admin/cliente/save");
		List<Cliente> clientList = null;
		
		try {
			clientList = this.clienteService.findAll();
			model.addObject("clientList", clientList);
			
		} catch (NoResultException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}
	
	@RequestMapping("/admin/cliente/editar")
	public ModelAndView editar(@RequestParam(required=false, value="idCliente") Long id) {
		ModelAndView model = new ModelAndView();
		Cliente cliente = null;
		List<Cliente> clientList = null;
		try {
			cliente = this.clienteService.findById(id);
			clientList = this.clienteService.findAll();
			
		} catch (Exception e) {
			e.printStackTrace();
			model.setViewName("redirect:/admin/cliente/list.jhtml");
			return model;
		}
		
		model.addObject("cliente", cliente);
		model.addObject("clientList", clientList);
		model.setViewName("/admin/cliente/save");
		
		return model;
	}
	
	@RequestMapping("/admin/cliente/save")
	public ModelAndView save(
			HttpSession session,
			HttpServletRequest request,
			@RequestParam(required=false, value="idCliente") Long idCliente,
			Cliente cliente
	) throws Exception {
		ModelAndView model = new ModelAndView();
		model.setViewName("redirect:/admin/cliente/list.jhtml");
		
		try{
			this.clienteService.save(cliente);	
		}catch(Exception e){
			model.addObject("cliente",cliente);
			model.setViewName("/admin/cliente/save");
		}

		return model;
	}
	
}
