
package br.com.mcp.config.security;
  
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder; 
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.  EnableWebSecurity; 
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



import br.com.mcp.repository.UsuarioRepository;
  
  
@EnableWebSecurity
@Configuration
@Profile(value= {"default","test","prod"})
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {

	@Autowired  
	private UsuarioRepository usuarioRepository;  
	  
    @Autowired
	private TokenService tokenService;  
	
	@Autowired
	private AutenticacaoService autenticacaoService;
	
	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
  
  	//Configurações de autenticação
  	@Override 
  	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(autenticacaoService).passwordEncoder(new BCryptPasswordEncoder());
  	}
  
  	//Configuração de autorização
  	@Override 
	protected void configure(HttpSecurity http) throws Exception {
  		
  		
  		
		http.authorizeRequests()
		.antMatchers(HttpMethod.GET, "/topicos").permitAll()
		.antMatchers(HttpMethod.GET, "/topicos/*").permitAll()
  		.antMatchers(HttpMethod.GET, "/actuator/**").permitAll()
  		.antMatchers(HttpMethod.POST, "/auth/*").permitAll()
  		.antMatchers(HttpMethod.DELETE, "/topicos/*").hasAnyRole("MODERADOR")
  		.anyRequest().authenticated()
  		.and().csrf().disable()
  		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
  		.and().addFilterBefore(new AutenticacaoTokenFilter(tokenService,usuarioRepository), UsernamePasswordAuthenticationFilter.class);
  	}
	
  	//Configuração para recursos estaticos (js, css, imagens, etc)
  	@Override 
  	public void configure(WebSecurity web) throws Exception {
  		web.ignoring().antMatchers("/**.html", "/v2/api-docs", "/webjars/**", "/configuration/**", "/swagger-resources/**");
  	}
  
  
}
 
 
