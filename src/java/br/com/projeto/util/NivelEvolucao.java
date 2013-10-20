package br.com.projeto.util;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.context.ApplicationContext;

import br.com.projeto.entity.Etapa;
import br.com.projeto.entity.SubEtapa;
import br.com.projeto.service.NivelEtapaSubEtapaService;
import br.com.projeto.util.spring.AppContext;

public class NivelEvolucao {
	
	public static Map<NivelEnum, List<Etapa>> nivel = new TreeMap<NivelEnum, List<Etapa>>();

	static {
		ApplicationContext ctx = AppContext.getApplicationContext();
		NivelEtapaSubEtapaService service = ctx.getBean(NivelEtapaSubEtapaService.class);
		for(NivelEnum nivelEnum : NivelEnum.values()){
			List<Etapa> etapas = null;
			try {
				etapas = service.findEtapasByNivel(nivelEnum);
				for(Etapa etapa: etapas){
					etapa.setSubEtapas(service.findSubEtapasByEtapaAndNivel(etapa.getId(), nivelEnum));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			nivel.put(nivelEnum, etapas);
		}
	}
	
	public static List<Etapa> getEtapasByNivel(NivelEnum nivelEnum){
		return nivel.get(nivelEnum);
	}
	
	public static List<SubEtapa> getSubEtapasByEtapaAndNivel(Long idEtapa, NivelEnum nivelEnum){
		for(Etapa etapa : nivel.get(nivelEnum)){
			if(etapa.getId().equals(idEtapa)){
				return etapa.getSubEtapas();
			}
		}
		return null;
	}
	
	public static SubEtapa getSubEtapaByEtapaAndNivel(Long idEtapa, Long idSubEtapa, NivelEnum nivelEnum){
		for(Etapa etapa : nivel.get(nivelEnum)){
			if(etapa.getId().equals(idEtapa)){
				for(SubEtapa subEtapa : etapa.getSubEtapas()){
					if(subEtapa.getId().equals(idSubEtapa))
						return subEtapa;
				}
			}
		}
		return null;
	}
	
	public static int count(NivelEnum nivelEnum){
		int count = 0;
		for(Etapa etapa : nivel.get(nivelEnum)){
			count += etapa.getSubEtapas().size();
		}
		return count;
	}
}
