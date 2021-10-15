package com.cmrise.ejb.model.candidates.exams;

public class CandHExamenes {
	private String nombre;
	private String tipo;
	private int totalReactivos;
	private int reactivosRespondidos;
	private int tiempoLimite;
	private Long numeroCandExamen;
	
	private String totalScore;
	private String obtainScore;
	private String avgScore;
	private String percentage;
	

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

	public int getReactivosRespondidos() {
		return reactivosRespondidos;
	}

	public void setReactivosRespondidos(int reactivosRespondidos) {
		this.reactivosRespondidos = reactivosRespondidos;
	}

	public Long getNumeroCandExamen() {
		return numeroCandExamen;
	}

	public void setNumeroCandExamen(Long numeroCandExamen) {
		this.numeroCandExamen = numeroCandExamen;
	}

	public String getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(String totalScore) {
		this.totalScore = totalScore;
	}

	public String getObtainScore() {
		return obtainScore;
	}

	public void setObtainScore(String obtainScore) {
		this.obtainScore = obtainScore;
	}

	public String getAvgScore() {
		return avgScore;
	}

	public void setAvgScore(String avgScore) {
		this.avgScore = avgScore;
	}

	public String getPercentage() {
		return percentage;
	}

	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}
	
	
	
}
