package com.cmrise.ejb.helpers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

import com.cmrise.ejb.services.admin.TablasUtilitariasValoresLocal;
import com.cmrise.jpa.dto.admin.TablasUtilitariasValoresDto;

@ManagedBean  
@RequestScoped
public class SelectsHelper {

	@Inject 
	TablasUtilitariasValoresLocal tablasUtilitariasValoresLocal; 
	
	public List<SelectItem> getSelectTipoPreguntaItems(){
		List<SelectItem> lselectTipoPreguntaItems = new ArrayList<SelectItem>();
		List<TablasUtilitariasValoresDto> listTipoPreguntaValores =  tablasUtilitariasValoresLocal.findByTipoTabla("TIPO_PREGUNTA");  
		Iterator<TablasUtilitariasValoresDto> iterTipoPreguntaValores = listTipoPreguntaValores.iterator(); 
		while(iterTipoPreguntaValores.hasNext()) {
			TablasUtilitariasValoresDto tablasUtilitariasValoresDto = iterTipoPreguntaValores.next();
			SelectItem selectItem = new SelectItem(tablasUtilitariasValoresDto.getCodigoTabla(),tablasUtilitariasValoresDto.getSignificado()); 
			lselectTipoPreguntaItems.add(selectItem); 
		}
		return lselectTipoPreguntaItems; 
	}
	
	public List<SelectItem> getSelectTemaDePreguntaItems(){
		List<SelectItem> lselectTemaDePreguntaItems = new ArrayList<SelectItem>();
		List<TablasUtilitariasValoresDto> listTemaDePreguntaValores =  tablasUtilitariasValoresLocal.findByTipoTabla("TEMA_DE_PREGUNTA");  
		Iterator<TablasUtilitariasValoresDto> iterTemaDePreguntaValores = listTemaDePreguntaValores.iterator(); 
		while(iterTemaDePreguntaValores.hasNext()) {
			TablasUtilitariasValoresDto tablasUtilitariasValoresDto = iterTemaDePreguntaValores.next();
			SelectItem selectItem = new SelectItem(tablasUtilitariasValoresDto.getCodigoTabla(),tablasUtilitariasValoresDto.getCodigoTabla()+" - "+tablasUtilitariasValoresDto.getSignificado()); 
			lselectTemaDePreguntaItems.add(selectItem); 
		}
		return lselectTemaDePreguntaItems; 
	}
	
	public List<SelectItem> getSelectEstatusMrqsItems(){
		List<SelectItem> lselectEstatusMrqsItems = new ArrayList<SelectItem>();
		List<TablasUtilitariasValoresDto> listEstatusMrqsValores =  tablasUtilitariasValoresLocal.findByTipoTabla("ESTATUS_MRQ");  
		Iterator<TablasUtilitariasValoresDto> iterEstatusMrqsValores = listEstatusMrqsValores.iterator(); 
		while(iterEstatusMrqsValores.hasNext()) {
			TablasUtilitariasValoresDto tablasUtilitariasValoresDto = iterEstatusMrqsValores.next();
			SelectItem selectItem = new SelectItem(tablasUtilitariasValoresDto.getCodigoTabla(),tablasUtilitariasValoresDto.getSignificado()); 
			lselectEstatusMrqsItems.add(selectItem); 
		}
		return lselectEstatusMrqsItems; 
	}
	
	public List<SelectItem> getSelectScoringValueItems(){
		List<SelectItem> lselectScoringValueItems = new ArrayList<SelectItem>();
		List<TablasUtilitariasValoresDto> listScoringValuesValores =  tablasUtilitariasValoresLocal.findByTipoTabla("SCORING_VALUE");  
		Iterator<TablasUtilitariasValoresDto> iterEstatusMrqsValores = listScoringValuesValores.iterator(); 
		while(iterEstatusMrqsValores.hasNext()) {
			TablasUtilitariasValoresDto tablasUtilitariasValoresDto = iterEstatusMrqsValores.next();
			SelectItem selectItem = new SelectItem(tablasUtilitariasValoresDto.getCodigoTabla(),tablasUtilitariasValoresDto.getSignificado()); 
			lselectScoringValueItems.add(selectItem); 
		}
		return lselectScoringValueItems; 
	}
	
}
