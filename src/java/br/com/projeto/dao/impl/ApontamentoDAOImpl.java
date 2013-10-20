package br.com.projeto.dao.impl;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.projeto.dao.ApontamentoDAO;
import br.com.projeto.entity.Apontamento;
import br.com.projeto.entity.Item;
import br.com.projeto.entity.Unidade;
import br.com.projeto.util.NivelEnum;
import br.com.projeto.vo.ChartEtapaVO;

/**
 * Custom DAO example class
 * @author gb
 *
 */
@Repository
public class ApontamentoDAOImpl implements ApontamentoDAO {
	
	private EntityManager em;
	
	@PersistenceContext
	public void setEntity(EntityManager entity) {
		this.em = entity;
	}	
	
	@Transactional
	public void save(Apontamento apontamento)	{
		if (apontamento.getId() == null)	{
			this.em.persist(apontamento);
		}	else	{
			this.em.merge(apontamento);
		}
	}
	
	@Transactional
	public int delete(Long idApontamento)	{
		Query q = em.createQuery("DELETE FROM Apontamento a WHERE a.id = :idApontamento");
		q.setParameter("idApontamento", idApontamento);
        int retorno = 0;
		try{
			retorno = q.executeUpdate();
        	
        }catch(NoResultException nre){
        	return 0;
        }catch(Exception e){
        	return 0;
        }
		return retorno;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<Apontamento> findAll() throws NoResultException, Exception {
		Query q = em.createQuery("SELECT a FROM Apontamento a");
        List<Apontamento> pagesList = null;
		try{
			pagesList = q.getResultList();
        	
        }catch(NoResultException nre){
        	return null;
        }catch(Exception e){
        	return null;
        }
        
        return pagesList;
	}

	@Transactional(readOnly=true)
	public Apontamento findLastByProduto(Long idProduto, Long idSubEtapa) throws NoResultException, Exception {
		StringBuffer sb = new StringBuffer("");
		sb.append("SELECT a ");
		sb.append("FROM Apontamento a ");
		sb.append("INNER JOIN a.nivel n ");
		sb.append("WHERE a.produto.id = :idProduto ");
		sb.append("AND a.subEtapa.id = :idSubEtapa ");
		sb.append("AND n.nivel = :produto ");
		sb.append("ORDER BY a.dataCriacao DESC");
		Query q = em.createQuery(sb.toString());
		q.setParameter("idProduto", idProduto);
		q.setParameter("idSubEtapa", idSubEtapa);
		q.setParameter("produto", NivelEnum.PD.toString());
		q.setMaxResults(1);
        Apontamento apontamento = null;
		try{
			apontamento = (Apontamento) q.getSingleResult();
        	
        }catch(NoResultException nre){
        	return null;
        }catch(Exception e){
        	return null;
        }
        
        return apontamento;
	}
	
	@Transactional(readOnly=true)
	public Apontamento findLastByOperacao(Long idOperacao, Long idSubEtapa) throws NoResultException, Exception {
		StringBuffer sb = new StringBuffer("");
		sb.append("SELECT a ");
		sb.append("FROM Apontamento a ");
		sb.append("INNER JOIN a.nivel n ");
		sb.append("WHERE a.operacao.id = :idOperacao ");
		sb.append("AND a.subEtapa.id = :idSubEtapa ");
		sb.append("AND n.nivel = :operacao ");
		sb.append("ORDER BY a.dataCriacao DESC");
		Query q = em.createQuery(sb.toString());
		q.setParameter("idOperacao", idOperacao);
		q.setParameter("idSubEtapa", idSubEtapa);
		q.setParameter("operacao", NivelEnum.OP.toString());
		q.setMaxResults(1);
        Apontamento apontamento = null;
		try{
			apontamento = (Apontamento) q.getSingleResult();
        	
        }catch(NoResultException nre){
        	return null;
        }catch(Exception e){
        	return null;
        }
        
        return apontamento;
	}
	
	@Transactional(readOnly=true)
	public Apontamento findLastByUnidade(Long idUnidade, Long idSubEtapa) throws NoResultException, Exception {
		StringBuffer sb = new StringBuffer("");
		sb.append("SELECT a ");
		sb.append("FROM Apontamento a ");
		sb.append("INNER JOIN a.nivel n ");
		sb.append("WHERE a.unidade.id = :idUnidade ");
		sb.append("AND a.subEtapa.id = :idSubEtapa ");
		sb.append("AND n.nivel = :unidade ");
		sb.append("ORDER BY a.dataCriacao DESC");
		Query q = em.createQuery(sb.toString());
		q.setParameter("idUnidade", idUnidade);
		q.setParameter("idSubEtapa", idSubEtapa);
		q.setParameter("unidade", NivelEnum.UN.toString());
		q.setMaxResults(1);
        Apontamento apontamento = null;
		try{
			apontamento = (Apontamento) q.getSingleResult();
        	
        }catch(NoResultException nre){
        	return null;
        }catch(Exception e){
        	return null;
        }
        
        return apontamento;
	}
	
	@Transactional(readOnly=true)
	public Apontamento findLastByItemComercial(Long idItemComercial, Long idSubEtapa) throws NoResultException, Exception {
		StringBuffer sb = new StringBuffer("");
		sb.append("SELECT a ");
		sb.append("FROM Apontamento a ");
		sb.append("INNER JOIN a.nivel n ");
		sb.append("WHERE a.itemComercial.id = :idItemComercial ");
		sb.append("AND a.subEtapa.id = :idSubEtapa ");
		sb.append("AND n.nivel = :itemComercial ");
		sb.append("ORDER BY a.dataCriacao DESC");
		Query q = em.createQuery(sb.toString());
		q.setParameter("idItemComercial", idItemComercial);
		q.setParameter("idSubEtapa", idSubEtapa);
		q.setParameter("itemComercial", NivelEnum.IC.toString());
		q.setMaxResults(1);
        Apontamento apontamento = null;
		try{
			apontamento = (Apontamento) q.getSingleResult();
        	
        }catch(NoResultException nre){
        	return null;
        }catch(Exception e){
        	return null;
        }
        
        return apontamento;
	}
	
	@Transactional(readOnly=true)
	public Apontamento findLastByPosicao(Long idItem, Long idSubEtapa) throws NoResultException, Exception {
		StringBuffer sb = new StringBuffer("");
		sb.append("SELECT a ");
		sb.append("FROM Apontamento a ");
		sb.append("INNER JOIN a.nivel n ");
		sb.append("WHERE a.item.id = :idItem ");
		sb.append("AND a.subEtapa.id = :idSubEtapa ");
		sb.append("AND n.nivel = :posicao ");
		sb.append("ORDER BY a.dataCriacao DESC");
		Query q = em.createQuery(sb.toString());
		q.setParameter("idItem", idItem);
		q.setParameter("idSubEtapa", idSubEtapa);
		q.setParameter("posicao", NivelEnum.PO.toString());
		q.setMaxResults(1);
        Apontamento apontamento = null;
		try{
			apontamento = (Apontamento) q.getSingleResult();
        }catch(NoResultException nre){
        	return null;
        }catch(Exception e){
        	return null;
        }
        return apontamento;
	}
	
	@Transactional(readOnly=true)
	public Apontamento findLastBySubPosicao(Long idItem, Long idSubEtapa) throws NoResultException, Exception {
		StringBuffer sb = new StringBuffer("");
		sb.append("SELECT a ");
		sb.append("FROM Apontamento a ");
		sb.append("INNER JOIN a.nivel n ");
		sb.append("WHERE a.item.id = :idItem ");
		sb.append("AND a.subEtapa.id = :idSubEtapa ");
		sb.append("AND n.nivel = :subPosicao ");
		sb.append("ORDER BY a.dataCriacao DESC");
		Query q = em.createQuery(sb.toString());
		q.setParameter("idItem", idItem);
		q.setParameter("idSubEtapa", idSubEtapa);
		q.setParameter("subPosicao", NivelEnum.SP.toString());
		q.setMaxResults(1);
        Apontamento apontamento = null;
		try{
			apontamento = (Apontamento) q.getSingleResult();
        }catch(NoResultException nre){
        	return null;
        }catch(Exception e){
        	return null;
        }
        return apontamento;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<Long> findLastsByPosicao(Item item) throws NoResultException, Exception {
		StringBuffer sb = new StringBuffer("");
		if(item.getConjuntoSoldado() == Item.NORMAL){
			sb.append("SELECT max(a.porcentagem), a.nivel.id ");
		}else{
			sb.append("SELECT max(a.porcentagem), a.item.id ");
		}
		sb.append("FROM Apontamento a ");
		sb.append("INNER JOIN a.nivel n ");
		
		if(item.getConjuntoSoldado() == Item.NORMAL){
			sb.append("WHERE a.item.id = :idItem ");
		}else{
			sb.append("INNER JOIN a.item i ");
			sb.append("WHERE i.itemPosicao.id = :idItem ");
		}
		sb.append("AND n.nivel = :subPosicao ");
		if(item.getConjuntoSoldado() == Item.NORMAL){
			sb.append("GROUP BY a.nivel.id ");
		}else{
			sb.append("GROUP BY a.item.id ");
		}
		
		sb.append("ORDER BY a.dataCriacao DESC");
		Query q = em.createQuery(sb.toString());
		q.setParameter("idItem", item.getId());
		if(item.getConjuntoSoldado() == Item.NORMAL){
			q.setParameter("subPosicao", NivelEnum.PO.toString());
		}else{
			q.setParameter("subPosicao", NivelEnum.SP.toString());
		}
		List<Long> porcents = new ArrayList<Long>();
		List<Object[]> result = new ArrayList<Object[]>();
		try{
			result = q.getResultList();
			for(Object[] row : result){
				porcents.add((Long) row[0]);
			}
        }catch(NoResultException nre){
        	return null;
        }catch(Exception e){
        	return null;
        }
        return porcents;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<Long> findLastsBySubPosicao(Item item) throws NoResultException, Exception {
		StringBuffer sb = new StringBuffer("");
		sb.append("SELECT max(a.porcentagem), a.nivel.id ");
		sb.append("FROM Apontamento a ");
		sb.append("LEFT JOIN a.nivel n ");
		sb.append("WHERE a.item.id = :idItem ");
		sb.append("AND n.nivel = :subPosicao ");
		sb.append("GROUP BY a.nivel.id ");
		sb.append("ORDER BY a.dataCriacao DESC");
		Query q = em.createQuery(sb.toString());
		q.setParameter("idItem", item.getId());
		q.setParameter("subPosicao", NivelEnum.SP.toString());
		List<Long> porcents = new ArrayList<Long>();
		List<Object[]> result = new ArrayList<Object[]>();
		try{
			result = q.getResultList();
			for(Object[] row : result){
				porcents.add((Long) row[0]);
			}
        }catch(NoResultException nre){
        	return null;
        }catch(Exception e){
        	return null;
        }
        return porcents;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<Long> findLastsByUnidade(Unidade unidade) throws NoResultException, Exception {
		StringBuffer sb = new StringBuffer("");
		sb.append("SELECT max(a.porcentagem), a.unidade.id ");
		sb.append("FROM Apontamento a ");
		sb.append("INNER JOIN a.nivel n ");
		
		sb.append("INNER JOIN a.unidade u ");
		sb.append("WHERE u.id = :idUnidade ");
			
		sb.append("AND n.nivel = :unidade ");
		sb.append("GROUP BY a.unidade.id ");
		sb.append("ORDER BY a.dataCriacao DESC");
		Query q = em.createQuery(sb.toString());
		q.setParameter("idUnidade", unidade.getId());
		q.setParameter("unidade", NivelEnum.UN.toString());
		List<Long> porcents = new ArrayList<Long>();
		List<Object[]> result = new ArrayList<Object[]>();
		try{
			result = q.getResultList();
			for(Object[] row : result){
				porcents.add((Long) row[0]);
			}
        }catch(NoResultException nre){
        	return null;
        }catch(Exception e){
        	return null;
        }
        return porcents;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<ChartEtapaVO> findRealizadoToChart(Long idEtapa, Long idProjeto, Date date) throws NoResultException, Exception {
		StringBuffer sb = new StringBuffer("");
		sb.append("select max(coalesce(a.porcentagem, 0)) * s.peso_etapa porcentagem, a.id_sub_etapa ");
		sb.append("from apontamento a ");
		sb.append("inner join sub_etapa s on a.id_sub_etapa = s.id_sub_etapa ");
		sb.append("inner join etapa e on s.id_etapa = e.id_etapa ");
		sb.append("where e.id_etapa = :idEtapa ");
		sb.append("and a.id_projeto = :idProjeto ");
		sb.append("and a.dt_criacao <= :date ");
		sb.append("group by a.id_sub_etapa");
		Query q = em.createNativeQuery(sb.toString());
		q.setParameter("idEtapa", idEtapa);
		q.setParameter("idProjeto", idProjeto);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		q.setParameter("date", df.format(date));
		List<ChartEtapaVO> retorno = new ArrayList<ChartEtapaVO>();
		List<Object[]> result = null;
		ChartEtapaVO chart = null;
		try{
			result = (List<Object[]>)q.getResultList();
			for(Object[] obj : result){
				chart = new ChartEtapaVO();
				chart.setIdSubEtapa(((BigInteger)obj[1]).longValue());
				chart.setPorcentagem(((Double)obj[0]).floatValue());
				retorno.add(chart);
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
	public List<ChartEtapaVO> findPrevistoToChart(Long idEtapa, Long idProjeto, Date date) throws NoResultException, Exception {
		StringBuffer sb = new StringBuffer("");
		sb.append("select max(100) * s.peso_etapa porcentagem, a.id_sub_etapa ");
		sb.append("from apontamento a ");
		sb.append("inner join sub_etapa s on a.id_sub_etapa = s.id_sub_etapa ");
		sb.append("inner join etapa e on s.id_etapa = e.id_etapa ");
		sb.append("where e.id_etapa = :idEtapa ");
		sb.append("and a.id_projeto = :idProjeto ");
		sb.append("and a.previsto_fim <= :date ");
		sb.append("group by a.id_sub_etapa");
		Query q = em.createNativeQuery(sb.toString());
		q.setParameter("idEtapa", idEtapa);
		q.setParameter("idProjeto", idProjeto);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		q.setParameter("date", df.format(date));
		List<ChartEtapaVO> retorno = new ArrayList<ChartEtapaVO>();
		List<Object[]> result = null;
		ChartEtapaVO chart = null;
		try{
			result = (List<Object[]>)q.getResultList();
			for(Object[] obj : result){
				chart = new ChartEtapaVO();
				chart.setIdSubEtapa(((BigInteger)obj[1]).longValue());
				chart.setPorcentagem(((Double)obj[0]).floatValue());
				retorno.add(chart);
			}
        }catch(NoResultException nre){
        	return null;
        }catch(Exception e){
        	return null;
        }
        return retorno;
	}
}
