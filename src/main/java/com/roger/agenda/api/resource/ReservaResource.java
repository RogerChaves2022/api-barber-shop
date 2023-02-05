package com.roger.agenda.api.resource;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.roger.agenda.api.dto.ReservaDTO;
import com.roger.agenda.exception.RegraNegocioException;
import com.roger.agenda.model.entity.Produto;
import com.roger.agenda.model.entity.Profissional;
import com.roger.agenda.model.entity.Reserva;
import com.roger.agenda.model.entity.Usuario;
import com.roger.agenda.model.enums.Status;
import com.roger.agenda.service.ProdutoService;
import com.roger.agenda.service.ProfissionalService;
import com.roger.agenda.service.ReservaService;
import com.roger.agenda.service.UsuarioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/reserva")
@CrossOrigin(origins="*", allowedHeaders = "*")
@RequiredArgsConstructor
public class ReservaResource {
	
	private final ReservaService service;
	private final UsuarioService usuarioService;
	private final ProfissionalService profissionalService;
	private final ProdutoService produtoService;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping
	public ResponseEntity cadastrarReserva(@RequestBody ReservaDTO dto) {
		if(dto == null) {
			throw new RegraNegocioException("Não existe dados na reserva");
		}
		Reserva reserva = converter(dto);
		boolean reservaCadastradaMesmaData = service.ObterPorDataReservaEProfissional(dto.getDataReserva(), dto.getProfissional()).isPresent();
		if(!reservaCadastradaMesmaData) {
		reserva = service.cadastrarReserva(reserva);
		return new ResponseEntity(reserva, HttpStatus.CREATED);
		}else {
		return ResponseEntity.badRequest().body("Data já está reservada.");
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping("{id}")
	public ResponseEntity obterReservaPorId(@PathVariable("id") Long id) {
		return service.obterPorId(id)
				.map(reserva -> new ResponseEntity(converter(reserva),HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity(HttpStatus.NOT_FOUND));
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@DeleteMapping("{id}")
	public ResponseEntity deletarReserva(@PathVariable("id")Long id) {
		return service.obterPorId(id).map(e -> {
			service.deletarReserva(e);
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}).orElseGet(() ->
				new ResponseEntity("Reserva não encontrada.", HttpStatus.BAD_REQUEST));
	}
	
	@SuppressWarnings("rawtypes")
	@GetMapping("/cliente/{id}/obterReserva/{status}")
	public ResponseEntity obterReservaPorUsuarioEStatus(@PathVariable("id")Long id, @PathVariable("status")Status status){
		Optional<Usuario> usuario = usuarioService.obterPorId(id);
		if(!usuario.isPresent()) {
			return ResponseEntity.badRequest().body("Não foi possivel encontrar o usuário informado");
		}
		List<Reserva> reservas = service.buscarReservasPorUsuariosStatus(usuario.get().getId(), status);
		return ResponseEntity.ok(reservas);
	}
	
	@SuppressWarnings("rawtypes")
	@GetMapping("/profissional/{id}/obterReserva/{status}")
	public ResponseEntity obterReservaPorProfissionalEStatus(@PathVariable("id")Long id, @PathVariable("status")Status status){
		Optional<Profissional> profissional = profissionalService.obterPorId(id);
		if(!profissional.isPresent()) {
			return ResponseEntity.badRequest().body("Não foi possivel encontrar o profissioanl informado");
		}
		List<Reserva> reservas = service.buscarReservasPorProfissionalStatus(profissional.get().getId(), status);
		return ResponseEntity.ok(reservas);
	}
	
	private ReservaDTO converter(Reserva reserva) {
		return ReservaDTO.builder()
				.id(reserva.getId())
				.produto(reserva.getProduto().getId())
				.profissional(reserva.getProfissional().getId())
				.usuario(reserva.getUsuario().getId())
				.dataReserva(reserva.getDataReserva())
				.build();
	}
	
	
	
	private Reserva converter(ReservaDTO dto) {
		Reserva reserva = new Reserva();
		reserva.setId(dto.getId());
		reserva.setDataReserva(dto.getDataReserva());
		
		Usuario usuario = usuarioService
				.obterPorId(dto.getUsuario())
				.orElseThrow(() -> new RegraNegocioException("Usuario não encontrado."));
		Profissional profissional = profissionalService
				.obterPorId(dto.getProfissional())
				.orElseThrow(() -> new RegraNegocioException("Profissional não encontrado."));
		Produto produto = produtoService
				.obterPorId(dto.getProduto())
				.orElseThrow(() -> new RegraNegocioException("Produto não encontrado."));
		reserva.setProduto(produto);
		reserva.setProfissional(profissional);
		reserva.setUsuario(usuario);
		reserva.setStatus(dto.getStatus());
		return reserva;
	}
}
