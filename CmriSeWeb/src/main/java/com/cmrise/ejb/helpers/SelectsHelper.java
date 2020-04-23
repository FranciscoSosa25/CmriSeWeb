package com.cmrise.ejb.helpers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

import com.cmrise.ejb.model.admin.AdmonMateria;
import com.cmrise.ejb.services.admin.AdmonMateriaLocal;
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
	
	@Inject 
	AdmonMateriaLocal admonMateriaLocal; 
	
	
	private List<SelectItem> selectTipoPreguntaItems; 
	private List<SelectItem> selectTipoExamenItems; 
	private List<SelectItem> selectTemaExamenItems; 
	private List<SelectItem> selectVisibilidadItems; 
	private List<SelectItem> selectEstatusExamenItems;
	private List<SelectItem> selectEstatusMrqsItems;
	private List<SelectItem> selectTemaDePreguntaItems; 
	private List<SelectItem> selectScoringValueItems;
	private List<SelectItem> selectScoringMethodItems; 
	private List<SelectItem> selectTipoPreguntaExamenItems; 
	private List<SelectItem> selectTipoPreguntaCoreCaseItems; 
	private List<SelectItem> selectEstadosMexicoItems;
	private List<SelectItem> selectSedeHospitalItems;
	private List<SelectItem> selectAdmonExamenesItems; 
	private List<SelectItem> selectAdmonMateriaItems; 
	
	@PostConstruct
    public void init() {
       System.out.println("Comienza SelectsHelper init()");
       environmentTipoPregunta();
       environmentTipoExamen();
       environmentTemaExamen();
       environmentVisibilidad();
       environmentEstatusExamen();
       environmentEstatusMrqs();
       environmentTemaDePregunta();
       environmentScoringValue();
       environmentScoringMethod();
       environmentTipoPreguntaExamen();
       environmentTipoPreguntaCoreCase();
       environmentEstadosMexico();
       environmentSedeHospital();
       environmentAdmonExamenes();
       environmentAdmonMateria(); 
       System.out.println("Sale SelectsHelper init()");
    }
	
	private void environmentAdmonMateria() {
		this.selectAdmonMateriaItems =  new ArrayList<SelectItem>();
		List<AdmonMateria> listAdmonMateria = admonMateriaLocal.findAll(); 
		for(AdmonMateria i:listAdmonMateria) {
			SelectItem selectItem = new SelectItem(i.getNumero(),i.getNombre());
			this.selectAdmonMateriaItems.add(selectItem); 
		}
		
	}
	
	public List<SelectItem> getSelectAdmonMateriaItems() {
		return selectAdmonMateriaItems;
	}


	private void environmentAdmonExamenes() {
		this.selectAdmonExamenesItems = new ArrayList<SelectItem>();
		List<TablasUtilitariasValoresDto> listAdmonExamenes =  tablasUtilitariasValoresLocal.findByTipoTabla("ADMON_EXAMENES");  
		Iterator<TablasUtilitariasValoresDto> iterAdmonExamenes = listAdmonExamenes.iterator(); 
		while(iterAdmonExamenes.hasNext()) {
			TablasUtilitariasValoresDto tablasUtilitariasValoresDto = iterAdmonExamenes.next();
			SelectItem selectItem = new SelectItem(tablasUtilitariasValoresDto.getCodigoTabla(),tablasUtilitariasValoresDto.getSignificado()); 
			this.selectAdmonExamenesItems.add(selectItem); 
		}
	}
	
	public List<SelectItem> getSelectAdmonExamenesItems() {
		return this.selectAdmonExamenesItems; 
	}

	private void environmentEstadosMexico() {
		this.selectEstadosMexicoItems = new ArrayList<SelectItem>();
		List<TablasUtilitariasValoresDto> listEstadosMexico =  tablasUtilitariasValoresLocal.findByTipoTabla("ESTADOS_MEXICO");  
		Iterator<TablasUtilitariasValoresDto> iterEstadosMexico = listEstadosMexico.iterator(); 
		while(iterEstadosMexico.hasNext()) {
			TablasUtilitariasValoresDto tablasUtilitariasValoresDto = iterEstadosMexico.next();
			SelectItem selectItem = new SelectItem(tablasUtilitariasValoresDto.getCodigoTabla(),tablasUtilitariasValoresDto.getSignificado()); 
			this.selectEstadosMexicoItems.add(selectItem); 
		}
	}
	
	
	public List<SelectItem> getSelectEstadosMexicoItems(){
		
		return this.selectEstadosMexicoItems; 
	}
	
	private void environmentSedeHospital() {
		this.selectSedeHospitalItems = new ArrayList<SelectItem>();
		List<TablasUtilitariasValoresDto> listTipoPreguntaValores =  tablasUtilitariasValoresLocal.findByTipoTabla("SEDES_HOSPITALARIAS");  
		Iterator<TablasUtilitariasValoresDto> iterTipoPreguntaValores = listTipoPreguntaValores.iterator(); 
		while(iterTipoPreguntaValores.hasNext()) {
			TablasUtilitariasValoresDto tablasUtilitariasValoresDto = iterTipoPreguntaValores.next();
			SelectItem selectItem = new SelectItem(tablasUtilitariasValoresDto.getCodigoTabla(),tablasUtilitariasValoresDto.getSignificado()); 
			this.selectSedeHospitalItems.add(selectItem); 
		}
	}
	
	public List<SelectItem> getSelectSedeHospitalItems(){
		
		return this.selectSedeHospitalItems; 
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
	
	private void environmentTipoPreguntaCoreCase() {
		this.selectTipoPreguntaCoreCaseItems = new ArrayList<SelectItem>();
		List<TablasUtilitariasValoresDto> listTipoPreguntaValores =  tablasUtilitariasValoresLocal.findByTipoTabla("TIPO_PREGUNTA");  
		Iterator<TablasUtilitariasValoresDto> iterTipoPreguntaValores = listTipoPreguntaValores.iterator(); 
		while(iterTipoPreguntaValores.hasNext()) {
			TablasUtilitariasValoresDto tablasUtilitariasValoresDto = iterTipoPreguntaValores.next();
			if(!"IMAGEN_ANOTADA".equals(tablasUtilitariasValoresDto.getCodigoTabla())
			 &&!"RESP_TEXTO_LIBRE".equals(tablasUtilitariasValoresDto.getCodigoTabla())
			  ) {
			SelectItem selectItem = new SelectItem(tablasUtilitariasValoresDto.getCodigoTabla(),tablasUtilitariasValoresDto.getSignificado()); 
			this.selectTipoPreguntaCoreCaseItems.add(selectItem); 
			}
		}
	}
	
	public List<SelectItem> getSelectTipoPreguntaCoreCaseItems(){
		return this.selectTipoPreguntaCoreCaseItems; 
	}
	
	private void environmentTemaDePregunta() {
		this.selectTemaDePreguntaItems = new ArrayList<SelectItem>();
		List<TablasUtilitariasValoresDto> listTemaDePreguntaValores =  tablasUtilitariasValoresLocal.findByTipoTabla("TEMA_DE_PREGUNTA");  
		Iterator<TablasUtilitariasValoresDto> iterTemaDePreguntaValores = listTemaDePreguntaValores.iterator(); 
		while(iterTemaDePreguntaValores.hasNext()) {
			TablasUtilitariasValoresDto tablasUtilitariasValoresDto = iterTemaDePreguntaValores.next();
			SelectItem selectItem = new SelectItem(tablasUtilitariasValoresDto.getCodigoTabla(),tablasUtilitariasValoresDto.getCodigoTabla()+" - "+tablasUtilitariasValoresDto.getSignificado()); 
			selectTemaDePreguntaItems.add(selectItem); 
		}
	}
	
	public List<SelectItem> getSelectTemaDePreguntaItems(){
		return this.selectTemaDePreguntaItems; 
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
	
	private void environmentScoringValue() {
		this.selectScoringValueItems = new ArrayList<SelectItem>();
		List<TablasUtilitariasValoresDto> listScoringValuesValores =  tablasUtilitariasValoresLocal.findByTipoTabla("SCORING_VALUE");  
		Iterator<TablasUtilitariasValoresDto> iterEstatusMrqsValores = listScoringValuesValores.iterator(); 
		while(iterEstatusMrqsValores.hasNext()) {
			TablasUtilitariasValoresDto tablasUtilitariasValoresDto = iterEstatusMrqsValores.next();
			SelectItem selectItem = new SelectItem(tablasUtilitariasValoresDto.getCodigoTabla(),tablasUtilitariasValoresDto.getSignificado()); 
			this.selectScoringValueItems.add(selectItem); 
		}
	}
	
	public List<SelectItem> getSelectScoringValueItems(){
		return this.selectScoringValueItems; 
	}
	
	private void environmentScoringMethod() {
		this.selectScoringMethodItems = new ArrayList<SelectItem>();
		List<TablasUtilitariasValoresDto> listScoringMethodValores =  tablasUtilitariasValoresLocal.findByTipoTabla("SCORING_METHOD");  
		Iterator<TablasUtilitariasValoresDto> iterScoringMethodValores = listScoringMethodValores.iterator(); 
		while(iterScoringMethodValores.hasNext()) {
			TablasUtilitariasValoresDto tablasUtilitariasValoresDto = iterScoringMethodValores.next();
			SelectItem selectItem = new SelectItem(tablasUtilitariasValoresDto.getCodigoTabla(),tablasUtilitariasValoresDto.getSignificado()); 
			this.selectScoringMethodItems.add(selectItem); 
		}
	}
	
	public List<SelectItem> getSelectScoringMethodItems(){
		return this.selectScoringMethodItems; 
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
	
	public List<SelectItem> getSelectTipoPreguntaExamenItems() {
		return selectTipoPreguntaExamenItems;
	}

	private void environmentTipoPreguntaExamen() {
		this.selectTipoPreguntaExamenItems = new ArrayList<SelectItem>();
		List<TablasUtilitariasValoresDto> listTipoPreguntaValores =  tablasUtilitariasValoresLocal.findByTipoTabla("TIPO_PREGUNTA_EXAMEN");  
		Iterator<TablasUtilitariasValoresDto> iterTipoPreguntaValores = listTipoPreguntaValores.iterator(); 
		while(iterTipoPreguntaValores.hasNext()) {
			TablasUtilitariasValoresDto tablasUtilitariasValoresDto = iterTipoPreguntaValores.next();
			SelectItem selectItem = new SelectItem(tablasUtilitariasValoresDto.getCodigoTabla(),tablasUtilitariasValoresDto.getSignificado()); 
			this.selectTipoPreguntaExamenItems.add(selectItem); 
		}
	}

}
