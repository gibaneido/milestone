package br.com.projeto.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.com.projeto.util.NivelEnum;
import br.com.projeto.util.NivelEvolucao;

/**
 * Entity annotation map
 * @author gb
 *
 */
@Entity
@Table(name = "produto")
public class Produto implements Serializable {

	private static final long serialVersionUID = 617167654746419195L;

	@Id
	@GeneratedValue
	@Column(name = "id_produto")
	private Long id;

	@Column(name = "descricao", length = 45)
	private String descricao;
	
	@Column(name = "numero", length = 45)
	private String numero;
	
	@Column(name = "dt_criacao", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCriacao;
	
	@Column(name = "dt_atualizacao")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataAtualizacao;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name="id_usuario", insertable = true, updatable = true)
	private Usuario usuario;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name="id_projeto", insertable = true, updatable = true)
	private Projeto projeto;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "operacao", joinColumns = @JoinColumn(name = "id_produto"))
	private List<Operacao> operacoes = new ArrayList<Operacao>();
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "item_comercial", joinColumns = @JoinColumn(name = "id_produto"))
	private List<ItemComercial> itensComerciais = new ArrayList<ItemComercial>();
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="id_produto", insertable=true, updatable=true)
	private List<Apontamento> apontamentos = new ArrayList<Apontamento>();
	
	@Transient
	private Apontamento ultimoApontamento;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Date getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(Date dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}

	public List<Operacao> getOperacoes() {
		return operacoes;
	}

	public void setOperacoes(List<Operacao> operacoes) {
		this.operacoes = operacoes;
	}

	public List<ItemComercial> getItensComerciais() {
		return itensComerciais;
	}

	public void setItensComerciais(List<ItemComercial> itensComerciais) {
		this.itensComerciais = itensComerciais;
	}

	public List<Apontamento> getApontamentos() {
		return apontamentos;
	}

	public void setApontamentos(List<Apontamento> apontamentos) {
		this.apontamentos = apontamentos;
	}
	
	public Long getPorcentagem(){
		Float porcent = 0f;
		for(Apontamento apontamento : this.apontamentos){
			if(apontamento.getPorcentagem() != null){
				SubEtapa sub;
				//sub = NivelEnum.PD.getSubEtapaFromEtapa(apontamento.getSubEtapa().getEtapa().getId(), apontamento.getSubEtapa().getId());
				sub = NivelEvolucao.getSubEtapaByEtapaAndNivel(apontamento.getSubEtapa().getEtapa().getId(), apontamento.getSubEtapa().getId(), NivelEnum.PD);
				porcent = porcent + new Float(apontamento.getPorcentagem() * sub.getPesoEtapa());
			}
		}
		
		int numEtapas = NivelEvolucao.getEtapasByNivel(NivelEnum.PD).size();
		//if(this.apontamentos != null && this.apontamentos.size() > 0){
			
		//}
		return porcent.longValue()/numEtapas;	
	}

	public Apontamento getUltimoApontamento() {
		return ultimoApontamento;
	}

	public void setUltimoApontamento(Apontamento ultimoApontamento) {
		this.ultimoApontamento = ultimoApontamento;
	}
}
