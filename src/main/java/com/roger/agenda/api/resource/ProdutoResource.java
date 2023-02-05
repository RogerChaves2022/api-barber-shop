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

import com.roger.agenda.api.dto.ProdutoDTO;
import com.roger.agenda.exception.RegraNegocioException;
import com.roger.agenda.model.entity.Produto;
import com.roger.agenda.service.ProdutoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/produto")
@CrossOrigin(origins="*", allowedHeaders = "*")
@RequiredArgsConstructor
public class ProdutoResource {
	
	private final ProdutoService service;
	
	@SuppressWarnings({ "rawtypes"})
	@GetMapping("/buscarTodos")
	public ResponseEntity<List> buscarTodos(){
		return ResponseEntity.ok(service.buscarProdutos());
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping("{id}")
	public ResponseEntity buscarPorId(@PathVariable("id") Long id) {
		return service.obterPorId(id)
				.map(produto -> new ResponseEntity(converter(produto),HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity(HttpStatus.NOT_FOUND));
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping
	public ResponseEntity cadastrarProduto(@RequestBody ProdutoDTO dto) {
		Produto produto = Produto.builder()
				.nome(dto.getNome())
				.descricao(dto.getDescricao())
				.categoria(dto.getCategoria())
				.tempo(dto.getTempo())
				.valor(dto.getValor())
				.build();
		try {
			Produto produtoSalvo = service.cadastrarProduto(produto);
			return new ResponseEntity(produtoSalvo, HttpStatus.CREATED);	
		} catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@DeleteMapping("{id}")
	public ResponseEntity deletarProduto(@PathVariable("id")Long id) {
		return service.obterPorId(id).map(produto -> {
			service.deletarProduto(produto);
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}).orElseGet(() ->
				new ResponseEntity("Produto não encontrado.", HttpStatus.BAD_REQUEST));
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PutMapping("{id}")
	public ResponseEntity atualizarProduto(@PathVariable("id") Long id, @RequestBody ProdutoDTO dto) {
		return service.obterPorId(id).map(p -> {
			try {
				Produto produto = converter(dto);
				produto.setId(p.getId());
				service.alterarProduto(produto);
				return ResponseEntity.ok(produto);
			} catch (RegraNegocioException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}).orElseGet(()->
		new ResponseEntity("Produto não encontrado.", HttpStatus.BAD_REQUEST));
	}
	
	private ProdutoDTO converter(Produto produto) {
		return ProdutoDTO.builder()
				.id(produto.getId())
				.nome(produto.getNome())
				.descricao(produto.getDescricao())
				.categoria(produto.getCategoria())
				.tempo(produto.getTempo())
				.valor(produto.getValor())
				.build();
	}

	private Produto converter(ProdutoDTO dto) {
		Produto produto = new Produto();
		produto.setId(dto.getId());
		produto.setNome(dto.getNome());
		produto.setDescricao(dto.getDescricao());
		produto.setCategoria(dto.getCategoria());
		produto.setTempo(dto.getTempo());
		produto.setValor(dto.getValor());
		return produto;
	}
}
