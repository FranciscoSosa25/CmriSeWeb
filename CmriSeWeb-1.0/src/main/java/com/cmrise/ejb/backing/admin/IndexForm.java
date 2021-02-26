package com.cmrise.ejb.backing.admin;

import java.io.IOException;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



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
	    
		int intLoginMaestro = admonUsuariosRolesLocal.loginUsuarioRol(curp, Utilitarios.ROL_MAESTRO_REACT, password);
		int intLoginUsuario = admonUsuariosRolesLocal.loginUsuarioRol(curp, Utilitarios.ROL_ADMIN, password);
		int intLoginAlumno = admonUsuariosRolesLocal.loginUsuarioRol(curp, Utilitarios.ROL_ALUMNO, password);
		
		if(intLoginMaestro!=0) {
			System.out.println("intLoginMaestroReactivo:"+intLoginMaestro);
			AdmonUsuariosRolesV1Dto admonUsuariosRolesV1Dto = admonUsuariosRolesLocal.findLoginUsusarioRol(curp, Utilitarios.ROL_MAESTRO_REACT, password);
			userLogin.setNumeroUsuario(admonUsuariosRolesV1Dto.getNumeroUsuario());
			userLogin.setMatricula(admonUsuariosRolesV1Dto.getMatricula());
			userLogin.setNombreCompletoUsuario(admonUsuariosRolesV1Dto.getNombreCompletoUsuario());
			userLogin.setDescripcionRol(admonUsuariosRolesV1Dto.getDescripcionRol());
			userLogin.setCurp(this.curp);
			request.getSession().setAttribute("xXRole",Utilitarios.ROL_MAESTRO_REACT);
			return "PaginaPrincipal"; 
		} else if(intLoginUsuario!=0) {
			System.out.println("intLoginAdmin:"+intLoginUsuario);
			AdmonUsuariosRolesV1Dto admonUsuariosRolesV1Dto = admonUsuariosRolesLocal.findLoginUsusarioRol(curp, Utilitarios.ROL_ADMIN, password); 
			userLogin.setNumeroUsuario(admonUsuariosRolesV1Dto.getNumeroUsuario());
			userLogin.setMatricula(admonUsuariosRolesV1Dto.getMatricula());
			userLogin.setNombreCompletoUsuario(admonUsuariosRolesV1Dto.getNombreCompletoUsuario());
			userLogin.setDescripcionRol(admonUsuariosRolesV1Dto.getDescripcionRol());
			userLogin.setCurp(this.curp);
			request.getSession().setAttribute("xXRole",Utilitarios.ROL_ADMIN);
			return "PaginaPrincipal"; 
		}else if(intLoginAlumno!=0) {
			System.out.println("intLoginAlumno:"+intLoginAlumno);
			AdmonUsuariosRolesV1Dto admonUsuariosRolesV1Dto = admonUsuariosRolesLocal.findLoginUsusarioRol(curp, Utilitarios.ROL_ALUMNO, password); 
			userLogin.setNumeroUsuario(admonUsuariosRolesV1Dto.getNumeroUsuario());
			userLogin.setMatricula(admonUsuariosRolesV1Dto.getMatricula());
			userLogin.setNombreCompletoUsuario(admonUsuariosRolesV1Dto.getNombreCompletoUsuario());
			userLogin.setDescripcionRol(admonUsuariosRolesV1Dto.getDescripcionRol());
			userLogin.setCurp(this.curp);
			request.getSession().setAttribute("xXRole",Utilitarios.ROL_ALUMNO);
			getGuestPreferences().setTheme("deep-purple");
			return "Candidates-Manage-Exams"; 
		} else {
			context.addMessage(null, new FacesMessage("Acesso no valido","Porfavor ingrese las credenciales correctas") );
	        System.out.println("Sale IndexForm login()");
			return "/index.xhtml"; 
		}
	}
	
	public void logout ()  {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().invalidateSession();
		
		try {
			context.getExternalContext().redirect("/CmriSeWeb/faces/index.xhtml");
		} catch (IOException e) {
			e.printStackTrace();			
		}
		
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
