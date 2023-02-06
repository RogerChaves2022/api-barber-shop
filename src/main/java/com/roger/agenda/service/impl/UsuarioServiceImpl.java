package com.roger.agenda.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.roger.agenda.exception.ErroAutenticacao;
import com.roger.agenda.exception.RegraNegocioException;
import com.roger.agenda.model.entity.Usuario;
import com.roger.agenda.model.repository.UsuarioRepository;
import com.roger.agenda.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	private UsuarioRepository repository;
	
	public UsuarioServiceImpl(UsuarioRepository repository) {
		this.repository = repository;
	}

	@Override
	public Usuario autenticar(String email, String senha) {
		Optional<Usuario> usuario = repository.findByEmail(email);
		if(!usuario.isPresent())
		{
			throw new ErroAutenticacao("Usuario não encontrado para o email informado.");
		}
		
		boolean validaSenha = usuario.get().getSenha().equals(senha);
		if(!validaSenha) {
			throw new ErroAutenticacao("Senha Inválida");
		}
		
		return usuario.get();
	}

	@Override
	@Transactional
	public Usuario salvarUsuario(Usuario usuario) {
		validarUsuario(usuario);
		validarEmail(usuario.getEmail());
		return repository.save(usuario);
	}

	private void validarUsuario(Usuario usuario) {
		if(usuario.getNome().isEmpty() || usuario.getNome().matches("/^[a-zA-z]+$/")) {
			throw new RegraNegocioException("Informe um nome válido!");
		}
		if(usuario.getSenha().isEmpty() || usuario.getSenha().matches("/^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$/")) {
			throw new RegraNegocioException("Informe uma senha válida!");
		}
		if(usuario.getEmail().isEmpty() || usuario.getEmail().matches("/^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$/")) {
			throw new RegraNegocioException("Informe um email válido!");
		}
		if(usuario.getTelefone().isEmpty() || usuario.getTelefone().matches("/^[0-9]+$/")) {
			throw new RegraNegocioException("Informe um telefone válido!");
		}
		
	}

	@Override
	public Optional<Usuario> obterPorId(Long id) {
		return repository.findById(id);
	}

	@Override
	public void validarEmail(String email) {
		boolean existe = repository.existsByEmail(email);
		if(existe) {
			throw new RegraNegocioException("Já existe um usuário cadastrado com este e-mail.");
		}	
	}
	
}
