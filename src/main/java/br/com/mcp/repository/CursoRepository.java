package br.com.mcp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.mcp.modelo.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long>{
	Curso findByNome(String nome);
}
