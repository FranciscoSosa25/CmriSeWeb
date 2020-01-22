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

import com.cmrise.ejb.model.admin.AdmonUsuarios;
import com.cmrise.ejb.services.admin.AdmonUsuariosLocal;
import com.cmrise.jpa.dto.admin.AdmonUsuariosDto;
import com.cmrise.utils.Utilitarios;
import com.cmrise.utils.UtilitariosLocal;

@ManagedBean
@ViewScoped
public class AdmonUsuariosForm {
    private String nombre; 
    private String apellidoPaterno; 
    private String apellidoMaterno; 
    private String contrasenia; 
    private String tipoUsuario;
    private String correoElectronico;
    private Date fechaEfectivaDesde;
	private Date fechaEfectivaHasta;
	private List<AdmonUsuarios> listAdmonUsuarios = new ArrayList<AdmonUsuarios>();
	private AdmonUsuarios admonUsuariosForAction = new AdmonUsuarios();
	
	@Inject 
	AdmonUsuariosLocal admonUsuariosLocal; 
	
	@Inject 
	UtilitariosLocal utilitariosLocal;
	
    @PostConstruct
	public void init() {
		 refreshEntity();
	 }		 
	
    public void refreshEntity() {
		List<AdmonUsuariosDto> listAdmonUsuariosDto = admonUsuariosLocal.findTop500(); 
		Iterator<AdmonUsuariosDto> iterAdmonUsuariosDto = listAdmonUsuariosDto.iterator(); 
		listAdmonUsuarios = new ArrayList<AdmonUsuarios>();
		while(iterAdmonUsuariosDto.hasNext()) {
			AdmonUsuariosDto admonUsuariosDto = iterAdmonUsuariosDto.next(); 
			AdmonUsuarios admonUsuarios = new AdmonUsuarios();
			admonUsuarios.setNumero(admonUsuariosDto.getNumero());
			admonUsuarios.setNombre(admonUsuariosDto.getNombre());
			admonUsuarios.setApellidoPaterno(admonUsuariosDto.getApellidoPaterno());
			admonUsuarios.setApellidoMaterno(admonUsuariosDto.getApellidoMaterno());
			admonUsuarios.setContrasenia(admonUsuariosDto.getContrasenia());
			admonUsuarios.setCorreoElectronico(admonUsuariosDto.getCorreoElectronico());
			admonUsuarios.setFechaEfectivaDesde(utilitariosLocal.toUtilDate(admonUsuariosDto.getFechaEfectivaDesde()));
			if(Utilitarios.endOfTime.equals(admonUsuariosDto.getFechaEfectivaHasta())) {
				admonUsuarios.setFechaEfectivaHasta(null);	
			}else {
				admonUsuarios.setFechaEfectivaHasta(utilitariosLocal.toUtilDate(admonUsuariosDto.getFechaEfectivaHasta()));	
			}
			listAdmonUsuarios.add(admonUsuarios);
		}
	}
	
	public void create() {
		AdmonUsuariosDto admonUsuariosDto = new AdmonUsuariosDto();
		 boolean createIn = false; 
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
		 admonUsuariosLocal.insert(admonUsuariosDto);
		 refreshEntity();
		 createIn = true; 
		 PrimeFaces.current().ajax().addCallbackParam("createIn", createIn);
	}
	
	public void selectForAction(AdmonUsuarios pAdmonUsuarios) {
		System.out.println("*pAdmonUsuarios.getNumero():"+pAdmonUsuarios.getNumero());
		admonUsuariosForAction.setNumero(pAdmonUsuarios.getNumero());
		admonUsuariosForAction.setNombre(pAdmonUsuarios.getNombre());
		admonUsuariosForAction.setApellidoPaterno(pAdmonUsuarios.getApellidoPaterno());
		admonUsuariosForAction.setApellidoMaterno(pAdmonUsuarios.getApellidoMaterno());
		admonUsuariosForAction.setCorreoElectronico(pAdmonUsuarios.getCorreoElectronico());
		admonUsuariosForAction.setFechaEfectivaDesde(pAdmonUsuarios.getFechaEfectivaDesde());
		admonUsuariosForAction.setFechaEfectivaHasta(pAdmonUsuarios.getFechaEfectivaHasta());
		admonUsuariosForAction.setContrasenia(pAdmonUsuarios.getContrasenia());
	}
	
	
	public void delete() {
		boolean deleteIn = false; 
		admonUsuariosLocal.delete(admonUsuariosForAction.getNumero());
		refreshEntity();
		deleteIn = true; 
		PrimeFaces.current().ajax().addCallbackParam("deleteIn", deleteIn);
	}
	
	public void update() {
		boolean updateIn = false; 
		AdmonUsuariosDto admonUsuariosDto = new AdmonUsuariosDto();
		System.out.println("admonUsuariosForAction.getNombre():"+admonUsuariosForAction.getNombre());
		System.out.println("admonUsuariosForAction.getNumero():"+admonUsuariosForAction.getNumero());
		admonUsuariosDto.setNombre(admonUsuariosForAction.getNombre());
		admonUsuariosDto.setApellidoPaterno(admonUsuariosForAction.getApellidoPaterno());
		admonUsuariosDto.setApellidoMaterno(admonUsuariosForAction.getApellidoMaterno());
		admonUsuariosDto.setCorreoElectronico(admonUsuariosForAction.getCorreoElectronico());
		admonUsuariosDto.setContrasenia(admonUsuariosForAction.getContrasenia());
		admonUsuariosDto.setFechaEfectivaDesde(utilitariosLocal.toSqlDate(admonUsuariosForAction.getFechaEfectivaDesde()));
		if(null!=admonUsuariosForAction.getFechaEfectivaHasta()) {
			admonUsuariosDto.setFechaEfectivaHasta(utilitariosLocal.toSqlDate(admonUsuariosForAction.getFechaEfectivaHasta()));	
		}else {
			admonUsuariosDto.setFechaEfectivaHasta(Utilitarios.endOfTime);
		}
		admonUsuariosLocal.update(admonUsuariosForAction.getNumero(), admonUsuariosDto);
		refreshEntity();
		updateIn = true; 
		PrimeFaces.current().ajax().addCallbackParam("updateIn", updateIn);
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
	public String getTipoUsuario() {
		return tipoUsuario;
	}
	public void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}
	public String getCorreoElectronico() {
		return correoElectronico;
	}
	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
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
	public List<AdmonUsuarios> getListAdmonUsuarios() {
		return listAdmonUsuarios;
	}
	public void setListAdmonUsuarios(List<AdmonUsuarios> listAdmonUsuarios) {
		this.listAdmonUsuarios = listAdmonUsuarios;
	}
	public AdmonUsuarios getAdmonUsuariosForAction() {
		return admonUsuariosForAction;
	}
	public void setAdmonUsuariosForAction(AdmonUsuarios admonUsuariosForAction) {
		this.admonUsuariosForAction = admonUsuariosForAction;
	}
}
