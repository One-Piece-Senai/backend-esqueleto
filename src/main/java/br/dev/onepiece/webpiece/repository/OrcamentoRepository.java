package br.dev.onepiece.webpiece.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.dev.onepiece.webpiece.enums.StatusOrcamentos;
import br.dev.onepiece.webpiece.model.Orcamento;

public interface OrcamentoRepository extends JpaRepository<Orcamento, Long> {
	
	// Método para buscar orçamentos por ID do usuário (projetista)
    List<Orcamento> findByUsuarioId(Long idUsuario);

	Orcamento findByUsuarioIdAndProjetoId(Long id, Long id2);
	
	 // Método para buscar orçamentos por status
    List<Orcamento> findByStatus(StatusOrcamentos status);
	
	
}