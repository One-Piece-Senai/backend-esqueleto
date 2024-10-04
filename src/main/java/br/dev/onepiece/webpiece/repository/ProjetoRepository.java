package br.dev.onepiece.webpiece.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.dev.onepiece.webpiece.model.Projeto;

public interface ProjetoRepository extends JpaRepository<Projeto, Long> {
    // Métodos adicionais, se necessário
}