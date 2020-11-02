package com.cmrise.jpa.dto.mrqs;

import java.io.Serializable;
import javax.persistence.*;

import java.sql.Date;
import java.sql.Timestamp;


/**
 * The persistent class for the MRQS_PREGUNTAS_HDR_V1 database table.
 * 
 */
@Entity
@Table(name="MRQS_PREGUNTAS_HDR_V1")
@NamedQuery(name="MrqsPreguntasHdrV1Dto.findAll", query="SELECT m FROM MrqsPreguntasHdrV1Dto m")
public class MrqsPreguntasHdrV1Dto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="ACTUALIZADO_POR")
	private long actualizadoPor;

	@Column(name="NOTAS")
	private String notas;

	@Column(name="CREADO_POR")
	private long creadoPor;

	@Column(name="ESTATUS")
	private String estatus;

	@Column(name="ESTATUS_DESC")
	private String estatusDesc;

	@Column(name="DIAGNOSTICO")
	private String diagnostico;

	@Column(name="FECHA_ACTUALIZACION")
	private Timestamp fechaActualizacion;

	@Column(name="FECHA_CREACION")
	private Timestamp fechaCreacion;

	@Column(name="FECHA_EFECTIVA_DESDE")
	private Date fechaEfectivaDesde;

	@Column(name="FECHA_EFECTIVA_HASTA")
	private Date fechaEfectivaHasta;

	@Column(name="ADMON_EXAMEN")
	private long admonExamen;

	@Id
	@Column(name="NUMERO")
	private long numero;

	@Column(name="SOCIEDAD")
	private String sociedad;

	@Column(name="ADMON_SUBMATERIA")
	private long admonSubmateria;

	@Column(name="TIPO_PREGUNTA")
	private String tipoPregunta;

	@Column(name="TIPO_PREGUNTA_DESC")
	private String tipoPreguntaDesc;

	@Column(name="ADMON_MATERIA")
	private long admonMateria;
	
	@Column(name="ADMON_EXAMEN_DESC")
	private String admonExamenDesc;
	
	@Column(name="ADMON_MATERIA_DESC")
	private String admonMateriaDesc;
	
	@Column(name="ADMON_SUBMATERIA_DESC")
	private String admonSubmateriaDesc; 

	@Column(name="FECHA_ELABORACION")
	private Date fechaElaboracion; 
	
	@Column(name="BIBLIOGRAFIA")
	private String bibliografia; 
	
	public MrqsPreguntasHdrV1Dto() {
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

	public long getNumero() {
		return this.numero;
	}

	public void setNumero(long numero) {
		this.numero = numero;
	}

	public String getSociedad() {
		return this.sociedad;
	}

	public void setSociedad(String sociedad) {
		this.sociedad = sociedad;
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

	public long getAdmonExamen() {
		return admonExamen;
	}

	public void setAdmonExamen(long admonExamen) {
		this.admonExamen = admonExamen;
	}

	public long getAdmonMateria() {
		return admonMateria;
	}

	public void setAdmonMateria(long admonMateria) {
		this.admonMateria = admonMateria;
	}

	public long getAdmonSubmateria() {
		return admonSubmateria;
	}

	public void setAdmonSubmateria(long admonSubmateria) {
		this.admonSubmateria = admonSubmateria;
	}

	public String getAdmonExamenDesc() {
		return admonExamenDesc;
	}

	public void setAdmonExamenDesc(String admonExamenDesc) {
		this.admonExamenDesc = admonExamenDesc;
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

	public String getNotas() {
		return notas;
	}

	public void setNotas(String notas) {
		this.notas = notas;
	}

	public String getDiagnostico() {
		return diagnostico;
	}

	public void setDiagnostico(String diagnostico) {
		this.diagnostico = diagnostico;
	}

	public Date getFechaElaboracion() {
		return fechaElaboracion;
	}

	public void setFechaElaboracion(Date fechaElaboracion) {
		this.fechaElaboracion = fechaElaboracion;
	}

	public String getBibliografia() {
		return bibliografia;
	}

	public void setBibliografia(String bibliografia) {
		this.bibliografia = bibliografia;
	}


}