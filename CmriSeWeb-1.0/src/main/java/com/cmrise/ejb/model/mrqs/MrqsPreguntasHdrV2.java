package com.cmrise.ejb.model.mrqs;

public class MrqsPreguntasHdrV2 extends MrqsPreguntasHdrV1 {

	private static final long serialVersionUID = 1L;

	private String textoPregunta;
	private String textoSugerencias; 
	private long numeroGrupo;
	private String tituloGrupo; 

	public String getTextoPregunta() {
		return textoPregunta;
	}

	public void setTextoPregunta(String textoPregunta) {
		this.textoPregunta = textoPregunta;
	}

	public String getTextoSugerencias() {
		return textoSugerencias;
	}

	public void setTextoSugerencias(String textoSugerencias) {
		this.textoSugerencias = textoSugerencias;
	}

	public String getTituloGrupo() {
		return tituloGrupo;
	}

	public void setTituloGrupo(String tituloGrupo) {
		this.tituloGrupo = tituloGrupo;
	}

	public long getNumeroGrupo() {
		return numeroGrupo;
	}

	public void setNumeroGrupo(long numeroGrupo) {
		this.numeroGrupo = numeroGrupo;
	} 
	
}
