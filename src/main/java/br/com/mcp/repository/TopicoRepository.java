package br.com.mcp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.mcp.modelo.Topico;

public interface TopicoRepository extends JpaRepository<Topico, Long>{

	Page<Topico> findByCurso_Nome(String nomeCurso, Pageable paginacao);
	
	@Query("select t from Topico t where t.curso.nome = :nomeCurso")
	Page<Topico> carregarCursoPorNome(@Param("nomeCurso") String nomeCurso, Pageable paginacao);

}
