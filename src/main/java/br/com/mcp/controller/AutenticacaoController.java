package br.com.mcp.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.mcp.config.security.TokenService;
import br.com.mcp.controller.dto.TokenDTO;
import br.com.mcp.controller.form.LoginForm;

@RestController
@RequestMapping("/auth")
@Profile(value= {"default","test","prod"})
public class AutenticacaoController {
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private TokenService tokenService;
	
	@PostMapping("/autenticar")
	public ResponseEntity<TokenDTO> autenticar(@RequestBody @Valid LoginForm loginForm){
		UsernamePasswordAuthenticationToken dadosLogin = loginForm.converter();
		
		try {
			Authentication authentication =  authManager.authenticate(dadosLogin);
			String token = tokenService.gerarToken(authentication);

			return ResponseEntity.ok(new TokenDTO(token,"Bearer"));
		} catch (AuthenticationException e) {
			return ResponseEntity.badRequest().build();
		}
		
	}
}
