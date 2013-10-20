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
@Table(name = "operacao")
public class Operacao implements Serializable {

	private static final long serialVersionUID = 8054061546945391488L;

	@Id
	@GeneratedValue
	@Column(name = "id_operacao")
	private Long id;
	
	@Column(name = "op", length = 45)
	private String op;

	@Column(name = "descricao", length = 45)
	private String descricao;
	
	@Column(name = "numero_desenho", length = 45)
	private String numeroDesenho;
	
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
    @JoinColumn(name="id_usuario", insertable = true, updatable = true)
	private Usuario usuario;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name="id_produto", insertable = true, updatable = true)
	private Produto produto;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "unidade", joinColumns = @JoinColumn(name = "id_operacao"))
	private List<Unidade> unidades = new ArrayList<Unidade>();
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="id_operacao", insertable=true, updatable=true)
	private List<Apontamento> apontamentos = new ArrayList<Apontamento>();
	
	@Transient
	private Apontamento ultimoApontamento;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getNumeroDesenho() {
		return numeroDesenho;
	}

	public void setNumeroDesenho(String numeroDesenho) {
		this.numeroDesenho = numeroDesenho;
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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public List<Unidade> getUnidades() {
		return unidades;
	}

	public void setUnidades(List<Unidade> unidades) {
		this.unidades = unidades;
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
				sub = NivelEvolucao.getSubEtapaByEtapaAndNivel(apontamento.getSubEtapa().getEtapa().getId(), apontamento.getSubEtapa().getId(), NivelEnum.OP);
				porcent = porcent + new Float(apontamento.getPorcentagem() * sub.getPesoEtapa());
			}
		}
		
		int numEtapas = NivelEvolucao.getEtapasByNivel(NivelEnum.OP).size();
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
