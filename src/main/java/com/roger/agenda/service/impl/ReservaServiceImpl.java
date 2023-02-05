package com.roger.agenda.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.roger.agenda.exception.RegraNegocioException;
import com.roger.agenda.model.entity.Reserva;
import com.roger.agenda.model.enums.Status;
import com.roger.agenda.model.repository.ReservaRepository;
import com.roger.agenda.service.ReservaService;

@Service
public class ReservaServiceImpl implements ReservaService {

	private ReservaRepository service;

	public ReservaServiceImpl(ReservaRepository service) {
		this.service = service;
	}

	@Override
	public Reserva cadastrarReserva(Reserva reserva) {
		validarReserva(reserva);
		return service.save(reserva);
	}

	@Override
	public Reserva alterarStatusReserva(Reserva reserva, Status status) {
		reserva.setStatus(status);
		return service.save(reserva);
	}

	@Override
	public void deletarReserva(Reserva reserva) {
		Objects.requireNonNull(reserva);
		service.delete(reserva);		
	}

	@Override
	public Optional<Reserva> obterPorId(Long reserva) {
		return service.findById(reserva);
	}

	@Override
	public List<Reserva> buscarReservasPorUsuariosStatus(Long id, Status status) {
		return service.obterReservaPorUsuarioStatus(id, status);
	}
	
	@Override
	public List<Reserva> buscarReservasPorProfissionalStatus(Long id, Status status) {
		return service.obterReservaPorProfissionalStatus(id, status);
	}

	@Override
	public Optional<Reserva> ObterPorDataReservaEProfissional(Date data, Long id) {
		return service.obterReservaDataReserva(data, id);
	}
	
	@Override
	public void validarReserva(Reserva reserva) {
		if(reserva.getProduto() == null) {
			throw new RegraNegocioException("Escolha um produto para sua reserva");
		}
		if(reserva.getDataReserva() == null) {
			throw new RegraNegocioException("Escolha uma data para sua reserva");
		}
		if(reserva.getProfissional() == null) {
			throw new RegraNegocioException("Escolha um profissional para o serviço");
		}
	}



}
