package br.dev.onepiece.webpiece.service;

import org.springframework.beans.factory.annotation.Autowired;

import br.dev.onepiece.webpiece.enums.StatusProjeto;
import br.dev.onepiece.webpiece.model.Projeto;
import br.dev.onepiece.webpiece.repository.ProjetoRepository;

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

}
