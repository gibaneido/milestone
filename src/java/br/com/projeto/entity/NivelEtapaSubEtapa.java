package br.com.projeto.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Entity annotation map
 * @author gb
 *
 */
@Entity
@Table(name = "nivel_etapa_sub_etapa")
public class NivelEtapaSubEtapa implements Serializable {

	private static final long serialVersionUID = 7956362499263954049L;

	@Id
	@GeneratedValue
	@Column(name = "id_nivel_etapa_sub_etapa")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_etapa")
	private Etapa etapa;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_sub_etapa")
	private SubEtapa subEtapa;
	
	//@Enumerated(EnumType.STRING)
	@Column(name = "nivel", length = 5)
	private String nivel;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Etapa getEtapa() {
		return etapa;
	}

	public void setEtapa(Etapa etapa) {
		this.etapa = etapa;
	}

	public SubEtapa getSubEtapa() {
		return subEtapa;
	}

	public void setSubEtapa(SubEtapa subEtapa) {
		this.subEtapa = subEtapa;
	}

	public String getNivel() {
		return nivel;
	}

	public void setNivel(String nivel) {
		this.nivel = nivel;
	}
}
