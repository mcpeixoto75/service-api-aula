package br.com.mcp.config.validacao;

import lombok.Getter;


@Getter
public class ErroDTO {
	
	private String campo;
	private String erro;

	public ErroDTO(String campo, String erro) {
		super();
		this.campo = campo;
		this.erro = erro;
	}
	
	
}
