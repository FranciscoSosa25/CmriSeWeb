package com.cmrise.ejb.backing.corecases;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.model.file.UploadedFile;
import org.primefaces.model.file.UploadedFiles;

import com.cmrise.ejb.helpers.GuestPreferences;
import com.cmrise.ejb.helpers.UserLogin;
import com.cmrise.ejb.model.admin.AdmonExamenHdr;
import com.cmrise.ejb.model.admin.AdmonMateriaHdr;
import com.cmrise.ejb.model.admin.AdmonSubMateria;
import com.cmrise.ejb.model.corecases.CcOpcionMultiple;
import com.cmrise.ejb.model.corecases.CcPreguntasFtaV1;
import com.cmrise.ejb.model.corecases.CcPreguntasHdrV1;
import com.cmrise.ejb.model.corecases.img.CcImagenes;
import com.cmrise.ejb.model.corecases.img.CcImagenesGrp;
import com.cmrise.ejb.services.admin.AdmonExamenHdrLocal;
import com.cmrise.ejb.services.admin.AdmonMateriaHdrLocal;
import com.cmrise.ejb.services.admin.AdmonSubMateriaLocal;
import com.cmrise.ejb.services.admin.TablasUtilitariasValoresLocal;
import com.cmrise.ejb.services.corecases.CcOpcionMultipleLocal;
import com.cmrise.ejb.services.corecases.CcPreguntasFtaLocal;
import com.cmrise.ejb.services.corecases.CcPreguntasFtaSinonimosLocal;
import com.cmrise.ejb.services.corecases.CcPreguntasHdrLocal;
import com.cmrise.ejb.services.corecases.img.CcImagenesGrpLocal;
import com.cmrise.jpa.dto.admin.TablasUtilitariasValoresDto;
import com.cmrise.jpa.dto.corecases.CcOpcionMultipleDto;
import com.cmrise.jpa.dto.corecases.CcPreguntasFtaDto;
import com.cmrise.jpa.dto.corecases.CcPreguntasFtaSinonimos;
import com.cmrise.jpa.dto.corecases.CcPreguntasFtaV1Dto;
import com.cmrise.jpa.dto.corecases.CcPreguntasHdrDto;
import com.cmrise.jpa.dto.corecases.CcPreguntasHdrV1Dto;
import com.cmrise.jpa.dto.mrqs.MrqsPreguntasFtaSinonimos;
import com.cmrise.utils.CorrelacionColumnasInsertException;
import com.cmrise.utils.Utilitarios;
import com.cmrise.utils.UtilitariosLocal;

@ManagedBean
@ViewScoped
public class UpdateQuestionFtaCoreCaseForm {
	private long numeroCcHdr;

	private List<AdmonExamenHdr> examenesHdr = new ArrayList<AdmonExamenHdr>();
	private List<AdmonMateriaHdr> materiasHdr = new ArrayList<AdmonMateriaHdr>();
	private List<AdmonSubMateria> subMaterias = new ArrayList<AdmonSubMateria>();
	private List<SelectItem> selectExamenesHdr = new ArrayList<SelectItem>();
	private List<SelectItem> selectMateriasHdr = new ArrayList<SelectItem>();
	private List<SelectItem> selectSubMaterias = new ArrayList<SelectItem>();

	private boolean multipleChoice;
	private boolean limitedFreeTextAnswer;
	private boolean indicateImage;

	private CcPreguntasHdrV1 ccPreguntasHdrV1ForAction = new CcPreguntasHdrV1();
	private CcPreguntasFtaV1 ccPreguntasFtaV1ForUpdate = new CcPreguntasFtaV1();
	private List<SelectItem> selectScoringMethodItems = new ArrayList<SelectItem>();

	private boolean ftaRecord;
	private long numeroFtaRecord;

	private List<CcPreguntasHdrV1> listCcPreguntasHdrV1 = new ArrayList<CcPreguntasHdrV1>();

	/**********************************************************************
	 * Atributos Opcion Multiple
	 **********************************************************************/
	private List<CcOpcionMultiple> listCcOpcionMultiple = new ArrayList<CcOpcionMultiple>();
	private int idxOM = 0;
	private CcOpcionMultiple ccOpcionMultipleForAction = new CcOpcionMultiple();

	/************************************************************************
	 * Archivos E Imagenes
	 */

	private UploadedFiles presentationFiles;
	private CcImagenesGrp presentCcImagenesGrp = new CcImagenesGrp();
	private CcImagenesGrp editPresentCcImagenesGrp = new CcImagenesGrp();
	private List<CcImagenesGrp> listPresentCcImagenesGrp = new ArrayList<CcImagenesGrp>();
	private long numeroFta;
	private List<CcPreguntasFtaSinonimos> ccListaSinonimos = new ArrayList<CcPreguntasFtaSinonimos>();
	@Inject
	CcPreguntasFtaSinonimosLocal ccPreguntasFtaSinonimosLocal;
	@Inject
	UtilitariosLocal utilitariosLocal;

	@Inject
	CcPreguntasHdrLocal ccPreguntasHdrLocal;

	@Inject
	CcPreguntasFtaLocal ccPreguntasFtaLocal;

	@Inject
	CcOpcionMultipleLocal ccOpcionMultipleLocal;

	@Inject
	CcImagenesGrpLocal ccImagenesGrpLocal;

	@ManagedProperty(value = "#{guestPreferences}")
	GuestPreferences guestPreferences;

	@Inject
	AdmonExamenHdrLocal admonExamenHdrLocal;

	@Inject
	AdmonMateriaHdrLocal admonMateriaHdrLocal;

	@Inject
	AdmonSubMateriaLocal admonSubMateriaLocal;

	@Inject
	TablasUtilitariasValoresLocal tablasUtilitariasValoresLocal;

	@ManagedProperty(value = "#{userLogin}")
	private UserLogin userLogin;

	@PostConstruct
	public void init() {
		System.out.println("Entra " + this.getClass() + " init()");
		refreshEntity();
		obtenerSinonimos();
		environmentScoringMethod();
		System.out.println("Sale " + this.getClass() + " init()");
	}

	public void addOpcionMultiple() {
		CcOpcionMultiple ccOpcionMultiple = new CcOpcionMultiple();
		idxOM++;
		ccOpcionMultiple.setIdxTemp(idxOM);
		listCcOpcionMultiple.add(ccOpcionMultiple);
	}

	public void selectOpcionMultipleForAction(CcOpcionMultiple pCcOpcionMultiple) {
		ccOpcionMultipleForAction.setNumero(pCcOpcionMultiple.getNumero());
		ccOpcionMultipleForAction.setIdxTemp(pCcOpcionMultiple.getIdxTemp());
	}

	public void deleteOpcionMultiple() {

		if (0 != ccOpcionMultipleForAction.getNumero()) {
			ccOpcionMultipleLocal.delete(ccOpcionMultipleForAction.getNumero());
			for (CcOpcionMultiple i : listCcOpcionMultiple) {
				if (i.getNumero() == ccOpcionMultipleForAction.getNumero()) {
					listCcOpcionMultiple.remove(i);
				}
			}
		} else if (0 != ccOpcionMultipleForAction.getIdxTemp()) {
			for (CcOpcionMultiple i : listCcOpcionMultiple) {
				if (i.getIdxTemp() == ccOpcionMultipleForAction.getIdxTemp()) {
					listCcOpcionMultiple.remove(i);
				}
			}
		}

	}

	public void environmentScoringMethod() {
		this.selectScoringMethodItems = new ArrayList<SelectItem>();
		List<TablasUtilitariasValoresDto> listScoringMethodValores = tablasUtilitariasValoresLocal
				.findByTipoTabla("SCORING_METHOD");
		Iterator<TablasUtilitariasValoresDto> iterScoringMethodValores = listScoringMethodValores.iterator();
		if ("OPCION_MULTIPLE".equals(ccPreguntasHdrV1ForAction.getTipoPregunta())) {
			while (iterScoringMethodValores.hasNext()) {
				TablasUtilitariasValoresDto tablasUtilitariasValoresDto = iterScoringMethodValores.next();
				if ("PROP_SCORING".equals(tablasUtilitariasValoresDto.getCodigoTabla())) {
					SelectItem selectItem = new SelectItem(tablasUtilitariasValoresDto.getCodigoTabla(),
							tablasUtilitariasValoresDto.getSignificado());
					this.selectScoringMethodItems.add(selectItem);
					ccPreguntasFtaV1ForUpdate.setMetodoPuntuacion(tablasUtilitariasValoresDto.getCodigoTabla());
				}
			}
		}

		if (Utilitarios.RESP_TEXTO_LIBRE.equals(ccPreguntasHdrV1ForAction.getTipoPregunta())) {
			while (iterScoringMethodValores.hasNext()) {
				TablasUtilitariasValoresDto tablasUtilitariasValoresDto = iterScoringMethodValores.next();
				if ("WRONG_CORRECT".equals(tablasUtilitariasValoresDto.getCodigoTabla())) {
					SelectItem selectItem = new SelectItem(tablasUtilitariasValoresDto.getCodigoTabla(),
							tablasUtilitariasValoresDto.getSignificado());
					this.selectScoringMethodItems.add(selectItem);
				}
			}
		}

		if (Utilitarios.IMAGEN_INDICADA.equals(ccPreguntasHdrV1ForAction.getTipoPregunta())) {
			while (iterScoringMethodValores.hasNext()) {
				TablasUtilitariasValoresDto tablasUtilitariasValoresDto = iterScoringMethodValores.next();
				if ("WRONG_CORRECT".equals(tablasUtilitariasValoresDto.getCodigoTabla())) {
					SelectItem selectItem = new SelectItem(tablasUtilitariasValoresDto.getCodigoTabla(),
							tablasUtilitariasValoresDto.getSignificado());
					this.selectScoringMethodItems.add(selectItem);
				}
			}
		}

		if (Utilitarios.IMAGEN_ANOTADA.equals(ccPreguntasHdrV1ForAction.getTipoPregunta())) {
			while (iterScoringMethodValores.hasNext()) {
				TablasUtilitariasValoresDto tablasUtilitariasValoresDto = iterScoringMethodValores.next();
				if ("WRONG_CORRECT".equals(tablasUtilitariasValoresDto.getCodigoTabla())
						|| "PROP_SCORING".equals(tablasUtilitariasValoresDto.getCodigoTabla())) {
					SelectItem selectItem = new SelectItem(tablasUtilitariasValoresDto.getCodigoTabla(),
							tablasUtilitariasValoresDto.getSignificado());
					this.selectScoringMethodItems.add(selectItem);
				}
			}
		}

	}

	public List<SelectItem> getSelectScoringMethodItems() {
		return this.selectScoringMethodItems;
	}

	private void refreshEntity() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		Object obNumeroCcHdr = session.getAttribute("NumeroCcHdrSV");
		Object obNumeroCcPreguntaHdrSV = session.getAttribute("NumeroCcPreguntaHdrSV");
		long longNumeroCcHdr = utilitariosLocal.objToLong(obNumeroCcHdr);
		long longNumeroCcPreguntaHdr = utilitariosLocal.objToLong(obNumeroCcPreguntaHdrSV);
		this.setNumeroCcHdr(longNumeroCcHdr);
		if (0 == longNumeroCcHdr || 0 == longNumeroCcPreguntaHdr) {
			return;
		}
		ccPreguntasHdrV1ForAction = ccPreguntasHdrLocal.findByNumeroObjMod(longNumeroCcPreguntaHdr);

		examenesHdr = admonExamenHdrLocal.findByTipo(Utilitarios.CORE_CASES);
		selectExamenesHdr = new ArrayList<SelectItem>();
		for (AdmonExamenHdr i : examenesHdr) {
			SelectItem selectItem = new SelectItem(i.getNumero(), i.getNombre());
			selectExamenesHdr.add(selectItem);
		}
		onAdmonExamenChange();
		onAdmonMateriaChange();

		// this.setTituloPreguntaHdr(ccPreguntasHdrV1ForAction.getTitulo());
		if (Utilitarios.LIMIT_RESP_TEXTO_LIBRE.equals(ccPreguntasHdrV1ForAction.getTipoPregunta())) {
			this.setLimitedFreeTextAnswer(true);
			
		} else if (Utilitarios.OPCION_MULTIPLE.equals(ccPreguntasHdrV1ForAction.getTipoPregunta())) {
			this.setMultipleChoice(true);
			initListCcOpcionMultiple();
		}

		long lNumeroCcFta = ccPreguntasFtaLocal.finNumeroByHdr(longNumeroCcPreguntaHdr);
		setNumeroFta(lNumeroCcFta);
		System.out.println("lNumeroCcFta:" + lNumeroCcFta);
		if (0l != lNumeroCcFta) {
			this.setFtaRecord(true);
			ccPreguntasFtaV1ForUpdate = ccPreguntasFtaLocal.findObjByNumeroHdr(longNumeroCcPreguntaHdr);
			System.out.println("ccPreguntasFtaV1ForUpdate.getNumero():" + ccPreguntasFtaV1ForUpdate.getNumero());
			System.out.println(
					"ccPreguntasFtaV1ForUpdate.getTituloPregunta():" + ccPreguntasFtaV1ForUpdate.getTituloPregunta());
			this.setNumeroFtaRecord(ccPreguntasFtaV1ForUpdate.getNumero());
			listCcOpcionMultiple = ccPreguntasHdrV1ForAction.getCcPreguntasFtaV1().getListCcOpcionMultiple();
			listPresentCcImagenesGrp = ccImagenesGrpLocal.findByFta(lNumeroCcFta, Utilitarios.INTRODUCCION);

		} else {
			this.setFtaRecord(false);
		}

		List<CcPreguntasHdrV1Dto> listCcPreguntasHdrV1Dto = ccPreguntasHdrLocal.findListByNumeroCcHdr(longNumeroCcHdr);
		listCcPreguntasHdrV1 = new ArrayList<CcPreguntasHdrV1>();
		for (CcPreguntasHdrV1Dto ccPreguntasHdrV1DtoTmp : listCcPreguntasHdrV1Dto) {
			CcPreguntasHdrV1 ccPreguntasHdrV1 = new CcPreguntasHdrV1();
			ccPreguntasHdrV1.setNumero(ccPreguntasHdrV1DtoTmp.getNumero());
			ccPreguntasHdrV1.setNumeroCcHdr(ccPreguntasHdrV1DtoTmp.getNumeroCcHdr());
			// ccPreguntasHdrV1.setTitulo(ccPreguntasHdrV1DtoTmp.getTitulo());
			ccPreguntasHdrV1.setTipoPreguntaDesc(ccPreguntasHdrV1DtoTmp.getTipoPreguntaDesc());
			ccPreguntasHdrV1.setEstatusDesc(ccPreguntasHdrV1DtoTmp.getEstatusDesc());
			ccPreguntasHdrV1.setMaxPuntuacion(ccPreguntasHdrV1DtoTmp.getMaxPuntuacion());
			ccPreguntasHdrV1.setEtiquetas(ccPreguntasHdrV1DtoTmp.getEtiquetas());
			listCcPreguntasHdrV1.add(ccPreguntasHdrV1);
		}
	}

	public void update() {
		CcPreguntasHdrDto ccPreguntasHdrDto = new CcPreguntasHdrDto();
		ccPreguntasHdrDto.setNumero(ccPreguntasHdrV1ForAction.getNumero());
		ccPreguntasHdrDto.setEstatus(ccPreguntasHdrV1ForAction.getEstatus());
		ccPreguntasHdrDto.setTipoPregunta(ccPreguntasHdrV1ForAction.getTipoPregunta());
		ccPreguntasHdrDto.setMaxPuntuacion(ccPreguntasHdrV1ForAction.getMaxPuntuacion());
		ccPreguntasHdrDto.setEtiquetas(ccPreguntasHdrV1ForAction.getEtiquetas());
		ccPreguntasHdrDto.setComentarios(ccPreguntasHdrV1ForAction.getComentarios());

		if (this.isFtaRecord()) {
			CcPreguntasFtaDto ccPreguntasFtaDto = new CcPreguntasFtaDto();
			ccPreguntasFtaDto.setCcPreguntasHdr(ccPreguntasHdrDto);
			ccPreguntasFtaDto.setRespuestaCorrecta(ccPreguntasFtaV1ForUpdate.getRespuestaCorrecta());
			ccPreguntasFtaDto.setTituloPregunta(ccPreguntasFtaV1ForUpdate.getTituloPregunta());
			ccPreguntasFtaDto.setTextoPregunta(ccPreguntasFtaV1ForUpdate.getTextoPregunta());
			ccPreguntasFtaDto.setTextoSugerencias(ccPreguntasFtaV1ForUpdate.getTextoSugerencias());
			ccPreguntasFtaDto.setSingleAnswerMode(ccPreguntasFtaV1ForUpdate.isSingleAnswerMode());
			ccPreguntasFtaDto.setSuffleAnswerOrder(ccPreguntasFtaV1ForUpdate.isSuffleAnswerOrder());
			ccPreguntasFtaDto.setMetodoPuntuacion(ccPreguntasFtaV1ForUpdate.getMetodoPuntuacion());
			ccPreguntasFtaDto.setValorPuntuacion(ccPreguntasFtaV1ForUpdate.getValorPuntuacion());
			ccPreguntasFtaDto.setLimiteCaracteres(ccPreguntasFtaV1ForUpdate.getLimiteCaracteres()!=null?ccPreguntasFtaV1ForUpdate.getLimiteCaracteres():
				ccPreguntasFtaV1ForUpdate.getRespuestaCorrecta()!=null?ccPreguntasFtaV1ForUpdate.getRespuestaCorrecta().length():0);
			ccPreguntasFtaLocal.update(this.getNumeroFtaRecord(), ccPreguntasFtaDto);

			if (null != listCcOpcionMultiple) {
				for (CcOpcionMultiple ccOpcionMultiple : listCcOpcionMultiple) {
					CcOpcionMultipleDto ccOpcionMultipleDto = new CcOpcionMultipleDto();
					if (0 != ccOpcionMultiple.getNumero()) {
						ccOpcionMultipleDto.setNumero(ccOpcionMultiple.getNumero());
						ccOpcionMultipleDto.setEstatus(ccOpcionMultiple.isEstatus());
						ccOpcionMultipleDto.setTextoExplicacion(ccOpcionMultiple.getTextoExplicacion());
						ccOpcionMultipleDto.setTextoRespuesta(ccOpcionMultiple.getTextoRespuesta());
						ccOpcionMultipleDto.setNumeroLinea(ccOpcionMultiple.getNumeroLinea());
						ccOpcionMultipleLocal.update(ccOpcionMultiple.getNumero(), ccOpcionMultipleDto);
					} else {
						ccOpcionMultipleDto.setEstatus(ccOpcionMultiple.isEstatus());
						ccOpcionMultipleDto.setTextoRespuesta(ccOpcionMultiple.getTextoRespuesta());
						ccOpcionMultipleDto.setTextoExplicacion(ccOpcionMultiple.getTextoExplicacion());
						ccOpcionMultipleDto.setFechaEfectivaDesde(Utilitarios.startOfTime);
						ccOpcionMultipleDto.setFechaEfectivaHasta(Utilitarios.endOfTime);
						ccOpcionMultipleDto.setNumeroFta(this.getNumeroFtaRecord());
						ccOpcionMultipleDto.setNumeroLinea(ccOpcionMultiple.getNumeroLinea());
						ccOpcionMultipleLocal.insert(ccOpcionMultipleDto);
					}
				}
			}
			/** END if(null!=listCcOpcionMultiple) { **/

			

		} else {
			CcPreguntasFtaDto ccPreguntasFtaDto = new CcPreguntasFtaDto();
			ccPreguntasFtaDto.setCcPreguntasHdr(ccPreguntasHdrDto);
			ccPreguntasFtaDto.setTituloPregunta(ccPreguntasFtaV1ForUpdate.getTituloPregunta());
			ccPreguntasFtaDto.setTextoPregunta(ccPreguntasFtaV1ForUpdate.getTextoPregunta());
			ccPreguntasFtaDto.setTextoSugerencias(ccPreguntasFtaV1ForUpdate.getTextoSugerencias());
			// ccPreguntasFtaDto.setRespuestaCorrecta(this.getRespuestaCorrecta());
			ccPreguntasFtaDto.setFechaEfectivaDesde(Utilitarios.startOfTime);
			ccPreguntasFtaDto.setFechaEfectivaHasta(Utilitarios.endOfTime);
			ccPreguntasFtaDto.setSingleAnswerMode(ccPreguntasFtaV1ForUpdate.isSingleAnswerMode());
			ccPreguntasFtaDto.setSuffleAnswerOrder(ccPreguntasFtaV1ForUpdate.isSuffleAnswerOrder());
			ccPreguntasFtaDto.setRespuestaCorrecta(ccPreguntasFtaV1ForUpdate.getRespuestaCorrecta());
			ccPreguntasFtaDto.setMetodoPuntuacion(ccPreguntasFtaV1ForUpdate.getMetodoPuntuacion());
			ccPreguntasFtaDto.setValorPuntuacion(ccPreguntasFtaV1ForUpdate.getValorPuntuacion());
			ccPreguntasFtaDto.setLimiteCaracteres(ccPreguntasFtaV1ForUpdate.getLimiteCaracteres()!=null?ccPreguntasFtaV1ForUpdate.getLimiteCaracteres():
				ccPreguntasFtaV1ForUpdate.getRespuestaCorrecta()!=null?ccPreguntasFtaV1ForUpdate.getRespuestaCorrecta().length():0);
			long numeroPreguntaFta = ccPreguntasFtaLocal.insert(ccPreguntasFtaDto);

			if (Utilitarios.OPCION_MULTIPLE.equals(ccPreguntasHdrDto.getTipoPregunta())) {
				for (CcOpcionMultiple ccOpcionMultiple : listCcOpcionMultiple) {
					updateOrInsertOpcionMultiple(ccOpcionMultiple);
					CcOpcionMultipleDto ccOpcionMultipleDto = new CcOpcionMultipleDto();
					ccOpcionMultipleDto.setEstatus(ccOpcionMultiple.isEstatus());
					ccOpcionMultipleDto.setTextoRespuesta(ccOpcionMultiple.getTextoRespuesta());
					ccOpcionMultipleDto.setTextoExplicacion(ccOpcionMultiple.getTextoExplicacion());
					ccOpcionMultipleDto.setFechaEfectivaDesde(Utilitarios.startOfTime);
					ccOpcionMultipleDto.setFechaEfectivaHasta(Utilitarios.endOfTime);
					ccOpcionMultipleDto.setNumeroFta(numeroPreguntaFta);
					ccOpcionMultipleLocal.insert(ccOpcionMultipleDto);
				}
			}

		}

		ccPreguntasHdrLocal.update(ccPreguntasHdrV1ForAction.getNumero(), ccPreguntasHdrDto);
		refreshEntity();
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Se actualizaron los datos correctamente", "Actualizacion correcta"));

	}

	private void updateImagenesGrp(long pNumeroFta, CcImagenesGrp pCcImagenesGrp) {
		if (0 != pCcImagenesGrp.getNumero()) {

		} else {
			insertaImagenesGrp(pNumeroFta, pCcImagenesGrp);
		}
	}

	private void insertaImagenesGrp(long pNumeroFta, CcImagenesGrp pCcImagenesGrp) {
		ccImagenesGrpLocal.insert(pNumeroFta, pCcImagenesGrp);
	}

	public String updatePregunta(CcPreguntasHdrV1 pCcPreguntasHdrV1) {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		long lNumeroCcHdr = pCcPreguntasHdrV1.getNumeroCcHdr();
		long lNumeroPreguntaHdr = pCcPreguntasHdrV1.getNumero();
		session.setAttribute("NumeroCcHdrSV", lNumeroCcHdr);
		session.setAttribute("NumeroCcPreguntaHdrSV", lNumeroPreguntaHdr);
		return "Actualizar-Pregunta-Fta-CoreCase";
	}

	private void updateOrInsertOpcionMultiple(CcOpcionMultiple pCcOpcionMultiple) {
		System.out.println("Aun sin implementacion");
	}

	public String nuevaPregunta() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		session.setAttribute("NumeroCcHdrSV", this.getNumeroCcHdr());
		return "Crear-Pregunta-CoreCase";
	}

	private void initListCcOpcionMultiple() {
		listCcOpcionMultiple = new ArrayList<CcOpcionMultiple>();
		int lineNumber = 1;
		CcOpcionMultiple ccOpcionMultiple = new CcOpcionMultiple();
		ccOpcionMultiple.setNumeroLinea(lineNumber);
		listCcOpcionMultiple.add(ccOpcionMultiple);
		lineNumber = lineNumber + 1;
		ccOpcionMultiple = new CcOpcionMultiple();
		ccOpcionMultiple.setNumeroLinea(lineNumber);
		listCcOpcionMultiple.add(ccOpcionMultiple);
		lineNumber = lineNumber + 1;
		ccOpcionMultiple = new CcOpcionMultiple();
		ccOpcionMultiple.setNumeroLinea(lineNumber);
		listCcOpcionMultiple.add(ccOpcionMultiple);
		lineNumber = lineNumber + 1;
		ccOpcionMultiple = new CcOpcionMultiple();
		ccOpcionMultiple.setNumeroLinea(lineNumber);
		listCcOpcionMultiple.add(ccOpcionMultiple);
		lineNumber = lineNumber + 1;
	}

	public String saveAndPreview() {
		ccPreguntasFtaV1ForUpdate.getValorPuntuacion();
		update();
		validarTextoLibre(ccPreguntasFtaV1ForUpdate.getNumero());
		getGuestPreferences().setTheme("deep-purple");
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		session.setAttribute("NumeroCcHdrSV", this.getNumeroCcHdr());
		session.setAttribute("NumeroCcPreguntaHdrSV", this.getCcPreguntasHdrV1ForAction().getNumero());
		System.out.println("Sale saveAndPreview()");
		return "CoreCase-Preview";
	}

	public String Preview() {

		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		session.setAttribute("NumeroCcHdrSV", this.getNumeroCcHdr());
		session.setAttribute("NumeroCcPreguntaHdrSV", this.getCcPreguntasHdrV1ForAction().getNumero());
		System.out.println("Sale saveAndPreview()");
		return "CoreCase-Preview";
	}

	public void uploadMultiple() {
		System.out.println("Entra uploadMultiple");
		if (this.presentationFiles != null) {
			if (this.presentationFiles.getSize() > 0) {

				CcImagenesGrp ccPresentaciones = new CcImagenesGrp();
				ccPresentaciones.setTituloSuperior(this.presentCcImagenesGrp.getTituloSuperior());
				ccPresentaciones.setTituloInferior(this.presentCcImagenesGrp.getTituloInferior());
				ccPresentaciones.setModality(this.presentCcImagenesGrp.getModality());
				List<CcImagenes> lListCcPresentaciones = new ArrayList<CcImagenes>();

				for (UploadedFile f : this.presentationFiles.getFiles()) {

					byte[] byteContent = f.getContent();

					System.out.println(f.getFileName());
					CcImagenes presentacionImagen = new CcImagenes();

					presentacionImagen.setNombreImagen(f.getFileName());
					presentacionImagen.setImagenContent(byteContent);
					lListCcPresentaciones.add(presentacionImagen);
				}

				ccPresentaciones.setListCcImagenes(lListCcPresentaciones);
				this.listPresentCcImagenesGrp.add(ccPresentaciones);
			}
		}
		System.out.println("Sale uploadMultiple");
		
		
		
		try {
			for (CcImagenesGrp ccImagenesGrp : listPresentCcImagenesGrp) {
				ccImagenesGrp.setTipo(Utilitarios.CORE_CASES);
				ccImagenesGrp.setSeccion(Utilitarios.INTRODUCCION);
				updateImagenesGrp(this.getNumeroFtaRecord(), ccImagenesGrp);
			}
		  }catch (Exception e) {
			// TODO: handle exception
		  }
		  listPresentCcImagenesGrp = ccImagenesGrpLocal.findByFta(this.numeroFtaRecord, Utilitarios.INTRODUCCION);
		  FacesContext context = FacesContext.getCurrentInstance();
	      context.addMessage(null, new FacesMessage("Se actualizaron los datos correctamente", "Actualizacion correcta"));
		  System.out.println("Sale Actualizar");
	}
	
	
	 public void uploadMultiple(CcImagenesGrp pCcImagenesGrp) {
		 System.out.println("Entra uploadMultiple");
		 pCcImagenesGrp = this.editPresentCcImagenesGrp;
		 pCcImagenesGrp.getListCcImagenes().clear();
			if (this.presentationFiles != null) {
				if (this.presentationFiles.getSize() > 0) {
					
					List<CcImagenes> lListCcPresentaciones = new ArrayList<CcImagenes>();
					for (UploadedFile f : this.presentationFiles.getFiles()) {
						byte[] byteContent = f.getContent();

						System.out.println(f.getFileName());
						CcImagenes presentacionImagen = new CcImagenes();

						presentacionImagen.setNombreImagen(f.getFileName());
						presentacionImagen.setImagenContent(byteContent);
						lListCcPresentaciones.add(presentacionImagen);
					}

					pCcImagenesGrp.setListCcImagenes(lListCcPresentaciones);
					
				}
			}
			
			  ccImagenesGrpLocal.update(this.numeroFtaRecord, pCcImagenesGrp);
			  refreshEntity();
			  FacesContext context = FacesContext.getCurrentInstance();
		      context.addMessage(null, new FacesMessage("Update DICOM image group successfully.", "Actualizacion correcta"));
			  System.out.println("Sale Actualizar");
			
	  
		 
	 }
	
	 public void deleteCCImageGroup(CcImagenesGrp ccImageGroup) {
		 this.selCcImagenesGrpDelete = ccImageGroup;
		 
	 }
	 
	 private CcImagenesGrp selCcImagenesGrpDelete;
	 private CcImagenes selCcImagenesToDelete;
	 
	 public void deleteImageFromGroup(CcImagenesGrp ccImageGroup, CcImagenes imagenes) {
		 this.selCcImagenesGrpDelete = ccImageGroup;
		 this.selCcImagenesToDelete = imagenes;
		 
	 }
	 
	 public void deleteCCImageGroup() {
		 FacesContext context = FacesContext.getCurrentInstance();
		 try {			
			 if(this.ccImagenesGrpLocal.deleteGroup(selCcImagenesGrpDelete)) {
				 context.addMessage(null, new FacesMessage("Deleted successfully", "Actualizacion correcta"));
				 listPresentCcImagenesGrp = ccImagenesGrpLocal.findByFta(this.numeroFtaRecord, Utilitarios.INTRODUCCION);
			 }else {
				 context.addMessage(null, new FacesMessage("Failed to delete .dcm image group.", "Actualizacion correcta"));
			 }
		 }catch (RuntimeException e) {
			
		      context.addMessage(null, new FacesMessage("Error while delete .dcm image group.", "Actualizacion correcta"));
		}
		 
	 }
	
	 public void onEditCCImageGroup(CcImagenesGrp ccImageGroup) {
			this.editPresentCcImagenesGrp = ccImageGroup;
			 
		 }
		 
	 public void deleteImageFromGroup() {
		 
		this.editPresentCcImagenesGrp.getListCcImagenes().remove(selCcImagenesToDelete);
		 ccImagenesGrpLocal.deleteGroupImage(selCcImagenesGrpDelete, selCcImagenesToDelete);
		  FacesContext context = FacesContext.getCurrentInstance();
	      context.addMessage(null, new FacesMessage("Dicom image deleted successfully.", "Actualizacion correcta"));
		  System.out.println("Sale Actualizar");
		 refreshEntity();
	 }
		 
	

	public void onAdmonExamenChange() {
		if (0 != ccPreguntasHdrV1ForAction.getAdmonExamen()) {
			materiasHdr = admonMateriaHdrLocal.findByNumeroAdmonExamen(ccPreguntasHdrV1ForAction.getAdmonExamen());
			selectMateriasHdr = new ArrayList<SelectItem>();
			for (AdmonMateriaHdr i : materiasHdr) {
				SelectItem selectItem = new SelectItem(i.getNumero(), i.getNombre());
				selectMateriasHdr.add(selectItem);
			}
		}
	}

	public void onAdmonMateriaChange() {
		if (0 != ccPreguntasHdrV1ForAction.getAdmonMateria()) {
			subMaterias = admonSubMateriaLocal.findByNumeroMateria(ccPreguntasHdrV1ForAction.getAdmonMateria());
			selectSubMaterias = new ArrayList<SelectItem>();
			for (AdmonSubMateria i : subMaterias) {
				System.out.println("*");
				SelectItem selectItem = new SelectItem(i.getNumero(), i.getNombre());
				selectSubMaterias.add(selectItem);
			}
		}
	}

	public void limpiarRespuestaCorrecta() {
		if (ccPreguntasFtaV1ForUpdate.getRespuestaCorrecta() != null
				&& ccPreguntasFtaV1ForUpdate.getLimiteCaracteres() != null)
			ccPreguntasFtaV1ForUpdate.setRespuestaCorrecta(ccPreguntasFtaV1ForUpdate.getRespuestaCorrecta()
					.length() > ccPreguntasFtaV1ForUpdate.getLimiteCaracteres()
							?ccPreguntasFtaV1ForUpdate.getRespuestaCorrecta().substring(0,
									ccPreguntasFtaV1ForUpdate.getLimiteCaracteres())
							: ccPreguntasFtaV1ForUpdate.getRespuestaCorrecta());
	}
	public void eliminarSinonimo(CcPreguntasFtaSinonimos item) {
		if(item.getNumero()!=0)
			deleteItemSinonimos(item);
		ccListaSinonimos.remove(item);	
		
	}
	public void agregarSinonimo() {
		ccListaSinonimos.add(new CcPreguntasFtaSinonimos("SINONIMO"+ccListaSinonimos.size()));
	}
	private long insertarSinonimos(List<CcPreguntasFtaSinonimos> sinonimos,long lNumeroFta) {
		try {
			return ccPreguntasFtaSinonimosLocal.insert(sinonimos, lNumeroFta,ccPreguntasFtaV1ForUpdate.getTextoPregunta()!=null?ccPreguntasFtaV1ForUpdate.getTextoPregunta():"Texto pregunta");
		} catch (Exception e) {
			limpiarMensajes();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, CorrelacionColumnasInsertException.ERROR_INSERTAR, null));
			return -1;
		}	
	}	
	private long validarTextoLibre(long lNumeroFta) {
		String d=getCcPreguntasHdrV1ForAction().getTipoPregunta();
		if(!Utilitarios.LIMIT_RESP_TEXTO_LIBRE.equals(getCcPreguntasHdrV1ForAction().getTipoPregunta()))
			return 1;		
		 return insertarSinonimos(this.ccListaSinonimos,lNumeroFta);
			 
	}
	private void obtenerSinonimos() {
		if(!Utilitarios.LIMIT_RESP_TEXTO_LIBRE.equals(getCcPreguntasHdrV1ForAction().getTipoPregunta()))
			return ;
		ccListaSinonimos = ccPreguntasFtaSinonimosLocal.findByFta( getNumeroFta());
		
		
	}
	private  void deleteItemSinonimos(CcPreguntasFtaSinonimos item) {		
		try {
			
			ccPreguntasFtaSinonimosLocal.deleteSinonimo(item);
			
		} catch (Exception e) {
			limpiarMensajes();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, CorrelacionColumnasInsertException.ERROR_INSERTAR, null));
		}
	}
	private void limpiarMensajes() {
		FacesContext context = FacesContext.getCurrentInstance();
		Iterator<FacesMessage> it = context.getMessages();
		while ( it.hasNext() ) {
		    it.next();
		    it.remove();
		}
	}
	public void borrar() {
		/**
		 * FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
		 * "Error!","Aun sin implementacion");
		 * FacesContext.getCurrentInstance().addMessage(null, msg);
		 **/
	}

	public void duplicar() {
		/**
		 * FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
		 * "Error!","Aun sin implementacion");
		 * FacesContext.getCurrentInstance().addMessage(null, msg);
		 **/
	}

	public boolean isMultipleChoice() {
		return multipleChoice;
	}

	public void setMultipleChoice(boolean multipleChoice) {
		if (multipleChoice) {
			this.setLimitedFreeTextAnswer(false);
			this.setIndicateImage(false);
		}
		this.multipleChoice = multipleChoice;
	}

	public boolean isLimitedFreeTextAnswer() {
		return limitedFreeTextAnswer;
	}

	public void setLimitedFreeTextAnswer(boolean limitedFreeTextAnswer) {
		if (limitedFreeTextAnswer) {
			this.setMultipleChoice(false);
			this.setIndicateImage(false);
		}
		this.limitedFreeTextAnswer = limitedFreeTextAnswer;
	}

	public boolean isIndicateImage() {
		return indicateImage;
	}

	public void setIndicateImage(boolean indicateImage) {
		if (indicateImage) {
			this.setMultipleChoice(false);
			this.setLimitedFreeTextAnswer(false);
		}
		this.indicateImage = indicateImage;
	}

	public CcPreguntasHdrV1 getCcPreguntasHdrV1ForAction() {
		return ccPreguntasHdrV1ForAction;
	}

	public void setCcPreguntasHdrV1ForAction(CcPreguntasHdrV1 pCcPreguntasHdrV1ForAction) {
		ccPreguntasHdrV1ForAction = pCcPreguntasHdrV1ForAction;
	}

	public boolean isFtaRecord() {
		return ftaRecord;
	}

	public void setFtaRecord(boolean ftaRecord) {
		this.ftaRecord = ftaRecord;
	}

	public long getNumeroFtaRecord() {
		return numeroFtaRecord;
	}

	public void setNumeroFtaRecord(long numeroFtaRecord) {
		this.numeroFtaRecord = numeroFtaRecord;
	}

	public List<CcPreguntasHdrV1> getListCcPreguntasHdrV1() {
		return listCcPreguntasHdrV1;
	}

	public void setListCcPreguntasHdrV1(List<CcPreguntasHdrV1> listCcPreguntasHdrV1) {
		this.listCcPreguntasHdrV1 = listCcPreguntasHdrV1;
	}

	public long getNumeroCcHdr() {
		return numeroCcHdr;
	}

	public void setNumeroCcHdr(long numeroCcHdr) {
		this.numeroCcHdr = numeroCcHdr;
	}

	public List<CcOpcionMultiple> getListCcOpcionMultiple() {
		return listCcOpcionMultiple;
	}

	public void setListCcOpcionMultiple(List<CcOpcionMultiple> listCcOpcionMultiple) {
		this.listCcOpcionMultiple = listCcOpcionMultiple;
	}

	public GuestPreferences getGuestPreferences() {
		return guestPreferences;
	}

	public void setGuestPreferences(GuestPreferences guestPreferences) {
		this.guestPreferences = guestPreferences;
	}

	public int getIdxOM() {
		return idxOM;
	}

	public void setIdxOM(int idxOM) {
		this.idxOM = idxOM;
	}

	public CcOpcionMultiple getCcOpcionMultipleForAction() {
		return ccOpcionMultipleForAction;
	}

	public void setCcOpcionMultipleForAction(CcOpcionMultiple ccOpcionMultipleForAction) {
		this.ccOpcionMultipleForAction = ccOpcionMultipleForAction;
	}

	public UploadedFiles getPresentationFiles() {
		return presentationFiles;
	}

	public void setPresentationFiles(UploadedFiles presentationFiles) {
		this.presentationFiles = presentationFiles;
	}

	public CcImagenesGrp getPresentCcImagenesGrp() {
		return presentCcImagenesGrp;
	}

	public void setPresentCcImagenesGrp(CcImagenesGrp presentCcImagenesGrp) {
		this.presentCcImagenesGrp = presentCcImagenesGrp;
	}

	public List<CcImagenesGrp> getListPresentCcImagenesGrp() {
		return listPresentCcImagenesGrp;
	}

	public void setListPresentCcImagenesGrp(List<CcImagenesGrp> listPresentCcImagenesGrp) {
		this.listPresentCcImagenesGrp = listPresentCcImagenesGrp;
	}

	public List<AdmonExamenHdr> getExamenesHdr() {
		return examenesHdr;
	}

	public void setExamenesHdr(List<AdmonExamenHdr> examenesHdr) {
		this.examenesHdr = examenesHdr;
	}

	public List<AdmonMateriaHdr> getMateriasHdr() {
		return materiasHdr;
	}

	public void setMateriasHdr(List<AdmonMateriaHdr> materiasHdr) {
		this.materiasHdr = materiasHdr;
	}

	public List<AdmonSubMateria> getSubMaterias() {
		return subMaterias;
	}

	public void setSubMaterias(List<AdmonSubMateria> subMaterias) {
		this.subMaterias = subMaterias;
	}

	public List<SelectItem> getSelectExamenesHdr() {
		return selectExamenesHdr;
	}

	public void setSelectExamenesHdr(List<SelectItem> selectExamenesHdr) {
		this.selectExamenesHdr = selectExamenesHdr;
	}

	public List<SelectItem> getSelectMateriasHdr() {
		return selectMateriasHdr;
	}

	public void setSelectMateriasHdr(List<SelectItem> selectMateriasHdr) {
		this.selectMateriasHdr = selectMateriasHdr;
	}

	public List<SelectItem> getSelectSubMaterias() {
		return selectSubMaterias;
	}

	public void setSelectSubMaterias(List<SelectItem> selectSubMaterias) {
		this.selectSubMaterias = selectSubMaterias;
	}

	public UserLogin getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(UserLogin userLogin) {
		this.userLogin = userLogin;
	}

	public CcPreguntasFtaV1 getCcPreguntasFtaV1ForUpdate() {
		return ccPreguntasFtaV1ForUpdate;
	}

	public void setCcPreguntasFtaV1ForUpdate(CcPreguntasFtaV1 ccPreguntasFtaV1ForUpdate) {
		this.ccPreguntasFtaV1ForUpdate = ccPreguntasFtaV1ForUpdate;
	}

	public List<CcPreguntasFtaSinonimos> getCcListaSinonimos() {
		return ccListaSinonimos;
	}

	public void setCcListaSinonimos(List<CcPreguntasFtaSinonimos> ccListaSinonimos) {
		this.ccListaSinonimos = ccListaSinonimos;
	}

	public long getNumeroFta() {
		return numeroFta;
	}

	public void setNumeroFta(long numeroFta) {
		this.numeroFta = numeroFta;
	}

	public CcImagenesGrp getEditPresentCcImagenesGrp() {
		return editPresentCcImagenesGrp;
	}

	public void setEditPresentCcImagenesGrp(CcImagenesGrp editPresentCcImagenesGrp) {
		this.editPresentCcImagenesGrp = editPresentCcImagenesGrp;
	}
	
	

}
