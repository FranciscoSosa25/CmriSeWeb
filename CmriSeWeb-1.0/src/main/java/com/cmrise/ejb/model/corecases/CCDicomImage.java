package com.cmrise.ejb.model.corecases;

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class CCDicomImage implements Serializable {

	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;

	private long numero;
	private long numeroCcHdr;
	private long creadoPor;
	private String dicomModality;
	private String filePath;
	
	private List<File> listDicomFiles;
	private String nombre;
	private String titulo;
	public long getNumero() {
		return numero;
	}
	public void setNumero(long numero) {
		this.numero = numero;
	}
	public long getNumeroCcHdr() {
		return numeroCcHdr;
	}
	public void setNumeroCcHdr(long numeroCcHdr) {
		this.numeroCcHdr = numeroCcHdr;
	}
	public long getCreadoPor() {
		return creadoPor;
	}
	public void setCreadoPor(long creadoPor) {
		this.creadoPor = creadoPor;
	}
	
	public String getDicomModality() {
		return dicomModality;
	}
	public void setDicomModality(String dicomModality) {
		this.dicomModality = dicomModality;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public List<File> getListDicomFiles() {
		return listDicomFiles;
	}
	public void setListDicomFiles(List<File> listDicomFiles) {
		this.listDicomFiles = listDicomFiles;
	}
	
	
	
	
	
	
	

}
