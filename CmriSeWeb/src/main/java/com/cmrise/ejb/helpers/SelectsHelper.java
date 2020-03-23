package com.cmrise.ejb.helpers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import com.cmrise.ejb.services.admin.AdmonRolesLocal;
import com.cmrise.ejb.services.admin.AdmonUsuariosLocal;
import com.cmrise.ejb.services.admin.TablasUtilitariasValoresLocal;
import com.cmrise.ejb.services.corecases.CcHdrLocal;
import com.cmrise.jpa.dto.admin.KeysDto;
import com.cmrise.jpa.dto.admin.TablasUtilitariasValoresDto;

@ManagedBean
@ViewScoped
public class SelectsHelper {

	@Inject 
	TablasUtilitariasValoresLocal tablasUtilitariasValoresLocal; 

	@Inject 
	AdmonRolesLocal admonRolesLocal;
	
	@Inject 
	AdmonUsuariosLocal admonUsuariosLocal;
	
	@Inject 
	CcHdrLocal ccHdrLocal; 
	
	
	private List<SelectItem> selectTipoPreguntaItems; 
	private List<SelectItem> selectTipoExamenItems; 
	private List<SelectItem> selectTemaExamenItems; 
	private List<SelectItem> selectVisibilidadItems; 
	private List<SelectItem> selectEstatusExamenItems;
	private List<SelectItem> selectEstatusMrqsItems;
	
	@PostConstruct
    public void init() {
       System.out.println("Comienza SelectsHelper init()");
       environmentTipoPregunta();
       environmentTipoExamen();
       environmentTemaExamen();
       environmentVisibilidad();
       environmentEstatusExamen();
       environmentEstatusMrqs();
       System.out.println("Sale SelectsHelper init()");
    }
	
	
	private void environmentTipoPregunta() {
		this.selectTipoPreguntaItems = new ArrayList<SelectItem>();
		List<TablasUtilitariasValoresDto> listTipoPreguntaValores =  tablasUtilitariasValoresLocal.findByTipoTabla("TIPO_PREGUNTA");  
		Iterator<TablasUtilitariasValoresDto> iterTipoPreguntaValores = listTipoPreguntaValores.iterator(); 
		while(iterTipoPreguntaValores.hasNext()) {
			TablasUtilitariasValoresDto tablasUtilitariasValoresDto = iterTipoPreguntaValores.next();
			SelectItem selectItem = new SelectItem(tablasUtilitariasValoresDto.getCodigoTabla(),tablasUtilitariasValoresDto.getSignificado()); 
			this.selectTipoPreguntaItems.add(selectItem); 
		}
	}
	
	
	public List<SelectItem> getSelectTipoPreguntaItems(){
		return this.selectTipoPreguntaItems; 
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
	
	private void environmentEstatusMrqs() {
	 this.selectEstatusMrqsItems = new ArrayList<SelectItem>();
		List<TablasUtilitariasValoresDto> listEstatusMrqsValores =  tablasUtilitariasValoresLocal.findByTipoTabla("ESTATUS_MRQ");  
		Iterator<TablasUtilitariasValoresDto> iterEstatusMrqsValores = listEstatusMrqsValores.iterator(); 
		while(iterEstatusMrqsValores.hasNext()) {
			TablasUtilitariasValoresDto tablasUtilitariasValoresDto = iterEstatusMrqsValores.next();
			SelectItem selectItem = new SelectItem(tablasUtilitariasValoresDto.getCodigoTabla(),tablasUtilitariasValoresDto.getSignificado()); 
			this.selectEstatusMrqsItems.add(selectItem); 
		}
	} 
	
	public List<SelectItem> getSelectEstatusMrqsItems(){
		return this.selectEstatusMrqsItems; 
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
	
	private void environmentTipoExamen() {
		 this.selectTipoExamenItems = new ArrayList<SelectItem>();
		List<TablasUtilitariasValoresDto> listTipoExamenValores =  tablasUtilitariasValoresLocal.findByTipoTabla("TIPO_EXAMEN");  
		Iterator<TablasUtilitariasValoresDto> iterTipoExamenValores = listTipoExamenValores.iterator(); 
		while(iterTipoExamenValores.hasNext()) {
			TablasUtilitariasValoresDto tablasUtilitariasValoresDto = iterTipoExamenValores.next();
			SelectItem selectItem = new SelectItem(tablasUtilitariasValoresDto.getCodigoTabla(),tablasUtilitariasValoresDto.getSignificado()); 
			this.selectTipoExamenItems.add(selectItem); 
		}
	}
	
	public List<SelectItem> getSelectTipoExamenItems(){
		return this.selectTipoExamenItems; 
	}
	
	private void environmentTemaExamen() {
		this.selectTemaExamenItems = new ArrayList<SelectItem>();
		List<TablasUtilitariasValoresDto> listTemaExamenValores =  tablasUtilitariasValoresLocal.findByTipoTabla("TEMA_DE_EXAMEN");  
		Iterator<TablasUtilitariasValoresDto> iterTemaExamenValores = listTemaExamenValores.iterator(); 
		while(iterTemaExamenValores.hasNext()) {
			TablasUtilitariasValoresDto tablasUtilitariasValoresDto = iterTemaExamenValores.next();
			SelectItem selectItem = new SelectItem(tablasUtilitariasValoresDto.getCodigoTabla(),tablasUtilitariasValoresDto.getSignificado()); 
			this.selectTemaExamenItems.add(selectItem); 
		}
	}
	
	public List<SelectItem> getSelectTemaExamenItems(){
		return this.selectTemaExamenItems; 
	}
	
	private void environmentVisibilidad() {
		 this.selectVisibilidadItems = new ArrayList<SelectItem>();
		List<TablasUtilitariasValoresDto> listVisibilidadValores =  tablasUtilitariasValoresLocal.findByTipoTabla("VISIBILIDAD_EXAMEN");  
		Iterator<TablasUtilitariasValoresDto> iterVisibilidadValores = listVisibilidadValores.iterator(); 
		while(iterVisibilidadValores.hasNext()) {
			TablasUtilitariasValoresDto tablasUtilitariasValoresDto = iterVisibilidadValores.next();
			SelectItem selectItem = new SelectItem(tablasUtilitariasValoresDto.getCodigoTabla(),tablasUtilitariasValoresDto.getSignificado()); 
			 this.selectVisibilidadItems .add(selectItem); 
		}
	}
	
	public List<SelectItem> getSelectVisibilidadItems(){
		return  this.selectVisibilidadItems ; 
	}
	
	private void environmentEstatusExamen() {
		this.selectEstatusExamenItems = new ArrayList<SelectItem>();
		List<TablasUtilitariasValoresDto> listEstatusExamenValores =  tablasUtilitariasValoresLocal.findByTipoTabla("ESTATUS_EXAMEN");  
		Iterator<TablasUtilitariasValoresDto> iterEstatusExamenValores = listEstatusExamenValores.iterator(); 
		while(iterEstatusExamenValores.hasNext()) {
			TablasUtilitariasValoresDto tablasUtilitariasValoresDto = iterEstatusExamenValores.next();
			SelectItem selectItem = new SelectItem(tablasUtilitariasValoresDto.getCodigoTabla(),tablasUtilitariasValoresDto.getSignificado()); 
			this.selectEstatusExamenItems.add(selectItem); 
		}
	}
	
	public List<SelectItem> getSelectEstatusExamenItems(){
		return this.selectEstatusExamenItems; 
	}
	
	
	public List<SelectItem> getSelectAdmonRolesItems(){
		List<SelectItem> lselectAdmonRolesItems = new ArrayList<SelectItem>();
		List<KeysDto> listAdmonRolesDto = admonRolesLocal.findKeys(); 
		Iterator<KeysDto> iterAdmonRolesDto = listAdmonRolesDto.iterator();
		while(iterAdmonRolesDto.hasNext()) {
			KeysDto keysDto = iterAdmonRolesDto.next();
			SelectItem selectItem = new SelectItem(keysDto.getNumero(),keysDto.getNombre());
			lselectAdmonRolesItems.add(selectItem);
		}
		return lselectAdmonRolesItems; 
	}
	
	public List<SelectItem> getSelectAdmonUsuariosItems(){
		List<SelectItem> lselectAdmonUsuariosItems = new ArrayList<SelectItem>();
		List<KeysDto> listAdmonUsuariosDto = admonUsuariosLocal.findKeys(); 
		Iterator<KeysDto> iterAdmonUsuariosDto = listAdmonUsuariosDto.iterator();
		while(iterAdmonUsuariosDto.hasNext()) {
			KeysDto keysDto = iterAdmonUsuariosDto.next();
			SelectItem selectItem = new SelectItem(keysDto.getNumero(),keysDto.getNombre());
			lselectAdmonUsuariosItems.add(selectItem);
		}
		return lselectAdmonUsuariosItems; 
	}
	
	public List<SelectItem> getSelectCoreCasesItems(){
		List<SelectItem> lselectCoreCasesItems = new ArrayList<SelectItem>();
		List<KeysDto> listCoreCasesDto = ccHdrLocal.findKeys(); 
		Iterator<KeysDto> iterCoreCasesDto = listCoreCasesDto.iterator();
		while(iterCoreCasesDto.hasNext()) {
			KeysDto keysDto = iterCoreCasesDto.next();
			SelectItem selectItem = new SelectItem(keysDto.getNumero(),keysDto.getNombre());
			lselectCoreCasesItems.add(selectItem);
		}
		return lselectCoreCasesItems; 
	}


}
