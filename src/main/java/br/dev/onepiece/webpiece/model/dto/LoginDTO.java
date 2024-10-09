package br.dev.onepiece.webpiece.model.dto;

import br.dev.onepiece.webpiece.enums.TipoUsuario;

public class LoginDTO {

	private String email;
	private String password;
	private TipoUsuario tipoUser;

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

	public TipoUsuario getTipoUser() {
		return tipoUser;
	}

	public void setTipoUser(TipoUsuario tipoUser) {
		this.tipoUser = tipoUser;
	}
	
	

}
