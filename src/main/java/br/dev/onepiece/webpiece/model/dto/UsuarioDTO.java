package br.dev.onepiece.webpiece.model.dto;

import br.dev.onepiece.webpiece.model.Usuario;

public class UsuarioDTO {
	private String nome;
	private String email;
	private String password;
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
	}
	
	
	
	

}
