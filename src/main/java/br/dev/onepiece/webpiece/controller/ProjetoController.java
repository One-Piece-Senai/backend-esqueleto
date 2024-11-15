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

import br.dev.onepiece.webpiece.enums.StatusProjeto;
import br.dev.onepiece.webpiece.model.Orcamento;
import br.dev.onepiece.webpiece.model.Projeto;
import br.dev.onepiece.webpiece.model.Usuario;
import br.dev.onepiece.webpiece.model.dto.OrcamentoRespostaDTO;
import br.dev.onepiece.webpiece.model.dto.ProjetoDTO;
import br.dev.onepiece.webpiece.repository.OrcamentoRepository;
import br.dev.onepiece.webpiece.repository.ProjetoRepository;
import br.dev.onepiece.webpiece.repository.UsuarioRepository;

@RestController
@CrossOrigin(origins = "*") // Permite requisições de qualquer origem
@RequestMapping("/projetos") // Caminho da API
public class ProjetoController {

    @Autowired
    private OrcamentoRepository orcamentoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProjetoRepository projetoRepository;

    // Listar todos os projetos
    @GetMapping("/listar")
    public List<ProjetoDTO> getAllProjetos() {
        List<Projeto> projetos = projetoRepository.findAll();
        List<ProjetoDTO> dtos = new ArrayList<>();

        for (Projeto projeto : projetos) {
            List<OrcamentoRespostaDTO> orcamentoDtos = new ArrayList<>();
            for (Orcamento orcamento : projeto.getOrcamentos()) {
                OrcamentoRespostaDTO orcamentoDto = new OrcamentoRespostaDTO(
                    orcamento.getId(),
                    orcamento.getValor(),
                    orcamento.getDataEntrega(),
                    orcamento.getFormaPagamento(),
                    orcamento.getStatus(),
                    orcamento.getUsuario()
                );
                orcamentoDtos.add(orcamentoDto);
            }
            dtos.add(new ProjetoDTO(projeto, orcamentoDtos));
        }

        return dtos;
    }
    
 // Método para listar projetos com status "ABERTO"
    //http://localhost:8080/projetos/listar-aberto
    @GetMapping("/listar-aberto")
    public ResponseEntity<List<ProjetoDTO>> getProjetosAbertos() {
        List<Projeto> projetosAbertos = projetoRepository.findByStatusprojeto(StatusProjeto.ABERTO);
        List<ProjetoDTO> dtos = new ArrayList<>();

        for (Projeto projeto : projetosAbertos) {
            List<OrcamentoRespostaDTO> orcamentoDtos = new ArrayList<>();
            for (Orcamento orcamento : projeto.getOrcamentos()) {
                OrcamentoRespostaDTO orcamentoDto = new OrcamentoRespostaDTO(
                    orcamento.getId(),
                    orcamento.getValor(),
                    orcamento.getDataEntrega(),
                    orcamento.getFormaPagamento(),
                    orcamento.getStatus(),
                    orcamento.getUsuario()
                );
                orcamentoDtos.add(orcamentoDto);
            }
            dtos.add(new ProjetoDTO(projeto, orcamentoDtos));
        }

        return ResponseEntity.ok(dtos);
    }

    // Atualizar status do projeto
    //PUT http://localhost:8080/projetos/atualizar-status/1?status={ABERTO/CONCLUIDO/NÃO_INICIADO/EM_ANDAMENTO}
    @PutMapping("/atualizar-status/{id}")
    public ResponseEntity<?> atualizarStatus(@PathVariable Long id, @RequestParam StatusProjeto status) {
        Optional<Projeto> projetoOptional = projetoRepository.findById(id);

        if (projetoOptional.isPresent()) {
            Projeto projeto = projetoOptional.get();
            projeto.setStatusprojeto(status);  // Atualiza o status do projeto com o novo valor
            Projeto projetoAtualizado = projetoRepository.save(projeto);
            return ResponseEntity.ok(projetoAtualizado);  // Retorna o projeto atualizado
        } else {
            return ResponseEntity.notFound().build();  // Retorna 404 se o projeto não for encontrado
        }
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<ProjetoDTO> getProjetoById(@PathVariable Long id) {
        Projeto projeto = projetoRepository.findById(id).orElse(null);
        
        if (projeto == null) {
            return ResponseEntity.notFound().build();
        }
        
        List<OrcamentoRespostaDTO> orcamentoDtos = new ArrayList<>();
        for (Orcamento orcamento : projeto.getOrcamentos()) {
            OrcamentoRespostaDTO orcamentoDto = new OrcamentoRespostaDTO(
                orcamento.getId(),
                orcamento.getValor(),
                orcamento.getDataEntrega(),
                orcamento.getFormaPagamento(),
                orcamento.getStatus(),
                orcamento.getUsuario()
            );
            orcamentoDtos.add(orcamentoDto);
        }
        
        ProjetoDTO projetoDTO = new ProjetoDTO(projeto, orcamentoDtos);
        return ResponseEntity.ok(projetoDTO);
    }

    // Criar um novo projeto
    @PostMapping("/criar")
    public ResponseEntity<?> createProjeto(@RequestBody Projeto projeto) {
        // Obter o ID do usuário a partir do projeto
        Long usuarioId = projeto.getUsuario() != null ? projeto.getUsuario().getId() : null;

        // Buscar o usuário pelo ID
        if (usuarioId != null) {
            Optional<Usuario> usuarioOptional = usuarioRepository.findById(usuarioId);
            if (usuarioOptional.isPresent()) {
                projeto.setUsuario(usuarioOptional.get());
                Projeto savedProjeto = projetoRepository.save(projeto);
                return ResponseEntity.ok(savedProjeto);
            } else {
                return ResponseEntity.badRequest().body("Usuário não encontrado.");
            }
        } else {
            return ResponseEntity.badRequest().body("ID do usuário não fornecido.");
        }
    }

    // Atualizar um projeto existente (completo)
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> updateProjeto(@PathVariable Long id, @RequestBody Projeto projetoDetails) {
        if (projetoDetails == null) {
            return ResponseEntity.badRequest().body("Dados do projeto não podem ser nulos.");
        }

        Optional<Projeto> projetoOptional = projetoRepository.findById(id);
        if (projetoOptional.isPresent()) {
            Projeto projetoToUpdate = projetoOptional.get();

            // Atualizar outros campos
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

            // Atualizar o usuário
            Long usuarioId = projetoDetails.getUsuario() != null ? projetoDetails.getUsuario().getId() : null;
            if (usuarioId != null) {
                Optional<Usuario> usuarioOptional = usuarioRepository.findById(usuarioId);
                if (usuarioOptional.isPresent()) {
                    projetoToUpdate.setUsuario(usuarioOptional.get());
                } else {
                    return ResponseEntity.badRequest().body("Usuário não encontrado.");
                }
            }

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

    // Listar projetos de um cliente específico pelo ID do usuário
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<ProjetoDTO>> getProjetosByClienteId(@PathVariable Long clienteId) {
        // Buscar o usuário pelo ID
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(clienteId);
        if (!usuarioOptional.isPresent()) {
            return ResponseEntity.badRequest().body(null);
        }

        Usuario usuario = usuarioOptional.get();
        // Buscar todos os projetos associados ao usuário
        List<Projeto> projetos = projetoRepository.findByUsuario(usuario);
        List<ProjetoDTO> dtos = new ArrayList<>();

        for (Projeto projeto : projetos) {
            List<OrcamentoRespostaDTO> orcamentoDtos = new ArrayList<>();
            for (Orcamento orcamento : projeto.getOrcamentos()) {
                OrcamentoRespostaDTO orcamentoDto = new OrcamentoRespostaDTO(
                    orcamento.getId(),
                    orcamento.getValor(),
                    orcamento.getDataEntrega(),
                    orcamento.getFormaPagamento(),
                    orcamento.getStatus(),
                    orcamento.getUsuario()
                );
                orcamentoDtos.add(orcamentoDto);
            }

            dtos.add(new ProjetoDTO(projeto, orcamentoDtos));
        }

        return ResponseEntity.ok(dtos);
    }
}
