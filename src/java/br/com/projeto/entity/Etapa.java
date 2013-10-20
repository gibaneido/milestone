package br.com.projeto.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Entity annotation map
 * @author gb
 *
 */
@Entity
@Table(name = "etapa")
public class Etapa implements Serializable {

	private static final long serialVersionUID = -8918734819400185397L;

	public Etapa(Long id, String descricao){
		this.id = id;
		this.descricao = descricao;
	}
	
	public Etapa(){
	}
	
	@Id
	@GeneratedValue
	@Column(name = "id_etapa")
	private Long id;

	@Column(name = "descricao", length = 45)
	private String descricao;
	
	@Column(name = "peso")
	private Long peso;
	
	//@OneToMany(fetch = FetchType.LAZY)
	//@JoinTable(name = "sub_etapa", joinColumns = @JoinColumn(name = "id_etapa"))
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="id_etapa", insertable=true, updatable=true)
	private List<SubEtapa> subEtapas = new ArrayList<SubEtapa>();
	
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

	public Long getPeso() {
		return peso;
	}

	public void setPeso(Long peso) {
		this.peso = peso;
	}

	public List<SubEtapa> getSubEtapas() {
		return subEtapas;
	}

	public void setSubEtapas(List<SubEtapa> subEtapas) {
		this.subEtapas = subEtapas;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
	        return true;
	    if (obj == null)
	        return false;
	    if (!(obj instanceof Etapa))
	        return false;
	    Etapa other = (Etapa) obj;
	    if (id == null) {
	        if (other.id != null)
	            return false;
	    } else if (!id.equals(other.id))
	        return false;
	    return true;

	}

	@Override
	public int hashCode() {
		final int prime = 31;
	    int result = 1;
	    result = prime * result + ((id == null) ? 0 : id.hashCode());
	    return result;

	}
	

}
