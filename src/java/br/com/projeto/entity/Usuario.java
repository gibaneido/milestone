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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * Entity annotation map
 * @author gb
 *
 */
@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1545215770451728907L;
	
	public static final String USUARIO_LOGADO = "usuarioLogado";

	@Id
	@GeneratedValue
	@Column(name = "id_usuario")
	private Long id;

	@Column(name = "nome", length = 45)
	private String nome;
	
	@Column(name = "email", length = 100)
	private String email;
	
	@Column(name = "senha", length = 255)
	private String senha;
	
	@Column(name = "dt_criacao", updatable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCriacao;
	
	@Column(name = "dt_atualizacao")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataAtualizacao;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(name="id_empreendimento", insertable = true, updatable = true)
	private Empreendimento empreendimento;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "usuario_perfil", 
		joinColumns = @JoinColumn(name = "id_usuario"), 
		inverseJoinColumns = @JoinColumn(name = "id_perfil"))
	private List<Perfil> perfis;
	
	@Transient
	private boolean god = false;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Empreendimento getEmpreendimento() {
		return empreendimento;
	}

	public void setEmpreendimento(Empreendimento empreendimento) {
		this.empreendimento = empreendimento;
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

	public List<Perfil> getPerfis() {
		return perfis;
	}

	public void setPerfis(List<Perfil> perfis) {
		this.perfis = perfis;
	}
	
	public void setPerfil(Perfil perfil){
		if(perfis == null){
			perfis = new ArrayList<Perfil>();
		}
		perfis.add(perfil);
	}
	
	public Perfil getPerfil(){
		Perfil perfil = null;
		if(this.perfis != null && this.perfis.size() > 0){
			perfil = this.perfis.get(0);
		}
		return perfil;
	}

	public boolean isGod() {
		return god;
	}

	public void setGod(boolean god) {
		this.god = god;
	}
	
}
