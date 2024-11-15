package br.dev.onepiece.webpiece.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.dev.onepiece.webpiece.enums.StatusProjeto;
import br.dev.onepiece.webpiece.model.Projeto;
import br.dev.onepiece.webpiece.repository.ProjetoRepository;

@Service
public class ProjetoService {
	
	@Autowired
    private ProjetoRepository projetoRepository;
	
//atualiza status do projeto
    public Projeto atualizarStatus(Long id, StatusProjeto novoStatus) {
        Projeto projeto = projetoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Projeto não encontrado"));
        projeto.setStatusprojeto(novoStatus);
        return projetoRepository.save(projeto);
    }
    
    public Map<String, Long> contarProjetosPorStatus(Long usuarioId) {
        Map<String, Long> contagem = new HashMap<>();

        contagem.put("EM_ANDAMENTO", projetoRepository.countByUsuarioIdAndStatusprojeto(usuarioId, StatusProjeto.EM_ANDAMENTO));
        contagem.put("CONCLUIDO", projetoRepository.countByUsuarioIdAndStatusprojeto(usuarioId, StatusProjeto.CONCLUIDO));
        contagem.put("ABERTO", projetoRepository.countByUsuarioIdAndStatusprojeto(usuarioId, StatusProjeto.ABERTO));
        contagem.put("NÃO_INICIADO", projetoRepository.countByUsuarioIdAndStatusprojeto(usuarioId, StatusProjeto.NÃO_INICIADO));

        System.out.println("Blabla");
        
        return contagem;
    }

}
