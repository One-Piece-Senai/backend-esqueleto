package br.dev.onepiece.webpiece.controller;

import br.dev.onepiece.webpiece.model.Endereco;
import br.dev.onepiece.webpiece.repository.EnderecoRepository; // Certifique-se de que você tem um repositório para a entidade Endereco
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

    @Autowired
    private EnderecoRepository enderecoRepository;

    // Listar todos os endereços
    @GetMapping("/listar")
    public List<Endereco> getAllEnderecos() {
        return enderecoRepository.findAll();
    }

    // Buscar endereço por ID
    @GetMapping("/buscar/{id}")
    public ResponseEntity<Endereco> getEnderecoById(@PathVariable Long id) {
        Optional<Endereco> endereco = enderecoRepository.findById(id);
        return endereco.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Criar um novo endereço
    @PostMapping("/criar")
    public Endereco createEndereco(@RequestBody Endereco endereco) {
        return enderecoRepository.save(endereco);
    }

    // Atualizar um endereço existente
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Endereco> updateEndereco(@PathVariable Long id, @RequestBody Endereco enderecoDetails) {
        Optional<Endereco> endereco = enderecoRepository.findById(id);
        if (endereco.isPresent()) {
            Endereco enderecoToUpdate = endereco.get();
            enderecoToUpdate.setCep(enderecoDetails.getCep());
            enderecoToUpdate.setLogradouro(enderecoDetails.getLogradouro());
            enderecoToUpdate.setNumeroLocal(enderecoDetails.getNumeroLocal());
            enderecoToUpdate.setCidade(enderecoDetails.getCidade());
            enderecoToUpdate.setUf(enderecoDetails.getUf());
            enderecoToUpdate.setComplemento(enderecoDetails.getComplemento());
            Endereco updatedEndereco = enderecoRepository.save(enderecoToUpdate);
            return ResponseEntity.ok(updatedEndereco);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Remover um endereço
    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Void> deleteEndereco(@PathVariable Long id) {
        if (enderecoRepository.existsById(id)) {
            enderecoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}