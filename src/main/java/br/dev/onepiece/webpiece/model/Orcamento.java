package br.dev.onepiece.webpiece.model;

import java.time.LocalDate;

import br.dev.onepiece.webpiece.enums.StatusOrcamentos;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Orcamento {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private float valor;

	private LocalDate dataEntrega;

	private String formaPagamento;

	@Enumerated(EnumType.STRING)
	private StatusOrcamentos status;

	@ManyToOne
	@JoinColumn(name = "idProjeto")
	private Projeto projeto;
	
	@OneToOne
	@JoinColumn(name = "idUsuario")
	private Usuario usuario;	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	
}
