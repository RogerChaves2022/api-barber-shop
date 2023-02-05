package com.roger.agenda.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.roger.agenda.model.entity.Reserva;
import com.roger.agenda.model.enums.Status;

public interface ReservaService {
	
	Reserva cadastrarReserva(Reserva reserva);
	
	Reserva alterarStatusReserva(Reserva reserva, Status status);
	
	void deletarReserva(Reserva reserva);
	
	Optional<Reserva> obterPorId(Long reserva);
	
	List<Reserva> buscarReservasPorUsuariosStatus(Long id, Status status);
	
	List<Reserva> buscarReservasPorProfissionalStatus(Long id, Status status);
	
	Optional<Reserva> ObterPorDataReservaEProfissional(Date data, Long id);
	
	void validarReserva(Reserva reserva);
}
