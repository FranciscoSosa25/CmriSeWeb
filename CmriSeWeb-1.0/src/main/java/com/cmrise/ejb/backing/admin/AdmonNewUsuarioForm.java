package com.cmrise.ejb.backing.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.faces.event.ValueChangeEvent;

import org.primefaces.PrimeFaces;
import org.primefaces.event.ToggleEvent;

import com.cmrise.ejb.helpers.SelectsHelper;
import com.cmrise.ejb.helpers.UserLogin;
import com.cmrise.ejb.model.admin.AdmonUsuarios;
import com.cmrise.ejb.services.admin.AdmonMaestroMateriaLocal;
import com.cmrise.ejb.services.admin.AdmonRolesLocal;
import com.cmrise.ejb.services.admin.AdmonUsuariosLocal;
import com.cmrise.ejb.services.admin.AdmonUsuariosRolesLocal;
import com.cmrise.jpa.dto.admin.AdmonMaestroMateriaDto;
import com.cmrise.jpa.dto.admin.AdmonRolesDto;
import com.cmrise.jpa.dto.admin.AdmonUsuariosDto;
import com.cmrise.jpa.dto.admin.AdmonUsuariosRolesDto;
import com.cmrise.jpa.dto.admin.KeysDto;
import com.cmrise.utils.Utilitarios;
import com.cmrise.utils.UtilitariosLocal;

@ManagedBean
@ViewScoped
public class AdmonNewUsuarioForm {
    private String nombre; 
    private String apellidoPaterno; 
    private String apellidoMaterno; 
    private String contrasenia; 
    private String tipoUsuario;
    private String correoElectronico;
    private Date fechaEfectivaDesde;
	private Date fechaEfectivaHasta;
	private String ErrorCaptura;
	private List<AdmonUsuarios> listAdmonUsuarios = new ArrayList<AdmonUsuarios>();
	private AdmonUsuarios admonUsuariosForAction = new AdmonUsuarios();
	private long numeroRol; 
	private String curp; 
	private List<SelectItem> Roles;
	private String[] selectedRoles;
	private List<SelectItem> Materias;
	private String[] selectedMaterias;
	private boolean dispMaterias = false;
	private List<Long> arrNumMaestro = new ArrayList<Long>();
	
	@Inject 
	AdmonUsuariosLocal admonUsuariosLocal; 
	
	@Inject 
	UtilitariosLocal utilitariosLocal;
	
	@Inject
	AdmonUsuariosRolesLocal admonUsuariosRolesLocal; 
	
	@Inject
	AdmonRolesLocal admonRoles;
	
	@Inject
	SelectsHelper selhel;
	
	@Inject
	AdmonMaestroMateriaLocal admonMaestroMateria;
	
	@ManagedProperty(value="#{userLogin}")
	private UserLogin userLogin;
	
    @PostConstruct
	public void init() {
		 refreshEntity();
		 List<SelectItem> data = selhel.getSelectAdmonRolesItemsNotCand();
		 data.forEach(item -> {
			 if (item.getLabel().toUpperCase().contains("MAESTRO")) {
				 arrNumMaestro.add(Long.parseLong(item.getValue().toString()));
			 }
		 });
	 }		 
	
    public void refreshEntity() {
		setNombre("");
		setApellidoPaterno("");
		setApellidoMaterno("");
		setContrasenia("");
		setTipoUsuario("");
		setCorreoElectronico("");
		setFechaEfectivaDesde(new Date());
		setFechaEfectivaHasta(null);
		setCurp("");
		setNumeroRol(0);
		setSelectedRoles(new String[0]);
		setSelectedMaterias(new String[0]);
	}
	
	public void create() {
		ErrorCaptura = "";
		java.sql.Date fechaED;
		java.sql.Date fechaEH;
		boolean createIn = false;
		
		if(getCurp().length() != 18)
			ErrorCaptura+="Curp invalida";
		
		if(getFechaEfectivaDesde() != null) 
			fechaED = new java.sql.Date(fechaEfectivaDesde.getTime());
		else {
			ErrorCaptura += "Fecha Efectiva Desde invalida";
			fechaED = new java.sql.Date(fechaEfectivaDesde.getTime());
		}
		
		if(getFechaEfectivaHasta() != null) 
			fechaEH = new java.sql.Date(fechaEfectivaHasta.getTime());
		else 			
			fechaEH = Utilitarios.endOfTime;
		
		if(isEmail(getCorreoElectronico())==false)
			ErrorCaptura += "Correo invalido. " + '\n';
		
		if(getSelectedRoles().length==0)
			ErrorCaptura += "Seleccione un rol"+'\n';
		
		if(getDispMaterias()==true && getSelectedMaterias().length == 0)
			ErrorCaptura += "Seleccione una materia. " + '\n';
		
		if(ErrorCaptura == "")
			createIn = creaNuevoUsuario(getCurp(), getNombre(), getApellidoPaterno(), getApellidoMaterno(), getContrasenia(), 
					getCorreoElectronico(), fechaED, fechaEH, getSelectedRoles(), getSelectedMaterias());
	    
	    if(ErrorCaptura != ""){
			
			System.out.println("NuevoUsuario: Error "+ErrorCaptura);
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error en la captura", ErrorCaptura));
	        ErrorCaptura = "";	        
		}else {	        
			System.out.println("NuevoUsuario:"+" OK");
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario", " registrado correctamente"));
		}
		
		 if	(createIn)	{
			 refreshEntity();		  
			 PrimeFaces.current().ajax().addCallbackParam("createIn", createIn);
		 }
	}
	
	private boolean creaNuevoUsuario(String curp, String nombre, String a_paterno, String a_materno, String password, String email, 
			java.sql.Date fechaED, java.sql.Date fechaEH, String[] rol, String[] materias) {
		boolean inserto = true;
		try {
			AdmonUsuariosDto admonUsuariosDto = new AdmonUsuariosDto();
			
			admonUsuariosDto.setCurp(curp);
			admonUsuariosDto.setNombre(nombre);
			admonUsuariosDto.setApellidoPaterno(a_paterno);
			admonUsuariosDto.setApellidoMaterno(a_materno);
			admonUsuariosDto.setContrasenia(password);
			admonUsuariosDto.setCorreoElectronico(email);
			admonUsuariosDto.setFechaEfectivaDesde(fechaED);
			admonUsuariosDto.setFechaEfectivaHasta(fechaEH);
			admonUsuariosDto.setCreadoPor(userLogin.getNumeroUsuario());
			admonUsuariosDto.setActualizadoPor(userLogin.getNumeroUsuario());
			
			long longNumeroUsusario = admonUsuariosLocal.insert(admonUsuariosDto);
		 
		    /**************************INSERTA*REGISTRO*EN*TABLA*USARIO-ROL*****************************/
		    AdmonUsuariosRolesDto admonUsuariosRolesDto = new AdmonUsuariosRolesDto();
			AdmonRolesDto admonRolesDto = new AdmonRolesDto();
			AdmonUsuariosDto admonUsuariosV2Dto = new AdmonUsuariosDto();
			System.out.println("longNumeroUsusario:" + longNumeroUsusario);
			
			admonUsuariosV2Dto.setNumero(longNumeroUsusario);
			admonUsuariosRolesDto.setAdmonUsuario(admonUsuariosV2Dto);
			admonUsuariosRolesDto.setAdmonRole(admonRolesDto);
			admonUsuariosRolesDto.setCreadoPor(userLogin.getNumeroUsuario());
			admonUsuariosRolesDto.setActualizadoPor(userLogin.getNumeroUsuario());
			admonUsuariosRolesDto.setFechaEfectivaDesde(utilitariosLocal.toSqlDate(fechaEfectivaDesde));
			if(null!=fechaEfectivaHasta) {
				admonUsuariosRolesDto.setFechaEfectivaHasta(utilitariosLocal.toSqlDate(fechaEfectivaHasta));	
			}else {
				admonUsuariosRolesDto.setFechaEfectivaHasta(Utilitarios.endOfTime);
			}			
			
			for(int i = 0; i< rol.length; i++)
			{
				System.out.println("this.getNumeroRol():" + rol[i]);
				admonRolesDto.setNumero(Long.parseLong(rol[i]));				
				admonUsuariosRolesLocal.insert(admonUsuariosRolesDto);	
			}
			/************************INSERTA*MAESTRO-MATERIA**********************************************/
			AdmonMaestroMateriaDto admonMaestroMateriaDto = new AdmonMaestroMateriaDto();
			admonMaestroMateriaDto.setIdMaestro(longNumeroUsusario);
			
			if(getDispMaterias()==true)
				for(int i = 0; i< materias.length; i++)
				{
					admonMaestroMateriaDto.setIdMateria(Long.parseLong(materias[i]));
					admonMaestroMateria.insert(admonMaestroMateriaDto);	
				}	
		}catch (Exception  ex) {
				Throwable e;
				String mg = "";
				 if( (e = ex.getCause()) != null) {
						while( e.getCause() != null ) {e = e.getCause();}				
						if(e.getMessage().contains("clave duplicada") ) {
							mg = e.getMessage().substring(e.getMessage().indexOf("duplicada es"));
							ErrorCaptura += " Clave "+ mg + '\n';
						}else {
							ErrorCaptura += " " + e.getMessage() + '\n';
						}
					}				
				 inserto = false;
		}			
		return inserto;
	}
	
	public UserLogin getUserLogin() {
		return userLogin;
	}
	public void setUserLogin(UserLogin userLogin) {
		this.userLogin = userLogin;
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
	public long getNumeroRol() {
		return numeroRol;
	}
	public void setNumeroRol(long numeroRol) {
		this.numeroRol = numeroRol;
	}
	public String getCurp() {
		return curp;
	}
	public void setCurp(String curp) {
		this.curp = curp;
	}
	public List<SelectItem> getRoles() {
		return Roles;
	}
	public void setRoles(List<SelectItem> roles) {
		Roles = roles;
	}
	public String[] getSelectedRoles() {
		return selectedRoles;
	}
	public void setSelectedRoles(String[] selectedRoles) {
		this.selectedRoles = selectedRoles;
	}
	public List<SelectItem> getMaterias() {
		return Materias;
	}
	public void setMaterias(List<SelectItem> materias) {
		Materias = materias;
	}
	public String[] getSelectedMaterias() {
		return selectedMaterias;
	}
	public void setSelectedMaterias(String[] selectedMaterias) {
		this.selectedMaterias = selectedMaterias;
	}
	public boolean getDispMaterias() {
		return dispMaterias;
	}
	public void setDispMaterias(boolean dispMaterias) {
		this.dispMaterias = dispMaterias;
	}
	
	public String cancel() {
		FacesContext context = FacesContext.getCurrentInstance(); 
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		//session.setAttribute("NumeroMrqsExamenSV",this.getNumeroMrqsExamen());
		return "Admon-Usuarios"; 
	}
	
	public boolean isEmail(String email) {
		Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        boolean result = false;
        Matcher mather = pattern.matcher(email);
 
        if (mather.find() == true) 
        	result = true;
        
        return result;
	}

	public List<SelectItem> getSelectAdmonFindAllMaterias(){
		List<SelectItem> lselectMateriasItems = new ArrayList<SelectItem>();
		List<KeysDto> listAdmonMaterias = admonMaestroMateria.findKeysMaterias();
		Iterator<KeysDto> iterMateriasDto = listAdmonMaterias.iterator();
		while(iterMateriasDto.hasNext()) {
			KeysDto keysDto = iterMateriasDto.next();
			SelectItem selectItem = new SelectItem(keysDto.getNumero(),keysDto.getNombre());
			lselectMateriasItems.add(selectItem);
		}
		return lselectMateriasItems; 
	}
	
	public void mostrarMaterias(){
		setDispMaterias(false);
		for(int i = 0; i<selectedRoles.length;i++){
			long n=Long.parseLong(selectedRoles[i]);			
	        System.out.println(n);
	        if(arrNumMaestro.contains(n)) {
	        	setDispMaterias(true);
	        	break;
	        }
	    }	    
	}
	
	public void handleToggle(ToggleEvent event) {
		//    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Toggled", "Visibility:" + event.getVisibility());
		//    FacesContext.getCurrentInstance().addMessage(null, msg);
	}
}
