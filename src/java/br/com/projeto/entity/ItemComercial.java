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
import javax.persistence.GenerationType;
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
@Table(name = "item_comercial")
public class ItemComercial implements Serializable {

	private static final long serialVersionUID = 1517775506419638814L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_item_comercial")
	private Long id;

	@Column(name = "descricao", length = 45)
	private String descricao;
	
	@Column(name = "codigo_norma", length = 45)
	private String codigoNorma;
	
	@Column(name = "qtd_direita")
	private Long qtdDireita;
	
	@Column(name = "qtd_esquerda")
	private Long qtdEsquerda;
	
	@Column(name = "dt_criacao", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCriacao;
	
	@Column(name = "dt_atualizacao")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataAtualizacao;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name="id_produto", referencedColumnName="id_produto", insertable = true, updatable = true)
	private Produto produto;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name="id_unidade", referencedColumnName="id_unidade", insertable = true, updatable = true)
	private Unidade unidade;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name="id_usuario", referencedColumnName="id_usuario", insertable = true, updatable = true)
	private Usuario usuario;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name="id_fornecedor", referencedColumnName="id_fornecedor", insertable = true, updatable = true)
	private Fornecedor fornecedor;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="id_item_comercial", insertable=true, updatable=true)
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

	public String getCodigoNorma() {
		return codigoNorma;
	}

	public void setCodigoNorma(String codigoNorma) {
		this.codigoNorma = codigoNorma;
	}

	public Long getQtdDireita() {
		if(qtdDireita == null){
			qtdDireita = 0L;
		}
		return qtdDireita;
	}

	public void setQtdDireita(Long quantidadeDireita) {
		this.qtdDireita = quantidadeDireita;
	}

	public Long getQtdEsquerda() {
		if(qtdEsquerda == null){
			qtdEsquerda = 0L;
		}
		return qtdEsquerda;
	}

	public void setQtdEsquerda(Long quantidadeEsquerda) {
		this.qtdEsquerda = quantidadeEsquerda;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
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
				sub = NivelEvolucao.getSubEtapaByEtapaAndNivel(apontamento.getSubEtapa().getEtapa().getId(), apontamento.getSubEtapa().getId(), NivelEnum.IC);
				porcent = porcent + new Float(apontamento.getPorcentagem() * sub.getPesoEtapa());
			}
		}
		
		int numEtapas = NivelEvolucao.getEtapasByNivel(NivelEnum.IC).size();
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
