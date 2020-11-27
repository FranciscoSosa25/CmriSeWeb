package com.cmrise.ejb.model.admin;

import java.io.Serializable;

public class TablasUtilitariasValoresV1 implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String tipoTabla; 
	private int numeroRegistros;
	public String getTipoTabla() {
		return tipoTabla;
	}
	public void setTipoTabla(String tipoTabla) {
		this.tipoTabla = tipoTabla;
	}
	public int getNumeroRegistros() {
		return numeroRegistros;
	}
	public void setNumeroRegistros(int numeroRegistros) {
		this.numeroRegistros = numeroRegistros;
	} 

}
