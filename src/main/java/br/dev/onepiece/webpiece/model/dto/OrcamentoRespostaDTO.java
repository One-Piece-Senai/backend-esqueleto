package br.dev.onepiece.webpiece.model.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.dev.onepiece.webpiece.enums.StatusOrcamentos;
import br.dev.onepiece.webpiece.model.Orcamento;
import br.dev.onepiece.webpiece.model.Projeto;
import br.dev.onepiece.webpiece.model.Usuario;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class OrcamentoRespostaDTO {
	
	private Long id;
	
	private float valor;

	private LocalDate dataEntrega;

	private String formaPagamento;

	@Enumerated(EnumType.STRING)
	private StatusOrcamentos status;
	
	private UsuarioDTO usuario;

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
	
	public UsuarioDTO getUsuario() {
		return usuario;
	}
	public void setUsuario(UsuarioDTO usuario) {
		this.usuario = usuario;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public OrcamentoRespostaDTO(Long id, float valor, LocalDate dataEntrega, String formaPagamento, StatusOrcamentos status,
			Usuario usuario) {
		super();
		this.id = id;
		this.valor = valor;
		this.dataEntrega = dataEntrega;
		this.formaPagamento = formaPagamento;
		this.status = status;
		this.usuario = new UsuarioDTO(usuario);
	}

	

}
