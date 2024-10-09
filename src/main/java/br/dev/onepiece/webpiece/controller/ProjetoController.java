package br.dev.onepiece.webpiece.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.dev.onepiece.webpiece.model.Projeto;
import br.dev.onepiece.webpiece.repository.ProjetoRepository;

@RestController
@CrossOrigin(origins = "*") // Permite requisições de qualquer origem
@RequestMapping("/projetos") // Caminho da API
public class ProjetoController {

    @Autowired
    private ProjetoRepository projetoRepository;

    // Listar todos os projetos
    @GetMapping("/listar")
    public List<Projeto> getAllProjetos() {
        return projetoRepository.findAll();
    }

    // Buscar projeto por ID
    @GetMapping("/buscar/{id}")
    public ResponseEntity<Projeto> getProjetoById(@PathVariable Long id) {
        Optional<Projeto> projeto = projetoRepository.findById(id);
        return projeto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Criar um novo projeto
    @PostMapping("/criar")
    public Projeto createProjeto(@RequestBody Projeto projeto) {
        return projetoRepository.save(projeto);
    }

    // Atualizar um projeto existente
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Projeto> updateProjeto(@PathVariable Long id, @RequestBody Projeto projetoDetails) {
        Optional<Projeto> projeto = projetoRepository.findById(id);
        if (projeto.isPresent()) {
            Projeto projetoToUpdate = projeto.get();
            projetoToUpdate.setDataFinalizacao(projetoDetails.getDataFinalizacao());
            projetoToUpdate.setImagem(projetoDetails.getImagem());
            projetoToUpdate.setFollowup(projetoDetails.getFollowup());
            projetoToUpdate.setStatusprojeto(projetoDetails.getStatusprojeto());
            projetoToUpdate.setLargura(projetoDetails.getLargura());
            projetoToUpdate.setComprimento(projetoDetails.getComprimento());
            projetoToUpdate.setAltura(projetoDetails.getAltura());
            projetoToUpdate.setMaterial(projetoDetails.getMaterial());
            projetoToUpdate.setDescricao(projetoDetails.getDescricao());
            projetoToUpdate.setCaminhoArquivo(projetoDetails.getCaminhoArquivo());
            
            
            
            Projeto updatedProjeto = projetoRepository.save(projetoToUpdate);
            return ResponseEntity.ok(updatedProjeto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Remover um projeto
    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Void> deleteProjeto(@PathVariable Long id) {
        if (projetoRepository.existsById(id)) {
            projetoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
