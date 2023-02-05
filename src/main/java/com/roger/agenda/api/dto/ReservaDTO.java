package com.roger.agenda.api.dto;

import java.util.Date;

import com.roger.agenda.model.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservaDTO {
	private Long id;
	private Long produto;
	private Long usuario;
	private Long profissional;
	private Date dataReserva;
	private Status status;
}
