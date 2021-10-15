package com.cmrise.jpa.dto.candidates.exams;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@Table(name="CAND_EXAMENES_V2")
@NamedQuery(name="CandExamenesV2Dto.findAll", query="SELECT c FROM CandExamenesV2Dto c")
public class CandExamenesV2Dto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="NUMERO")
	private long numero;
	
	@Column(name="NUMERO_EXAMEN")
	private long numeroExamen;
	
	@Column(name="NUMERO_USUARIO")
	private long numeroUsuario;
	
	
	@Column(name="DESCRIPCION")
	private String descripcion; 
	
	@Column(name="TIPO")
	private String tipo; 
	
	@Column(name="ESTATUS")
	private String estatus; 
	
	@Column(name="ACTUALIZADO_POR")
	private long actualizadoPor;

	@Column(name="CREADO_POR")
	private long creadoPor;

	@Column(name="FECHA_ACTUALIZACION")
	private Timestamp fechaActualizacion;

	@Column(name="FECHA_CREACION")
	private Timestamp fechaCreacion;

	@Column(name="MATRICULA")
	private String matricula; 
	
	@Column(name="NOMBRE_USUARIO")
	private String nombreUsuario;
	
	@Column(name="APELLIDO_PATERNO")
	private String apellidoPaterno;
	
	@Column(name="APELLIDO_MATERNO")
	private String apellidoMaterno;
	
	@Column(name="NOMBRE_ACT_POR")
	private String nombreActualizadoPor;
	
	@Column(name="NOMBRE_COMPLETO_USUARIO")
	private String nombreCompletoUsuario;
	
	@Column(name="NOMBRE_ROL")
	private String nombreRol; 
	
	@Column(name="DESCRIPCION_ROL")
	private String descripcionRol; 
	
	@Column(name="TITULO")
	private String titulo; 
	
	@Column(name="TIEMPO_LIMITE")
	private short tiempoLimite;
	
	@Column(name="TOTAL_PUNTUACION")
	private double totalPuntuacion;

	@Column(name="CURP")
	private String curp; 
	
	@Column(name="FECHA_EFECTIVA_DESDE_EXAMEN")
	private Timestamp fechaEfectivaDesdeExamen;
	
	@Column(name="FECHA_EFECTIVA_HASTA_EXAMEN")
	private Timestamp fechaEfectivaHastaExamen;
	
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

	public Timestamp getFechaActualizacion() {
		return this.fechaActualizacion;
	}

	public void setFechaActualizacion(Timestamp fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public Timestamp getFechaCreacion() {
		return this.fechaCreacion;
	}

	public void setFechaCreacion(Timestamp fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
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

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	public String getNombreActualizadoPor() {
		return nombreActualizadoPor;
	}

	public void setNombreActualizadoPor(String nombreActualizadoPor) {
		this.nombreActualizadoPor = nombreActualizadoPor;
	}
	
	public String getNombreCompletoUsuario() {
		return nombreCompletoUsuario;
	}

	public void setNombreCompletoUsuario(String nombreCompletoUsuario) {
		this.nombreCompletoUsuario = nombreCompletoUsuario;
	}

	public String getNombreRol() {
		return nombreRol;
	}

	public void setNombreRol(String nombreRol) {
		this.nombreRol = nombreRol;
	}

	public String getDescripcionRol() {
		return descripcionRol;
	}

	public void setDescripcionRol(String descripcionRol) {
		this.descripcionRol = descripcionRol;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public short getTiempoLimite() {
		return tiempoLimite;
	}

	public void setTiempoLimite(short tiempoLimite) {
		this.tiempoLimite = tiempoLimite;
	}
	
	public double getTotalPuntuacion() {
		return totalPuntuacion;
	}

	public void setTotalPuntuacion(double totalPuntuacion) {
		this.totalPuntuacion = totalPuntuacion;
	}

	public String getCurp() {
		return curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}

	public Timestamp getFechaEfectivaDesdeExamen() {
		return fechaEfectivaDesdeExamen;
	}

	public void setFechaEfectivaDesdeExamen(Timestamp fechaEfectivaDesdeExamen) {
		this.fechaEfectivaDesdeExamen = fechaEfectivaDesdeExamen;
	}

	public Timestamp getFechaEfectivaHastaExamen() {
		return fechaEfectivaHastaExamen;
	}

	public void setFechaEfectivaHastaExamen(Timestamp fechaEfectivaHastaExamen) {
		this.fechaEfectivaHastaExamen = fechaEfectivaHastaExamen;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
	
}
