package br.com.mcp.controller.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.mcp.modelo.Topico;
import br.com.mcp.repository.TopicoRepository;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter	
public class AtualizarTopicoForm {

	@NotNull @NotEmpty @Length(min = 5, max = 20, message = "Tamanho inválido")
	private String titulo;
	
	@NotNull @NotEmpty @Length(min = 5)
	private String mensagem;

	public Topico atualizar(Long id, TopicoRepository topicoRepository) {
		Topico topico = topicoRepository.getById(id);
		topico.setTitulo(this.titulo);
		topico.setMensagem(this.mensagem);
		
		return topico;
	}
	
	
}
