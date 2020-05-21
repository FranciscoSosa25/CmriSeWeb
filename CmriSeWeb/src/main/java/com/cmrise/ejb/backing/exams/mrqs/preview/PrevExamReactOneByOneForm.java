package com.cmrise.ejb.backing.exams.mrqs.preview;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import com.cmrise.ejb.helpers.UserLogin;
import com.cmrise.ejb.model.exams.MrqsExamenes;
import com.cmrise.ejb.model.exams.MrqsGrupoHdr;
import com.cmrise.ejb.model.exams.MrqsGrupoLines;
import com.cmrise.ejb.model.mrqs.MrqsPreguntasFtaV1;
import com.cmrise.ejb.model.mrqs.MrqsPreguntasHdrV1;
import com.cmrise.ejb.services.exams.MrqsExamenesLocal;
import com.cmrise.ejb.services.exams.MrqsGrupoHdrLocal;
import com.cmrise.ejb.services.exams.MrqsGrupoLinesLocal;
import com.cmrise.ejb.services.mrqs.MrqsOpcionMultipleLocal;
import com.cmrise.ejb.services.mrqs.MrqsPreguntasFtaLocal;
import com.cmrise.utils.Utilitarios;
import com.cmrise.utils.UtilitariosLocal;

@ManagedBean
@ViewScoped
public class PrevExamReactOneByOneForm {
private MrqsExamenes mrqsExamen = new MrqsExamenes(); 
	
	@Inject
	UtilitariosLocal utilitariosLocal; 
	
	@Inject 
	MrqsExamenesLocal mrqsExamenesLocal; 
	
	@Inject 
	MrqsGrupoHdrLocal mrqsGrupoHdrLocal; 
	
	@Inject 
	MrqsGrupoLinesLocal mrqsGrupoLinesLocal; 
	
	@Inject 
	MrqsOpcionMultipleLocal mrqsOpcionMultipleLocal; 
	
	@Inject 
	MrqsPreguntasFtaLocal mrqsPreguntasFtaLocal; 
	
	@ManagedProperty(value="#{userLogin}")
	private UserLogin userLogin; 
	
	private Date fechaActual  = new Date(); 
	
	/*******************************************************************/
	private List<MrqsGrupoHdr> listMrqsGrupoHdr = new ArrayList<MrqsGrupoHdr>(); 
	private MrqsGrupoHdr mrqsGrupoHdrForRead = new MrqsGrupoHdr(); 
	private int idxMrqsGrupo = 0; 
	private int listMrqsGrupoHdrSize = 0; 
	private int idxMrqsGrupoLines = 0; 
	private int listMrqsGrupoLinesSize = 0; 
	private boolean flag1 = true; 
	private boolean flag2 = true; 
	private boolean flag3 = true; 
	private List<MrqsGrupoLines> listMrqsGrupoLinesForRead = new ArrayList<MrqsGrupoLines>(); 
	private MrqsGrupoLines mrqsGrupoLinesForRead = new MrqsGrupoLines(); 
	private MrqsPreguntasHdrV1 mrqsPreguntasHdrV1ForRead = new MrqsPreguntasHdrV1(); 
	/*******************************************************************/
	
	@PostConstruct
	public void init() {
		 FacesContext context = FacesContext.getCurrentInstance(); 
		 HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		 Object objNumeroMrqsExamenSV = session.getAttribute("NumeroMrqsExamenSV"); 
	     long numeroMrqsExamenSV = Utilitarios.objToLong(objNumeroMrqsExamenSV); 
	     System.out.println("numeroMrqsExamenSV:"+numeroMrqsExamenSV);
	     if(null==mrqsExamen) {
	    	  mrqsExamen = mrqsExamenesLocal.findObjMod(numeroMrqsExamenSV); 
	   	 }else {
	   		 if(mrqsExamen.getNumero()==0) {
	   			mrqsExamen = mrqsExamenesLocal.findObjMod(numeroMrqsExamenSV); 
	   		 }
	   	 }
	     
	     if(null!=mrqsExamen) {
	       if(mrqsExamen.getNumero()!=0) {
	    	   listMrqsGrupoHdr = mrqsExamen.getListMrqsGrupoHdr(); 
	    	   for(MrqsGrupoHdr i:listMrqsGrupoHdr) {
	    		   idxMrqsGrupo = listMrqsGrupoHdr.indexOf(i); 
	    		   listMrqsGrupoHdrSize = listMrqsGrupoHdr.size()-1; 
	    		   mrqsGrupoHdrForRead = i; 
	    		   
	    		   if(mrqsGrupoHdrForRead.getNumero()!=0) {
	    				listMrqsGrupoLinesForRead = mrqsGrupoHdrForRead.getListMrqsGrupoLines();
	    				for(MrqsGrupoLines j:listMrqsGrupoLinesForRead) {
	    					mrqsGrupoLinesForRead = j; 
	    					mrqsPreguntasHdrV1ForRead = mrqsGrupoLinesForRead.getMrqsPreguntasHdrV1();
	    					idxMrqsGrupoLines = listMrqsGrupoLinesForRead.indexOf(j); 
	    					listMrqsGrupoLinesSize = listMrqsGrupoLinesForRead.size()-1; 
	    					flag2 = false; 
	    					break; 
	    				}
	    			}
	    		   break; 
	    	   }
	       }
	     }
	}

	public String backExamenesReactivos() {
		return "Exams-MRQs-Update"; 
	}
	
	public void regresar() {
		System.out.println("idxMrqsGrupo:"+idxMrqsGrupo);
		System.out.println("listMrqsGrupoHdrSize:"+listMrqsGrupoHdrSize);
		
		if(idxMrqsGrupoLines>0) {
			idxMrqsGrupoLines = idxMrqsGrupoLines-1; 
			mrqsGrupoLinesForRead = listMrqsGrupoLinesForRead.get(idxMrqsGrupoLines); 
			mrqsPreguntasHdrV1ForRead = mrqsGrupoLinesForRead.getMrqsPreguntasHdrV1();
			flag2 = false; 
			if(idxMrqsGrupoLines==0) {
				flag1= true; 
			}
		}else {
			flag1= true; 
		}
		
		if(idxMrqsGrupo>0) {
			idxMrqsGrupo = idxMrqsGrupo-1; 
			mrqsGrupoHdrForRead = listMrqsGrupoHdr.get(idxMrqsGrupo);
			flag2 = false; 
		}else {
			flag1= true; 
		}
		System.out.println("idxMrqsGrupo:"+idxMrqsGrupo);
	}
	
	public void continuar() {
		System.out.println("idxMrqsGrupo:"+idxMrqsGrupo);
		System.out.println("listMrqsGrupoHdrSize:"+listMrqsGrupoHdrSize);
		
		if(idxMrqsGrupoLines<listMrqsGrupoLinesSize) {
			idxMrqsGrupoLines = idxMrqsGrupoLines+1; 
			mrqsGrupoLinesForRead = listMrqsGrupoLinesForRead.get(idxMrqsGrupoLines); 
			mrqsPreguntasHdrV1ForRead = mrqsGrupoLinesForRead.getMrqsPreguntasHdrV1();
			if(idxMrqsGrupoLines==listMrqsGrupoLinesSize) {
				flag1= false; 
			}
			flag1= false; 
		}else {
			flag2 = true; 
		}
		/*
		if(idxMrqsGrupo<listMrqsGrupoHdrSize) {
			idxMrqsGrupo = idxMrqsGrupo+1;
			mrqsGrupoHdrForRead = listMrqsGrupoHdr.get(idxMrqsGrupo);
			flag1= false; 
		}else {
			flag2 = true; 
		}
		*/
		System.out.println("idxMrqsGrupo:"+idxMrqsGrupo);
	}
	
	public void finalizarExamen() {
		
	}
	
	public MrqsExamenes getMrqsExamen() {
		return mrqsExamen;
	}

	public void setMrqsExamen(MrqsExamenes mrqsExamen) {
		this.mrqsExamen = mrqsExamen;
	}
	
	public UserLogin getUserLogin() {
		return userLogin;
	}
	public void setUserLogin(UserLogin userLogin) {
		this.userLogin = userLogin;
	}

	public Date getFechaActual() {
		return fechaActual;
	}

	public void setFechaActual(Date fechaActual) {
		this.fechaActual = fechaActual;
	}

	public List<MrqsGrupoHdr> getListMrqsGrupoHdr() {
		return listMrqsGrupoHdr;
	}

	public void setListMrqsGrupoHdr(List<MrqsGrupoHdr> listMrqsGrupoHdr) {
		this.listMrqsGrupoHdr = listMrqsGrupoHdr;
	}

	public MrqsGrupoHdr getMrqsGrupoHdrForRead() {
		return mrqsGrupoHdrForRead;
	}

	public void setMrqsGrupoHdrForRead(MrqsGrupoHdr mrqsGrupoHdrForRead) {
		this.mrqsGrupoHdrForRead = mrqsGrupoHdrForRead;
	}

	public boolean isFlag1() {
		return flag1;
	}

	public void setFlag1(boolean flag1) {
		this.flag1 = flag1;
	}

	public boolean isFlag2() {
		return flag2;
	}

	public void setFlag2(boolean flag2) {
		this.flag2 = flag2;
	}

	public boolean isFlag3() {
		return flag3;
	}

	public void setFlag3(boolean flag3) {
		this.flag3 = flag3;
	}

	public List<MrqsGrupoLines> getListMrqsGrupoLinesForRead() {
		return listMrqsGrupoLinesForRead;
	}

	public void setListMrqsGrupoLinesForRead(List<MrqsGrupoLines> listMrqsGrupoLinesForRead) {
		this.listMrqsGrupoLinesForRead = listMrqsGrupoLinesForRead;
	}

	public MrqsGrupoLines getMrqsGrupoLinesForRead() {
		return mrqsGrupoLinesForRead;
	}

	public void setMrqsGrupoLinesForRead(MrqsGrupoLines mrqsGrupoLinesForRead) {
		this.mrqsGrupoLinesForRead = mrqsGrupoLinesForRead;
	}

	public MrqsPreguntasHdrV1 getMrqsPreguntasHdrV1ForRead() {
		return mrqsPreguntasHdrV1ForRead;
	}

	public void setMrqsPreguntasHdrV1ForRead(MrqsPreguntasHdrV1 mrqsPreguntasHdrV1ForRead) {
		this.mrqsPreguntasHdrV1ForRead = mrqsPreguntasHdrV1ForRead;
	}

}
