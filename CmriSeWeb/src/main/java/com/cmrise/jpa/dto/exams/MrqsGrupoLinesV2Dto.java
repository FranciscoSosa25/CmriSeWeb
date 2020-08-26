package com.cmrise.jpa.dto.exams;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the MRQS_GRUPO_LINES_V2 database table.
 * 
 */
@Entity
@Table(name="MRQS_GRUPO_LINES_V2")
@NamedQuery(name="MrqsGrupoLinesV2Dto.findAll", query="SELECT m FROM MrqsGrupoLinesV2Dto m")
public class MrqsGrupoLinesV2Dto  implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="NUMERO")
	private long numero;
	
	//@Column(name="TITULO_GRUPO")
	//private String tituloGrupo; 
	
	@Column(name="ACTUALIZADO_POR")
	private long actualizadoPor;

	@Column(name="CREADO_POR")
	private long creadoPor;

	@Column(name="FECHA_ACTUALIZACION")
	private Timestamp fechaActualizacion;

	@Column(name="FECHA_CREACION")
	private Timestamp fechaCreacion;

	@Column(name="NUMERO_HDR")
	private long numeroHdr; 
	
	@Column(name="NUMERO_PREGUNTA")
	private long numeroPregunta; 
		
	@Column(name="TIPO_PREGUNTA")
	private String tipoPregunta; 
	
	@Column(name="TIPO_PREGUNTA_DESC")
	private String tipoPreguntaDesc; 
	
	@Column(name="ESTATUS")
	private String estatus;
	
	@Column(name="ESTATUS_DESC")
	private String estatusDesc;
	
	@Column(name="ADMON_MATERIA_DESC")
	private String admonMateriaDesc; 
	
	@Column(name="ADMON_SUBMATERIA")
	private long admonSubmateria; 
	
	@Column(name="ADMON_SUBMATERIA_DESC")
	private String admonSubmateriaDesc; 
	
	@Column(name="DIAGNOSTICO")
	private String diagnostico; 
	
	@Column(name="TEXTO_PREGUNTA")
	private String textoPregunta; 
	
	@Column(name="TEXTO_SUGERENCIAS")
	private String textoSugerencias; 
	
	@Column(name="SINGLE_ANSWER_MODE")
	private boolean singleAnswerMode;
	
	@Column(name="SUFFLE_ANSWER_ORDER")
	private boolean suffleAnswerOrder;
	
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

	public long getNumeroHdr() {
		return numeroHdr;
	}

	public void setNumeroHdr(long numeroHdr) {
		this.numeroHdr = numeroHdr;
	}

	public long getNumeroPregunta() {
		return numeroPregunta;
	}

	public void setNumeroPregunta(long numeroPregunta) {
		this.numeroPregunta = numeroPregunta;
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

	public String getEstatusDesc() {
		return estatusDesc;
	}

	public void setEstatusDesc(String estatusDesc) {
		this.estatusDesc = estatusDesc;
	}

	public String getTextoPregunta() {
		return textoPregunta;
	}

	public void setTextoPregunta(String textoPregunta) {
		this.textoPregunta = textoPregunta;
	}

	public String getTextoSugerencias() {
		return textoSugerencias;
	}

	public void setTextoSugerencias(String textoSugerencias) {
		this.textoSugerencias = textoSugerencias;
	}

	/*public String getTituloGrupo() {
		return tituloGrupo;
	}

	public void setTituloGrupo(String tituloGrupo) {
		this.tituloGrupo = tituloGrupo;
	}*/

	public boolean isSingleAnswerMode() {
		return singleAnswerMode;
	}

	public void setSingleAnswerMode(boolean singleAnswerMode) {
		this.singleAnswerMode = singleAnswerMode;
	}

	public boolean isSuffleAnswerOrder() {
		return suffleAnswerOrder;
	}

	public void setSuffleAnswerOrder(boolean suffleAnswerOrder) {
		this.suffleAnswerOrder = suffleAnswerOrder;
	}

	public long getAdmonSubmateria() {
		return admonSubmateria;
	}

	public void setAdmonSubmateria(long admonSubmateria) {
		this.admonSubmateria = admonSubmateria;
	}

	public String getDiagnostico() {
		return diagnostico;
	}

	public void setDiagnostico(String diagnostico) {
		this.diagnostico = diagnostico;
	}

	public String getAdmonMateriaDesc() {
		return admonMateriaDesc;
	}

	public void setAdmonMateriaDesc(String admonMateriaDesc) {
		this.admonMateriaDesc = admonMateriaDesc;
	}

	public String getAdmonSubmateriaDesc() {
		return admonSubmateriaDesc;
	}

	public void setAdmonSubmateriaDesc(String admonSubmateriaDesc) {
		this.admonSubmateriaDesc = admonSubmateriaDesc;
	}
	

}
