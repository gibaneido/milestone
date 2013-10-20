package br.com.projeto.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.projeto.dao.NivelEtapaSubEtapaDAO;
import br.com.projeto.entity.Etapa;
import br.com.projeto.entity.NivelEtapaSubEtapa;
import br.com.projeto.entity.SubEtapa;
import br.com.projeto.util.NivelEnum;

/**
 * @author gb
 */
@Repository
public class NivelEtapaSubEtapaDAOImpl implements NivelEtapaSubEtapaDAO {
	
	private EntityManager em;
	
	@PersistenceContext
	public void setEntity(EntityManager entity) {
		this.em = entity;
	}	
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<Etapa> findEtapasByNivel(NivelEnum nivel) throws NoResultException, Exception {
		Query q = em.createQuery("SELECT DISTINCT n.etapa.id, n.etapa.descricao FROM NivelEtapaSubEtapa n WHERE n.nivel = :nivel");
		q.setParameter("nivel", nivel.toString());
        List<Etapa> retorno = new ArrayList<Etapa>();
        List<Object[]> list = new ArrayList<Object[]>();
		try{
			list = q.getResultList();
			for(Object[] obj : list){
				Etapa etapa = new Etapa();
				etapa.setId((Long)obj[0]);
				etapa.setDescricao((String)obj[1]);
				retorno.add(etapa);
			}
        }catch(NoResultException nre){
        	return null;
        }catch(Exception e){
        	return null;
        }
        return retorno;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<SubEtapa> findSubEtapasByEtapaAndNivel(Long idEtapa, NivelEnum nivel) throws NoResultException, Exception {
		StringBuilder sb = new StringBuilder();
		sb.append("Select s.id, s.descricao, s.pesoEtapa ");
		sb.append("From SubEtapa s ");
		sb.append("Inner Join s.niveis n ");
		sb.append("Where s.etapa.id = :idEtapa ");
		sb.append("And n.nivel = :nivel");
		//Query q = em.createQuery("SELECT DISTINCT s FROM NivelEtapaSubEtapa n INNER JOIN FETCH n.subEtapa s WHERE n.etapa.id = :idEtapa AND n.nivel = :nivel");
		Query q = em.createQuery(sb.toString());
		q.setParameter("idEtapa", idEtapa);
		q.setParameter("nivel", nivel.toString());
        List<SubEtapa> retorno = new ArrayList<SubEtapa>();
        List<Object[]> list = new ArrayList<Object[]>();
		try{
			list = q.getResultList();
			for(Object[] obj : list){
				SubEtapa subEtapa = new SubEtapa();
				subEtapa.setId((Long)obj[0]);
				subEtapa.setDescricao((String)obj[1]);
				subEtapa.setPesoEtapa((Float)obj[2]);
				retorno.add(subEtapa);
			}
        }catch(NoResultException nre){
        	return null;
        }catch(Exception e){
        	return null;
        }
        return retorno;
	}
	
	@Transactional(readOnly=true)
	public NivelEtapaSubEtapa findBySubEtapa(Long idSubEtapa, NivelEnum nivel) throws NoResultException, Exception {
		StringBuffer sb = new StringBuffer("");
		sb.append("FROM NivelEtapaSubEtapa n ");
		sb.append("WHERE n.subEtapa.id = :idSubEtapa ");
		sb.append("AND n.nivel = :nivel");
		Query q = em.createQuery(sb.toString());
		q.setParameter("idSubEtapa", idSubEtapa);
		q.setParameter("nivel", nivel.toString());
		NivelEtapaSubEtapa retorno = new NivelEtapaSubEtapa();
		try{
			retorno = (NivelEtapaSubEtapa) q.getSingleResult();
        }catch(NoResultException nre){
        	return null;
        }catch(Exception e){
        	return null;
        }
        return retorno;
	}
}
