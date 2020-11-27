package com.cmrise.ejb.model.corecases;

import java.io.Serializable;

public class CcHdrForAction implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private long numero;
	private String nombre;
	private String estatus; 
	private String estatusDesc; 
	private String tema; 
	private String temaDesc;
	
	public long getNumero() {
		return numero;
	}
	public void setNumero(long numero) {
		this.numero = numero;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
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
	public String getTema() {
		return tema;
	}
	public void setTema(String tema) {
		this.tema = tema;
	}
	public String getTemaDesc() {
		return temaDesc;
	}
	public void setTemaDesc(String temaDesc) {
		this.temaDesc = temaDesc;
	} 
    
	 @Override
	 public int hashCode() {
		 int hash = 0;
		 if(this.numero!=0l) {
		   Long longNumero = new Long(this.numero);
		   hash=hash+longNumero.hashCode(); 
		 }
	     return hash;
	 }
	 
	 @Override
	 public boolean equals(Object object) {
		 if (!(object instanceof CcHdrForAction)) {
	            return false;
	      }
		 CcHdrForAction other = (CcHdrForAction) object;
		 Long longNumero = new Long(this.numero);
		 Long longOtherNumero = new Long(other.getNumero());
		 if ((longNumero == null && longOtherNumero != null)
		    || (longNumero != null && !longNumero.equals(longOtherNumero))
		    ) {
	            return false;
	        }
		 return true;
	 }
	
}
