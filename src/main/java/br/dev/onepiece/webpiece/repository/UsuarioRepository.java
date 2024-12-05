package br.dev.onepiece.webpiece.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.dev.onepiece.webpiece.enums.TipoUsuario;
import br.dev.onepiece.webpiece.model.Usuario;

public interface UsuarioRepository extends JpaRepository <Usuario, Long>{
	
	Optional<Usuario> findByEmail(String email);
	
	// Consulta para listar usuários do tipo "PROJETISTA" ou "CLIENTE"
    List<Usuario> findByTipo(TipoUsuario tipo); // Usa a enum como parâmetro
}
