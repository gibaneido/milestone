package br.com.projeto.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * Entity annotation map
 * @author gb
 *
 */
@Entity
@Table(name = "apontamento")
public class Apontamento implements Serializable {

	private static final long serialVersionUID = 697457617167887368L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_apontamento")
	private Long id;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name="id_produto", referencedColumnName="id_produto", insertable = true, updatable = true)
	private Produto produto;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name="id_operacao", referencedColumnName="id_operacao", insertable = true, updatable = true)
	private Operacao operacao;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name="id_unidade", referencedColumnName="id_unidade", insertable = true, updatable = true)
	private Unidade unidade;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name="id_item_comercial", referencedColumnName="id_item_comercial", insertable = true, updatable = true)
	private ItemComercial itemComercial;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name="id_item", referencedColumnName="id_item", columnDefinition="BIGINT", insertable = true, updatable = true)
	private Item item;  
	   
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name="id_sub_etapa", referencedColumnName="id_sub_etapa", insertable = true, updatable = true)
	private SubEtapa subEtapa;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name="id_nivel_etapa_sub_etapa", referencedColumnName="id_nivel_etapa_sub_etapa", insertable = true, updatable = true)
	private NivelEtapaSubEtapa nivel;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name="id_projeto", referencedColumnName="id_projeto", insertable = true, updatable = true)
	private Projeto projeto;
	
	@Column(name = "cronograma_inicio")
	@Temporal(TemporalType.TIMESTAMP)
	private Date cronogramaInicio;
	
	@Column(name = "cronograma_fim")
	@Temporal(TemporalType.TIMESTAMP)
	private Date cronogramaFim;
	
	@Column(name = "previsto_inicio")
	@Temporal(TemporalType.TIMESTAMP)
	private Date previstoInicio;
	
	@Column(name = "previsto_fim")
	@Temporal(TemporalType.TIMESTAMP)
	private Date previstoFim;
	
	@Column(name = "realizado")
	@Temporal(TemporalType.TIMESTAMP)
	private Date realizado;
	
	@Column(name = "porcentagem")
	private Long porcentagem;

	@Column(name = "na")
	private Boolean na;
	
	@Column(name = "dt_criacao", updatable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCriacao;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Operacao getOperacao() {
		return operacao;
	}

	public void setOperacao(Operacao operacao) {
		this.operacao = operacao;
	}

	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

	public ItemComercial getItemComercial() {
		return itemComercial;
	}

	public void setItemComercial(ItemComercial itemComercial) {
		this.itemComercial = itemComercial;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public SubEtapa getSubEtapa() {
		return subEtapa;
	}

	public void setSubEtapa(SubEtapa subEtapa) {
		this.subEtapa = subEtapa;
	}

	public NivelEtapaSubEtapa getNivel() {
		return nivel;
	}

	public void setNivel(NivelEtapaSubEtapa nivel) {
		this.nivel = nivel;
	}
	
	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}

	public Date getCronogramaInicio() {
		return cronogramaInicio;
	}

	public void setCronogramaInicio(Date cronogramaInicio) {
		this.cronogramaInicio = cronogramaInicio;
	}

	public Date getCronogramaFim() {
		return cronogramaFim;
	}

	public void setCronogramaFim(Date cronogramaFim) {
		this.cronogramaFim = cronogramaFim;
	}

	public Date getPrevistoInicio() {
		return previstoInicio;
	}

	public void setPrevistoInicio(Date previstoInicio) {
		this.previstoInicio = previstoInicio;
	}

	public Date getPrevistoFim() {
		return previstoFim;
	}

	public void setPrevistoFim(Date previstoFim) {
		this.previstoFim = previstoFim;
	}

	public Date getRealizado() {
		return realizado;
	}

	public void setRealizado(Date realizado) {
		this.realizado = realizado;
	}

	public Long getPorcentagem() {
		return porcentagem;
	}

	public void setPorcentagem(Long porcentagem) {
		this.porcentagem = porcentagem;
	}

	public Boolean getNa() {
		return na;
	}

	public void setNa(Boolean na) {
		this.na = na;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

}
