package com.cmrise.jpa.dto.exams;

import java.io.Serializable;
import javax.persistence.*;

import java.sql.Date;
import java.sql.Timestamp;


/**
 * The persistent class for the CC_EXAMENES_V1 database table.
 * 
 */
@Entity
@Table(name="CC_EXAMENES_V1")
@NamedQuery(name="CcExamenesV1Dto.findAll", query="SELECT c FROM CcExamenesV1Dto c")
public class CcExamenesV1Dto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="ACTUALIZADO_POR")
	private long actualizadoPor;

	@Column(name="ALEATORIO_GRUPO")
	private boolean aleatorioGrupo;

	@Column(name="ALEATORIO_PREGUNTAS")
	private boolean aleatorioPreguntas;

	@Column(name="COMENTARIOS")
	private String comentarios;

	@Column(name="CONFIRMACION_ASISTENCIA")
	private boolean confirmacionAsistencia;

	@Column(name="CREADO_POR")
	private long creadoPor;

	@Column(name="DESCRIPCION")
	private String descripcion;

	@Column(name="DIPLOMA")
	private boolean diploma;

	@Column(name="ESTATUS")
	private String estatus;

	@Column(name="ESTATUS_DESC")
	private String estatusDesc;

	@Column(name="FECHA_ACTUALIZACION")
	private Timestamp fechaActualizacion;

	@Column(name="FECHA_CREACION")
	private Timestamp fechaCreacion;

	@Column(name="FECHA_EFECTIVA_DESDE")
	private Date fechaEfectivaDesde;

	@Column(name="FECHA_EFECTIVA_HASTA")
	private Date fechaEfectivaHasta;

	@Column(name="MENSAJE_FINALIZACION")
	private String mensajeFinalizacion;

	@Column(name="MOSTRAR_RESPUESTAS")
	private boolean mostrarRespuestas;

	@Column(name="NOMBRE")
	private String nombre;

	@Id
	@Column(name="NUMERO")
	private long numero;

	@Column(name="SALTAR_CASOS")
	private boolean saltarCasos;

	@Column(name="SALTAR_PREGUNTAS")
	private boolean saltarPreguntas;

	@Column(name="SELECCION_CASOS_ALEATORIOS")
	private boolean seleccionCasosAleatorios;

	@Column(name="SOCIEDAD")
	private String sociedad;

	@Column(name="TEMA")
	private String tema;

	@Column(name="TEMA_DESC")
	private String temaDesc;

	@Column(name="TIEMPO_LIMITE")
	private short tiempoLimite;

	@Column(name="TIENE_PASSMARK")
	private boolean tienePassmark;

	@Column(name="TIPO_EXAMEN")
	private String tipoExamen;

	@Column(name="TIPO_EXAMEN_DESC")
	private String tipoExamenDesc;

	@Column(name="TIPO_PREGUNTA")
	private String tipoPregunta;

	@Column(name="TIPO_PREGUNTA_DESC")
	private String tipoPreguntaDesc;

	@Column(name="TITULO")
	private String titulo;

	@Column(name="VISIBILIDAD")
	private String visibilidad;

	@Column(name="VISIBILIDAD_DESC")
	private String visibilidadDesc;

	public CcExamenesV1Dto() {
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

	public String getComentarios() {
		return this.comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

	public boolean getConfirmacionAsistencia() {
		return this.confirmacionAsistencia;
	}

	public void setConfirmacionAsistencia(boolean confirmacionAsistencia) {
		this.confirmacionAsistencia = confirmacionAsistencia;
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

	public boolean getDiploma() {
		return this.diploma;
	}

	public void setDiploma(boolean diploma) {
		this.diploma = diploma;
	}

	public String getEstatus() {
		return this.estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public String getEstatusDesc() {
		return this.estatusDesc;
	}

	public void setEstatusDesc(String estatusDesc) {
		this.estatusDesc = estatusDesc;
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

	public Date getFechaEfectivaDesde() {
		return this.fechaEfectivaDesde;
	}

	public void setFechaEfectivaDesde(Date fechaEfectivaDesde) {
		this.fechaEfectivaDesde = fechaEfectivaDesde;
	}

	public Date getFechaEfectivaHasta() {
		return this.fechaEfectivaHasta;
	}

	public void setFechaEfectivaHasta(Date fechaEfectivaHasta) {
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

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public long getNumero() {
		return this.numero;
	}

	public void setNumero(long numero) {
		this.numero = numero;
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

	public String getSociedad() {
		return this.sociedad;
	}

	public void setSociedad(String sociedad) {
		this.sociedad = sociedad;
	}

	public String getTema() {
		return this.tema;
	}

	public void setTema(String tema) {
		this.tema = tema;
	}

	public String getTemaDesc() {
		return this.temaDesc;
	}

	public void setTemaDesc(String temaDesc) {
		this.temaDesc = temaDesc;
	}

	public short getTiempoLimite() {
		return this.tiempoLimite;
	}

	public void setTiempoLimite(short tiempoLimite) {
		this.tiempoLimite = tiempoLimite;
	}

	public boolean getTienePassmark() {
		return this.tienePassmark;
	}

	public void setTienePassmark(boolean tienePassmark) {
		this.tienePassmark = tienePassmark;
	}

	public String getTipoExamen() {
		return this.tipoExamen;
	}

	public void setTipoExamen(String tipoExamen) {
		this.tipoExamen = tipoExamen;
	}

	public String getTipoExamenDesc() {
		return this.tipoExamenDesc;
	}

	public void setTipoExamenDesc(String tipoExamenDesc) {
		this.tipoExamenDesc = tipoExamenDesc;
	}

	public String getTipoPregunta() {
		return this.tipoPregunta;
	}

	public void setTipoPregunta(String tipoPregunta) {
		this.tipoPregunta = tipoPregunta;
	}

	public String getTipoPreguntaDesc() {
		return this.tipoPreguntaDesc;
	}

	public void setTipoPreguntaDesc(String tipoPreguntaDesc) {
		this.tipoPreguntaDesc = tipoPreguntaDesc;
	}

	public String getTitulo() {
		return this.titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getVisibilidad() {
		return this.visibilidad;
	}

	public void setVisibilidad(String visibilidad) {
		this.visibilidad = visibilidad;
	}

	public String getVisibilidadDesc() {
		return this.visibilidadDesc;
	}

	public void setVisibilidadDesc(String visibilidadDesc) {
		this.visibilidadDesc = visibilidadDesc;
	}

}