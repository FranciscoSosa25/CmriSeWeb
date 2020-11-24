package com.cmrise.jpa.dto.admin;

import java.io.Serializable;

public class KeysDto  implements Serializable{
	
	private static final long serialVersionUID = -12766305140621045L;
	
	private Long numero;
	  private String nombre;
	  public KeysDto(Long pNumero,String pNombre) {
		  this.setNumero(pNumero); 
		  this.setNombre(pNombre); 
	  }
	public Long getNumero() {
		return numero;
	}
	public void setNumero(Long numero) {
		this.numero = numero;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
