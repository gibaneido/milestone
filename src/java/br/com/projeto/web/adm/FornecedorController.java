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

import br.com.projeto.entity.Fornecedor;
import br.com.projeto.service.FornecedorService;

@Controller
public class FornecedorController {
	
	@Autowired
	private FornecedorService fornecedorService;
	
	@RequestMapping(value = "/admin/fornecedor/list", method = RequestMethod.GET)
	public ModelAndView listar(
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session,
			@RequestParam(required=false, value="pageScreen") Long pageScreen,
			@RequestParam(required=false, value="returnMessage") String returnMessage
	) {

		ModelAndView model = new ModelAndView();
		model.setViewName("/admin/fornecedor/list");

		if(returnMessage != null){
			model.addObject("returnMessage", returnMessage);
		}
		
		Long registerAmount = 15L;
		Long registerNumber = 0L;
		if(pageScreen != null && pageScreen.intValue() > 0){
			registerNumber = pageScreen.intValue() * registerAmount;
		}
		Long count = 0L;
		List<Fornecedor> fornecedorList = null;
		
		try{
			
			fornecedorList = this.fornecedorService.findAll(registerNumber, registerAmount);
			count = new Long(fornecedorList.size());
			model.addObject("totalPages", count);
			model.addObject("fornecedorList", fornecedorList);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return model;
	}
	
	@RequestMapping("/admin/fornecedor/novo")
	public ModelAndView novo(HttpServletRequest request) {
		
		ModelAndView model = new ModelAndView();
		model.setViewName("/admin/fornecedor/save");
		List<Fornecedor> clientList = null;
		
		try {
			clientList = this.fornecedorService.findAll();
			model.addObject("clientList", clientList);
			
		} catch (NoResultException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}
	
	@RequestMapping("/admin/fornecedor/editar")
	public ModelAndView editar(@RequestParam(required=false, value="idFornecedor") Long id) {
		ModelAndView model = new ModelAndView();
		Fornecedor fornecedor = null;
		List<Fornecedor> clientList = null;
		try {
			fornecedor = this.fornecedorService.findById(id);
			clientList = this.fornecedorService.findAll();
			
		} catch (Exception e) {
			e.printStackTrace();
			model.setViewName("redirect:/admin/fornecedor/list.jhtml");
			return model;
		}
		
		model.addObject("fornecedor", fornecedor);
		model.addObject("clientList", clientList);
		model.setViewName("/admin/fornecedor/save");
		
		return model;
	}
	
	@RequestMapping("/admin/fornecedor/save")
	public ModelAndView save(
			HttpSession session,
			HttpServletRequest request,
			@RequestParam(required=false, value="idFornecedor") Long idFornecedor,
			Fornecedor fornecedor
	) throws Exception {
		ModelAndView model = new ModelAndView();
		model.setViewName("redirect:/admin/fornecedor/list.jhtml");
		
		try{
			this.fornecedorService.save(fornecedor);	
		}catch(Exception e){
			model.addObject("fornecedor",fornecedor);
			model.setViewName("/admin/fornecedor/save");
		}

		return model;
	}
	
}
