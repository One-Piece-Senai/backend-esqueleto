package br.dev.onepiece.webpiece.model.dto;

import java.time.LocalDate;

import br.dev.onepiece.webpiece.enums.StatusOrcamentos;
import br.dev.onepiece.webpiece.enums.StatusProjeto;

public class ProjetoProjDTO {
	
	private Long id;

	private String descricao; 
	
	private String titulo;
	
	private StatusProjeto statusProjeto;

	private LocalDate dataFinalizacao;

	private float valor;

	private LocalDate dataEntrega;

	private String formaPagamento;

	private StatusOrcamentos statusOrcamento;
	
	

	public ProjetoProjDTO(Long id, String descricao, String titulo, StatusProjeto statusProjeto,
			LocalDate dataFinalizacao, float valor, LocalDate dataEntrega, String formaPagamento,
			StatusOrcamentos statusOrcamento) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.titulo = titulo;
		this.statusProjeto = statusProjeto;
		this.dataFinalizacao = dataFinalizacao;
		this.valor = valor;
		this.dataEntrega = dataEntrega;
		this.formaPagamento = formaPagamento;
		this.statusOrcamento = statusOrcamento;
	}

	
	public ProjetoProjDTO(Long id, String descricao, String titulo, StatusProjeto statusProjeto, LocalDate dataFinalizacao) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.titulo = titulo;
		this.statusProjeto = statusProjeto;
		this.dataFinalizacao = dataFinalizacao;
	}
	
	

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

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public StatusProjeto getStatusProjeto() {
		return statusProjeto;
	}

	public void setStatusProjeto(StatusProjeto statusProjeto) {
		this.statusProjeto = statusProjeto;
	}

	public LocalDate getDataFinalizacao() {
		return dataFinalizacao;
	}

	public void setDataFinalizacao(LocalDate dataFinalizacao) {
		this.dataFinalizacao = dataFinalizacao;
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

	public StatusOrcamentos getStatusOrcamento() {
		return statusOrcamento;
	}

	public void setStatusOrcamento(StatusOrcamentos statusOrcamento) {
		this.statusOrcamento = statusOrcamento;
	}

	
	
	

}
