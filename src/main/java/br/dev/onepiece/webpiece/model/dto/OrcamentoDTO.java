package br.dev.onepiece.webpiece.model.dto;

import java.time.LocalDate;

import br.dev.onepiece.webpiece.enums.StatusOrcamentos;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class OrcamentoDTO {
	

	private float valor;

	private LocalDate dataEntrega;

	private String formaPagamento;

	@Enumerated(EnumType.STRING)
	private StatusOrcamentos status;
	
	private Long idUsuario;
	private Long idProjeto;

	public float getValor() {
		return valor;
	}
	public void setValor(float valor) {
		this.valor = valor;
	}
	public LocalDate getDataEntrega() {
		return dataEntrega;
	}
	public void setDataEntrega(LocalDate dataEntrega) {
		this.dataEntrega = dataEntrega;
	}
	public String getFormaPagamento() {
		return formaPagamento;
	}
	public void setFormaPagamento(String formaPagamento) {
		this.formaPagamento = formaPagamento;
	}
	public StatusOrcamentos getStatus() {
		return status;
	}
	public void setStatus(StatusOrcamentos status) {
		this.status = status;
	}
	
	public Long getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
	public Long getIdProjeto() {
		return idProjeto;
	}
	public void setIdProjeto(Long idProjeto) {
		this.idProjeto = idProjeto;
	}
	public OrcamentoDTO( float valor, LocalDate dataEntrega, String formaPagamento, StatusOrcamentos status,
			Long idUsuario, Long idProjeto) {
		super();
		this.valor = valor;
		this.dataEntrega = dataEntrega;
		this.formaPagamento = formaPagamento;
		this.status = status;
		this.idUsuario = idUsuario;
		this.idProjeto = idProjeto;
	}
	
	public OrcamentoDTO() {
		
	}
	

}
