package br.dev.onepiece.webpiece.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.dev.onepiece.webpiece.model.Endereco;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
    // Métodos adicionais de consulta podem ser definidos aqui, se necessário
}