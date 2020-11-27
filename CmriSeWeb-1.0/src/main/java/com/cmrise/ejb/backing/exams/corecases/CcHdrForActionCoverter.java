package com.cmrise.ejb.backing.exams.corecases;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import com.cmrise.ejb.model.corecases.CcHdrForAction;

@FacesConverter(value="ccHdrForActionCoverter")
public class CcHdrForActionCoverter implements Converter {

	private final static String DELIMITER = "~";
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
	    System.out.println("value:"+value);
	    String [] arrayList = value.split(this.DELIMITER); 
	    CcHdrForAction ccHdrForAction = new CcHdrForAction(); 
	    long longNumero = new Long(arrayList[0]);
	    ccHdrForAction.setNumero(longNumero);
	    ccHdrForAction.setNombre(arrayList[1]);
	    ccHdrForAction.setEstatus(arrayList[2]);
	    ccHdrForAction.setEstatusDesc(arrayList[3]);
	    ccHdrForAction.setTema(arrayList[4]);
	    ccHdrForAction.setTemaDesc(arrayList[5]);
	  return ccHdrForAction;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		 CcHdrForAction cHdrForAction = null; 
	     cHdrForAction = (CcHdrForAction)value; 
	     String retval =""; 
	     retval =retval+cHdrForAction.getNumero();
	     retval =retval+this.DELIMITER+cHdrForAction.getNombre();
	     retval =retval+this.DELIMITER+cHdrForAction.getEstatus();
	     retval =retval+this.DELIMITER+cHdrForAction.getEstatusDesc();
	     retval =retval+this.DELIMITER+cHdrForAction.getTema();
	     retval =retval+this.DELIMITER+cHdrForAction.getTemaDesc();
	   return retval;
	   
	}

}
