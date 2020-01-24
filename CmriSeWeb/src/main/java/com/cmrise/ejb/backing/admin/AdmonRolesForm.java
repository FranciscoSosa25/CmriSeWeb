package com.cmrise.ejb.backing.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.PrimeFaces;

import com.cmrise.ejb.model.admin.AdmonRoles;
import com.cmrise.ejb.services.admin.AdmonRolesLocal;
import com.cmrise.jpa.dto.admin.AdmonRolesDto;
import com.cmrise.utils.Utilitarios;
import com.cmrise.utils.UtilitariosLocal;

@ManagedBean
@ViewScoped
public class AdmonRolesForm {

	private String nombre; 
	private String descripcion; 
	private Date fechaEfectivaDesde;
	private Date fechaEfectivaHasta;
	private List<AdmonRoles> listAdmonRoles = new ArrayList<AdmonRoles>();
	private AdmonRoles admonRolesForAction = new AdmonRoles();
	
	@Inject
	AdmonRolesLocal admonRolesLocal;
	
	@Inject 
	UtilitariosLocal utilitariosLocal;
	
	 @PostConstruct
	 public void init() {
		 System.out.println("Entra AdmonRolesForm init()");
		 refreshEntity();
		 System.out.println("Sale AdmonRolesForm init()");
	 }		 
	
	public void refreshEntity() {
		 List<AdmonRolesDto> listAdmonRolesDto = admonRolesLocal.findAll(); 
		 Iterator<AdmonRolesDto> iterAdmonRolesDto = listAdmonRolesDto.iterator(); 
		 listAdmonRoles = new ArrayList<AdmonRoles>();
		 while(iterAdmonRolesDto.hasNext()) {
			 AdmonRolesDto admonRolesDto = iterAdmonRolesDto.next(); 
			 AdmonRoles admonRoles= new AdmonRoles(); 
			 admonRoles.setNumero(admonRolesDto.getNumero());
			 admonRoles.setNombre(admonRolesDto.getNombre());
			 admonRoles.setDescripcion(admonRolesDto.getDescripcion());
			 admonRoles.setFechaEfectivaDesde(utilitariosLocal.toUtilDate(admonRolesDto.getFechaEfectivaDesde()));
			 if(null!=admonRolesDto.getFechaEfectivaHasta()) {
			  if(Utilitarios.endOfTime.equals(admonRolesDto.getFechaEfectivaHasta())) {
			    admonRoles.setFechaEfectivaHasta(null);
			  }else {
				 admonRoles.setFechaEfectivaHasta(utilitariosLocal.toUtilDate(admonRolesDto.getFechaEfectivaHasta()));
			  }
			 }
			 listAdmonRoles.add(admonRoles); 
		 }
	}
	 
	public void create() {
	 System.out.println("Entra AdmonRolesForm Create");
	 boolean createIn = false; 
	 AdmonRolesDto admonRolesDto = new AdmonRolesDto();
	 admonRolesDto.setNombre(nombre);
	 admonRolesDto.setDescripcion(descripcion);
	 java.sql.Date sqlFechaEfectivaDesde = null; 
	 java.sql.Date sqlFechaEfectivaHasta = null; 
	 if(null!=fechaEfectivaDesde) {
	   sqlFechaEfectivaDesde = new java.sql.Date(fechaEfectivaDesde.getTime());
	   admonRolesDto.setFechaEfectivaDesde(sqlFechaEfectivaDesde);
	 }
	 
	 if(null!=fechaEfectivaHasta) {
		 sqlFechaEfectivaHasta = new java.sql.Date(fechaEfectivaHasta.getTime());
	 }else {
		 sqlFechaEfectivaHasta = Utilitarios.endOfTime;
	 }
	 admonRolesDto.setFechaEfectivaHasta(sqlFechaEfectivaHasta);
	 admonRolesLocal.insert(admonRolesDto);
	 refreshEntity();
	 createIn = true;
	 PrimeFaces.current().ajax().addCallbackParam("createIn", createIn);
		
	 System.out.println("Sale AdmonRolesForm Create");
	}

	public void selectForAction(AdmonRoles pAdmonRoles) {
		admonRolesForAction.setNumero(pAdmonRoles.getNumero());
		admonRolesForAction.setNombre(pAdmonRoles.getNombre());
		admonRolesForAction.setDescripcion(pAdmonRoles.getDescripcion());
		admonRolesForAction.setFechaEfectivaDesde(pAdmonRoles.getFechaEfectivaDesde());
		admonRolesForAction.setFechaEfectivaHasta(pAdmonRoles.getFechaEfectivaHasta());
	}
	
	public void update() {
		boolean updateIn = false; 
		AdmonRolesDto admonRolesDto = new AdmonRolesDto();
		admonRolesDto.setNombre(admonRolesForAction.getNombre());
		admonRolesDto.setDescripcion(admonRolesForAction.getDescripcion());
		 java.sql.Date sqlFechaEfectivaDesde = null; 
		 java.sql.Date sqlFechaEfectivaHasta = null; 
		if(null!=admonRolesForAction.getFechaEfectivaDesde()) {
			sqlFechaEfectivaDesde = utilitariosLocal.toSqlDate(admonRolesForAction.getFechaEfectivaDesde());
		}
		if(null!=admonRolesForAction.getFechaEfectivaHasta()) {
			sqlFechaEfectivaHasta = utilitariosLocal.toSqlDate(admonRolesForAction.getFechaEfectivaHasta());
		}else {
			 sqlFechaEfectivaHasta = Utilitarios.endOfTime;
		 }
		admonRolesDto.setFechaEfectivaDesde(sqlFechaEfectivaDesde);
		admonRolesDto.setFechaEfectivaHasta(sqlFechaEfectivaHasta);
		admonRolesLocal.update(admonRolesForAction.getNumero(), admonRolesDto);
		refreshEntity();
		updateIn = true;
	    PrimeFaces.current().ajax().addCallbackParam("updateIn", updateIn);
	}
	
	public void delete() {
		boolean deleteIn = false; 
		admonRolesLocal.delete(admonRolesForAction.getNumero());
	    refreshEntity();
	    deleteIn = true;
	    PrimeFaces.current().ajax().addCallbackParam("deleteIn", deleteIn);
			
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFechaEfectivaDesde() {
		return fechaEfectivaDesde;
	}

	public void setFechaEfectivaDesde(Date fechaEfectivaDesde) {
		this.fechaEfectivaDesde = fechaEfectivaDesde;
	}

	public Date getFechaEfectivaHasta() {
		return fechaEfectivaHasta;
	}

	public void setFechaEfectivaHasta(Date fechaEfectivaHasta) {
		this.fechaEfectivaHasta = fechaEfectivaHasta;
	}

	public List<AdmonRoles> getListAdmonRoles() {
		return listAdmonRoles;
	}

	public void setListAdmonRoles(List<AdmonRoles> listAdmonRoles) {
		this.listAdmonRoles = listAdmonRoles;
	}

	public AdmonRoles getAdmonRolesForAction() {
		return admonRolesForAction;
	}

	public void setAdmonRolesForAction(AdmonRoles admonRolesForAction) {
		this.admonRolesForAction = admonRolesForAction;
	}
	
}
