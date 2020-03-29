package com.cmrise.ejb.backing.admin;

import java.io.IOException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import com.cmrise.ejb.services.admin.AdmonUsuariosRolesLocal;
import com.cmrise.utils.Utilitarios;

@ManagedBean
@RequestScoped
public class IndexForm {

	private String username; 
	private String password;
	
	
	@Inject 
	AdmonUsuariosRolesLocal admonUsuariosRolesLocal; 
	
	
	public String login() throws IOException, ServletException{
		System.out.println("Entra IndexForm login()");
		System.out.println("username:"+username);
		System.out.println("password:"+password);
		
		FacesContext context = FacesContext.getCurrentInstance();
	    ExternalContext externalContext = context.getExternalContext();
	    HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
	    
		int intLoginMaestro = admonUsuariosRolesLocal.loginUsuarioRol(username, Utilitarios.ROL_MAESTRO, password);
		System.out.println("intLoginMaestro:"+intLoginMaestro);
		if(intLoginMaestro!=0) {
			request.getSession().setAttribute("xXRole",Utilitarios.ROL_MAESTRO);
			return "PaginaPrincipal"; 
		}
		
		int intLoginUsuario = admonUsuariosRolesLocal.loginUsuarioRol(username, Utilitarios.ROL_USUARIO, password);
		System.out.println("intLoginUsuario:"+intLoginUsuario);
		if(intLoginUsuario!=0) {
			request.getSession().setAttribute("xXRole",Utilitarios.ROL_USUARIO);
			return "PaginaPrincipal"; 
		}
		int intLoginAlumno = admonUsuariosRolesLocal.loginUsuarioRol(username, Utilitarios.ROL_ALUMNO, password);
		System.out.println("intLoginAlumno:"+intLoginAlumno);
		if(intLoginAlumno!=0) {
			request.getSession().setAttribute("xXRole",Utilitarios.ROL_ALUMNO);
			return "PaginaPrincipal"; 
		}
		context.addMessage(null, new FacesMessage("Acesso no valido","Porfavor ingrese las credenciales correctas") );
        System.out.println("Sale IndexForm login()");
		return "/index.xhtml"; 
			
	}
	
	

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	} 
	
}
