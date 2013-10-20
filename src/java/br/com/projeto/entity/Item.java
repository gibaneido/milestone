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
@Table(name = "item")
public class Item implements Serializable {

	private static final long serialVersionUID = 2271941648828652137L;
	public static final char CONJUNTO_SOLDADO = 'S';
	public static final char NORMAL = 'N';

	@Id
	@GeneratedValue
	@Column(name = "id_item")
	private Long id;

	@Column(name = "posicao", length = 45)
	private String posicao;
	
	@Column(name = "descricao", length = 45)
	private String descricao;
	
	@Column(name = "dt_criacao", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCriacao;
	
	@Column(name = "dt_atualizacao")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataAtualizacao;
	
	@Column(name = "material", length = 45)
	private String material;
	
	@Column(name = "qtd_direita")
	private Long qtdDireita;
	
	@Column(name = "qtd_esquerda")
	private Long qtdEsquerda;
	
	@Column(name = "codigo_norma", length = 45)
	private String codigoNorma;
	
	@Column(name = "altura", length = 45)
	private String altura;
	
	@Column(name = "largura_diametro", length = 45)
	private String larguraDiametro;
	
	@Column(name = "espessura_alma", length = 45)
	private String espessuraAlma;
	
	@Column(name = "comprimento", length = 45)
	private String comprimento;
	
	@Column(name = "conjunto_soldado", length = 1)
	private char conjuntoSoldado = 'N';
	
	@Column(name = "observacao", length = 255)
	private String observacao;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name="id_usuario", insertable = true, updatable = true)
	private Usuario usuario;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name="id_unidade", referencedColumnName="id_unidade", insertable = true, updatable = true)
	private Unidade unidade;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name="id_fornecedor", insertable = true, updatable = true)
	private Fornecedor fornecedor;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name="id_item_pai", insertable = true, updatable = true)
	private Item itemPosicao;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="id_item", insertable=true, updatable=true)
	private List<Apontamento> apontamentos = new ArrayList<Apontamento>();
	
	@Transient
	private Apontamento ultimoApontamento;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPosicao() {
		return posicao;
	}

	public void setPosicao(String posicao) {
		this.posicao = posicao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
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
	
	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
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

	public String getCodigoNorma() {
		return codigoNorma;
	}

	public void setCodigoNorma(String codigoNorma) {
		this.codigoNorma = codigoNorma;
	}

	public String getAltura() {
		return altura;
	}

	public void setAltura(String altura) {
		this.altura = altura;
	}

	public String getLarguraDiametro() {
		return larguraDiametro;
	}

	public void setLarguraDiametro(String larguraDiametro) {
		this.larguraDiametro = larguraDiametro;
	}

	public String getEspessuraAlma() {
		return espessuraAlma;
	}

	public void setEspessuraAlma(String espessuraAlma) {
		this.espessuraAlma = espessuraAlma;
	}

	public String getComprimento() {
		return comprimento;
	}

	public void setComprimento(String comprimento) {
		this.comprimento = comprimento;
	}

	public char getConjuntoSoldado() {
		return conjuntoSoldado;
	}

	public void setConjuntoSoldado(char conjuntoSoldado) {
		this.conjuntoSoldado = conjuntoSoldado;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public Item getItemPosicao() {
		return itemPosicao;
	}

	public void setItemPosicao(Item itemPosicao) {
		this.itemPosicao = itemPosicao;
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
				if(this.conjuntoSoldado == Item.CONJUNTO_SOLDADO){
					sub = NivelEvolucao.getSubEtapaByEtapaAndNivel(apontamento.getSubEtapa().getEtapa().getId(), apontamento.getSubEtapa().getId(), NivelEnum.SP);
				}else{
					sub = NivelEvolucao.getSubEtapaByEtapaAndNivel(apontamento.getSubEtapa().getEtapa().getId(), apontamento.getSubEtapa().getId(), NivelEnum.PO);
				}
				
				porcent = porcent + new Float(apontamento.getPorcentagem() * sub.getPesoEtapa());
			}
		}
		int numEtapas = 1;
		if(this.conjuntoSoldado == Item.CONJUNTO_SOLDADO){
			numEtapas = NivelEvolucao.getEtapasByNivel(NivelEnum.SP).size();
		}else{
			numEtapas = NivelEvolucao.getEtapasByNivel(NivelEnum.PO).size();
		}
		return porcent.longValue()/numEtapas;
		
	}
	
	public Apontamento getUltimoApontamento() {
		return ultimoApontamento;
	}

	public void setUltimoApontamento(Apontamento ultimoApontamento) {
		this.ultimoApontamento = ultimoApontamento;
	}
}
