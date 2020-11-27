package com.cmrise.jpa.dao.mrqs;

import java.util.ArrayList;
import java.util.List;

import com.cmrise.jpa.dto.mrqs.MrqsCorrelacionColumnasDto;
import com.cmrise.jpa.dto.mrqs.MrqsCorrelacionColumnasRespuestasDto;

public class MrqsCorrelacionColumnaPair {
	MrqsCorrelacionColumnasDto respuesta;
	MrqsCorrelacionColumnasRespuestasDto texto;
	
	public MrqsCorrelacionColumnaPair() {
		
	}
	public MrqsCorrelacionColumnaPair(MrqsCorrelacionColumnasDto respuesta,MrqsCorrelacionColumnasRespuestasDto texto) {
		setRespuesta(respuesta);
		setTexto(texto);
		
	}
	public MrqsCorrelacionColumnasDto getRespuesta() {
		return respuesta;
	}
	public void setRespuesta(MrqsCorrelacionColumnasDto respuesta) {
		this.respuesta = respuesta;
	}
	public MrqsCorrelacionColumnasRespuestasDto getTexto() {
		return texto;
	}
	public void setTexto(MrqsCorrelacionColumnasRespuestasDto texto) {
		this.texto = texto;
	}

	
}
