package br.com.projeto.vo;

import java.io.Serializable;

public class ChartEtapaVO implements Serializable{

	private static final long serialVersionUID = 6572736698680730653L;
	
	private Float porcentagem;
	
	private Long idSubEtapa;

	public Float getPorcentagem() {
		return porcentagem;
	}

	public void setPorcentagem(Float porcentagem) {
		this.porcentagem = porcentagem;
	}

	public Long getIdSubEtapa() {
		return idSubEtapa;
	}

	public void setIdSubEtapa(Long idSubEtapa) {
		this.idSubEtapa = idSubEtapa;
	}
	
}
