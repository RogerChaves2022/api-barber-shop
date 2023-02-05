package com.roger.agenda.api.resource;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.roger.agenda.api.dto.ProfissionalDTO;
import com.roger.agenda.exception.RegraNegocioException;
import com.roger.agenda.model.entity.Profissional;
import com.roger.agenda.service.ProfissionalService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/profissional")
@CrossOrigin(origins="*", allowedHeaders = "*")
@RequiredArgsConstructor
public class ProfissionalResource {
	
	private final ProfissionalService service;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping
	public ResponseEntity cadastrarProfissional(@RequestBody ProfissionalDTO dto) {
		Profissional profissional = Profissional.builder()
				.nome(dto.getNome())
				.especialidade(dto.getEspecialidade())
				.descricao(dto.getDescricao())
				.build();
		try {
			Profissional profissionalSalvo = service.cadastrarProfissional(profissional);
			return new ResponseEntity(profissionalSalvo, HttpStatus.CREATED);
		} catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PutMapping("{id}")
	public ResponseEntity atualizarProfissional(@PathVariable("id") Long id, @RequestBody ProfissionalDTO dto) {
		return service.obterPorId(id).map(p -> {
			try {
				Profissional profissional = converter(dto);
				profissional.setId(p.getId());
				service.alterarProfissional(profissional);
				return ResponseEntity.ok(profissional);
			} catch (RegraNegocioException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}).orElseGet(()->
		new ResponseEntity("Produto não encontrado.", HttpStatus.BAD_REQUEST));
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@DeleteMapping("{id}")
	public ResponseEntity deletarProfissional(@PathVariable("id") Long id) {
		return service.obterPorId(id).map(p -> {
				service.deletarProfissional(p);
				return new ResponseEntity(HttpStatus.NO_CONTENT);
	}).orElseGet(() -> 
			new ResponseEntity("Profissional não encontrado.", HttpStatus.BAD_REQUEST));
	}
	
	@SuppressWarnings("rawtypes")
	@GetMapping("/buscarTodos")
	public ResponseEntity<List> buscarTodos(){
		return ResponseEntity.ok(service.buscarTodos());
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping("{id}")
	public ResponseEntity buscarPorId(@PathVariable("id") Long id) {
		return service.obterPorId(id)
				.map(profissional -> new ResponseEntity(converter(profissional),HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity(HttpStatus.NOT_FOUND));
	}

	private ProfissionalDTO converter(Profissional profissional) {
		return ProfissionalDTO.builder()
				.id(profissional.getId())
				.nome(profissional.getNome())
				.especialidade(profissional.getEspecialidade())
				.descricao(profissional.getDescricao())
				.build();
	}
	
	@SuppressWarnings("unused")
	private Profissional converter(ProfissionalDTO dto) {
		Profissional profissional = new Profissional();
		profissional.setId(dto.getId());
		profissional.setNome(dto.getNome());
		profissional.setEspecialidade(dto.getEspecialidade());
		profissional.setDescricao(dto.getDescricao());
		return profissional;
		
	}
}
