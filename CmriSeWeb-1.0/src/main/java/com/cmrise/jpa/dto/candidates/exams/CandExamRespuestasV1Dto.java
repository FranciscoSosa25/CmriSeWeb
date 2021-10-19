package com.cmrise.jpa.dto.candidates.exams;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="CAND_EXAM_RESPUESTAS_V1")
@NamedQuery(name="CandExamRespuestasV1Dto.findAll", query="SELECT c FROM CandExamRespuestasV1Dto c")
public class CandExamRespuestasV1Dto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="NUMERO")
	private long numero;
	
	@Column(name="ACTUALIZADO_POR")
	private long actualizadoPor;

	@Column(name="CREADO_POR")
	private long creadoPor;

	@Column(name="FECHA_ACTUALIZACION")
	private Timestamp fechaActualizacion;

	@Column(name="FECHA_CREACION")
	private Timestamp fechaCreacion;
	
	@Column(name="NUMERO_CAND_EXAMEN")
	private long numeroCandExamen; 
	
	@Column(name="NUMERO_GRUPO")
	private long numeroGrupo; 
	
	@Column(name="NUMERO_PREGUNTA_HDR")
	private long numeroPreguntaHdr; 
	
	@Column(name="NUMERO_PREGUNTA_FTA")
	private long numeroPreguntaFta; 
	
	@Column(name="RESPUESTA")
	private String respuesta; 
	
	@Column(name="VALOR_PUNTUACION")
	private double valorPuntuacion;
	
	@Column(name="PUNTUACION")
	private double puntuacion; 
	
	@Column(name="NUMERO_EXAMEN")
	private long numeroExamen;
	
	@Column(name="DURATION")
	private Long duration;
	
	@Column(name="NUMERO_USUARIO")
	private long numeroUsuario; 
	
	@Column(name="TIPO_EXAMEN")
	private String tipoExamen; 
	
	@Column(name="TITULO_PREGUNTA")
	private String tituloPregunta; 
	
	@Column(name="TIPO_PREGUNTA")
	private String tipoPregunta; 
	
	@Column(name="TIPO_PREGUNTA_DESC")
	private String tipoPreguntaDesc; 
	
	@Column(name="ESTATUS")
	private String estatus; 
	
	@Column(name="NUM_OPC_CORRECTAS")
	private int numOpcCorrectas; 
	
	@Column(name="NUM_OPC_INCORRECTAS")
	private int numOpcIncorrectas; 
	
	
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

	
	
}
