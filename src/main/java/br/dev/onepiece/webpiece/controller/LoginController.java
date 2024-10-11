package br.dev.onepiece.webpiece.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.dev.onepiece.webpiece.model.Usuario;
import br.dev.onepiece.webpiece.model.dto.LoginDTO;
import br.dev.onepiece.webpiece.repository.UsuarioRepository;

@RestController
@RequestMapping("/login")
public class LoginController {

	@Autowired
	private UsuarioRepository usuarioRepository;

	//@Autowired
	//private PasswordEncoder passwordEncoder; // Alterado para PasswordEncoder
	
	@PostMapping()
	public ResponseEntity<LoginDTO> login(@RequestBody LoginDTO login) {
		Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(login.getEmail());

		if (usuarioOpt.isPresent()) {
			Usuario usuario = usuarioOpt.get();
			// Verifica se a senha está correta
			
			System.err.println(login.getPassword());
			System.out.println(usuario.getSenha());
			if ((usuario.getSenha().equals(login.getPassword()))) {//passwordEncoder.matches
				LoginDTO loginDTO = new LoginDTO();
				loginDTO.setEmail(usuario.getEmail());
				loginDTO.setTipoUser(usuario.getTipo());
				loginDTO.setId(usuario.getId());

				return ResponseEntity.ok(loginDTO); // Retorna o usuário se a autenticação for bem-sucedida
			} else {
				return ResponseEntity.status(401).body(null); // Senha incorreta
			}
		} else {
			return ResponseEntity.status(404).body(null); // Usuário não encontrado
		}
	}

}
