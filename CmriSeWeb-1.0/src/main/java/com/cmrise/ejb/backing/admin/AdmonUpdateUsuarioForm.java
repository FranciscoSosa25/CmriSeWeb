package com.cmrise.ejb.backing.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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

import org.primefaces.PrimeFaces;
import org.primefaces.event.ToggleEvent;

import com.cmrise.ejb.helpers.SelectsHelper;
import com.cmrise.ejb.helpers.UserLogin;
import com.cmrise.ejb.model.admin.AdmonUsuarios;
import com.cmrise.ejb.model.admin.AdmonUsuariosRolesV1;
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
public class AdmonUpdateUsuarioForm {
    private String nombre; 
    private String apellidoPaterno; 
    private String apellidoMaterno; 
    private String contrasenia; 
    private String correoElectronico;
    private Date fechaEfectivaDesde;
	private Date fechaEfectivaHasta;
	private String ErrorCaptura;
	private List<AdmonUsuarios> listAdmonUsuarios = new ArrayList<AdmonUsuarios>();
	private AdmonUsuarios admonUsuariosForAction = new AdmonUsuarios(); 
	private List<AdmonUsuariosRolesV1> listAdmonUsuariosRolesV1 = new ArrayList<AdmonUsuariosRolesV1>();
	private String curp; 
	private long numeroUsuario;
	private List<SelectItem> Roles;
	private String[] selectedRoles;
	private List<SelectItem> Materias;
	private String[] selectedMaterias;
	private boolean dispMaterias = false;
	private List<Long> arrNumMaestro = new ArrayList<Long>();
	private List<Long> arrRolesUsuario = new ArrayList<Long>();
	private String[] rolesOriginalesUser;
	private List<Long> lstmateriasUsuario = new ArrayList<Long>();
	private String[] arrayparamaterias;
	private boolean updateIn;
	
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
    	System.out.println("Comienza UpdatUsuario init()");   	
		
		FacesContext context = FacesContext.getCurrentInstance(); 
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		Object objNumeroUsuario = session.getAttribute("NumeroUsuario");
		setNumeroUsuario(utilitariosLocal.objToLong(objNumeroUsuario)); 
		
		List<SelectItem> data = selhel.getSelectAdmonRolesItemsNotCand();
		 data.forEach(item -> {
			 if (item.getLabel().toUpperCase().contains("MAESTRO")) {
				 arrNumMaestro.add(Long.parseLong(item.getValue().toString()));
			 }
		 });
		
		updateIn = false;		
		listAdmonUsuariosRolesV1 =  admonUsuariosRolesLocal.findUser(numeroUsuario); 
		Iterator<AdmonUsuariosRolesV1> iterAdmonUsuariosRolesV1 = listAdmonUsuariosRolesV1.iterator(); 
	
		while(iterAdmonUsuariosRolesV1.hasNext()) {
			AdmonUsuariosRolesV1 admonUsuariosRolesV1 = iterAdmonUsuariosRolesV1.next(); 
			admonUsuariosForAction.setNumero(admonUsuariosRolesV1.getNumeroUsuario());
			admonUsuariosForAction.setCurp(admonUsuariosRolesV1.getCurp());
			setCurp(admonUsuariosRolesV1.getCurp());
			admonUsuariosForAction.setNombre(admonUsuariosRolesV1.getNombreUsuario());
			setNombre(admonUsuariosRolesV1.getNombreUsuario());
			admonUsuariosForAction.setApellidoPaterno(admonUsuariosRolesV1.getApellidoPaterno());
			setApellidoPaterno(admonUsuariosRolesV1.getApellidoPaterno());
			admonUsuariosForAction.setApellidoMaterno(admonUsuariosRolesV1.getApellidoMaterno());
			setApellidoMaterno(admonUsuariosRolesV1.getApellidoMaterno());
			admonUsuariosForAction.setContrasenia(admonUsuariosRolesV1.getContrasenia());
			setContrasenia(admonUsuariosRolesV1.getContrasenia());
			admonUsuariosForAction.setCorreoElectronico(admonUsuariosRolesV1.getCorreoElectronico());
			setCorreoElectronico(admonUsuariosRolesV1.getCorreoElectronico());
			admonUsuariosForAction.setFechaEfectivaDesde(admonUsuariosRolesV1.getFedAu());
			setFechaEfectivaDesde(admonUsuariosRolesV1.getFedAu());
			if(Utilitarios.endOfTime.equals(admonUsuariosRolesV1.getFehAu())) {
				admonUsuariosForAction.setFechaEfectivaHasta(null);	
				
			}else {
				admonUsuariosForAction.setFechaEfectivaHasta(admonUsuariosRolesV1.getFehAu());	
				setFechaEfectivaHasta(admonUsuariosRolesV1.getFehAu());
			}	
			
			setSelectedRoles(admonRoles.findKeysRolesUser(getNumeroUsuario()));
			setRolesOriginalesUser(admonRoles.findKeysRolesUser(getNumeroUsuario()));
			
			for(String text:rolesOriginalesUser) 
				if(text!=null)
					arrRolesUsuario.add(Long.parseLong(text));
		      
			
			setDispMaterias(false);
			for(int i = 0; i<selectedRoles.length;i++){
				if(selectedRoles[i] != null){
					long n=Long.parseLong(selectedRoles[i]);	
			        if(arrNumMaestro.contains(n)) {
			        	setDispMaterias(true);
			        	break;
			        }
				}
		    }
			
			if(isDispMaterias()==true) {
				setSelectedMaterias(admonMaestroMateria.findKeysMateriasOfMaestros(getNumeroUsuario()));
				setArrayparamaterias(admonMaestroMateria.findKeysMateriasOfMaestros(getNumeroUsuario()));
				
				for(String text:arrayparamaterias) 
					if(text!=null)
						lstmateriasUsuario.add(Long.parseLong(text));
			}
		}
		
		refreshEntity();     
	 }		 
	
    public void refreshEntity() {	
	}
    
    public void Update() throws IOException {
		ErrorCaptura = "";
		java.sql.Date fechaED;
		java.sql.Date fechaEH;
		
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
		
		if(isDispMaterias()==true && getSelectedMaterias().length == 0)
			ErrorCaptura += "Seleccione una materia. " + '\n';
		
		if(ErrorCaptura == "")
			updateIn = updateUsuario(getCurp(), getNombre(), getApellidoPaterno(), getApellidoMaterno(), getContrasenia(), 
					getCorreoElectronico(), fechaED, fechaEH, getSelectedRoles(), getSelectedMaterias());
	    
	    if(ErrorCaptura != ""){
			
			System.out.println("ActualizaUsuario: Error "+ErrorCaptura);
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cambios no realizados", ErrorCaptura));
	        ErrorCaptura = "";	        
		}
	    PrimeFaces.current().ajax().addCallbackParam("createIn", updateIn);
	    if(updateIn) {
	    	showMessage();
	    	cancel();
	    }
	}
    
	public boolean updateUsuario(String curp, String nombre, String aPaterno, String aMaterno, String pass, String email, 
			java.sql.Date fechaDesde, java.sql.Date fechaHasta, String[] roles, String[] materias) {
		boolean insertaUser = true; 
		
		try {
		AdmonUsuariosDto admonUsuariosDto = new AdmonUsuariosDto();
		admonUsuariosDto.setCurp(curp);
		admonUsuariosDto.setNombre(nombre);
		admonUsuariosDto.setApellidoPaterno(aPaterno);
		admonUsuariosDto.setApellidoMaterno(aMaterno);
		admonUsuariosDto.setCorreoElectronico(email);
		admonUsuariosDto.setContrasenia(pass);
		admonUsuariosDto.setFechaEfectivaDesde(fechaDesde);
		admonUsuariosDto.setFechaEfectivaHasta(fechaHasta);
		admonUsuariosDto.setActualizadoPor(userLogin.getNumeroUsuario());
		admonUsuariosLocal.update(admonUsuariosForAction.getNumero(), admonUsuariosDto);
		
		AdmonUsuariosRolesDto admonUsuariosRolesDto = new AdmonUsuariosRolesDto();
		AdmonRolesDto admonRolesDto = new AdmonRolesDto();
		admonUsuariosDto.setNumero(admonUsuariosForAction.getNumero());
		admonUsuariosRolesDto.setAdmonUsuario(admonUsuariosDto);
		admonUsuariosRolesDto.setAdmonRole(admonRolesDto);		
		admonUsuariosRolesDto.setFechaEfectivaDesde(fechaDesde);
		admonUsuariosRolesDto.setFechaEfectivaHasta(fechaHasta);	
		admonUsuariosRolesDto.setActualizadoPor(userLogin.getNumeroUsuario());
		
		List<Long> listroles = new ArrayList<Long>();
		List<Long> listmaterias = new ArrayList<Long>();
		
		for(String text: roles)
			listroles.add(Long.parseLong(text));
		
		for(Long n: arrRolesUsuario)
		if(!listroles.contains(n)) {
			int intValidaUsarioRol = admonUsuariosRolesLocal.validaUsuarioRolID(admonUsuariosForAction.getNumero(), n);
			admonUsuariosRolesLocal.deleteLogico(intValidaUsarioRol, admonUsuariosRolesDto);
		}
		
		for(int i = 0; i< roles.length; i++){			
			int intValidaUsarioRol = admonUsuariosRolesLocal.validaUsuarioRolID(admonUsuariosForAction.getNumero(), Long.parseLong(roles[i]));
			
			if(intValidaUsarioRol == 0) {					
				admonRolesDto.setNumero(Long.parseLong(roles[i]));
				admonUsuariosRolesDto.setEstatus(true);
				admonUsuariosRolesLocal.insert(admonUsuariosRolesDto);		
			}else {					
				admonRolesDto.setNumero(Long.parseLong(roles[i]));
				admonUsuariosRolesLocal.activaRolUsuario(intValidaUsarioRol, admonUsuariosRolesDto);
			}
		}
		
		AdmonMaestroMateriaDto admonMaestroMateriaDto = new AdmonMaestroMateriaDto();
		admonMaestroMateriaDto.setIdMaestro(admonUsuariosForAction.getNumero());
		
		if(isDispMaterias()==true) {
			for(String text: materias)
				listmaterias.add(Long.parseLong(text));
			
			for(Long n: lstmateriasUsuario)
				if(!listmaterias.contains(n)) {
					int intValidaUsarioRol = admonMaestroMateria.validaMaestroMateriaID(admonUsuariosForAction.getNumero(), n);
					admonMaestroMateria.delete(intValidaUsarioRol);
				}
			
			for(int i = 0; i< materias.length; i++){
				int validaMateria = admonMaestroMateria.validaMaestroMateria(admonUsuariosForAction.getNumero(), Long.parseLong(materias[i]));
				
				if(validaMateria == 0){
					admonMaestroMateriaDto.setIdMateria(Long.parseLong(materias[i]));
					admonMaestroMateriaDto.setEstatus(true);
					admonMaestroMateria.insert(admonMaestroMateriaDto);	
				}
			}	
		}else if(isDispMaterias()== false && lstmateriasUsuario.size()>0) {
			for(Long n: lstmateriasUsuario) {
				int intValidaUsarioRol = admonMaestroMateria.validaMaestroMateriaID(admonUsuariosForAction.getNumero(), n);
				admonMaestroMateria.delete(intValidaUsarioRol);
			}
		}
		
		} catch(Exception ex) {
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
			 insertaUser = false;
		}
		return insertaUser;
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
	public String getCurp() {
		return curp;
	}
	public void setCurp(String curp) {
		this.curp = curp;
	}
	public String getErrorCaptura() {
		return ErrorCaptura;
	}
	public void setErrorCaptura(String errorCaptura) {
		ErrorCaptura = errorCaptura;
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
	public boolean isDispMaterias() {
		return dispMaterias;
	}
	public void setDispMaterias(boolean dispMaterias) {
		this.dispMaterias = dispMaterias;
	}
	public List<Long> getArrNumMaestro() {
		return arrNumMaestro;
	}
	public void setArrNumMaestro(List<Long> arrNumMaestro) {
		this.arrNumMaestro = arrNumMaestro;
	}
	public long getNumeroUsuario() {
		return numeroUsuario;
	}
	public void setNumeroUsuario(long numeroUsuario) {
		this.numeroUsuario = numeroUsuario;
	}	
	public List<AdmonUsuariosRolesV1> getListAdmonUsuariosRolesV1() {
		return listAdmonUsuariosRolesV1;
	}
	public void setListAdmonUsuariosRolesV1(List<AdmonUsuariosRolesV1> listAdmonUsuariosRolesV1) {
		this.listAdmonUsuariosRolesV1 = listAdmonUsuariosRolesV1;
	}
	
	public void cancel() throws IOException {
		FacesContext context = FacesContext.getCurrentInstance(); 
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);		
		System.out.println("Finaliza UpdateUsuarioForm init()");
		context.getExternalContext().redirect("/CmriSeWeb/faces/cmrise/admin/AdmonUsuarios.xhtml");
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
	        if(arrNumMaestro.contains(n)) {
	        	setDispMaterias(true);
	        	break;
	        }
	    }	    
	}
	
	public void handleToggle(ToggleEvent event) {}

	public List<Long> getArrRolesUsuario() {
		return arrRolesUsuario;
	}

	public void setArrRolesUsuario(List<Long> arrRolesUsuario) {
		this.arrRolesUsuario = arrRolesUsuario;
	}

	public String[] getRolesOriginalesUser() {
		return rolesOriginalesUser;
	}

	public void setRolesOriginalesUser(String[] rolesOriginalesUser) {
		this.rolesOriginalesUser = rolesOriginalesUser;
	}

	public List<Long> getLstmateriasUsuario() {
		return lstmateriasUsuario;
	}

	public void setLstmateriasUsuario(List<Long> lstmateriasUsuario) {
		this.lstmateriasUsuario = lstmateriasUsuario;
	}

	public String[] getArrayparamaterias() {
		return arrayparamaterias;
	}

	public void setArrayparamaterias(String[] arrayparamaterias) {
		this.arrayparamaterias = arrayparamaterias;
	}
	
	public void showMessage() {
		Map<String,Object> options = new HashMap<>();	    
	    options.put("modal", true);

	    PrimeFaces.current().dialog().openDynamic("Usuario", options, null);
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Nuevo Usuario", " Cambios guardados exitosamente");
        PrimeFaces.current().dialog().showMessageDynamic(message);
   }
}
