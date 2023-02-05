package com.roger.agenda.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.roger.agenda.model.enums.Categoria;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "produto", schema = "agenda_app")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Produto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name= "nome")
	private String nome;
		
	@Column(name= "descricao")
	private String descricao;
	
	@Column(name = "categoria")
	@Enumerated(value = EnumType.STRING)
	private Categoria categoria;
	
	@Column(name = "valor")
	private Integer valor;
	
	@Column(name = "tempo")
	private Integer tempo;
}
