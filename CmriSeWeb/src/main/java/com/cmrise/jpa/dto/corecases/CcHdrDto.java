package com.cmrise.jpa.dto.corecases;

import java.io.Serializable;
import javax.persistence.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the CC_HDR database table.
 * 
 */
@Entity
@Table(name="CC_HDR")
@NamedQuery(name="CcHdrDto.findAll", query="SELECT c FROM CcHdrDto c")
public class CcHdrDto implements Serializable {
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

	@Column(name="FECHA_EFECTIVA_DESDE")
	private Date fechaEfectivaDesde;

	@Column(name="FECHA_EFECTIVA_HASTA")
	private Date fechaEfectivaHasta;

	@Column(name="NOMBRE")
	private String nombre;
	
	@Column(name="TEMA")
	private String tema; 
	
	@Column(name="HISTORIAL_CLINICO")
	private String historialClinico; 
	
	@Column(name="DESCRIPCION_TECNICA")
	private String descripcionTecnica; 
	
	
	@Column(name="OPCION_INSEGURA")
	private boolean opcionInsegura; 
	
	@Column(name="ETIQUETAS")
	private String etiquetas; 
	
	@Column(name="NOTA")
	private String nota; 
	
	@Column(name="ESTATUS")
	private String estatus;
	
	@Column(name="SOCIEDAD")
	private String sociedad;
	
	//bi-directional many-to-one association to CcPreguntasHdrDto
	@OneToMany(mappedBy="ccHdr")
	private List<CcPreguntasHdrDto> ccPreguntasHdrs;

	public CcHdrDto() {
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

	public List<CcPreguntasHdrDto> getCcPreguntasHdrs() {
		return this.ccPreguntasHdrs;
	}

	public void setCcPreguntasHdrs(List<CcPreguntasHdrDto> ccPreguntasHdrs) {
		this.ccPreguntasHdrs = ccPreguntasHdrs;
	}

	public CcPreguntasHdrDto addCcPreguntasHdr(CcPreguntasHdrDto ccPreguntasHdr) {
		getCcPreguntasHdrs().add(ccPreguntasHdr);
		ccPreguntasHdr.setCcHdr(this);

		return ccPreguntasHdr;
	}

	public CcPreguntasHdrDto removeCcPreguntasHdr(CcPreguntasHdrDto ccPreguntasHdr) {
		getCcPreguntasHdrs().remove(ccPreguntasHdr);
		ccPreguntasHdr.setCcHdr(null);

		return ccPreguntasHdr;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTema() {
		return tema;
	}

	public void setTema(String tema) {
		this.tema = tema;
	}

	public String getHistorialClinico() {
		return historialClinico;
	}

	public void setHistorialClinico(String historialClinico) {
		this.historialClinico = historialClinico;
	}

	public String getDescripcionTecnica() {
		return descripcionTecnica;
	}

	public void setDescripcionTecnica(String descripcionTecnica) {
		this.descripcionTecnica = descripcionTecnica;
	}

	public boolean getOpcionInsegura() {
		return opcionInsegura;
	}

	public void setOpcionInsegura(boolean opcionInsegura) {
		this.opcionInsegura = opcionInsegura;
	}

	public String getEtiquetas() {
		return etiquetas;
	}

	public void setEtiquetas(String etiquetas) {
		this.etiquetas = etiquetas;
	}

	public String getNota() {
		return nota;
	}

	public void setNota(String nota) {
		this.nota = nota;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public String getSociedad() {
		return sociedad;
	}

	public void setSociedad(String sociedad) {
		this.sociedad = sociedad;
	}

}