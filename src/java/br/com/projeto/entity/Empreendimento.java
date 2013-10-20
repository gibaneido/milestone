package br.com.projeto.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * Entity annotation map
 * @author gb
 *
 */
@Entity
@Table(name = "empreendimento")
/*@NamedQueries( 
		{ 
			@NamedQuery(
					name = "findDepartamentoAll", 
					query = "SELECT d FROM departamento d"
			) 
		}
	)*/
public class Empreendimento implements Serializable {

	private static final long serialVersionUID = -2208414619344853991L;

	@Id
	@GeneratedValue
	@Column(name = "id_empreendimento")
	private Long id;

	@Column(name = "descricao", length = 45)
	private String descricao;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "perfil_empreendimento", 
		joinColumns = @JoinColumn(name = "id_empreendimento"), 
		inverseJoinColumns = @JoinColumn(name = "id_perfil"))
	private List<Perfil> perfis;

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

	public List<Perfil> getPerfis() {
		return perfis;
	}

	public void setPerfis(List<Perfil> perfis) {
		this.perfis = perfis;
	}
}
