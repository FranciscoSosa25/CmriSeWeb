package com.cmrise.ejb.model.exams;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.cmrise.ejb.model.corecases.CcHdrV1;


public class CandExams implements Serializable {

	private static final long serialVersionUID = 1L;

	private long numeroUsuario;
	private String matricula; 
	private String curp; 
	private long examCount;
	private String nombreCompletoUsuario;
	public long getNumeroUsuario() {
		return numeroUsuario;
	}
	public void setNumeroUsuario(long numeroUsuario) {
		this.numeroUsuario = numeroUsuario;
	}
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	public String getCurp() {
		return curp;
	}
	public void setCurp(String curp) {
		this.curp = curp;
	}
	public long getExamCount() {
		return examCount;
	}
	public void setExamCount(long examCount) {
		this.examCount = examCount;
	}
	public String getNombreCompletoUsuario() {
		return nombreCompletoUsuario;
	}
	public void setNombreCompletoUsuario(String nombreCompletoUsuario) {
		this.nombreCompletoUsuario = nombreCompletoUsuario;
	}

	
	
	



}
