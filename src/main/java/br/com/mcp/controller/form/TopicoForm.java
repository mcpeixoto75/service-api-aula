package br.com.mcp.controller.form;



import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.mcp.modelo.Curso;
import br.com.mcp.modelo.Topico;
import br.com.mcp.modelo.Usuario;
import br.com.mcp.repository.CursoRepository;
import br.com.mcp.repository.UsuarioRepository;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TopicoForm {
	
	@NotNull @NotEmpty @Length(min = 5, max = 20, message = "Tamanho inválido")
	private String titulo;
	
	@NotNull @NotEmpty @Length(min = 5)
	private String mensagem;
	
	@NotNull @NotEmpty
	private String nomeCurso;
	
	@NotBlank
	private String nomeAluno;
	
	
	
	public Topico converter(CursoRepository cursoRepository, UsuarioRepository usuarioRepository) {
		
		Curso curso = cursoRepository.findByNome(nomeCurso);
		Usuario autor = usuarioRepository.findByNome(nomeAluno);
		return new Topico(titulo,mensagem,curso, autor);
	}
	
	
}
