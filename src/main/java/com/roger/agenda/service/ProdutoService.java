package com.roger.agenda.service;

import java.util.List;
import java.util.Optional;

import com.roger.agenda.model.entity.Produto;

public interface ProdutoService {
	
	Produto cadastrarProduto(Produto produto);
	
	Produto alterarProduto(Produto produto);
	
	void deletarProduto(Produto produto);
	
	Optional<Produto> obterPorId(Long id);
	
	List<Produto> buscarProdutos();
	
	void validarProduto(Produto produto);
	
}
