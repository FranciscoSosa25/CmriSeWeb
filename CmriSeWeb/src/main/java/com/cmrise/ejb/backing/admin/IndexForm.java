package com.cmrise.ejb.backing.admin;

import java.io.IOException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.cmrise.ejb.helpers.GuestPreferences;
import com.cmrise.ejb.helpers.UserLogin;
import com.cmrise.ejb.services.admin.AdmonUsuariosRolesLocal;
import com.cmrise.jpa.dto.admin.AdmonUsuariosRolesV1Dto;
import com.cmrise.utils.Utilitarios;

@ManagedBean
@RequestScoped
public class IndexForm {

	private String curp; 
	private String password;
	
	
	@Inject 
	AdmonUsuariosRolesLocal admonUsuariosRolesLocal; 
	
	@ManagedProperty(value="#{guestPreferences}")
	GuestPreferences guestPreferences; 
	
	@ManagedProperty(value="#{userLogin}")
	private UserLogin userLogin; 
	
	public String login() throws IOException, ServletException{
		System.out.println("Entra IndexForm login()");
		System.out.println("curp:"+curp);
		System.out.println("password:"+password);
		
		FacesContext context = FacesContext.getCurrentInstance();
	    ExternalContext externalContext = context.getExternalContext();
	    HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
	    
		int intLoginMaestro = admonUsuariosRolesLocal.loginUsuarioRol(curp, Utilitarios.ROL_MAESTRO, password);
		System.out.println("intLoginMaestro:"+intLoginMaestro);
		if(intLoginMaestro!=0) {
			request.getSession().setAttribute("xXRole",Utilitarios.ROL_MAESTRO);
			return "PaginaPrincipal"; 
		}
		
		int intLoginUsuario = admonUsuariosRolesLocal.loginUsuarioRol(curp, Utilitarios.ROL_USUARIO, password);
		System.out.println("intLoginUsuario:"+intLoginUsuario);
		if(intLoginUsuario!=0) {
			AdmonUsuariosRolesV1Dto admonUsuariosRolesV1Dto = admonUsuariosRolesLocal.findLoginUsusarioRol(curp, Utilitarios.ROL_USUARIO, password); 
			userLogin.setNumeroUsuario(admonUsuariosRolesV1Dto.getNumeroUsuario());
			userLogin.setMatricula(admonUsuariosRolesV1Dto.getMatricula());
			userLogin.setNombreCompletoUsuario(admonUsuariosRolesV1Dto.getNombreCompletoUsuario());
			userLogin.setDescripcionRol(admonUsuariosRolesV1Dto.getDescripcionRol());
			userLogin.setCurp(this.curp);
			request.getSession().setAttribute("xXRole",Utilitarios.ROL_USUARIO);
			return "PaginaPrincipal"; 
		}
		int intLoginAlumno = admonUsuariosRolesLocal.loginUsuarioRol(curp, Utilitarios.ROL_ALUMNO, password);
		System.out.println("intLoginAlumno:"+intLoginAlumno);
		if(intLoginAlumno!=0) {
			AdmonUsuariosRolesV1Dto admonUsuariosRolesV1Dto = admonUsuariosRolesLocal.findLoginUsusarioRol(curp, Utilitarios.ROL_ALUMNO, password); 
			userLogin.setNumeroUsuario(admonUsuariosRolesV1Dto.getNumeroUsuario());
			userLogin.setMatricula(admonUsuariosRolesV1Dto.getMatricula());
			userLogin.setNombreCompletoUsuario(admonUsuariosRolesV1Dto.getNombreCompletoUsuario());
			userLogin.setDescripcionRol(admonUsuariosRolesV1Dto.getDescripcionRol());
			userLogin.setCurp(this.curp);
			request.getSession().setAttribute("xXRole",Utilitarios.ROL_ALUMNO);
			getGuestPreferences().setTheme("deep-purple");
			return "Candidates-Manage-Exams"; 
		}
		context.addMessage(null, new FacesMessage("Acesso no valido","Porfavor ingrese las credenciales correctas") );
        System.out.println("Sale IndexForm login()");
		return "/index.xhtml"; 
			
	}
	
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public GuestPreferences getGuestPreferences() {
		return guestPreferences;
	}

	public void setGuestPreferences(GuestPreferences guestPreferences) {
		this.guestPreferences = guestPreferences;
	}



	public UserLogin getUserLogin() {
		return userLogin;
	}



	public void setUserLogin(UserLogin userLogin) {
		this.userLogin = userLogin;
	}



	public String getCurp() {
		return curp;
	}



	public void setCurp(String curp) {
		this.curp = curp;
	} 
	
}
