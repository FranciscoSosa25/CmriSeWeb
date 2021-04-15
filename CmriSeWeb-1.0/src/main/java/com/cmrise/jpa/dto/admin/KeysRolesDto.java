package com.cmrise.jpa.dto.admin;

import java.io.Serializable;

public class KeysRolesDto  implements Serializable{
	
	private static final long serialVersionUID = -12766305140621045L;
	
	private long numero;
	private String nombre;
	private String style;
	private boolean selected;
	
	public KeysRolesDto() {
	}
	public KeysRolesDto(long pNumero,String pNombre) {
		this.setNumero(pNumero); 
		this.setNombre(pNombre); 
	}
	
	public KeysRolesDto(long pNumero,String pNombre, String pStyle) {
		this.setNumero(pNumero); 
		this.setNombre(pNombre); 
		this.setStyle(pStyle);
	}
	
	public long getNumero() {
		return numero;
	}
	public void setNumero(long numero) {
		this.numero = numero;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}
