package com.cmrise.jpa.dto.corecases.img;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="CC_IMAGENES")
@NamedQuery(name="CcImagenesDto.findAll", query="SELECT c FROM CcImagenesDto c")
public class CcImagenesDto implements Serializable {

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

	@Column(name="NOMBRE_IMAGEN")
	private String nombreImagen; 
	
	@Column(name="RUTA_IMAGEN")
	private String rutaImagen; 
	
	@Column(name="NUMERO_GRP")
	private long numeroGrp; 
	
	@Column(name="POLIGONO_MODEL")
	private String polygonoModel;
	
	@Column(name="HEIGHT")
	private Long height;
	
	@Column(name="WIDTH")
	private Long width;
	
	@Column(name="POLIGONOS")
	private Long poligonos; 

	
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

	public String getNombreImagen() {
		return nombreImagen;
	}

	public void setNombreImagen(String nombreImagen) {
		this.nombreImagen = nombreImagen;
	}

	public String getRutaImagen() {
		return rutaImagen;
	}

	public void setRutaImagen(String rutaImagen) {
		this.rutaImagen = rutaImagen;
	}

	public long getNumeroGrp() {
		return numeroGrp;
	}

	public void setNumeroGrp(long numeroGrp) {
		this.numeroGrp = numeroGrp;
	}

	public String getPolygonoModel() {
		return polygonoModel;
	}

	public void setPolygonoModel(String poligonoModel) {
		this.polygonoModel = poligonoModel;
	}

	public Long getHeight() {
		return height;
	}

	public void setHeight(Long height) {
		this.height = height;
	}

	public Long getWidth() {
		return width;
	}

	public void setWidth(Long width) {
		this.width = width;
	}

	public Long getPoligonos() {
		return poligonos;
	}

	public void setPoligonos(Long poligons) {
		this.poligonos = poligons;
	}

	
	
	

}

