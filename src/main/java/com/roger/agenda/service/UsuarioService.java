package com.roger.agenda.service;

import java.util.Optional;

import com.roger.agenda.model.entity.Usuario;

public interface UsuarioService {
	
	Usuario autenticar(String email, String senha);
	
	Usuario salvarUsuario(Usuario usuario);
	
	Optional<Usuario> obterPorId(Long id);
	
	void validarEmail(String email);
}
