package com.cmrise.ejb.backing.admin;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.PrimeFaces;

import com.cmrise.ejb.model.admin.AdmonUsuariosRolesV1;
import com.cmrise.ejb.services.admin.AdmonUsuariosRolesLocal;
import com.cmrise.jpa.dto.admin.AdmonRolesDto;
import com.cmrise.jpa.dto.admin.AdmonUsuariosDto;
import com.cmrise.jpa.dto.admin.AdmonUsuariosRolesDto;
import com.cmrise.jpa.dto.admin.AdmonUsuariosRolesV1Dto;
import com.cmrise.utils.Utilitarios;
import com.cmrise.utils.UtilitariosLocal;

@ManagedBean
@ViewScoped
public class AdmonUsuariosRolesForm {

	private long numeroRol;
	private long numeroUsuario;
	private Date fechaEfectivaDesde;
	private Date fechaEfectivaHasta;
	private List<AdmonUsuariosRolesV1> listAdmonUsuariosRolesV1 = new ArrayList<AdmonUsuariosRolesV1>();
	private AdmonUsuariosRolesV1 admonUsuariosRolesV1ForAction = new AdmonUsuariosRolesV1();
	
	@Inject
	AdmonUsuariosRolesLocal admonUsuariosRolesLocal; 
	
	@Inject 
	UtilitariosLocal utilitariosLocal;
	
	 @PostConstruct
	 public void init() {
			 refreshEntity();
	 }		 
	
	private void refreshEntity() {
		List<AdmonUsuariosRolesV1Dto> listAdmonUsuariosRolesV1Dto =admonUsuariosRolesLocal.findAll();
		Iterator<AdmonUsuariosRolesV1Dto> iterAdmonUsuariosRolesV1Dto = listAdmonUsuariosRolesV1Dto.iterator();
		listAdmonUsuariosRolesV1 = new ArrayList<AdmonUsuariosRolesV1>();
		while(iterAdmonUsuariosRolesV1Dto.hasNext()) {
			AdmonUsuariosRolesV1Dto admonUsuariosRolesV1Dto = iterAdmonUsuariosRolesV1Dto.next();
			AdmonUsuariosRolesV1 admonUsuariosRolesV1 = new AdmonUsuariosRolesV1();
			admonUsuariosRolesV1.setNumero(admonUsuariosRolesV1Dto.getNumero());
			admonUsuariosRolesV1.setNumeroUsuario(admonUsuariosRolesV1Dto.getNumeroUsuario());
			admonUsuariosRolesV1.setNumeroRol(admonUsuariosRolesV1Dto.getNumeroRol());
			admonUsuariosRolesV1.setMatricula(admonUsuariosRolesV1Dto.getMatricula());
			admonUsuariosRolesV1.setNombreCompletoUsuario(admonUsuariosRolesV1Dto.getNombreCompletoUsuario());
			admonUsuariosRolesV1.setNombreRol(admonUsuariosRolesV1Dto.getNombreRol());
			admonUsuariosRolesV1.setFedAur(utilitariosLocal.toUtilDate(admonUsuariosRolesV1Dto.getFedAur()));
			if(Utilitarios.endOfTime.equals(admonUsuariosRolesV1Dto.getFedAur())) {
				admonUsuariosRolesV1.setFehAur(utilitariosLocal.toUtilDate(admonUsuariosRolesV1Dto.getFehAur()));
			}else {
				admonUsuariosRolesV1.setFehAur(null);
			}
			
			listAdmonUsuariosRolesV1.add(admonUsuariosRolesV1);
		}
	}

	public void create() {
		 boolean createIn = false; 
		AdmonUsuariosRolesDto admonUsuariosRolesDto = new AdmonUsuariosRolesDto();
		AdmonUsuariosDto admonUsuariosDto = new AdmonUsuariosDto();
		AdmonRolesDto admonRolesDto = new AdmonRolesDto();
		admonUsuariosDto.setNumero(numeroUsuario);
		admonRolesDto.setNumero(numeroRol);
		admonUsuariosRolesDto.setAdmonUsuario(admonUsuariosDto);
		admonUsuariosRolesDto.setAdmonRole(admonRolesDto);
		if(null!=this.fechaEfectivaDesde) {
			admonUsuariosRolesDto.setFechaEfectivaDesde(utilitariosLocal.toSqlDate(fechaEfectivaDesde));
		}
		if(null!=this.fechaEfectivaHasta) {
			admonUsuariosRolesDto.setFechaEfectivaHasta(utilitariosLocal.toSqlDate(fechaEfectivaHasta));
		}else {
			admonUsuariosRolesDto.setFechaEfectivaHasta(Utilitarios.endOfTime);
		}
		admonUsuariosRolesLocal.insert(admonUsuariosRolesDto);
		refreshEntity();
		createIn = true; 
		PrimeFaces.current().ajax().addCallbackParam("createIn", createIn);
		
	}
	
	public void selectForAction(AdmonUsuariosRolesV1 pAdmonUsuariosRolesV1) {
		admonUsuariosRolesV1ForAction.setNumero(pAdmonUsuariosRolesV1.getNumero());
		admonUsuariosRolesV1ForAction.setNumeroUsuario(pAdmonUsuariosRolesV1.getNumeroUsuario());
		admonUsuariosRolesV1ForAction.setNumeroRol(pAdmonUsuariosRolesV1.getNumeroRol());
		admonUsuariosRolesV1ForAction.setFedAur(pAdmonUsuariosRolesV1.getFedAur());
		admonUsuariosRolesV1ForAction.setFehAur(pAdmonUsuariosRolesV1.getFehAur());
	}
	
	public void update() {
		boolean updateIn = false; 
		AdmonUsuariosRolesDto admonUsuariosRolesDto = new AdmonUsuariosRolesDto();
		AdmonUsuariosDto admonUsuariosDto = new AdmonUsuariosDto();
		AdmonRolesDto admonRolesDto = new AdmonRolesDto();
		admonUsuariosDto.setNumero(admonUsuariosRolesV1ForAction.getNumeroUsuario());
		admonRolesDto.setNumero(admonUsuariosRolesV1ForAction.getNumeroRol());
		admonUsuariosRolesDto.setAdmonUsuario(admonUsuariosDto);
		admonUsuariosRolesDto.setAdmonRole(admonRolesDto);
		if(null!=admonUsuariosRolesV1ForAction.getFedAur()) {
			admonUsuariosRolesDto.setFechaEfectivaDesde(utilitariosLocal.toSqlDate(admonUsuariosRolesV1ForAction.getFedAur()));
		}
		if(null!=admonUsuariosRolesV1ForAction.getFehAur()) {
			admonUsuariosRolesDto.setFechaEfectivaHasta(utilitariosLocal.toSqlDate(admonUsuariosRolesV1ForAction.getFehAur()));
		}else {
			admonUsuariosRolesDto.setFechaEfectivaHasta(Utilitarios.endOfTime);
		}
		admonUsuariosRolesLocal.update(admonUsuariosRolesV1ForAction.getNumero()
				                     , admonUsuariosRolesDto
				                     );
		refreshEntity();
		updateIn = true; 
		PrimeFaces.current().ajax().addCallbackParam("updateIn", updateIn);
	}
	
	public void delete() {
		boolean deleteIn = false; 
		admonUsuariosRolesLocal.delete(admonUsuariosRolesV1ForAction.getNumero());
		refreshEntity();
		deleteIn = true; 
		PrimeFaces.current().ajax().addCallbackParam("deleteIn", deleteIn);
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

	public List<AdmonUsuariosRolesV1> getListAdmonUsuariosRolesV1() {
		return listAdmonUsuariosRolesV1;
	}

	public void setListAdmonUsuariosRolesV1(List<AdmonUsuariosRolesV1> listAdmonUsuariosRolesV1) {
		this.listAdmonUsuariosRolesV1 = listAdmonUsuariosRolesV1;
	}

	public AdmonUsuariosRolesV1 getAdmonUsuariosRolesV1ForAction() {
		return admonUsuariosRolesV1ForAction;
	}

	public void setAdmonUsuariosRolesV1ForAction(AdmonUsuariosRolesV1 admonUsuariosRolesV1ForAction) {
		this.admonUsuariosRolesV1ForAction = admonUsuariosRolesV1ForAction;
	}
	
}
