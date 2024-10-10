package br.dev.onepiece.webpiece.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.dev.onepiece.webpiece.model.Usuario;
import br.dev.onepiece.webpiece.repository.UsuarioRepository;

@RestController
@RequestMapping("/usuario")
public class LoginController {
	
	 @Autowired
	    private UsuarioRepository usuarioRepository;
	 
	 @Autowired
	    private PasswordEncoder passwordEncoder; // Alterado para PasswordEncoder
	 
	 @PostMapping("/login")
	    public ResponseEntity<Usuario> login(@RequestParam String email, 
                							 @RequestParam String senha) 
	                                          {
	        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);

	        if (usuarioOpt.isPresent()) {
	            Usuario usuario = usuarioOpt.get();
	            // Verifica se a senha está correta
	            if (passwordEncoder.matches(senha, usuario.getSenha())) {
	                return ResponseEntity.ok(usuario); // Retorna o usuário se a autenticação for bem-sucedida
	            } else {
	                return ResponseEntity.status(401).body(null); // Senha incorreta
	            }
	        } else {
	            return ResponseEntity.status(404).body(null); // Usuário não encontrado
	        }
	    }
	 
	 

}
