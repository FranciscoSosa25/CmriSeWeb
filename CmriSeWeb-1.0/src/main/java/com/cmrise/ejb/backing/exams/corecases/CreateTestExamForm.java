package com.cmrise.ejb.backing.exams.corecases;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import com.cmrise.ejb.helpers.UserLogin;
import com.cmrise.ejb.model.admin.AdmonExamenHdr;
import com.cmrise.ejb.services.admin.AdmonExamenHdrLocal;
import com.cmrise.ejb.services.exams.CcExamenesLocal;
import com.cmrise.jpa.dto.exams.CcExamenesDto;
import com.cmrise.utils.Utilitarios;
import com.cmrise.utils.UtilitariosLocal;

@ManagedBean
@ViewScoped
public class CreateTestExamForm {

	private String descripcion;  
	private String visibilidad; 
	private int idTipoExamen;
	private Date fechaEfectivaDesde; 
	private Date fechaEfectivaHasta; 
	private Date fechaCreacion;
	private short limiteTiempo; 
	private boolean saltarCasos; 
	private boolean mostrarRespuestas; 
	private String mensajeFinalizacion; 	
	private List<AdmonExamenHdr> examenesHdr = new ArrayList<AdmonExamenHdr>();
	private List<SelectItem> selectExamenesHdr = new ArrayList<SelectItem>(); 
	private CcExamenesDto ccExamenesDto = new CcExamenesDto();
	
	@Inject
	CcExamenesLocal ccExamenesLocal; 
	
	@Inject 
	UtilitariosLocal utilitariosLocal; 
	
	@Inject 
	AdmonExamenHdrLocal admonExamenHdrLocal;
	
	@ManagedProperty(value="#{userLogin}")
	private UserLogin userLogin; 
	
	@PostConstruct
	 public void init() {
		 examenesHdr = admonExamenHdrLocal.findByTipo(Utilitarios.CORE_CASES); 
		 for(AdmonExamenHdr i:examenesHdr) {
			 SelectItem selectItem = new SelectItem(i.getNumero(),i.getNombre());
			 selectExamenesHdr.add(selectItem); 
		 }		
		 setFechaCreacion(new java.util.Date());
	 }
	
	public String create() {
		java.util.Date sysdate = new java.util.Date();
		java.sql.Timestamp sqlsysdate = new java.sql.Timestamp(sysdate.getTime());
		ccExamenesDto.setFechaCreacion(sqlsysdate);
		ccExamenesDto.setFechaActualizacion(sqlsysdate);
		ccExamenesDto.setDescripcion(this.getDescripcion());
		ccExamenesDto.setVisibilidad(this.getVisibilidad());
		ccExamenesDto.setCreadoPor(userLogin.getNumeroUsuario());
		ccExamenesDto.setActualizadoPor(userLogin.getNumeroUsuario());
		
		ccExamenesDto.setFechaEfectivaDesde(new java.sql.Timestamp(this.getFechaEfectivaDesde().getTime()));
		if(null!=this.getFechaEfectivaHasta()) {
			ccExamenesDto.setFechaEfectivaHasta(new java.sql.Timestamp(this.getFechaEfectivaHasta().getTime()));
		}else {
			ccExamenesDto.setFechaEfectivaHasta(new java.sql.Timestamp(Utilitarios.endOfTime.getTime()));
		}
		
		FacesContext context = FacesContext.getCurrentInstance(); 
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		
		if(ccExamenesDto.getFechaEfectivaHasta() != null) {
			if(ccExamenesDto.getFechaEfectivaDesde().equals(ccExamenesDto.getFechaEfectivaHasta()) || !ccExamenesDto.getFechaEfectivaDesde().before(ccExamenesDto.getFechaEfectivaHasta())) {
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
						"La fecha efectiva desde no puede ser igual o menor que la fecha efectiva hasta"));
				System.out.println("error de fechas!");
				return null;
			}
		}
		
		ccExamenesDto.setTiempoLimite(this.getLimiteTiempo());
		ccExamenesDto.setSaltarCasos(this.isSaltarCasos());
		ccExamenesDto.setMostrarRespuestas(this.isMostrarRespuestas());
		ccExamenesDto.setMensajeFinalizacion(this.getMensajeFinalizacion());
		ccExamenesDto.setSociedad(Utilitarios.SOCIEDAD);
		ccExamenesDto.setEstatus(Utilitarios.INITIAL_STATUS_CC_EXAM);
		long numeroCcExamen = ccExamenesLocal.insert(ccExamenesDto); 		
		
		session.setAttribute("NumeroCcExamenSV", numeroCcExamen);  		
		return "Exams-CoreCases-Update"; 
	}
	
	public CcExamenesDto getCcExamenesDto() {
		return ccExamenesDto;
	}
	public void setCcExamenesDto(CcExamenesDto ccExamenesDto) {
		this.ccExamenesDto = ccExamenesDto;
	}
	public int getTipoExamen() {
		return idTipoExamen;
	}
	public void setIdTipoExamen(int idTipoExamen) {
		this.idTipoExamen = idTipoExamen;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getVisibilidad() {
		return visibilidad;
	}
	public void setVisibilidad(String visibilidad) {
		this.visibilidad = visibilidad;
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
	public short getLimiteTiempo() {
		return limiteTiempo;
	}
	public void setLimiteTiempo(short limiteTiempo) {
		this.limiteTiempo = limiteTiempo;
	}
	public boolean isSaltarCasos() {
		return saltarCasos;
	}
	public void setSaltarCasos(boolean saltarCasos) {
		this.saltarCasos = saltarCasos;
	}
	public boolean isMostrarRespuestas() {
		return mostrarRespuestas;
	}
	public void setMostrarRespuestas(boolean mostrarRespuestas) {
		this.mostrarRespuestas = mostrarRespuestas;
	}
	public String getMensajeFinalizacion() {
		return mensajeFinalizacion;
	}
	public void setMensajeFinalizacion(String mensajeFinalizacion) {
		this.mensajeFinalizacion = mensajeFinalizacion;
	}
	
	public List<AdmonExamenHdr> getExamenesHdr() {
		return examenesHdr;
	}

	public void setExamenesHdr(List<AdmonExamenHdr> examenesHdr) {
		this.examenesHdr = examenesHdr;
	}

	public List<SelectItem> getSelectExamenesHdr() {
		return selectExamenesHdr;
	}

	public void setSelectExamenesHdr(List<SelectItem> selectExamenesHdr) {
		this.selectExamenesHdr = selectExamenesHdr;
	}
	
	public UserLogin getUserLogin() {
		return userLogin;
	}
	public void setUserLogin(UserLogin userLogin) {
		this.userLogin = userLogin;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
}
