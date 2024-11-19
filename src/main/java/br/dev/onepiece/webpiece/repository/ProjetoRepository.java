package br.dev.onepiece.webpiece.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.dev.onepiece.webpiece.enums.StatusProjeto;
import br.dev.onepiece.webpiece.model.Projeto;
import br.dev.onepiece.webpiece.model.Usuario;

public interface ProjetoRepository extends JpaRepository<Projeto, Long> {
    // Métodos adicionais, se necessário
	
	Optional<List<Usuario>> findByUsuario_Id(Long id);// Quando logar como Cliente
	
	//Optional<List<Usuario>> findByUsuario_(Long id);// Quando logar como Projtista I - enxerga os próprios projetos "em andamento"
	
	//Optional<List<Usuario>> findByUsuario_Id(Long id);// Quando logar como Projtista II - enxerga os projetos "não iniciados"
	public List<Projeto> findByUsuario(Usuario usuario);
	

	@Query(nativeQuery = true, value ="select "
			+ " p.id as id_projeto, p.altura,p.caminho_arquivo,p.comprimento,p.data_finalizacao,p.descricao,p.followup,\r\n"
			+ " p.imagem,p.largura,p.material,p.statusprojeto,p.id_usuario as id_usuariocliente,o.id as id_orcamento,o.data_entrega,\r\n"
			+ " o.forma_pagamento,o.status,o.valor,o.id_usuario "
			+ " from projeto p "
			+ " left join orcamento o\r\n"
			+ "         on p.id = o.id_projeto\r\n"
			+ "         join usuario u\r\n"
			+ "         on u.id = o.id_usuario\r\n"
			+ "   where u.id = :id")
	public List<Projeto> findByProjetoOrcamentoUsuario(@Param("id") Long id);
	
	@Query("SELECT p FROM Projeto p INNER JOIN p.orcamentos o WHERE o.usuario.id = ?1")
	public List<Projeto> findByProjetoOrcamentoUsuario2(Long id);
	
	long countByUsuarioIdAndStatusprojeto(Long usuarioId, StatusProjeto statusprojeto);

	List<Projeto> findByStatusprojeto(StatusProjeto aberto);
	
	
}