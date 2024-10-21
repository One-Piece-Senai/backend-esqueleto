package br.dev.onepiece.webpiece.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.dev.onepiece.webpiece.model.Usuario;
import br.dev.onepiece.webpiece.repository.UsuarioRepository;
import br.dev.onepiece.webpiece.utils.ValidadorCpfCnpj; // Importando a classe utilitária

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/listar")
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/criar")
    public ResponseEntity<?> createUsuario(@RequestBody Usuario usuario) {
        String cpfCnpj = usuario.getCpf_cnpj();

        // Validação para CPF (11 dígitos) ou CNPJ (14 dígitos)
        if (cpfCnpj.length() == 11) {
            if (!ValidadorCpfCnpj.isCpf(cpfCnpj)) {
                return ResponseEntity.badRequest().body("CPF inválido.");
            }
        } else if (cpfCnpj.length() == 14) {
            if (!ValidadorCpfCnpj.isCnpj(cpfCnpj)) {
                return ResponseEntity.badRequest().body("CNPJ inválido.");
            }
        } else {
            return ResponseEntity.badRequest().body("O número informado não é um CPF nem um CNPJ válido.");
        }

        // Codificação de senha, se necessário
        String encodedPassword = usuario.getSenha();
        usuario.setSenha(encodedPassword); // Define a senha codificada

        Usuario savedUsuario = usuarioRepository.save(usuario);
        return ResponseEntity.ok(savedUsuario);
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> updateUsuario(@PathVariable Long id, @RequestBody Usuario usuarioDetails) {
        String cpfCnpj = usuarioDetails.getCpf_cnpj();

        // Validação para CPF (11 dígitos) ou CNPJ (14 dígitos)
        if (cpfCnpj.length() == 11) {
            if (!ValidadorCpfCnpj.isCpf(cpfCnpj)) {
                return ResponseEntity.badRequest().body("CPF inválido.");
            }
        } else if (cpfCnpj.length() == 14) {
            if (!ValidadorCpfCnpj.isCnpj(cpfCnpj)) {
                return ResponseEntity.badRequest().body("CNPJ inválido.");
            }
        } else {
            return ResponseEntity.badRequest().body("O número informado não é um CPF nem um CNPJ válido.");
        }

        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isPresent()) {
            Usuario usuarioToUpdate = usuario.get();

            // Verifica e atualiza a senha, se necessário
            if (!usuarioDetails.getSenha().equals(usuarioToUpdate.getSenha())) {
                String encodedPassword = usuarioDetails.getSenha();
                usuarioToUpdate.setSenha(encodedPassword);
            }

            // Atualiza os outros campos
            usuarioToUpdate.setUsername(usuarioDetails.getUsername());
            usuarioToUpdate.setEmail(usuarioDetails.getEmail());
            usuarioToUpdate.setNome(usuarioDetails.getNome());
            usuarioToUpdate.setCpf_cnpj(usuarioDetails.getCpf_cnpj());
            usuarioToUpdate.setTelefone(usuarioDetails.getTelefone());
            usuarioToUpdate.setTipo(usuarioDetails.getTipo());
            usuarioToUpdate.setCep(usuarioDetails.getCep());
            usuarioToUpdate.setEndereco(usuarioDetails.getEndereco());
            usuarioToUpdate.setNumero(usuarioDetails.getNumero());
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
