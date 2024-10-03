package br.dev.onepiece.webpiece.controller;

import br.dev.onepiece.webpiece.model.Usuario;
import br.dev.onepiece.webpiece.repository.UsuarioRepository; // Certifique-se de que você tem um repositório para a entidade Usuario
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping ("/listar")
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping ("/criar")
    public Usuario createUsuario(@RequestBody Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable Long id, @RequestBody Usuario usuarioDetails) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isPresent()) {
            Usuario usuarioToUpdate = usuario.get();
            usuarioToUpdate.setUsername(usuarioDetails.getUsername());
            usuarioToUpdate.setSenha(usuarioDetails.getSenha());
            usuarioToUpdate.setEmail(usuarioDetails.getEmail());
            usuarioToUpdate.setNome(usuarioDetails.getNome());
            usuarioToUpdate.setCpf_cnpj(usuarioDetails.getCpf_cnpj());
            usuarioToUpdate.setTelefone(usuarioDetails.getTelefone());
            usuarioToUpdate.setTipo(usuarioDetails.getTipo());
            usuarioToUpdate.setDescricaoPerfil(usuarioDetails.getDescricaoPerfil());
            usuarioToUpdate.setFotoPerfil(usuarioDetails.getFotoPerfil());
            Usuario updatedUsuario = usuarioRepository.save(usuarioToUpdate);
            return ResponseEntity.ok(updatedUsuario);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
