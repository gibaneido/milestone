package br.com.projeto.vo;

import java.io.Serializable;
import java.util.Date;

public class ChartProjetoVO implements Serializable{

	private static final long serialVersionUID = 6572736698680730653L;
	
	private Long porcentagem;
	
	private Date point;

	public Long getPorcentagem() {
		return porcentagem;
	}

	public void setPorcentagem(Long porcentagem) {
		this.porcentagem = porcentagem;
	}

	public Date getPoint() {
		return point;
	}

	public void setPoint(Date point) {
		this.point = point;
	}
	
}
