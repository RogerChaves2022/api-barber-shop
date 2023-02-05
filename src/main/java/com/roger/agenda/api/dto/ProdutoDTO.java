package com.roger.agenda.api.dto;

import com.roger.agenda.model.enums.Categoria;

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
public class ProdutoDTO {
	private Long id;
	private String nome;
	private String descricao;
	private Categoria categoria;
	private Integer valor;
	private Integer tempo;
	
}
