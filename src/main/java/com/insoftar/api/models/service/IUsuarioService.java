package com.insoftar.api.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.insoftar.api.models.entity.Usuario;

public interface IUsuarioService {
	
	public List<Usuario> findAll();
	
	public Page<Usuario> findAll(Pageable pageable);
	
	public Usuario save(Usuario usuario);
	
	public Usuario findUsuario(Usuario usuario);
	
	
}
