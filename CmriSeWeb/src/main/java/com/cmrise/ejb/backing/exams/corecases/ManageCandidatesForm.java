package com.cmrise.ejb.backing.exams.corecases;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.primefaces.PrimeFaces;

import com.cmrise.ejb.model.admin.AdmonCcCandidatos;
import com.cmrise.ejb.model.admin.AdmonUsuarios;
import com.cmrise.ejb.services.admin.AdmonCcCandidatosLocal;
import com.cmrise.ejb.services.admin.AdmonUsuariosLocal;
import com.cmrise.jpa.dto.admin.AdmonCcCandidatosDto;
import com.cmrise.jpa.dto.admin.AdmonCcCandidatosV1Dto;
import com.cmrise.jpa.dto.admin.AdmonUsuariosDto;
import com.cmrise.utils.Utilitarios;

@ManagedBean
@ViewScoped
public class ManageCandidatesForm {

	private String searchUsuario;
	private List<AdmonUsuarios> listAdmonUsuarios = new ArrayList<AdmonUsuarios>();
	private AdmonUsuarios admonUsuariosForAction = new AdmonUsuarios();
	private long numeroCcExamen; 
	private List<AdmonCcCandidatos> listAdmonCcCandidatos = new ArrayList<AdmonCcCandidatos>();
	private AdmonCcCandidatos admonCcCandidatosForAction = new AdmonCcCandidatos();
	
	@Inject 
	AdmonUsuariosLocal admonUsuariosLocal; 
	
	@Inject 
	AdmonCcCandidatosLocal admonCcCandidatosLocal; 
	
	 @PostConstruct
	 public void init() {
		System.out.println("Comienza "+this.getClass()+" init()");
		
		FacesContext context = FacesContext.getCurrentInstance(); 
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		Object objNumeroCcExamenSV = session.getAttribute("NumeroCcExamenSV");
		if(objNumeroCcExamenSV instanceof Long) {
			this.numeroCcExamen = (Long)objNumeroCcExamenSV; 
		}
		filtrarAdmonCcCandidatos(); 
		System.out.println("Finaliza "+this.getClass()+" init()");
	 }	

	public void filtrarAdmonCcCandidatos() {
		List<AdmonCcCandidatosV1Dto> listAdmonCcCandidatosV1Dto = admonCcCandidatosLocal.findByNumeroCcExamenWD(this.getNumeroCcExamen());
		listAdmonCcCandidatos = new ArrayList<AdmonCcCandidatos>();
		for(AdmonCcCandidatosV1Dto admonCcCandidatosV1Dto:listAdmonCcCandidatosV1Dto) {
			AdmonCcCandidatos admonCcCandidatos = new AdmonCcCandidatos(); 
			admonCcCandidatos.setNumero(admonCcCandidatosV1Dto.getNumero());
			admonCcCandidatos.setNombreCompletoUsuario(admonCcCandidatosV1Dto.getNombreCompletoUsuario());
			admonCcCandidatos.setContrasenia(admonCcCandidatosV1Dto.getContrasenia());
			admonCcCandidatos.setCorreoElectronico(admonCcCandidatosV1Dto.getCorreoElectronico());
			admonCcCandidatos.setInformacionExtra(admonCcCandidatosV1Dto.getInformacionExtra());
			listAdmonCcCandidatos.add(admonCcCandidatos); 
		}
	}
	 
	public void filtrarUsuarios() {
		boolean searchIn = false; 
		List<AdmonUsuariosDto> listAdmonUsuariosDto  = admonUsuariosLocal.findTop500ByFilters(this.getNumeroCcExamen()
				                                                                             ,this.getSearchUsuario());
		listAdmonUsuarios = new ArrayList<AdmonUsuarios>();
		for(AdmonUsuariosDto admonUsuariosDto:listAdmonUsuariosDto) {
			AdmonUsuarios admonUsuarios = new AdmonUsuarios(); 
			admonUsuarios.setNumero(admonUsuariosDto.getNumero());
			admonUsuarios.setNombre(admonUsuariosDto.getNombre());
			admonUsuarios.setApellidoPaterno(admonUsuariosDto.getApellidoPaterno());
			admonUsuarios.setApellidoMaterno(admonUsuariosDto.getApellidoMaterno());
			admonUsuarios.setCorreoElectronico(admonUsuariosDto.getCorreoElectronico());
			listAdmonUsuarios.add(admonUsuarios); 
		}
		searchIn = true; 
		PrimeFaces.current().ajax().addCallbackParam("searchIn", searchIn);
	}
	
	public void agregarCandidato(AdmonUsuarios pAdmonUsuarios) {
		AdmonCcCandidatosDto admonCcCandidatosDto = new AdmonCcCandidatosDto();
		admonCcCandidatosDto.setNumeroCcExamen(this.getNumeroCcExamen());
		admonCcCandidatosDto.setNumeroUsuario(pAdmonUsuarios.getNumero());
		admonCcCandidatosDto.setFechaEfectivaDesde(Utilitarios.startOfTime);
		admonCcCandidatosDto.setFechaEfectivaHasta(Utilitarios.endOfTime);
		admonCcCandidatosLocal.insert(admonCcCandidatosDto); 
		filtrarUsuarios(); 
		filtrarAdmonCcCandidatos(); 
	}
	
	public void selectForAction(AdmonCcCandidatos pAdmonCcCandidatos) {
		System.out.println("selectForAction pAdmonCcCandidatos.getNumero():"+pAdmonCcCandidatos.getNumero());
		this.admonCcCandidatosForAction.setNumero(pAdmonCcCandidatos.getNumero());
	}
	
	public void delete() {
		boolean deleteIn = false; 
		admonCcCandidatosLocal.delete(this.admonCcCandidatosForAction.getNumero());
		filtrarAdmonCcCandidatos(); 
		deleteIn = true; 
		PrimeFaces.current().ajax().addCallbackParam("deleteIn", deleteIn);
	}
	
	public void deleteAll(){
		boolean deleteAllIn = false; 
		admonCcCandidatosLocal.deleteAll(this.getNumeroCcExamen());
		filtrarAdmonCcCandidatos(); 
		deleteAllIn = true; 
		PrimeFaces.current().ajax().addCallbackParam("deleteAllIn", deleteAllIn);
	}
	
	public String getSearchUsuario() {
		return searchUsuario;
	}

	public void setSearchUsuario(String searchUsuario) {
		this.searchUsuario = searchUsuario;
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

	public long getNumeroCcExamen() {
		return numeroCcExamen;
	}

	public void setNumeroCcExamen(long numeroCcExamen) {
		this.numeroCcExamen = numeroCcExamen;
	}

	public List<AdmonCcCandidatos> getListAdmonCcCandidatos() {
		return listAdmonCcCandidatos;
	}

	public void setListAdmonCcCandidatos(List<AdmonCcCandidatos> listAdmonCcCandidatos) {
		this.listAdmonCcCandidatos = listAdmonCcCandidatos;
	}

	public AdmonCcCandidatos getAdmonCcCandidatosForAction() {
		return admonCcCandidatosForAction;
	}

	public void setAdmonCcCandidatosForAction(AdmonCcCandidatos admonCcCandidatosForAction) {
		this.admonCcCandidatosForAction = admonCcCandidatosForAction;
	} 
	
}
