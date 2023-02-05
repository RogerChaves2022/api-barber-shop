package com.roger.agenda.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.roger.agenda.model.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reservas", schema = "agenda_app")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reserva {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@JoinColumn(name="id_produto")
	@ManyToOne
	private Produto produto;
	
	@JoinColumn(name="id_usuario")
	@ManyToOne
	private Usuario usuario;
	
	@JoinColumn(name="id_profissional")
	@ManyToOne
	private Profissional profissional;
	
	@Column(name="data_reserva")
	private Date dataReserva;
	
	@Column(name="status")
	@Enumerated(value = EnumType.STRING)
	private Status status;	
	
}
