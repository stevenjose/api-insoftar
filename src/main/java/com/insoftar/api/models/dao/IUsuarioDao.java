package com.insoftar.api.models.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.insoftar.api.models.entity.Gusto;
import com.insoftar.api.models.entity.Usuario;

public interface IUsuarioDao extends JpaRepository<Usuario, Long>{
	
	public Usuario findByCedulaOrCorreo(String cedula, String correo);

	public Usuario findByCorreo(String correo);
	
	@Query("select u from Usuario u where u.id=?1")
	public Usuario findId(Long id);
	
	@Query("from Gusto")
	public List<Gusto> findAllGustos();
	
}
