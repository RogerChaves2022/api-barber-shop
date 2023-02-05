package com.roger.agenda.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.roger.agenda.exception.RegraNegocioException;
import com.roger.agenda.model.entity.Profissional;
import com.roger.agenda.model.repository.ProfissionalRepository;
import com.roger.agenda.service.ProfissionalService;

@Service
public class ProfissionalServiceImpl implements ProfissionalService {
	
	private ProfissionalRepository repository;
	
	public ProfissionalServiceImpl(ProfissionalRepository repository) {
		this.repository = repository;
	}

	@Override
	public Profissional cadastrarProfissional(Profissional profissional) {
		validarProfissional(profissional);
		return repository.save(profissional);
	}

	@Override
	public Profissional alterarProfissional(Profissional profissional) {
		Objects.requireNonNull(profissional);
		return repository.save(profissional);
	}

	@Override
	public Optional<Profissional> obterPorId(Long id) {
		return repository.findById(id);
	}

	@Override
	public void deletarProfissional(Profissional profissional) {
		Objects.requireNonNull(profissional);
		repository.delete(profissional);
		
	}

	@Override
	public List<Profissional> buscarTodos() {
		return repository.findAll();
	}

	@Override
	public void validarProfissional(Profissional profissional) {
		if(profissional.getEspecialidade() == null || profissional.getEspecialidade().trim().equals("")) {
			throw new RegraNegocioException("Preencha com uma especialidade válida.");
		}
		if(profissional.getNome() == null || profissional.getNome().trim().equals("")) {
			throw new RegraNegocioException("Preencha com um nome válido.");
		}
	}
	
}
