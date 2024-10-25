package br.dev.onepiece.webpiece.controller;

import java.util.ArrayList;
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

import br.dev.onepiece.webpiece.model.Orcamento;
import br.dev.onepiece.webpiece.model.Projeto;
import br.dev.onepiece.webpiece.model.Usuario;
import br.dev.onepiece.webpiece.model.dto.OrcamentoDTO;
import br.dev.onepiece.webpiece.model.dto.OrcamentoRespostaDTO;
import br.dev.onepiece.webpiece.repository.OrcamentoRepository;
import br.dev.onepiece.webpiece.repository.ProjetoRepository;
import br.dev.onepiece.webpiece.repository.UsuarioRepository;

@RestController
@CrossOrigin(origins = "*") // Permite requisições de qualquer origem
@RequestMapping("/orcamentos") // Caminho da API
public class OrcamentoController {

    @Autowired
    private OrcamentoRepository orcamentoRepository;
    
    @Autowired
    private UsuarioRepository usuarioController;
    
    @Autowired
    private ProjetoRepository projetoController;
    
 

    // Listar todos os orcamentos
    @GetMapping("/listar")
    public List<OrcamentoRespostaDTO> getAllOrcamentos() {
       List<Orcamento> orcamentos = orcamentoRepository.findAll();
       List<OrcamentoRespostaDTO> dtos = new ArrayList<>();
       orcamentos.stream()
       .forEach(o -> {
    	   dtos.add(new OrcamentoRespostaDTO(o.getId(), o.getValor(), o.getDataEntrega(), o.getFormaPagamento(), o.getStatus(), o.getUsuario()));
       });
       return dtos;
    }

    // Buscar orcamento por ID
    @GetMapping("/buscar/{id}")
    public ResponseEntity<Orcamento> getOrcamentoById(@PathVariable Long id) {
        Optional<Orcamento> orcamento = orcamentoRepository.findById(id);
        return orcamento.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Criar um novo orcamento
    @PostMapping("/criar")
    public Orcamento createOrcamento(@RequestBody OrcamentoDTO dto) {
    	Usuario usuario = usuarioController.findById(dto.getIdUsuario()).orElse(null);
    	Projeto projeto = projetoController.findById(dto.getIdProjeto()).orElse(null);
    	Orcamento orcamento = new Orcamento(dto.getValor(), dto.getDataEntrega(), dto.getFormaPagamento(), dto.getStatus(), projeto, usuario);
    	
        return orcamentoRepository.save(orcamento);
    }

    // Atualizar um orcamento existente
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Orcamento> updateOrcamento(@PathVariable Long id, @RequestBody Orcamento orcamentoDetails) {
        Optional<Orcamento> orcamento = orcamentoRepository.findById(id);
        if (orcamento.isPresent()) {
            Orcamento orcamentoToUpdate = orcamento.get();
            orcamentoToUpdate.setDataEntrega(orcamentoDetails.getDataEntrega());
            orcamentoToUpdate.setValor(orcamentoDetails.getValor());
            orcamentoToUpdate.setFormaPagamento(orcamentoDetails.getFormaPagamento());
            orcamentoToUpdate.setStatus(orcamentoDetails.getStatus());
           
            
            
            
            Orcamento updatedOrcamento = orcamentoRepository.save(orcamentoToUpdate);
            return ResponseEntity.ok(updatedOrcamento);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Remover um orcamento
    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Void> deleteOrcamento(@PathVariable Long id) {
        if (orcamentoRepository.existsById(id)) {
            orcamentoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}