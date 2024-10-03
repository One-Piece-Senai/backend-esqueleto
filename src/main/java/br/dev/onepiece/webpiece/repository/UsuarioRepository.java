package br.dev.onepiece.webpiece.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.dev.onepiece.webpiece.model.Usuario;

public interface UsuarioRepository extends JpaRepository <Usuario, Long>{
	//Optional <Usuario> findbyEmail(String email);
	//Optional <Usuario> findbyUsername(String username);
}
