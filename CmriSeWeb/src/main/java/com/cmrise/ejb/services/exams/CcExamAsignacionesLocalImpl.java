package com.cmrise.ejb.services.exams;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.cmrise.ejb.model.corecases.CcHdrV1;
import com.cmrise.ejb.model.corecases.CcPreguntasFtaV1;
import com.cmrise.ejb.model.corecases.CcPreguntasHdrV1;
import com.cmrise.ejb.model.exams.CcExamAsignaciones;
import com.cmrise.jpa.dao.corecases.CcHdrDao;
import com.cmrise.jpa.dao.corecases.CcPreguntasFtaDao;
import com.cmrise.jpa.dao.corecases.CcPreguntasHdrDao;
import com.cmrise.jpa.dao.exams.CcExamAsignacionesDao;
import com.cmrise.jpa.dto.corecases.CcHdrV1Dto;
import com.cmrise.jpa.dto.corecases.CcPreguntasFtaV1Dto;
import com.cmrise.jpa.dto.corecases.CcPreguntasHdrV1Dto;
import com.cmrise.jpa.dto.exams.CcExamAsignacionesDto;

@Stateless
public class CcExamAsignacionesLocalImpl implements CcExamAsignacionesLocal {

	@Inject 
	CcExamAsignacionesDao ccExamAsignacionesDao; 
	
	@Inject 
	CcHdrDao ccHdrDao; 
	
	@Inject 
	CcPreguntasHdrDao  ccPreguntasHdrDao;
	
	@Inject 
	CcPreguntasFtaDao  ccPreguntasFtaDao;
	
	@Override
	public long insert(CcExamAsignacionesDto pCcExamAsignacionesDto) {
		return ccExamAsignacionesDao.insert(pCcExamAsignacionesDto);
	}

	@Override
	public List<CcExamAsignaciones> findByNumeroExamenWD(long pNumeroExamen) {
		List<CcExamAsignaciones> retval = new ArrayList<CcExamAsignaciones>(); 
		List<Object> listObjects = ccExamAsignacionesDao.findByNumeroExamenWD(pNumeroExamen); 
		for(Object object:listObjects) {
			if(object instanceof Object[]) {
				CcExamAsignaciones ccExamAsignaciones = new CcExamAsignaciones(); 
				Object[] row = (Object[]) object;
				if(row[0] instanceof BigInteger) {
					ccExamAsignaciones.setId(((BigInteger)row[0]).longValue());
				}
				if(row[1] instanceof Object) {
									
				}
				if(row[2] instanceof Object) {
					
				}
				if(row[3] instanceof Object) {  /** NUMERO_CORE_CASE **/
					ccExamAsignaciones.setNumeroCoreCase(((BigInteger)row[3]).longValue());					
				}
				if(row[4] instanceof Object) {
					
				}
				if(row[5] instanceof Object) {
					
				}
				if(row[6] instanceof Object) {
					
				}
				if(row[7] instanceof Object) {
					
				}
				if(row[8] instanceof Object) {
					
				}
				if(row[9] instanceof Object) {
					
				}
				if(row[10] instanceof BigInteger) { /** NUMERO_PREGUNTA **/
					ccExamAsignaciones.setNumeroPreguntaHdr(((BigInteger)row[10]).longValue());
				}
				if(row[11] instanceof String) { /** TITULO_PREGUNTA **/
					ccExamAsignaciones.setTituloPregunta((String)row[11]);
				}
				if(row[12] instanceof String) { /** ESTATUS **/
					ccExamAsignaciones.setEstatusPregunta((String)row[12]);
				}
				if(row[13] instanceof String) { /** ESTATUS_DESC **/
					ccExamAsignaciones.setEstatusPreguntaDesc((String)row[13]);
				}
				if(row[14] instanceof Object) { /** TEMA_PREGUNTA **/
					ccExamAsignaciones.setTemaPregunta((String)row[14]);
				}
				if(row[15] instanceof Object) { /** TEMA_PREGUNTA_DESC **/
					ccExamAsignaciones.setTemaPreguntaDesc((String)row[15]);
				}
				if(row[16] instanceof BigDecimal) { /** MAX_PUNTUACION **/
					ccExamAsignaciones.setMaxPuntuacionPregunta((BigDecimal)row[16]);
				}
				if(row[17] instanceof Object) { /** ETIQUETAS **/
					ccExamAsignaciones.setEtiquetas((String)row[17]);
				}
				if(row[18] instanceof String) { /** TIPO_PREGUNTA **/
					ccExamAsignaciones.setTipoPregunta((String)row[18]);					
				}
				if(row[19] instanceof String) { /** TIPO_PREGUNTA_DESC **/
					ccExamAsignaciones.setTipoPreguntaDesc((String)row[19]);
				}
				retval.add(ccExamAsignaciones); 
			} /** END if(object instanceof Object[]) { **/
		} /** END for(Object object:listObjects) { **/
		return retval; 
	}

	@Override
	public List<CcExamAsignaciones> findByNumeroExamenObjMod(long pNumeroExamen) {
		List<CcExamAsignaciones> retval = new ArrayList<CcExamAsignaciones>(); 
		List<CcExamAsignacionesDto> listCcExamAsignacionesDto  = ccExamAsignacionesDao.findByNumeroExamenDtos(pNumeroExamen); 
		if(null!=listCcExamAsignacionesDto) {
			for(CcExamAsignacionesDto i:listCcExamAsignacionesDto) {
				CcExamAsignaciones ccExamAsignaciones = new CcExamAsignaciones(); 
				ccExamAsignaciones.setNumero(i.getNumero());
				ccExamAsignaciones.setNumeroCcExamen(i.getNumeroCcExamen());
				ccExamAsignaciones.setNumeroCoreCase(i.getNumeroCoreCase());
				ccExamAsignaciones.setFechaEfectivaDesde(new java.util.Date(i.getFechaEfectivaDesde().getTime()));
				ccExamAsignaciones.setFechaEfectivaHasta(new java.util.Date(i.getFechaEfectivaHasta().getTime()));
				
				CcHdrV1 ccHdrV1 = new CcHdrV1(); 
				CcHdrV1Dto ccHdrV1Dto = ccHdrDao.findByNumero(i.getNumeroCoreCase()); 
				
				ccHdrV1.setNumero(ccHdrV1Dto.getNumero());
			//	ccHdrV1.setNombre(ccHdrV1Dto.getNombre());
			//	ccHdrV1.setTema(ccHdrV1Dto.getTema());
			//	ccHdrV1.setTemaDesc(ccHdrV1Dto.getTemaDesc());
				ccHdrV1.setHistorialClinico(ccHdrV1Dto.getHistorialClinico());
				ccHdrV1.setDescripcionTecnica(ccHdrV1Dto.getDescripcionTecnica());
				ccHdrV1.setOpcionInsegura(ccHdrV1Dto.getOpcionInsegura());
				ccHdrV1.setEtiquetas(ccHdrV1Dto.getEtiquetas());
				ccHdrV1.setNota(ccHdrV1Dto.getNota());
				ccHdrV1.setEstatus(ccHdrV1Dto.getEstatus());
				ccHdrV1.setEstatusDesc(ccHdrV1Dto.getEstatusDesc());
			//	ccHdrV1.setSociedad(ccHdrV1Dto.getSociedad());
				ccHdrV1.setFechaEfectivaDesde(new java.util.Date(ccHdrV1Dto.getFechaEfectivaDesde().getTime()));
				ccHdrV1.setFechaEfectivaHasta(new java.util.Date(ccHdrV1Dto.getFechaEfectivaHasta().getTime()));
				
				List<CcPreguntasHdrV1Dto> listCcPreguntasHdrV1Dto =  ccPreguntasHdrDao.findListByNumeroCcHdr(i.getNumeroCoreCase()); 
				if(null!=listCcPreguntasHdrV1Dto) {
					List<CcPreguntasHdrV1> listCcPreguntasHdrV1 = new ArrayList<CcPreguntasHdrV1>();
				 	for(CcPreguntasHdrV1Dto j :listCcPreguntasHdrV1Dto) {
				 		CcPreguntasHdrV1 ccPreguntasHdrV1 = new CcPreguntasHdrV1();
				 		ccPreguntasHdrV1.setNumero(j.getNumero());
				     	ccPreguntasHdrV1.setNumeroCcHdr(j.getNumeroCcHdr());
				     	//ccPreguntasHdrV1.setTitulo(j.getTitulo());
				     	ccPreguntasHdrV1.setTipoPregunta(j.getTipoPregunta());
				     	ccPreguntasHdrV1.setTipoPreguntaDesc(j.getTipoPreguntaDesc());
				     	//ccPreguntasHdrV1.setTemaPregunta(j.getTemaPregunta());
				     	//ccPreguntasHdrV1.setTemaPreguntaDesc(j.getTemaPreguntaDesc());
				     	ccPreguntasHdrV1.setEstatus(j.getEstatus());
				     	ccPreguntasHdrV1.setEstatusDesc(j.getEstatusDesc());
				     	ccPreguntasHdrV1.setMaxPuntuacion(j.getMaxPuntuacion());
				     	ccPreguntasHdrV1.setEtiquetas(j.getEtiquetas());
				     	
				     	CcPreguntasFtaV1Dto ccPreguntasFtaV1Dto = ccPreguntasFtaDao.findDtoByNumeroHdr(j.getNumero()); 
				     	CcPreguntasFtaV1 ccPreguntasFtaV1 = new CcPreguntasFtaV1(); 
				     	ccPreguntasFtaV1.setNumero(ccPreguntasFtaV1Dto.getNumero());
				     	ccPreguntasFtaV1.setNumeroHdr(ccPreguntasFtaV1Dto.getNumeroHdr());
				     	ccPreguntasFtaV1.setTituloPregunta(ccPreguntasFtaV1Dto.getTituloPregunta());
				     	ccPreguntasFtaV1.setTextoPregunta(ccPreguntasFtaV1Dto.getTextoPregunta());
				     	ccPreguntasFtaV1.setTextoSugerencias(ccPreguntasFtaV1Dto.getTextoSugerencias());
				     	ccPreguntasHdrV1.setCcPreguntasFtaV1(ccPreguntasFtaV1);
				     	
				     	listCcPreguntasHdrV1.add(ccPreguntasHdrV1);
				 	}
				 	ccHdrV1.setListCcPreguntasHdrV1(listCcPreguntasHdrV1);
				}	
				
				ccExamAsignaciones.setCcHdrV1(ccHdrV1);
				if(null!=ccExamAsignaciones.getCcHdrV1().getListCcPreguntasHdrV1()) {
					if(ccExamAsignaciones.getCcHdrV1().getListCcPreguntasHdrV1().size()>0) { /** Valida Casos con preguntas a los candidatos **/
						retval.add(ccExamAsignaciones); 
					}
				}
				
			}
		}
		return retval;
	}


}
