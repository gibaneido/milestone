package br.com.projeto.entity;

import java.io.Serializable;
import java.util.ArrayList;
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

/**
 * Entity annotation map
 * @author gb
 *
 */
@Entity
@Table(name = "sub_etapa")
public class SubEtapa implements Serializable {

	private static final long serialVersionUID = -3716365391858487065L;

	public SubEtapa(Long id, String descricao, Float pesoEtapa){
		this.id = id;
		this.descricao = descricao;
		this.pesoEtapa = pesoEtapa;
	}
	
	public SubEtapa(){
	}
	
	@Id
	@GeneratedValue
	@Column(name = "id_sub_etapa")
	private Long id;

	@Column(name = "descricao", length = 45)
	private String descricao;
	
	@Column(name = "peso_etapa")
	private Float pesoEtapa;
	
	@Column(name = "peso_projeto")
	private Float pesoProjeto;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name="id_etapa", insertable = true, updatable = true)
	private Etapa etapa;
	
	/*@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name="id_etapa", insertable = false, updatable = false)
	private NivelEtapaSubEtapa nivel;*/
	
	@OneToMany(mappedBy="subEtapa", fetch = FetchType.LAZY)
	//@JoinTable(name = "nivel_etapa_sub_etapa", joinColumns = @JoinColumn(name = "id_sub_etapa"))
	private List<NivelEtapaSubEtapa> niveis = new ArrayList<NivelEtapaSubEtapa>();
	
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

	public Etapa getEtapa() {
		return etapa;
	}

	public void setEtapa(Etapa etapa) {
		this.etapa = etapa;
	}

	public Float getPesoEtapa() {
		return pesoEtapa;
	}

	public void setPesoEtapa(Float pesoEtapa) {
		this.pesoEtapa = pesoEtapa;
	}

	public Float getPesoProjeto() {
		return pesoProjeto;
	}

	public void setPesoProjeto(Float pesoProjeto) {
		this.pesoProjeto = pesoProjeto;
	}

	public List<NivelEtapaSubEtapa> getNiveis() {
		return niveis;
	}

	public void setNiveis(List<NivelEtapaSubEtapa> niveis) {
		this.niveis = niveis;
	}

	/*public List<Apontamento> getApontamentos() {
		return apontamentos;
	}

	public void setApontamentos(List<Apontamento> apontamentos) {
		this.apontamentos = apontamentos;
	}*/

}
