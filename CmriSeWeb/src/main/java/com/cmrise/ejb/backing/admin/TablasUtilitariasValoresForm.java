package com.cmrise.ejb.backing.admin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.PrimeFaces;

import com.cmrise.ejb.model.admin.TablasUtilitariasValores;
import com.cmrise.ejb.services.admin.TablasUtilitariasValoresLocal;
import com.cmrise.jpa.dto.admin.TablasUtilitariasValoresDto;

@ManagedBean
@ViewScoped
public class TablasUtilitariasValoresForm {
	
	private List<TablasUtilitariasValores> listTabUtilVal = new ArrayList<TablasUtilitariasValores>();

	private int countRec=1;
	private java.sql.Date endOfTime = java.sql.Date.valueOf("9999-12-31");
	private String tipoTabla; 
	private String buscarTipoTabla; 
	private long numeroForAction; 
	
	@Inject 
	TablasUtilitariasValoresLocal tablasUtilitariasValoresLocal; 
	
    public void increment() {
    	countRec++;
    }
    
	public void nuevo() {
		System.out.println("Entra nuevo");
		listTabUtilVal = new ArrayList<TablasUtilitariasValores>();
		TablasUtilitariasValores tabUtilVal = new TablasUtilitariasValores(); 
		/**tabUtilVal.setNumero((long)countRec);**/
		listTabUtilVal.add(tabUtilVal); 
		increment();
		System.out.println("Sale nuevo");
	}
	
	public void guardar() {
		System.out.println("Entra guardar");
		if(null!=listTabUtilVal) {
			if(listTabUtilVal.size()>0) {
				Iterator<TablasUtilitariasValores> iterTUV = listTabUtilVal.iterator();
				while(iterTUV.hasNext()) {
					TablasUtilitariasValores  tablasUtilitariasValores = iterTUV.next(); 
					System.out.println(tablasUtilitariasValores.getNumero());
					if(0l==tablasUtilitariasValores.getNumero()) {
					java.util.Date sysDate = new java.util.Date(); 
					java.sql.Timestamp sqlSysDate = new java.sql.Timestamp(sysDate.getTime());
					TablasUtilitariasValoresDto tablasUtilitariasValoresDto = new TablasUtilitariasValoresDto(); 
					tablasUtilitariasValoresDto.setTipoTabla(this.tipoTabla);
					tablasUtilitariasValoresDto.setCodigoTabla(tablasUtilitariasValores.getCodigoTabla());
					tablasUtilitariasValoresDto.setSignificado(tablasUtilitariasValores.getSignificado());
					tablasUtilitariasValoresDto.setDescripcion(tablasUtilitariasValores.getDescripcion());
					tablasUtilitariasValoresDto.setEstado(tablasUtilitariasValores.getEstado());
					tablasUtilitariasValoresDto.setFechaEfectivaDesde(new java.sql.Date(tablasUtilitariasValores.getFechaEfectivaDesde().getTime()));
					if(null!=tablasUtilitariasValores.getFechaEfectivaHasta()) {
						tablasUtilitariasValoresDto.setFechaEfectivaHasta(new java.sql.Date(tablasUtilitariasValores.getFechaEfectivaHasta().getTime()));
					}else {
						tablasUtilitariasValoresDto.setFechaEfectivaHasta(endOfTime);
					}
					
					tablasUtilitariasValoresDto.setFechaCreacion(sqlSysDate);
					tablasUtilitariasValoresDto.setFechaActualizacion(sqlSysDate); 
					tablasUtilitariasValoresDto.setCreadoPor((short)-1);
					tablasUtilitariasValoresDto.setActualizadoPor((short)-1);
					tablasUtilitariasValoresLocal.insert(tablasUtilitariasValoresDto);
					}else {
						System.out.println("Registro por Actualizar");
						TablasUtilitariasValoresDto tablasUtilitariasValoresDto = new TablasUtilitariasValoresDto(); 
						
						tablasUtilitariasValoresDto.setCodigoTabla(tablasUtilitariasValores.getCodigoTabla());
						tablasUtilitariasValoresDto.setDescripcion(tablasUtilitariasValores.getDescripcion());
						tablasUtilitariasValoresDto.setSignificado(tablasUtilitariasValores.getSignificado());
						java.sql.Date sqlFechaDesde = new java.sql.Date(tablasUtilitariasValores.getFechaEfectivaDesde().getTime());
						tablasUtilitariasValoresDto.setFechaEfectivaDesde(sqlFechaDesde);
						if(null!=tablasUtilitariasValores.getFechaEfectivaHasta()) {
							tablasUtilitariasValoresDto.setFechaEfectivaHasta(new java.sql.Date(tablasUtilitariasValores.getFechaEfectivaHasta().getTime()));
						}else {
							tablasUtilitariasValoresDto.setFechaEfectivaHasta(endOfTime);
						}
						tablasUtilitariasValoresDto.setEstado(tablasUtilitariasValores.getEstado());
						
						tablasUtilitariasValoresLocal.update(tablasUtilitariasValores.getNumero(), tablasUtilitariasValoresDto);
					}
				}
			}
		}
		System.out.println("Sale guardar");
	}
	
	public void registroAnterior() {
		System.out.println("Entra registroAnterior");
		System.out.println("Sale registroAnterior");
	}
	
    public void registroSiguiente() {
    	System.out.println("Entra registroSiguiente");
		System.out.println("Sale registroSiguiente");
	}
    
    public void agregarLinea() {
    	System.out.println("Entra agregarLinea");
    	TablasUtilitariasValores tabUtilVal = new TablasUtilitariasValores(); 
		/*tabUtilVal.setNumero((long)countRec);*/
		listTabUtilVal.add(tabUtilVal); 
		increment();
		System.out.println("Sale agregarLinea");
    }
    
    public void buscar() {
    	System.out.println("Entra buscar");
    	List<TablasUtilitariasValoresDto> listTabUtiValDto  = tablasUtilitariasValoresLocal.findByTipoTabla(buscarTipoTabla); 
    	listTabUtilVal = new ArrayList<TablasUtilitariasValores>();
    	Iterator<TablasUtilitariasValoresDto> iterTabUtiValDto = listTabUtiValDto.iterator(); 
		while(iterTabUtiValDto.hasNext()) {
			TablasUtilitariasValoresDto tablasUtilitariasValoresDto = iterTabUtiValDto.next();
			TablasUtilitariasValores tablasUtilitariasValores = new TablasUtilitariasValores(); 
			
			tablasUtilitariasValores.setNumero(tablasUtilitariasValoresDto.getNumero());
			tablasUtilitariasValores.setTipoTabla(tablasUtilitariasValoresDto.getTipoTabla());
			tablasUtilitariasValores.setCodigoTabla(tablasUtilitariasValoresDto.getCodigoTabla());
			tablasUtilitariasValores.setSignificado(tablasUtilitariasValoresDto.getSignificado());
			tablasUtilitariasValores.setDescripcion(tablasUtilitariasValoresDto.getDescripcion());
			tablasUtilitariasValores.setEstado(tablasUtilitariasValoresDto.getEstado());
			tablasUtilitariasValores.setFechaEfectivaDesde(tablasUtilitariasValoresDto.getFechaEfectivaDesde());
			java.util.Date utilFechaHasta = new java.util.Date(tablasUtilitariasValoresDto.getFechaEfectivaHasta().getTime());
			if(null!=utilFechaHasta) {
				if(0==utilFechaHasta.compareTo(endOfTime)) {
					tablasUtilitariasValores.setFechaEfectivaHasta(null);
				}else {
					tablasUtilitariasValores.setFechaEfectivaHasta(tablasUtilitariasValoresDto.getFechaEfectivaHasta());
				}
			}
			this.setTipoTabla(tablasUtilitariasValoresDto.getTipoTabla());
			listTabUtilVal.add(tablasUtilitariasValores);
			
		}
    	System.out.println("Sale buscar");
    }

    public void selectForAction(TablasUtilitariasValores pTablasUtilitariasValores) {
    	this.setNumeroForAction(pTablasUtilitariasValores.getNumero());
    }
    
    public void delete() {
    	boolean deleteIn = false; 
    	System.out.println("this.getNumeroForAction():"+this.getNumeroForAction());
    	if(0!=this.getNumeroForAction()) {
    	tablasUtilitariasValoresLocal.delete(this.getNumeroForAction());
    	}
    	buscar(); 
		deleteIn = true; 
		PrimeFaces.current().ajax().addCallbackParam("deleteIn", deleteIn);
    }
    
	public List<TablasUtilitariasValores> getListTabUtilVal() {
		return listTabUtilVal;
	}

	public void setListTabUtilVal(List<TablasUtilitariasValores> listTabUtilVal) {
		this.listTabUtilVal = listTabUtilVal;
	}

	public int getCountRec() {
        return countRec;
    }

	public java.sql.Date getEndOfTime() {
		return endOfTime;
	}

	public void setEndOfTime(java.sql.Date endOfTime) {
		this.endOfTime = endOfTime;
	}

	public String getTipoTabla() {
		return tipoTabla;
	}

	public void setTipoTabla(String tipoTabla) {
		this.tipoTabla = tipoTabla;
	}

	public String getBuscarTipoTabla() {
		return buscarTipoTabla;
	}

	public void setBuscarTipoTabla(String buscarTipoTabla) {
		this.buscarTipoTabla = buscarTipoTabla;
	}

	public long getNumeroForAction() {
		return numeroForAction;
	}

	public void setNumeroForAction(long numeroForAction) {
		this.numeroForAction = numeroForAction;
	}
	
}

