package com.cmrise.ejb.backing.admin;

import java.util.List;
import java.util.ArrayList; 
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.cmrise.ejb.helpers.UserLogin;
import com.cmrise.ejb.model.admin.TablasUtilitariasValoresV1;
import com.cmrise.ejb.services.admin.TablasUtilitariasValoresLocal;

@ManagedBean
@ViewScoped
public class ManageTablasUtilitariasForm {
	
	 private List<TablasUtilitariasValoresV1> listTablasUtilitariasValoresV1 = new ArrayList<TablasUtilitariasValoresV1>(); 
	 private String tipoTabla; 
	 
	 @Inject 
	 TablasUtilitariasValoresLocal tablasUtilitariasValoresLocal; 
	 
	 @ManagedProperty(value="#{userLogin}")
	 private UserLogin userLogin; 

	 
	 @PostConstruct
	 public void init() {
		 listTablasUtilitariasValoresV1 =  tablasUtilitariasValoresLocal.findAllByGroup(); 
	 }
	 
	 public String toRegistros(TablasUtilitariasValoresV1 pTablasUtilitariasValoresV1) {
		 FacesContext context = FacesContext.getCurrentInstance();  
    	 HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest(); 
	     HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
	     session.setAttribute("tablaUtilitariaSV", pTablasUtilitariasValoresV1.getTipoTabla());
	     return "Admin-TablasUtilitariasvalores"; 
	 }

	 public String nuevaLista() {
		 FacesContext context = FacesContext.getCurrentInstance();  
    	 HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest(); 
	     HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
	     session.setAttribute("tablaUtilitariaSV", tipoTabla);
	     return "Admin-TablasUtilitariasvalores"; 
	 }
	 
	public List<TablasUtilitariasValoresV1> getListTablasUtilitariasValoresV1() {
		return listTablasUtilitariasValoresV1;
	}

	public void setListTablasUtilitariasValoresV1(List<TablasUtilitariasValoresV1> listTablasUtilitariasValoresV1) {
		this.listTablasUtilitariasValoresV1 = listTablasUtilitariasValoresV1;
	}
	
	public UserLogin getUserLogin() {
		return userLogin;
	}
	public void setUserLogin(UserLogin userLogin) {
		this.userLogin = userLogin;
	}

	public String getTipoTabla() {
		return tipoTabla;
	}

	public void setTipoTabla(String tipoTabla) {
		this.tipoTabla = tipoTabla;
	}
	
}
