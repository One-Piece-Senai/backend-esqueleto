package br.dev.onepiece.webpiece.model;

import java.sql.Date;

import br.dev.onepiece.webpiece.enums.StatusOrcamentos;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity

public class Orcamento {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long idOrcamento;
	
	private float valor;
	
	private Date dataEntrega;
	
	@Enumerated (EnumType.STRING)
	private StatusOrcamentos status;

	public Long getIdOrcamento() {
		return idOrcamento;
	}

	public void setIdOrcamento(Long idOrcamento) {
		this.idOrcamento = idOrcamento;
	}

	public float getValor() {
		return valor;
	}

	public void setValor(float valor) {
		this.valor = valor;
	}

	public Date getDataEntrega() {
		return dataEntrega;
	}

	public void setDataEntrega(Date dataEntrega) {
		this.dataEntrega = dataEntrega;
	}

	public StatusOrcamentos getStatus() {
		return status;
	}

	public void setStatus(StatusOrcamentos status) {
		this.status = status;
	}
	
	
	
	
	

}
