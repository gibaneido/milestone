package br.com.projeto.dao;

import java.util.List;

import javax.persistence.NoResultException;

import br.com.projeto.entity.Etapa;
import br.com.projeto.entity.NivelEtapaSubEtapa;
import br.com.projeto.entity.SubEtapa;
import br.com.projeto.util.NivelEnum;

/**
 * @author gb
 */
public interface NivelEtapaSubEtapaDAO {
	
	public List<Etapa> findEtapasByNivel(NivelEnum nivel) throws NoResultException, Exception;
	
	public List<SubEtapa> findSubEtapasByEtapaAndNivel(Long idEtapa, NivelEnum nivel) throws NoResultException, Exception;
	
	public NivelEtapaSubEtapa findBySubEtapa(Long idSubEtapa, NivelEnum nivel) throws NoResultException, Exception;
}
