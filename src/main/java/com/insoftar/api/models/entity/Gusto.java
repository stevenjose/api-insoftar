package com.insoftar.api.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "gustos")
public class Gusto implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(length = 150, nullable = false, unique = true)
	private String gusto;
	
	
	

	public long getId() {
		return id;
	}




	public void setId(long id) {
		this.id = id;
	}




	public String getGusto() {
		return gusto;
	}




	public void setGusto(String gusto) {
		this.gusto = gusto;
	}




	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
