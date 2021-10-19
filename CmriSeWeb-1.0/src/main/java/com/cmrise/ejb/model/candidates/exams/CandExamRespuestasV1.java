package com.cmrise.ejb.model.candidates.exams;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;

public class CandExamRespuestasV1 implements Serializable {

	private static final long serialVersionUID = 1L;
	private long numero;
	private long actualizadoPor;
	private long creadoPor;
	private Date fechaActualizacion;
	private Date fechaCreacion;
	private long numeroCandExamen; 
	private long numeroGrupo; 
	private long numeroPreguntaHdr; 
	private long numeroPreguntaFta; 
	private String respuesta; 
	private double valorPuntuacion;
	private double puntuacion; 
	private long numeroExamen; 
	private long numeroUsuario; 
	private String tipoExamen; 
	private String tituloPregunta; 
	private String tipoPregunta; 
	private String tipoPreguntaDesc; 
	private String estatus; 
	private int numOpcCorrectas; 
	private int numOpcIncorrectas;
	private Long duration;
	private String timeSpent;
	
	
	public long getNumero() {
		return this.numero;
	}

	public void setNumero(long numero) {
		this.numero = numero;
	}

	public long getActualizadoPor() {
		return this.actualizadoPor;
	}

	public void setActualizadoPor(long actualizadoPor) {
		this.actualizadoPor = actualizadoPor;
	}

	public long getCreadoPor() {
		return this.creadoPor;
	}

	public void setCreadoPor(long creadoPor) {
		this.creadoPor = creadoPor;
	}

	public Date getFechaActualizacion() {
		return this.fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public Date getFechaCreacion() {
		return this.fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public long getNumeroCandExamen() {
		return numeroCandExamen;
	}

	public void setNumeroCandExamen(long numeroCandExamen) {
		this.numeroCandExamen = numeroCandExamen;
	}

	public long getNumeroGrupo() {
		return numeroGrupo;
	}

	public void setNumeroGrupo(long numeroGrupo) {
		this.numeroGrupo = numeroGrupo;
	}

	public long getNumeroPreguntaHdr() {
		return numeroPreguntaHdr;
	}

	public void setNumeroPreguntaHdr(long numeroPreguntaHdr) {
		this.numeroPreguntaHdr = numeroPreguntaHdr;
	}

	public long getNumeroPreguntaFta() {
		return numeroPreguntaFta;
	}

	public void setNumeroPreguntaFta(long numeroPreguntaFta) {
		this.numeroPreguntaFta = numeroPreguntaFta;
	}
	
	
	public double getValorPuntuacion() {
		return valorPuntuacion;
	}

	public void setValorPuntuacion(double valorPuntuacion) {
		this.valorPuntuacion = valorPuntuacion;
	}

	public double getPuntuacion() {
		return puntuacion;
	}

	public void setPuntuacion(double puntuacion) {
		this.puntuacion = puntuacion;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public long getNumeroExamen() {
		return numeroExamen;
	}

	public void setNumeroExamen(long numeroExamen) {
		this.numeroExamen = numeroExamen;
	}

	public long getNumeroUsuario() {
		return numeroUsuario;
	}

	public void setNumeroUsuario(long numeroUsuario) {
		this.numeroUsuario = numeroUsuario;
	}

	public String getTipoExamen() {
		return tipoExamen;
	}

	public void setTipoExamen(String tipoExamen) {
		this.tipoExamen = tipoExamen;
	}

	public String getTituloPregunta() {
		return tituloPregunta;
	}

	public void setTituloPregunta(String tituloPregunta) {
		this.tituloPregunta = tituloPregunta;
	}

	public String getTipoPregunta() {
		return tipoPregunta;
	}

	public void setTipoPregunta(String tipoPregunta) {
		this.tipoPregunta = tipoPregunta;
	}

	public String getTipoPreguntaDesc() {
		return tipoPreguntaDesc;
	}

	public void setTipoPreguntaDesc(String tipoPreguntaDesc) {
		this.tipoPreguntaDesc = tipoPreguntaDesc;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public int getNumOpcCorrectas() {
		return numOpcCorrectas;
	}

	public void setNumOpcCorrectas(int numOpcCorrectas) {
		this.numOpcCorrectas = numOpcCorrectas;
	}

	public int getNumOpcIncorrectas() {
		return numOpcIncorrectas;
	}

	public void setNumOpcIncorrectas(int numOpcIncorrectas) {
		this.numOpcIncorrectas = numOpcIncorrectas;
	}

	
	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	public String getTimeSpent() {
		return timeSpent;
	}

	public void setTimeSpent(String timeSpent) {
		this.timeSpent = timeSpent;
	}
	

}
