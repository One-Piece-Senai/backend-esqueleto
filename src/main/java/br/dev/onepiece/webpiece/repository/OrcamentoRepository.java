package br.dev.onepiece.webpiece.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.dev.onepiece.webpiece.model.Orcamento;

public interface OrcamentoRepository extends JpaRepository <Orcamento, Long> {

}
