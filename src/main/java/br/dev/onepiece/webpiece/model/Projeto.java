package br.dev.onepiece.webpiece.model;

import java.sql.Date;

import br.dev.onepiece.webpiece.enums.FollowUp;
import br.dev.onepiece.webpiece.enums.StatusProjeto;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity

public class Projeto {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long idProjeto;
	
	private Date dataFinalizacao;
	
	private String imagem;
	
	@Enumerated (EnumType.STRING)
	private FollowUp followup;
	
	@Enumerated (EnumType.STRING)
	private StatusProjeto statusprojeto;

	public Long getIdProjeto() {
		return idProjeto;
	}

	public void setIdProjeto(Long idProjeto) {
		this.idProjeto = idProjeto;
	}

	public Date getDataFinalizacao() {
		return dataFinalizacao;
	}

	public void setDataFinalizacao(Date dataFinalizacao) {
		this.dataFinalizacao = dataFinalizacao;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public FollowUp getFollowup() {
		return followup;
	}

	public void setFollowup(FollowUp followup) {
		this.followup = followup;
	}

	public StatusProjeto getStatusprojeto() {
		return statusprojeto;
	}

	public void setStatusprojeto(StatusProjeto statusprojeto) {
		this.statusprojeto = statusprojeto;
	}
	
	
	
}
