package br.dev.onepiece.webpiece.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.dev.onepiece.webpiece.model.Usuario;

public interface UsuarioRepository extends JpaRepository <Usuario, Long>{
	
	Optional<Usuario> findByEmail(String email);
}
