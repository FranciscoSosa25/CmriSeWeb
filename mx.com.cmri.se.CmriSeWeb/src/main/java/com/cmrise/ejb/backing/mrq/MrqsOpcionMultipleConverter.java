package com.cmrise.ejb.backing.mrq;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import com.cmrise.ejb.model.mrqs.MrqsOpcionMultiple;

@FacesConverter(value="mrqsOpcionMultipleConverter")
public class MrqsOpcionMultipleConverter implements Converter{

	private final static String DELIMITER = "~";
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		 System.out.println("value1:"+value);
		 //String [] arrayList = value.split(this.DELIMITER); 
		 MrqsOpcionMultiple mrqsOpcionMultiple = new MrqsOpcionMultiple();
		return mrqsOpcionMultiple;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		System.out.println("value2:"+value);
		if(null==value) {
			return "Hola";
		}
		MrqsOpcionMultiple mrqsOpcionMultiple = null; 
		mrqsOpcionMultiple = (MrqsOpcionMultiple)value; 
		String retval =""; 
		System.out.println("mrqsOpcionMultiple.isEstatus():"+mrqsOpcionMultiple.isEstatus());
		retval =retval+mrqsOpcionMultiple.isEstatus()+"";
		System.out.println(retval);
		return retval;
	}

}
