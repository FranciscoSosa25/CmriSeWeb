package com.cmrise.ejb.model.candidates.exams;

public class CandHExamenes {
	private String nombre;
	private String tipo;
	private int totalReactivos;
	private int tiempoLimite;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getTotalReactivos() {
		return totalReactivos;
	}

	public void setTotalReactivos(int totalReactivos) {
		this.totalReactivos = totalReactivos;
	}

	public int getTiempoLimite() {
		return tiempoLimite;
	}

	public void setTiempoLimite(int tiempoLimite) {
		this.tiempoLimite = tiempoLimite;
	}
}
