package br.dev.onepiece.webpiece.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.dev.onepiece.webpiece.enums.StatusOrcamentos;
import br.dev.onepiece.webpiece.model.Orcamento;

public interface OrcamentoRepository extends JpaRepository<Orcamento, Long> {
	
	//Verificar se existe um orçamento com a mesma combinação idProjeto e idUsuario
	Optional<Orcamento> findByProjetoIdAndUsuarioId(Long idProjeto, Long idUsuario);
	
	// Método para buscar orçamentos por ID do usuário (projetista)
    List<Orcamento> findByUsuarioId(Long idUsuario);

	Orcamento findByUsuarioIdAndProjetoId(Long id, Long id2);
	
	 // Método para buscar orçamentos por status
    List<Orcamento> findByStatus(StatusOrcamentos status);
    
 // Método para encontrar orçamentos por ID do usuário e status
    List<Orcamento> findByUsuarioIdAndStatus(Long usuarioId, StatusOrcamentos status);
    
 // Método para listar os orçamentos por id do projeto  
    List<Orcamento> findByProjetoId(Long projetoId);
	
	
}