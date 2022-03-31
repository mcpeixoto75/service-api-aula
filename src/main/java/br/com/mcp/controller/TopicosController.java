package br.com.mcp.controller;


import java.net.URI;
import java.util.Optional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.mcp.controller.dto.TopicoDTO;
import br.com.mcp.controller.dto.TopicoDetalhesDTO;
import br.com.mcp.controller.form.AtualizarTopicoForm;
import br.com.mcp.controller.form.TopicoForm;
import br.com.mcp.modelo.Topico;
import br.com.mcp.repository.CursoRepository;
import br.com.mcp.repository.TopicoRepository;
import br.com.mcp.repository.UsuarioRepository;

@RestController
@RequestMapping("/topicos")
public class TopicosController {
	
	@Autowired
	private TopicoRepository topicoRepository;
	@Autowired
	private CursoRepository cursoRepository;
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	
	@GetMapping
	@Cacheable(value = "listaTopicos")
	public Page<TopicoDTO> lista(@RequestParam(required = false) String nomeCurso, 
			@PageableDefault(sort="id", direction = Direction.DESC, page = 0, size = 10) Pageable paginacao){
		
		
		
		if(nomeCurso == null){
			Page<Topico> topicos = topicoRepository.findAll(paginacao);
			return TopicoDTO.converter(topicos);
		}else {
			Page<Topico> topicos = topicoRepository.carregarCursoPorNome(nomeCurso, paginacao);
			return TopicoDTO.converter(topicos);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<TopicoDetalhesDTO> detalhar(@PathVariable("id") Long codigo){
		
		Optional<Topico> topico = topicoRepository.findById(codigo);
		if (topico.isPresent()) {
			return ResponseEntity.ok(new TopicoDetalhesDTO(topico.get()));	
		}
		 return ResponseEntity.notFound().build();
		
	}
	
	
	@PostMapping
	@Transactional
	@CacheEvict(value = "listaTopicos", allEntries = true)
	public ResponseEntity<TopicoDTO> cadastrar(@RequestBody @Valid TopicoForm topicoForm, UriComponentsBuilder uriBuilder) {
		
		Topico topico = topicoForm.converter(cursoRepository, usuarioRepository); 
		topicoRepository.save(topico);
		
		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
		
		return ResponseEntity.created(uri).body(new TopicoDTO(topico));
	}
	
	
	@PutMapping("/{codigo}")
	@Transactional
	@CacheEvict(value = "listaTopicos", allEntries = true)
	public ResponseEntity<TopicoDTO> atualizar(@PathVariable("codigo") Long id, @RequestBody @Valid AtualizarTopicoForm topicoForm){
		Optional<Topico> topicoP = topicoRepository.findById(id);
		if (topicoP.isPresent()) {
			Topico topico = topicoForm.atualizar(id, topicoRepository);
			return ResponseEntity.ok(new TopicoDTO(topico));	
		}
		
		 return ResponseEntity.notFound().build();
	}
	
	
	@DeleteMapping("/{codigo}")
	@Transactional
	@CacheEvict(value = "listaTopicos", allEntries = true)
	public ResponseEntity<?> remover(@PathVariable("codigo") Long id){
		
		Optional<Topico> topicoD = topicoRepository.findById(id);
		if (topicoD.isPresent()) {
			topicoRepository.deleteById(id);
			return ResponseEntity.ok().build();	
		}
		
		 return ResponseEntity.notFound().build();
	}

	
}
