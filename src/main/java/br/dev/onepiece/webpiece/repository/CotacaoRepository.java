package br.dev.onepiece.webpiece.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.dev.onepiece.webpiece.model.Cotacao;

public interface CotacaoRepository extends JpaRepository <Cotacao, Long> {

}
