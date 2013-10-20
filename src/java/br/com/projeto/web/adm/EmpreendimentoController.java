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

import br.com.projeto.entity.Empreendimento;
import br.com.projeto.service.EmpreendimentoService;

@Controller
public class EmpreendimentoController {
	
	@Autowired
	private EmpreendimentoService empreendimentoService;
	
	@RequestMapping(value = "/admin/empreendimento/ajax-list", method = RequestMethod.GET)
	public ModelAndView listarAjax(
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session,
			@RequestParam(required=true, value="idPerfil") Long idPerfil
	) {

		try{
			
			List<Empreendimento> empreendimentoList = null;
			empreendimentoList = empreendimentoService.find(idPerfil);
			response.setCharacterEncoding("UTF-8");
			PrintWriter pw = response.getWriter();
			pw.write("{");
			pw.write("	\"empreendimentos\":[");
			if(empreendimentoList != null && empreendimentoList.size() > 0){
				int i = 1;
				for(Iterator<Empreendimento> it = empreendimentoList.iterator(); it.hasNext(); i++){
					Empreendimento empreendimento = it.next();
					pw.write("		{");
					pw.write("		\"empreendimento\":{");
					pw.write("			\"id\":" + empreendimento.getId() + ",");
					pw.write("			\"descricao\":\"" + empreendimento.getDescricao() + "\"");
					pw.write("		}");
					if(i == empreendimentoList.size()){
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
}
