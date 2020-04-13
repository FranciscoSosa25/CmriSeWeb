package com.cmrise.ejb.backing.exams.mrqs;



import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.PrimeFaces;

import com.cmrise.ejb.model.admin.AdmonCandidatosV1;
import com.cmrise.ejb.model.admin.AdmonUsuarios;
import com.cmrise.ejb.services.admin.AdmonUsuariosLocal;
import com.cmrise.ejb.services.admin.AdmonUsuariosRolesLocal;
import com.cmrise.jpa.dto.admin.AdmonCandidatosV1Dto;
import com.cmrise.jpa.dto.admin.AdmonRolesDto;
import com.cmrise.jpa.dto.admin.AdmonUsuariosDto;
import com.cmrise.jpa.dto.admin.AdmonUsuariosRolesDto;
import com.cmrise.utils.Utilitarios;
import com.cmrise.utils.UtilitariosLocal;

@ManagedBean
@ViewScoped
public class CreateMrqsCandidatesForm {
	
	    private List<AdmonCandidatosV1> listAdmonCandidatosV1 = new ArrayList<AdmonCandidatosV1>();
	    private long numeroRol;
		private long numeroUsuario;
		private Date fechaEfectivaDesde;
		private Date fechaEfectivaHasta;
	    private String nombre; 
	    private String apellidoPaterno; 
	    private String apellidoMaterno; 
	    private String contrasenia; 
	    private String correoElectronico;
	    private String curp; 
		private AdmonUsuarios admonUsuariosForAction = new AdmonUsuarios();
	    
	    @Inject 
		AdmonUsuariosLocal admonUsuariosLocal; 
	    
		@Inject
		AdmonUsuariosRolesLocal admonUsuariosRolesLocal; 

		@Inject 
		UtilitariosLocal utilitariosLocal;
		
	    @PostConstruct
		 public void init() {
	    	System.out.println("Entra Init");
				 refreshEntity();
				 System.out.println("Sale Init");
		 }		
		
	    public void refreshEntity() {
	    	 List<AdmonCandidatosV1Dto> listAdmonCandidatosV1Dto = admonUsuariosLocal.findAll(); 
			 Iterator<AdmonCandidatosV1Dto> itertAdmonCandidatosV1Dto = listAdmonCandidatosV1Dto.iterator();
				while(itertAdmonCandidatosV1Dto.hasNext()) {
				AdmonCandidatosV1Dto admonCandidatosV1Dto = itertAdmonCandidatosV1Dto.next(); 
				AdmonCandidatosV1 admonCandidatosV1 = new AdmonCandidatosV1();
				System.out.println("Entra admonCandidatos");
				admonCandidatosV1.setNumeroUsuario(admonCandidatosV1Dto.getNumeroUsuario());
				admonCandidatosV1.setNumeroRol(admonCandidatosV1Dto.getNumeroRol());
				admonCandidatosV1.setNombreCompletoUsuario(admonCandidatosV1Dto.getNombreCompletoUsuario());
				admonCandidatosV1.setCorreoElectronico(admonCandidatosV1Dto.getCorreoElectronico());
				admonCandidatosV1.setCurp(admonCandidatosV1Dto.getCurp());
				
				System.out.println("admonCandidatosV1.getNumeroRol()");
				System.out.println("admonCandidatosV1.getCorreoElectronico()");
				System.out.println("admonCandidatosV1.getCurp()");
				System.out.println("Sale admonCandidatos");
				listAdmonCandidatosV1.add(admonCandidatosV1);
	    
			}
	    }
	    
		public void create() {
			AdmonUsuariosDto admonUsuariosDto = new AdmonUsuariosDto();
			 boolean createIn = false; 
			admonUsuariosDto.setCurp(this.curp);
			admonUsuariosDto.setNombre(this.nombre);
			admonUsuariosDto.setApellidoPaterno(this.apellidoPaterno);
			admonUsuariosDto.setApellidoMaterno(this.apellidoMaterno);
			admonUsuariosDto.setContrasenia(this.contrasenia);
			admonUsuariosDto.setCorreoElectronico(this.correoElectronico);
			 java.sql.Date sqlFechaEfectivaDesde = null; 
			 java.sql.Date sqlFechaEfectivaHasta = null; 
			 if(null!=fechaEfectivaDesde) {
			   sqlFechaEfectivaDesde = new java.sql.Date(fechaEfectivaDesde.getTime());
			   admonUsuariosDto.setFechaEfectivaDesde(sqlFechaEfectivaDesde);
			 }
			 
			 if(null!=fechaEfectivaHasta) {
				 sqlFechaEfectivaHasta = new java.sql.Date(fechaEfectivaHasta.getTime());
			 }else {
				 sqlFechaEfectivaHasta = Utilitarios.endOfTime;
			 }
			 admonUsuariosDto.setFechaEfectivaHasta(sqlFechaEfectivaHasta);
			 long longNumeroUsusario = admonUsuariosLocal.insert(admonUsuariosDto);
			 
			    /*************************************************************************/
			    AdmonUsuariosRolesDto admonUsuariosRolesDto = new AdmonUsuariosRolesDto();
				AdmonRolesDto admonRolesDto = new AdmonRolesDto();
				AdmonUsuariosDto admonUsuariosV2Dto = new AdmonUsuariosDto();
				System.out.println("longNumeroUsusario:"+longNumeroUsusario);
				System.out.println("this.getNumeroRol():"+this.getNumeroRol());
				admonUsuariosV2Dto.setNumero(longNumeroUsusario);
				admonRolesDto.setNumero(this.getNumeroRol());
				admonUsuariosRolesDto.setAdmonUsuario(admonUsuariosV2Dto);
				admonUsuariosRolesDto.setAdmonRole(admonRolesDto);
				admonUsuariosRolesDto.setFechaEfectivaDesde(utilitariosLocal.toSqlDate(fechaEfectivaDesde));
				if(null!=fechaEfectivaHasta) {
					admonUsuariosRolesDto.setFechaEfectivaHasta(utilitariosLocal.toSqlDate(fechaEfectivaHasta));	
				}else {
					admonUsuariosRolesDto.setFechaEfectivaHasta(Utilitarios.endOfTime);
				}
		     
				admonUsuariosRolesLocal.insert(admonUsuariosRolesDto);	
			    /***********************************************************************/
			 
			 refreshEntity();
			 createIn = true; 
			 PrimeFaces.current().ajax().addCallbackParam("createIn", createIn);
		}
		
		public void selectForAction(AdmonUsuarios pAdmonUsuarios) {
			System.out.println("*pAdmonUsuarios.getNumero():"+pAdmonUsuarios.getNumero());
			admonUsuariosForAction.setNumero(pAdmonUsuarios.getNumero());
			admonUsuariosForAction.setCurp(pAdmonUsuarios.getCurp());
			admonUsuariosForAction.setNombre(pAdmonUsuarios.getNombre());
			admonUsuariosForAction.setApellidoPaterno(pAdmonUsuarios.getApellidoPaterno());
			admonUsuariosForAction.setApellidoMaterno(pAdmonUsuarios.getApellidoMaterno());
			admonUsuariosForAction.setCorreoElectronico(pAdmonUsuarios.getCorreoElectronico());
			admonUsuariosForAction.setFechaEfectivaDesde(pAdmonUsuarios.getFechaEfectivaDesde());
			admonUsuariosForAction.setFechaEfectivaHasta(pAdmonUsuarios.getFechaEfectivaHasta());
			admonUsuariosForAction.setContrasenia(pAdmonUsuarios.getContrasenia());
			admonUsuariosForAction.setNumeroRol(pAdmonUsuarios.getNumeroRol());
		}
		
		
		public void delete() {
			boolean deleteIn = false; 
			admonUsuariosLocal.delete(admonUsuariosForAction.getNumero());
			refreshEntity();
			deleteIn = true; 
			PrimeFaces.current().ajax().addCallbackParam("deleteIn", deleteIn);
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
	

		public List<AdmonCandidatosV1> getListAdmonCandidatosV1() {
			return listAdmonCandidatosV1;
		}


		public void setListAdmonCandidatosV1(List<AdmonCandidatosV1> listAdmonCandidatosV1) {
			this.listAdmonCandidatosV1 = listAdmonCandidatosV1;
		}

		public long getNumeroRol() {
			return numeroRol;
		}

		public void setNumeroRol(long numeroRol) {
			this.numeroRol = numeroRol;
		}

		public long getNumeroUsuario() {
			return numeroUsuario;
		}

		public void setNumeroUsuario(long numeroUsuario) {
			this.numeroUsuario = numeroUsuario;
		}

		public String getNombre() {
			return nombre;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
		}
		public String getApellidoPaterno() {
			return apellidoPaterno;
		}
		public void setApellidoPaterno(String apellidoPaterno) {
			this.apellidoPaterno = apellidoPaterno;
		}
		public String getApellidoMaterno() {
			return apellidoMaterno;
		}
		public void setApellidoMaterno(String apellidoMaterno) {
			this.apellidoMaterno = apellidoMaterno;
		}
		public String getContrasenia() {
			return contrasenia;
		}
		public void setContrasenia(String contrasenia) {
			this.contrasenia = contrasenia;
		}
		public String getCorreoElectronico() {
			return correoElectronico;
		}
		public void setCorreoElectronico(String correoElectronico) {
			this.correoElectronico = correoElectronico;
		}
		public String getCurp() {
			return curp;
		}

		public void setCurp(String curp) {
			this.curp = curp;
		}
}
