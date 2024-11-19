package br.dev.onepiece.webpiece.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.dev.onepiece.webpiece.enums.TipoUsuario;
import br.dev.onepiece.webpiece.model.Usuario;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class UsuarioDTO {
	private String nome;
	private String email;
	@JsonIgnore
	private String password;
	private String cpf_cnpj;
	private String telefone;
	private String cep;
	@Enumerated(EnumType.STRING)
	private TipoUsuario tipo;
	
	public TipoUsuario getTipo() {
		return tipo;
	}
	public void setTipo(TipoUsuario tipo) {
		this.tipo = tipo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCpf_cnpj() {
		return cpf_cnpj;
	}
	public void setCpf_cnpj(String cpf_cnpj) {
		this.cpf_cnpj = cpf_cnpj;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public UsuarioDTO(Usuario usuario) {
		super();
		this.nome = usuario.getNome();
		this.email = usuario.getEmail();
		this.password = usuario.getSenha();
		this.cpf_cnpj = usuario.getCpf_cnpj();
		this.telefone = usuario.getTelefone();
		this.cep = usuario.getCep();
		this.tipo = usuario.getTipo();
	}
	
	
	
	

}
