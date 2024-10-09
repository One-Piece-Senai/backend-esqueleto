package br.dev.onepiece.webpiece.repository;

import br.dev.onepiece.webpiece.model.Orcamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrcamentoRepository extends JpaRepository<Orcamento, Long> {
}