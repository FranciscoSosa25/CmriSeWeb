package com.cmrise.ejb.helpers;

import java.util.List;

import javax.ejb.Local;
import javax.faces.model.SelectItem;

@Local
public interface SelectsHelperLocal {

	public List<SelectItem> getSelectTipoPreguntaItems(); 
	public List<SelectItem> getSelectTipoPreguntaCoreCaseItems();
	public List<SelectItem> getSelectTemaDePreguntaItems(); 
	public List<SelectItem> getSelectEstatusMrqsItems(); 
	public List<SelectItem> getSelectScoringValueItems(); 
	public List<SelectItem> getSelectScoringMethodItems(); 
	public List<SelectItem> getSelectTipoExamenItems(); 
	public List<SelectItem> getSelectTemaExamenItems(); 
	public List<SelectItem> getSelectVisibilidadItems(); 
	public List<SelectItem> getSelectEstatusExamenItems(); 
	
}
