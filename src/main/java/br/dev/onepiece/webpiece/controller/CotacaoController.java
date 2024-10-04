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

import br.dev.onepiece.webpiece.model.Cotacao;
import br.dev.onepiece.webpiece.repository.CotacaoRepository; // Certifique-se de ter um repositório para a entidade Cotacao

@RestController
@CrossOrigin(origins = "*") // Permite requisições de qualquer origem, modifique conforme necessário
@RequestMapping("/cotacao") // Caminho da API
public class CotacaoController {

    @Autowired
    private CotacaoRepository cotacaoRepository;

    // Listar todas as cotações
    @GetMapping("/listar")
    public List<Cotacao> getAllCotações() {
        return cotacaoRepository.findAll();
    }

    // Buscar cotação por ID
    @GetMapping("/buscar/{id}")
    public ResponseEntity<Cotacao> getCotacaoById(@PathVariable Long id) {
        Optional<Cotacao> cotacao = cotacaoRepository.findById(id);
        return cotacao.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Criar uma nova cotação
    @PostMapping("/criar")
    public Cotacao createCotacao(@RequestBody Cotacao cotacao) {
        return cotacaoRepository.save(cotacao);
    }

    // Atualizar uma cotação existente
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Cotacao> updateCotacao(@PathVariable Long id, @RequestBody Cotacao cotacaoDetails) {
        Optional<Cotacao> cotacao = cotacaoRepository.findById(id);
        if (cotacao.isPresent()) {
            Cotacao cotacaoToUpdate = cotacao.get();
            cotacaoToUpdate.setLargura(cotacaoDetails.getLargura());
            cotacaoToUpdate.setComprimento(cotacaoDetails.getComprimento());
            cotacaoToUpdate.setAltura(cotacaoDetails.getAltura());
            cotacaoToUpdate.setMaterial(cotacaoDetails.getMaterial());
            cotacaoToUpdate.setDescricao(cotacaoDetails.getDescricao());
            cotacaoToUpdate.setCaminhoArquivo(cotacaoDetails.getCaminhoArquivo());
            cotacaoToUpdate.setDataRecebimento(cotacaoDetails.getDataRecebimento());
            Cotacao updatedCotacao = cotacaoRepository.save(cotacaoToUpdate);
            return ResponseEntity.ok(updatedCotacao);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Remover uma cotação
    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Void> deleteCotacao(@PathVariable Long id) {
        if (cotacaoRepository.existsById(id)) {
            cotacaoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

