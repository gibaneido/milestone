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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.sun.istack.internal.Nullable;

/**
 * Entity annotation map
 * @author gb
 *
 */
@Entity
@Table(name = "projeto")
public class Projeto implements Serializable {

	private static final long serialVersionUID = 6747139677671888988L;
	
	public static final long CODE_SIZE = 4L;

	@Id
	@GeneratedValue
	@Column(name = "id_projeto")
	private Long id;

	@Column(name = "codigo", length = 10, updatable=false)
	private String codigo;
	
	@Column(name = "descricao", length = 45)
	private String descricao;
	
	@Column(name = "dt_criacao", updatable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCriacao;
	
	@Column(name = "dt_atualizacao")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataAtualizacao;
	
	@Column(name = "dt_inicio")
	@Temporal(TemporalType.TIMESTAMP)
	@Nullable
	private Date dataInicio;
	
	@Column(name = "dt_fim")
	@Temporal(TemporalType.TIMESTAMP)
	@Nullable
	private Date dataFim;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name="id_usuario", insertable = true, updatable = true)
	private Usuario usuario;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name="id_cliente", insertable = true, updatable = true)
	private Cliente cliente = new Cliente();
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name="id_cliente_final", insertable = true, updatable = true)
	private Cliente clienteFinal = new Cliente();
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="id_projeto", insertable=false, updatable=false)
	private List<Produto> produtos = new ArrayList<Produto>();
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "projeto_etapa", 
		joinColumns = @JoinColumn(name = "id_projeto"), 
		inverseJoinColumns = @JoinColumn(name = "id_etapa"))
	private List<Etapa> etapas;
	
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

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Cliente getClienteFinal() {
		return clienteFinal;
	}

	public void setClienteFinal(Cliente clienteFinal) {
		this.clienteFinal = clienteFinal;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public List<Etapa> getEtapas() {
		return etapas;
	}

	public void setEtapas(List<Etapa> etapas) {
		this.etapas = etapas;
	}

}
