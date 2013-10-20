package br.com.projeto.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.projeto.dao.DAO;
import br.com.projeto.dao.NivelEtapaSubEtapaDAO;
import br.com.projeto.entity.Etapa;
import br.com.projeto.entity.NivelEtapaSubEtapa;
import br.com.projeto.entity.SubEtapa;
import br.com.projeto.util.NivelEnum;

/**
 * @author gb
 */

@Service
public class NivelEtapaSubEtapaService {
	
	private final NivelEtapaSubEtapaDAO dao;
	
	private final DAO<NivelEtapaSubEtapa, Long> genericDAO;

	private static final Logger LOGGER = Logger.getLogger(NivelEtapaSubEtapaService.class);
	 
	@Autowired
	public NivelEtapaSubEtapaService(NivelEtapaSubEtapaDAO dao, DAO<NivelEtapaSubEtapa, Long> genericDAO)	{
		this.dao = dao;
		this.genericDAO = genericDAO;
	}
	 
	public List<Etapa> findEtapasByNivel(NivelEnum nivel) throws Exception{
		List<Etapa> pageList = null;
		try{
			pageList = dao.findEtapasByNivel(nivel);
		}catch(Exception e){
			LOGGER.error(e);
			throw e;
		}
		return pageList;
	}
	
	public List<SubEtapa> findSubEtapasByEtapaAndNivel(Long idEtapa, NivelEnum nivel) throws Exception{
		List<SubEtapa> pageList = null;
		try{
			pageList = dao.findSubEtapasByEtapaAndNivel(idEtapa, nivel);
		}catch(Exception e){
			LOGGER.error(e);
			throw e;
		}
		return pageList;
	}
	
	public NivelEtapaSubEtapa findById(Long id) throws Exception{
		NivelEtapaSubEtapa nivel = null;
		try {
			nivel = this.genericDAO.findById(NivelEtapaSubEtapa.class, id);
		} catch (SecurityException e) {
			LOGGER.error(e);
			throw new Exception(e);
		} catch (Exception e) {
			LOGGER.error(e);
			throw new Exception(e);
		}
		return nivel;
	}
	
	public NivelEtapaSubEtapa findBySubEtapa(Long idSubEtapa, NivelEnum nivel) throws Exception{
		try{
			return dao.findBySubEtapa(idSubEtapa, nivel);
		}catch(Exception e){
			LOGGER.error(e);
			throw e;
		}
	}
}
