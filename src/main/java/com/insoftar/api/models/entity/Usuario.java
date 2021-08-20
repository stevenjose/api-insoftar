package com.insoftar.api.models.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;




@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(length = 55, nullable = false)
	private String nombres;

	@Column(length = 55, nullable = false)
	@Pattern(regexp = "[^0-9]*",message = "no admite number")
	private String apellidos;
	
	@NotNull
	@Column(nullable = false, unique = true, length = 20)
	@Pattern(regexp = "[^a-zA-Z]*",message = "no admite letras")
	private String cedula;

	@NotEmpty
	@Email
	@Column(nullable = false, unique = true)
	private String correo;
	
	@NotNull
	@Column(nullable = false, unique = true, length = 20)
	@Pattern(regexp = "[^a-zA-Z]*",message = "no admite letras")
	private String telefono;
	

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="gusto_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	@NotNull(message = "el campo gustos no pueden estar vacio")
	private Gusto gusto;
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	
	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}


	
	public Gusto getGusto() {
		return gusto;
	}

	public void setGusto(Gusto gusto) {
		this.gusto = gusto;
	}



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
