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
	
	
	public List<SelectItem> getSelectTipoPreguntaCoreCaseItems(){
		List<SelectItem> lselectTipoPreguntaItems = new ArrayList<SelectItem>();
		List<TablasUtilitariasValoresDto> listTipoPreguntaValores =  tablasUtilitariasValoresLocal.findByTipoTabla("TIPO_PREGUNTA");  
		Iterator<TablasUtilitariasValoresDto> iterTipoPreguntaValores = listTipoPreguntaValores.iterator(); 
		while(iterTipoPreguntaValores.hasNext()) {
			TablasUtilitariasValoresDto tablasUtilitariasValoresDto = iterTipoPreguntaValores.next();
			if(!"IMAGEN_ANOTADA".equals(tablasUtilitariasValoresDto.getCodigoTabla())) {
			SelectItem selectItem = new SelectItem(tablasUtilitariasValoresDto.getCodigoTabla(),tablasUtilitariasValoresDto.getSignificado()); 
			lselectTipoPreguntaItems.add(selectItem); 
			}
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
	
	public List<SelectItem> getSelectScoringMethodItems(){
		List<SelectItem> lselectScoringMethodItems = new ArrayList<SelectItem>();
		List<TablasUtilitariasValoresDto> listScoringMethodValores =  tablasUtilitariasValoresLocal.findByTipoTabla("SCORING_METHOD");  
		Iterator<TablasUtilitariasValoresDto> iterScoringMethodValores = listScoringMethodValores.iterator(); 
		while(iterScoringMethodValores.hasNext()) {
			TablasUtilitariasValoresDto tablasUtilitariasValoresDto = iterScoringMethodValores.next();
			SelectItem selectItem = new SelectItem(tablasUtilitariasValoresDto.getCodigoTabla(),tablasUtilitariasValoresDto.getSignificado()); 
			lselectScoringMethodItems.add(selectItem); 
		}
		return lselectScoringMethodItems; 
	}
	
	public List<SelectItem> getSelectTipoExamenItems(){
		List<SelectItem> lselectTipoExamenItems = new ArrayList<SelectItem>();
		List<TablasUtilitariasValoresDto> listTipoExamenValores =  tablasUtilitariasValoresLocal.findByTipoTabla("TIPO_EXAMEN");  
		Iterator<TablasUtilitariasValoresDto> iterTipoExamenValores = listTipoExamenValores.iterator(); 
		while(iterTipoExamenValores.hasNext()) {
			TablasUtilitariasValoresDto tablasUtilitariasValoresDto = iterTipoExamenValores.next();
			SelectItem selectItem = new SelectItem(tablasUtilitariasValoresDto.getCodigoTabla(),tablasUtilitariasValoresDto.getSignificado()); 
			lselectTipoExamenItems.add(selectItem); 
		}
		return lselectTipoExamenItems; 
	}
	
	public List<SelectItem> getSelectTemaExamenItems(){
		List<SelectItem> lselectTemaExamenItems = new ArrayList<SelectItem>();
		List<TablasUtilitariasValoresDto> listTemaExamenValores =  tablasUtilitariasValoresLocal.findByTipoTabla("TEMA_DE_EXAMEN");  
		Iterator<TablasUtilitariasValoresDto> iterTemaExamenValores = listTemaExamenValores.iterator(); 
		while(iterTemaExamenValores.hasNext()) {
			TablasUtilitariasValoresDto tablasUtilitariasValoresDto = iterTemaExamenValores.next();
			SelectItem selectItem = new SelectItem(tablasUtilitariasValoresDto.getCodigoTabla(),tablasUtilitariasValoresDto.getSignificado()); 
			lselectTemaExamenItems.add(selectItem); 
		}
		return lselectTemaExamenItems; 
	}
	
	public List<SelectItem> getSelectVisibilidadItems(){
		List<SelectItem> lselectVisibilidadItems = new ArrayList<SelectItem>();
		List<TablasUtilitariasValoresDto> listVisibilidadValores =  tablasUtilitariasValoresLocal.findByTipoTabla("VISIBILIDAD_EXAMEN");  
		Iterator<TablasUtilitariasValoresDto> iterVisibilidadValores = listVisibilidadValores.iterator(); 
		while(iterVisibilidadValores.hasNext()) {
			TablasUtilitariasValoresDto tablasUtilitariasValoresDto = iterVisibilidadValores.next();
			SelectItem selectItem = new SelectItem(tablasUtilitariasValoresDto.getCodigoTabla(),tablasUtilitariasValoresDto.getSignificado()); 
			lselectVisibilidadItems.add(selectItem); 
		}
		return lselectVisibilidadItems; 
	}
	
	public List<SelectItem> getSelectEstatusExamenItems(){
		List<SelectItem> lselectEstatusExamenItems = new ArrayList<SelectItem>();
		List<TablasUtilitariasValoresDto> listEstatusExamenValores =  tablasUtilitariasValoresLocal.findByTipoTabla("ESTATUS_EXAMEN");  
		Iterator<TablasUtilitariasValoresDto> iterEstatusExamenValores = listEstatusExamenValores.iterator(); 
		while(iterEstatusExamenValores.hasNext()) {
			TablasUtilitariasValoresDto tablasUtilitariasValoresDto = iterEstatusExamenValores.next();
			SelectItem selectItem = new SelectItem(tablasUtilitariasValoresDto.getCodigoTabla(),tablasUtilitariasValoresDto.getSignificado()); 
			lselectEstatusExamenItems.add(selectItem); 
		}
		return lselectEstatusExamenItems; 
	}
	
}
