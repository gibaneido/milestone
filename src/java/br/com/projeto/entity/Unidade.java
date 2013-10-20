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
@Table(name = "unidade")
public class Unidade implements Serializable {

	private static final long serialVersionUID = -3432650775521656082L;

	@Id
	@GeneratedValue
	@Column(name = "id_unidade")
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
	
	@Column(name = "qtd_direita")
	private Long qtdDireita;
	
	@Column(name = "qtd_esquerda")
	private Long qtdEsquerda;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name="id_operacao", insertable = true, updatable = true)
	private Operacao operacao;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name="id_usuario", insertable = true, updatable = true)
	private Usuario usuario;
	
	@OneToMany(mappedBy="unidade", fetch = FetchType.LAZY)
	//@JoinTable(name = "posicao", joinColumns = @JoinColumn(name = "id_unidade"))
	private List<Item> itens = new ArrayList<Item>();
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="id_unidade", insertable=true, updatable=true)
	private List<Apontamento> apontamentos = new ArrayList<Apontamento>();
	
	@Transient
	private Apontamento ultimoApontamento;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Long getQtdDireita() {
		if(qtdDireita == null){
			qtdDireita = 0L;
		}
		return qtdDireita;
	}

	public void setQtdDireita(Long qtdDireita) {
		this.qtdDireita = qtdDireita;
	}

	public Long getQtdEsquerda() {
		if(qtdEsquerda == null){
			qtdEsquerda = 0L;
		}
		return qtdEsquerda;
	}

	public void setQtdEsquerda(Long qtdEsquerda) {
		this.qtdEsquerda = qtdEsquerda;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Operacao getOperacao() {
		return operacao;
	}

	public void setOperacao(Operacao operacao) {
		this.operacao = operacao;
	}

	public List<Item> getItens() {
		return itens;
	}

	public void setItens(List<Item> itens) {
		this.itens = itens;
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
				sub = NivelEvolucao.getSubEtapaByEtapaAndNivel(apontamento.getSubEtapa().getEtapa().getId(), apontamento.getSubEtapa().getId(), NivelEnum.UN);
				porcent = porcent + new Float(apontamento.getPorcentagem() * sub.getPesoEtapa());
			}
		}
		
		int numEtapas = NivelEvolucao.getEtapasByNivel(NivelEnum.UN).size();
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
