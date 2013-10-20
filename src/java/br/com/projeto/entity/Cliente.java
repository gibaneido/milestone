package br.com.projeto.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Entity annotation map
 * @author gb
 *
 */
@Entity
@Table(name = "cliente")
public class Cliente implements Serializable {

	private static final long serialVersionUID = -2242016526200683721L;

	@Id
	@GeneratedValue
	@Column(name = "id_cliente")
	private Long id;

	@Column(name = "razao_social", length = 100)
	private String razaoSocial;
	
	@Column(name = "cnpj", length = 45)
	private String cnpj;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "projeto", joinColumns = @JoinColumn(name = "id_cliente"))
	private List<Projeto> projetos = new ArrayList<Projeto>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public List<Projeto> getProjetos() {
		return projetos;
	}

	public void setProjetos(List<Projeto> projetos) {
		this.projetos = projetos;
	}

}
