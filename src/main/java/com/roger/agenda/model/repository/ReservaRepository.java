package com.roger.agenda.model.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.roger.agenda.model.entity.Reserva;
import com.roger.agenda.model.enums.Status;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
	
	@Query(value = "select r from Reserva r join r.usuario u"
			+ " where u.id = :idUsuario and r.status = :status order by r.dataReserva desc")
	List<Reserva> obterReservaPorUsuarioStatus(
			@Param("idUsuario") Long id,
			@Param("status") Status status);
	
	@Query(value = "select r from Reserva r join r.profissional p"
			+ " where p.id = :idProfissional and r.status = :status order by r.dataReserva desc")
	List<Reserva> obterReservaPorProfissionalStatus(
			@Param("idProfissional") Long id,
			@Param("status") Status status);
	
	@Query(value = "select r from Reserva r join r.profissional p"
			+ " where r.dataReserva = :dataReserva and p.id = :idProfissional")
	Optional<Reserva> obterReservaDataReserva(@Param("dataReserva")Date data, @Param("idProfissional") Long id);
}
