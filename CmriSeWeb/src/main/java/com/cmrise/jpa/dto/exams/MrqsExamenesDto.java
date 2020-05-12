package com.cmrise.jpa.dto.exams;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the MRQS_EXAMENES database table.
 * 
 */
@Entity
@Table(name="MRQS_EXAMENES")
@NamedQuery(name="MrqsExamenesDto.findAll", query="SELECT m FROM MrqsExamenesDto m")
public class MrqsExamenesDto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="NUMERO")
	private long numero;

	@Column(name="ACTUALIZADO_POR")
	private long actualizadoPor;

	@Column(name="ALEATORIO_GRUPO")
	private boolean aleatorioGrupo;

	@Column(name="ALEATORIO_PREGUNTAS")
	private boolean aleatorioPreguntas;

	@Column(name="CREADO_POR")
	private long creadoPor;

	@Column(name="DESCRIPCION")
	private String descripcion;

	@Column(name="ESTATUS")
	private String estatus;

	@Column(name="FECHA_ACTUALIZACION")
	private Timestamp fechaActualizacion;

	@Column(name="FECHA_CREACION")
	private Timestamp fechaCreacion;

	@Column(name="FECHA_EFECTIVA_DESDE")
	private Timestamp fechaEfectivaDesde;

	@Column(name="FECHA_EFECTIVA_HASTA")
	private Timestamp fechaEfectivaHasta;

	@Column(name="MENSAJE_FINALIZACION")
	private String mensajeFinalizacion;

	@Column(name="MOSTRAR_RESPUESTAS")
	private boolean mostrarRespuestas;

	@Column(name="SALTAR_CASOS")
	private boolean saltarCasos;

	@Column(name="SALTAR_PREGUNTAS")
	private boolean saltarPreguntas;

	@Column(name="SELECCION_CASOS_ALEATORIOS")
	private boolean seleccionCasosAleatorios;
	
	@Column(name="TIEMPO_LIMITE")
	private short tiempoLimite;

	@Column(name="VISIBILIDAD")
	private String visibilidad;

	@Column(name="ADMON_EXAMEN")
	private long admonExamen; 
	
	@Column(name="FECHA_ELABORACION")
	private Date fechaElaboracion; 
	
	public MrqsExamenesDto() {
	}

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

	public boolean getAleatorioGrupo() {
		return this.aleatorioGrupo;
	}

	public void setAleatorioGrupo(boolean aleatorioGrupo) {
		this.aleatorioGrupo = aleatorioGrupo;
	}

	public boolean getAleatorioPreguntas() {
		return this.aleatorioPreguntas;
	}

	public void setAleatorioPreguntas(boolean aleatorioPreguntas) {
		this.aleatorioPreguntas = aleatorioPreguntas;
	}


	public long getCreadoPor() {
		return this.creadoPor;
	}

	public void setCreadoPor(long creadoPor) {
		this.creadoPor = creadoPor;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	
	public String getEstatus() {
		return this.estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
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

	public Timestamp getFechaEfectivaDesde() {
		return this.fechaEfectivaDesde;
	}

	public void setFechaEfectivaDesde(Timestamp fechaEfectivaDesde) {
		this.fechaEfectivaDesde = fechaEfectivaDesde;
	}

	public Timestamp getFechaEfectivaHasta() {
		return this.fechaEfectivaHasta;
	}

	public void setFechaEfectivaHasta(Timestamp fechaEfectivaHasta) {
		this.fechaEfectivaHasta = fechaEfectivaHasta;
	}

	public String getMensajeFinalizacion() {
		return this.mensajeFinalizacion;
	}

	public void setMensajeFinalizacion(String mensajeFinalizacion) {
		this.mensajeFinalizacion = mensajeFinalizacion;
	}

	public boolean getMostrarRespuestas() {
		return this.mostrarRespuestas;
	}

	public void setMostrarRespuestas(boolean mostrarRespuestas) {
		this.mostrarRespuestas = mostrarRespuestas;
	}


	public boolean getSaltarCasos() {
		return this.saltarCasos;
	}

	public void setSaltarCasos(boolean saltarCasos) {
		this.saltarCasos = saltarCasos;
	}

	public boolean getSaltarPreguntas() {
		return this.saltarPreguntas;
	}

	public void setSaltarPreguntas(boolean saltarPreguntas) {
		this.saltarPreguntas = saltarPreguntas;
	}

	public boolean getSeleccionCasosAleatorios() {
		return this.seleccionCasosAleatorios;
	}

	public void setSeleccionCasosAleatorios(boolean seleccionCasosAleatorios) {
		this.seleccionCasosAleatorios = seleccionCasosAleatorios;
	}

	public short getTiempoLimite() {
		return this.tiempoLimite;
	}

	public void setTiempoLimite(short tiempoLimite) {
		this.tiempoLimite = tiempoLimite;
	}

	public String getVisibilidad() {
		return this.visibilidad;
	}

	public void setVisibilidad(String visibilidad) {
		this.visibilidad = visibilidad;
	}

	public long getAdmonExamen() {
		return admonExamen;
	}

	public void setAdmonExamen(long admonExamen) {
		this.admonExamen = admonExamen;
	}

	public Date getFechaElaboracion() {
		return fechaElaboracion;
	}

	public void setFechaElaboracion(Date fechaElaboracion) {
		this.fechaElaboracion = fechaElaboracion;
	}

}