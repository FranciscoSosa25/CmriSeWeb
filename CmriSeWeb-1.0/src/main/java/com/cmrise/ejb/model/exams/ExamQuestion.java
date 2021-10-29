package com.cmrise.ejb.model.exams;

public class ExamQuestion {
	
	private long numeroMRQsExamen;
	private long numeroCandExamen;
	private long numeroPreguntaFta;
	
	
	private MrqsGrupoLinesV2 mrqsGrupoLinesV2;
	private MrqsGrupoHdr mrqsGrupoHdr;
	private String respuestas; 
	
	private String questionNumber;
	private double puntuacion;
	private String estatus;
	
	
	
	public MrqsGrupoLinesV2 getMrqsGrupoLinesV2() {
		return mrqsGrupoLinesV2;
	}
	public void setMrqsGrupoLinesV2(MrqsGrupoLinesV2 mrqsGrupoLinesV2) {
		this.mrqsGrupoLinesV2 = mrqsGrupoLinesV2;
	}
	public long getNumeroMRQsExamen() {
		return numeroMRQsExamen;
	}
	public void setNumeroMRQsExamen(long numeroMRQsExamen) {
		this.numeroMRQsExamen = numeroMRQsExamen;
	}
	public long getNumeroCandExamen() {
		return numeroCandExamen;
	}
	public void setNumeroCandExamen(long numeroCandExamen) {
		this.numeroCandExamen = numeroCandExamen;
	}
	public long getNumeroPreguntaFta() {
		return numeroPreguntaFta;
	}
	public void setNumeroPreguntaFta(long numeroPreguntaFta) {
		this.numeroPreguntaFta = numeroPreguntaFta;
	}
	public MrqsGrupoHdr getMrqsGrupoHdr() {
		return mrqsGrupoHdr;
	}
	public void setMrqsGrupoHdr(MrqsGrupoHdr mrqsGrupoHdr) {
		this.mrqsGrupoHdr = mrqsGrupoHdr;
	}
	public String getQuestionNumber() {
		return questionNumber;
	}
	public void setQuestionNumber(String questionNumber) {
		this.questionNumber = questionNumber;
	}
	public String getRespuestas() {
		return respuestas;
	}
	public void setRespuestas(String respuestas) {
		this.respuestas = respuestas;
	}
	public double getPuntuacion() {
		return puntuacion;
	}
	public void setPuntuacion(double puntuacion) {
		this.puntuacion = puntuacion;
	}
	public String getEstatus() {
		return estatus;
	}
	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}
	
	
	
	
	
	
	
}
