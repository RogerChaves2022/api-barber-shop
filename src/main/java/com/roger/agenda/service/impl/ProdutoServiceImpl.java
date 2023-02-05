package com.roger.agenda.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.roger.agenda.exception.RegraNegocioException;
import com.roger.agenda.model.entity.Produto;
import com.roger.agenda.model.repository.ProdutoRepository;
import com.roger.agenda.service.ProdutoService;

@Service
public class ProdutoServiceImpl implements ProdutoService{

	private ProdutoRepository repository;
	
	public ProdutoServiceImpl(ProdutoRepository repository) {
		this.repository = repository;
	}

	@Override
	@Transactional
	public Produto cadastrarProduto(Produto produto) {
		validarProduto(produto);
		return repository.save(produto);
	}

	@Override
	@Transactional
	public Produto alterarProduto(Produto produto) {
		Objects.requireNonNull(produto.getId());
		return repository.save(produto);
	}

	@Override
	@Transactional
	public void deletarProduto(Produto produto) {
		Objects.requireNonNull(produto.getId());
		repository.delete(produto);		
	}

	@Override
	public Optional<Produto> obterPorId(Long id) {
		return repository.findById(id);
	}

	@Override
	public List<Produto> buscarProdutos() {
		return repository.findAll();
	}

	@Override
	public void validarProduto(Produto produto) {
		if(produto.getDescricao() == null || produto.getDescricao().trim().equals("")) {
			throw new RegraNegocioException("Informe uma Descrição valida.");
		}
		if(produto.getNome() == null || produto.getNome().trim().equals("")) {
			throw new RegraNegocioException("Informe um Nome valido.");
		}
		if(produto.getTempo() == null || produto.getTempo() < 0) {
			throw new RegraNegocioException("Informe um tempo acima de 0");
		}
		if(produto.getValor() == null || produto.getValor() < 0) {
			throw new RegraNegocioException("Informe um valor acima de 0.");
		}
		if(produto.getCategoria() == null) {
			throw new RegraNegocioException("Informe uma Categoria valida.");
		}
	}

}
