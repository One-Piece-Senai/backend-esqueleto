package br.dev.onepiece.webpiece.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import br.dev.onepiece.webpiece.enums.TipoUsuario;
import br.dev.onepiece.webpiece.model.Orcamento;
import br.dev.onepiece.webpiece.model.Projeto;
import br.dev.onepiece.webpiece.model.Usuario;
import br.dev.onepiece.webpiece.model.dto.OrcamentoDTO;
import br.dev.onepiece.webpiece.model.dto.OrcamentoRespostaDTO;
import br.dev.onepiece.webpiece.model.dto.ProjetoProjDTO;
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
    
    
 // Listar orçamentos por projeto (ID do projeto)
    @GetMapping("/listarPorProjeto/{idProjeto}")
    public ResponseEntity<List<OrcamentoRespostaDTO>> getOrcamentosByProjetoId(@PathVariable Long idProjeto) {
        // Verifica se o projeto existe
        Optional<Projeto> projetoOptional = projetoRepository.findById(idProjeto);
        if (projetoOptional.isPresent()) {
            // Busca os orçamentos relacionados ao projeto
            List<Orcamento> orcamentos = orcamentoRepository.findByProjetoId(idProjeto);
            List<OrcamentoRespostaDTO> dtos = new ArrayList<>();
            
            orcamentos.forEach(o -> {
                dtos.add(new OrcamentoRespostaDTO(o.getId(), o.getValor(), o.getDataEntrega(), o.getFormaPagamento(), o.getStatus(), o.getUsuario()));
            });
            
            return ResponseEntity.ok(dtos);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ArrayList<>());
        }
    }
    
 // Aceitar um orçamento e recusar os outros
    @PutMapping("/aceitar/{id}")
    public ResponseEntity<?> aceitarOrcamento(@PathVariable Long id) {
        Optional<Orcamento> orcamentoOptional = orcamentoRepository.findById(id);

        if (orcamentoOptional.isPresent()) {
            Orcamento orcamentoAceito = orcamentoOptional.get();

            // Verifica se o orçamento já foi aceito
            if (orcamentoAceito.getStatus() == StatusOrcamentos.ACEITO) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Orçamento já foi aceito.");
            }

            // Atualiza o status do orçamento para ACEITO
            orcamentoAceito.setStatus(StatusOrcamentos.ACEITO);
            orcamentoRepository.save(orcamentoAceito);

            // Recusa os outros orçamentos do mesmo projeto
            List<Orcamento> outrosOrcamentos = orcamentoRepository.findByProjetoId(orcamentoAceito.getProjeto().getId());
            outrosOrcamentos.forEach(o -> {
                if (!o.getId().equals(id)) {
                    o.setStatus(StatusOrcamentos.RECUSADO);
                    orcamentoRepository.save(o);
                }
            });

            return ResponseEntity.ok("Orçamento aceito com sucesso e os outros foram recusados.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Orçamento não encontrado.");
        }
    }


    
 // Listar orçamentos por status (escolher o Status)
 // http://localhost:8080/orcamentos/listarPorStatus/ACEITO
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
    public ResponseEntity<List<ProjetoProjDTO>> getProjetosAceitosByUsuarioId(@PathVariable Long idUsuario) {
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

            List<ProjetoProjDTO> projs = new ArrayList<>();
            projetosAceitos.forEach(o -> {
            	
            	projs.add(
              			new ProjetoProjDTO(o.getId(), 
            					o.getDescricao(), 
            					o.getTitulo(), 
            					o.getStatusprojeto(), 
                    			o.getDataFinalizacao(),
                    			o.getOrcamentos().get(0).getValor(), 
                    			o.getOrcamentos().get(0).getDataEntrega(), 
                    			o.getOrcamentos().get(0).getFormaPagamento(),
                    			o.getOrcamentos().get(0).getStatus()
                    			)            			
            			);		
            });            
            
            
            return ResponseEntity.ok(projs); // Retorna a lista de projetos aceitos
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

 // Método para criar um orçamento
    @PostMapping("/criar")
    public ResponseEntity<String> createOrcamento(@RequestBody OrcamentoDTO dto) {
        // Recupera o usuário pelo ID
        Usuario usuario = usuarioRepository.findById(dto.getIdUsuario()).orElse(null);
        
        // Verifica se o usuário foi encontrado
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado!");
        }

        // Verifica se o tipo de usuário é "PROJETISTA"
        if (usuario.getTipo() != TipoUsuario.PROJETISTA) {
            // Se o usuário não for do tipo "PROJETISTA", retorna uma mensagem de erro
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Somente projetistas podem gerar orçamento!");
        }

        // Recupera o projeto pelo ID
        Projeto projeto = projetoRepository.findById(dto.getIdProjeto()).orElse(null);
        
        // Verifica se o projeto foi encontrado
        if (projeto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Projeto não encontrado!");
        }
        
        // Verifica se já existe um orçamento com o mesmo idProjeto e idUsuario
        Optional<Orcamento> existingOrcamento = orcamentoRepository.findByProjetoIdAndUsuarioId(dto.getIdProjeto(), dto.getIdUsuario());
        if (existingOrcamento.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Já existe um orçamento para este projeto e usuário!");
        }
        
        // Cria o orçamento com os dados fornecidos
        Orcamento orcamento = new Orcamento(dto.getValor(), dto.getDataEntrega(), dto.getFormaPagamento(), dto.getStatus(), projeto, usuario);
        
        // Salva o orçamento no banco de dados
        orcamentoRepository.save(orcamento);
        
        // Retorna uma resposta de sucesso
        return ResponseEntity.status(HttpStatus.CREATED).body("Orçamento criado com sucesso!");
    }
    
    //PUT http://localhost:8080/orcamentos/atualizar-status/{ID orcamento}?status=EM_ANALISE/ACEITO/RECUSADO
    @PutMapping("/atualizar-status/{id}")
    public ResponseEntity<?> atualizarStatusOrcamento(@PathVariable Long id, @RequestParam StatusOrcamentos status) {
        Optional<Orcamento> orcamentoOptional = orcamentoRepository.findById(id);

        if (orcamentoOptional.isPresent()) {
            Orcamento orcamento = orcamentoOptional.get();

            // Verifica se o orçamento tem o status ACEITO e tenta mudar para RECUSADO ou EM_ANALISE
            if (orcamento.getStatus() == StatusOrcamentos.ACEITO && 
                (status == StatusOrcamentos.RECUSADO || status == StatusOrcamentos.EM_ANALISE)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Não é permitido alterar o status de APROVADO para RECUSADO ou EM_ANALISE.");
            }

            orcamento.setStatus(status); // Atualiza o status do orçamento com o novo valor
            Orcamento orcamentoAtualizado = orcamentoRepository.save(orcamento);
            return ResponseEntity.ok(orcamentoAtualizado); // Retorna o orçamento atualizado
        } else {
            return ResponseEntity.notFound().build(); // Retorna 404 se o orçamento não for encontrado
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


