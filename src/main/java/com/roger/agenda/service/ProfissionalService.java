package com.roger.agenda.service;

import java.util.List;
import java.util.Optional;

import com.roger.agenda.model.entity.Profissional;

public interface ProfissionalService {
	
	Profissional cadastrarProfissional(Profissional profissional);
	
	Profissional alterarProfissional(Profissional profissional);
	
	Optional<Profissional> obterPorId(Long id);
	
	void deletarProfissional(Profissional profissional);
	
	List<Profissional> buscarTodos();
	
	void validarProfissional(Profissional profissional);
}
