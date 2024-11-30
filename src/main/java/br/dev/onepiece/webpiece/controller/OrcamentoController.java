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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.dev.onepiece.webpiece.enums.StatusOrcamentos;
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
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private ProjetoRepository projetoRepository;
    
    // Listar todos os orçamentos
    @GetMapping("/listar")
    public List<OrcamentoRespostaDTO> getAllOrcamentos() {
       List<Orcamento> orcamentos = orcamentoRepository.findAll();
       List<OrcamentoRespostaDTO> dtos = new ArrayList<>();
       orcamentos.forEach(o -> {
           dtos.add(new OrcamentoRespostaDTO(o.getId(), o.getValor(), o.getDataEntrega(), o.getFormaPagamento(), o.getStatus(), o.getUsuario()));
       });
       return dtos;
    }
    
 // Listar orçamentos por status (escolher o Status)
 // http://localhost:8080/orcamentos/listarPorStatus/APROVADO
    @GetMapping("/listarPorStatus/{status}")
    public ResponseEntity<List<OrcamentoRespostaDTO>> getOrcamentosByStatus(@PathVariable StatusOrcamentos status) {
        // Buscar os orçamentos com o status especificado
        List<Orcamento> orcamentos = orcamentoRepository.findByStatus(status); // Método personalizado no repositório
        List<OrcamentoRespostaDTO> dtos = new ArrayList<>();
        
        orcamentos.forEach(o -> {
            dtos.add(new OrcamentoRespostaDTO(o.getId(), o.getValor(), o.getDataEntrega(), o.getFormaPagamento(), o.getStatus(), o.getUsuario()));
        });
        
        return ResponseEntity.ok(dtos); // Retorna a lista de orçamentos no formato DTO
    }

    
    // Buscar orçamentos por ID do Projetista (idUsuario)
    @GetMapping("/listarPorUsuario/{idUsuario}")
    public ResponseEntity<List<OrcamentoRespostaDTO>> getOrcamentosByUsuarioId(@PathVariable Long idUsuario) {
        // Verificar se o usuário existe
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(idUsuario);
        if (usuarioOptional.isPresent()) {
            // Buscar os orçamentos relacionados a este usuário
            List<Orcamento> orcamentos = orcamentoRepository.findByUsuarioId(idUsuario); // Método personalizado no repositório
            List<OrcamentoRespostaDTO> dtos = new ArrayList<>();
            orcamentos.forEach(o -> {
                dtos.add(new OrcamentoRespostaDTO(o.getId(), o.getValor(), o.getDataEntrega(), o.getFormaPagamento(), o.getStatus(), o.getUsuario()));
            });
            return ResponseEntity.ok(dtos);
        } else {
            return ResponseEntity.notFound().build(); // Retorna 404 se o usuário não existir
        }
    }
    
    //http://localhost:8080/orcamentos/listarProjetosAceitosPorUsuario/{idUsuario}
    @GetMapping("/listarProjetosAceitosPorUsuario/{idUsuario}")
    public ResponseEntity<List<Projeto>> getProjetosAceitosByUsuarioId(@PathVariable Long idUsuario) {
        // Verificar se o usuário existe
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(idUsuario);
        if (usuarioOptional.isPresent()) {
            // Buscar os orçamentos com status 'ACEITO' relacionados a este usuário
            List<Orcamento> orcamentosAceitos = orcamentoRepository.findByUsuarioIdAndStatus(idUsuario, StatusOrcamentos.ACEITO);
            
            // Obter a lista de projetos a partir dos orçamentos aceitos
            List<Projeto> projetosAceitos = new ArrayList<>();
            for (Orcamento orcamento : orcamentosAceitos) {
                projetosAceitos.add(orcamento.getProjeto());
            }

            return ResponseEntity.ok(projetosAceitos); // Retorna a lista de projetos aceitos
        } else {
            return ResponseEntity.notFound().build(); // Retorna 404 se o usuário não existir
        }
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
        Usuario usuario = usuarioRepository.findById(dto.getIdUsuario()).orElse(null);
        Projeto projeto = projetoRepository.findById(dto.getIdProjeto()).orElse(null);
        Orcamento orcamento = new Orcamento(dto.getValor(), dto.getDataEntrega(), dto.getFormaPagamento(), dto.getStatus(), projeto, usuario);
        
        return orcamentoRepository.save(orcamento);
    }
    
    //PUT http://localhost:8080/orcamentos/atualizar-status/{ID orcamento}?status=EM_ANALISE/ACEITO/RECUSADO
    @PutMapping("/atualizar-status/{id}")
    public ResponseEntity<?> atualizarStatusOrcamento(@PathVariable Long id, @RequestParam StatusOrcamentos status) {
        Optional<Orcamento> orcamentoOptional = orcamentoRepository.findById(id);

        if (orcamentoOptional.isPresent()) {
            Orcamento orcamento = orcamentoOptional.get();
            orcamento.setStatus(status);  // Atualiza o status do orcamento com o novo valor
            Orcamento orcamentoAtualizado = orcamentoRepository.save(orcamento);
            return ResponseEntity.ok(orcamentoAtualizado);  // Retorna o orcamento atualizado
        } else {
            return ResponseEntity.notFound().build();  // Retorna 404 se o orcamento não for encontrado
        }
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


