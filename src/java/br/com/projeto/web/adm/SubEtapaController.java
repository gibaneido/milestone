package br.com.projeto.web.adm;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.projeto.entity.SubEtapa;
import br.com.projeto.util.NivelEnum;
import br.com.projeto.util.NivelEvolucao;

@Controller
public class SubEtapaController {
	
	//@Autowired
	//private SubEtapaService subEtapaService;
	
	@RequestMapping(value = "/admin/sub-etapa/ajax-list-by-etapa", method = RequestMethod.GET)
	public ModelAndView listarAjax(
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session,
			@RequestParam(required=true, value="idEtapa") Long idEtapa,
			@RequestParam(required=true, value="nivel") NivelEnum nivel
	) {

		try{
			List<SubEtapa> subEtapaList = null;
			//subEtapaList = subEtapaService.findByEtapa(idEtapa);
			subEtapaList = NivelEvolucao.getSubEtapasByEtapaAndNivel(idEtapa, nivel);
			
			if(subEtapaList != null && subEtapaList.size() > 0){
				PrintWriter pw = response.getWriter();
				pw.write("{");
				pw.write("	\"subEtapas\":[");
				int i = 1;
				for(Iterator<SubEtapa> it = subEtapaList.iterator(); it.hasNext(); i++){
					SubEtapa subEtapa = it.next();
					pw.write("		{");
					pw.write("		\"subEtapa\":{");
					pw.write("			\"id\":" + subEtapa.getId() + ",");
					pw.write("			\"descricao\":\"" + subEtapa.getDescricao() + "\"");
					pw.write("		}");
					if(i == subEtapaList.size()){
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
