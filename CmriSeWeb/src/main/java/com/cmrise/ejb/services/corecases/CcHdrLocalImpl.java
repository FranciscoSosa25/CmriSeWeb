package com.cmrise.ejb.services.corecases;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.cmrise.ejb.model.corecases.CcHdrForAction;
import com.cmrise.ejb.model.corecases.CcHdrV1;
import com.cmrise.ejb.model.corecases.CcOpcionMultiple;
import com.cmrise.ejb.model.corecases.CcPreguntasFtaV1;
import com.cmrise.ejb.model.corecases.CcPreguntasHdrV1;
import com.cmrise.jpa.dao.corecases.CcHdrDao;
import com.cmrise.jpa.dao.corecases.CcOpcionMultipleDao;
import com.cmrise.jpa.dao.corecases.CcPreguntasFtaDao;
import com.cmrise.jpa.dao.corecases.CcPreguntasHdrDao;
import com.cmrise.jpa.dto.admin.KeysDto;
import com.cmrise.jpa.dto.corecases.CcHdrDto;
import com.cmrise.jpa.dto.corecases.CcHdrV1Dto;
import com.cmrise.jpa.dto.corecases.CcOpcionMultipleDto;
import com.cmrise.jpa.dto.corecases.CcPreguntasFtaV1Dto;
import com.cmrise.jpa.dto.corecases.CcPreguntasHdrV1Dto;
import com.cmrise.jpa.dto.mrqs.MrqsPreguntasHdrV1Dto;

@Stateless 
public class CcHdrLocalImpl implements CcHdrLocal {

	@Inject 
	CcHdrDao ccHdrDao; 
	
	@Inject 
	CcPreguntasHdrDao  ccPreguntasHdrDao;
	
	@Inject 
	CcPreguntasFtaDao  ccPreguntasFtaDao;
	
	@Inject 
	CcOpcionMultipleDao ccOpcionMultipleDao; 
	
	@Override
	public void insert(CcHdrDto pCcHdrDto) {
		ccHdrDao.insert(pCcHdrDto);
	}

	@Override
	public void delete(long pNumero) {
		ccHdrDao.delete(pNumero);
	}

	@Override
	public void update(long pNumero, CcHdrDto pCcHdrDto) {
		ccHdrDao.update(pNumero
				      , pCcHdrDto);
	}

	@Override
	public List<CcHdrV1Dto> findAll() {
		return ccHdrDao.findAll();
	}

	@Override
	public CcHdrV1Dto findByNumero(long pNumero) {
		return ccHdrDao.findByNumero(pNumero);
	}

	@Override
	public List<KeysDto> findKeys() {
		return ccHdrDao.findKeys();
	}

	@Override
	public List<CcHdrForAction> findCoreCasesForExam(long pNumeroExamen) {
		List<CcHdrForAction> retval = new ArrayList<CcHdrForAction>();
		List<Object> listObjects = ccHdrDao.findCoreCasesForExam(pNumeroExamen); 
		for(Object object:listObjects) {
			if(object instanceof Object[]) {
				CcHdrForAction ccHdrForAction = new CcHdrForAction(); 
				Object[] row = (Object[]) object;
				if(row[0] instanceof BigInteger) { /** [NUMERO] **/
					ccHdrForAction.setNumero(((BigInteger)row[0]).longValue());
				}
				if(row[1] instanceof String) { /** [NOMBRE] **/
					ccHdrForAction.setNombre((String)row[1]);
				}
				if(row[2] instanceof String) { /** [ESTATUS] **/
					ccHdrForAction.setEstatus((String)row[2]);
				}
				if(row[3] instanceof String) { /** [ESTATUS_DESC] **/
					ccHdrForAction.setEstatusDesc((String)row[3]);
				}
				if(row[4] instanceof String) { /** [TEMA] **/
					ccHdrForAction.setTema((String)row[4]);
				}
				if(row[5] instanceof String) { /** [TEMA_DESC] **/
					ccHdrForAction.setTemaDesc((String)row[5]);
				}
				retval.add(ccHdrForAction); 
			}
		}	
		return retval;
	}

	@Override
	public CcHdrV1 findByNumeroObjMod(long pNumeroCcHdr) {
		CcHdrV1 retval = new CcHdrV1(); 
		CcHdrV1Dto ccHdrV1Dto = ccHdrDao.findByNumero(pNumeroCcHdr); 
		retval.setNumero(ccHdrV1Dto.getNumero());
		retval.setNombre(ccHdrV1Dto.getNombre());
		retval.setTema(ccHdrV1Dto.getTema());
		retval.setTemaDesc(ccHdrV1Dto.getTemaDesc());
		retval.setHistorialClinico(ccHdrV1Dto.getHistorialClinico());
		retval.setDescripcionTecnica(ccHdrV1Dto.getDescripcionTecnica());
		retval.setOpcionInsegura(ccHdrV1Dto.getOpcionInsegura());
		retval.setEtiquetas(ccHdrV1Dto.getEtiquetas());
		retval.setNota(ccHdrV1Dto.getNota());
		retval.setEstatus(ccHdrV1Dto.getEstatus());
		retval.setEstatusDesc(ccHdrV1Dto.getEstatusDesc());
		retval.setSociedad(ccHdrV1Dto.getSociedad());
		retval.setFechaEfectivaDesde(new java.util.Date(ccHdrV1Dto.getFechaEfectivaDesde().getTime()));
		retval.setFechaEfectivaHasta(new java.util.Date(ccHdrV1Dto.getFechaEfectivaHasta().getTime()));
		
		List<CcPreguntasHdrV1Dto> listCcPreguntasHdrV1Dto =  ccPreguntasHdrDao.findListByNumeroCcHdr(pNumeroCcHdr); 
		
		List<CcPreguntasHdrV1> listCcPreguntasHdrV1 = new ArrayList<CcPreguntasHdrV1>();
	 	for(CcPreguntasHdrV1Dto i :listCcPreguntasHdrV1Dto) {
	 		CcPreguntasHdrV1 ccPreguntasHdrV1 = new CcPreguntasHdrV1();
	 		ccPreguntasHdrV1.setNumero(i.getNumero());
	     	ccPreguntasHdrV1.setNumeroCcHdr(i.getNumeroCcHdr());
	     	ccPreguntasHdrV1.setTitulo(i.getTitulo());
	     	ccPreguntasHdrV1.setTipoPregunta(i.getTipoPregunta());
	     	ccPreguntasHdrV1.setTipoPreguntaDesc(i.getTipoPreguntaDesc());
	     	ccPreguntasHdrV1.setTemaPregunta(i.getTemaPregunta());
	     	ccPreguntasHdrV1.setTemaPreguntaDesc(i.getTemaPreguntaDesc());
	     	ccPreguntasHdrV1.setEstatus(i.getEstatus());
	     	ccPreguntasHdrV1.setEstatusDesc(i.getEstatusDesc());
	     	ccPreguntasHdrV1.setMaxPuntuacion(i.getMaxPuntuacion());
	     	ccPreguntasHdrV1.setEtiquetas(i.getEtiquetas());
	     	
	     	CcPreguntasFtaV1 ccPreguntasFtaV1 = new CcPreguntasFtaV1(); 
	     	CcPreguntasFtaV1Dto ccPreguntasFtaV1Dto =  ccPreguntasFtaDao.findDtoByNumeroHdr(i.getNumero()); 
	     	ccPreguntasFtaV1.setNumero(ccPreguntasFtaV1Dto.getNumero());
	     	ccPreguntasFtaV1.setNumeroHdr(ccPreguntasFtaV1Dto.getNumeroHdr());
	     	ccPreguntasFtaV1.setTituloPregunta(ccPreguntasFtaV1Dto.getTituloPregunta()); 
	     	ccPreguntasFtaV1.setTextoPregunta(ccPreguntasFtaV1Dto.getTextoPregunta());
	     	ccPreguntasFtaV1.setTextoSugerencias(ccPreguntasFtaV1Dto.getTextoSugerencias());
	     	ccPreguntasFtaV1.setRespuestaCorrecta(ccPreguntasFtaV1Dto.getRespuestaCorrecta());
	     	ccPreguntasFtaV1.setSingleAnswerMode(ccPreguntasFtaV1Dto.isSingleAnswerMode());
	     	ccPreguntasFtaV1.setSuffleAnswerOrder(ccPreguntasFtaV1Dto.isSuffleAnswerOrder());
	     	
	     	 
	     	List<CcOpcionMultipleDto> listCcOpcionMultipleDto =  ccOpcionMultipleDao.findByNumeroFta(ccPreguntasFtaV1Dto.getNumero());
			if(null!=listCcOpcionMultipleDto) {
				List<CcOpcionMultiple> listCcOpcionMultiple = new ArrayList<CcOpcionMultiple>(); 
				 for(CcOpcionMultipleDto j:listCcOpcionMultipleDto) {
					 CcOpcionMultiple ccOpcionMultiple = new CcOpcionMultiple(); 
		        	 ccOpcionMultiple.setNumeroLinea(j.getNumeroLinea());
		        	 ccOpcionMultiple.setEstatus(j.isEstatus());
		        	 ccOpcionMultiple.setNumero(j.getNumero());
		        	 ccOpcionMultiple.setNumeroFta(j.getNumeroFta());
		        	 ccOpcionMultiple.setTextoExplicacion(j.getTextoExplicacion());
		        	 ccOpcionMultiple.setTextoRespuesta(j.getTextoRespuesta());
		        	 listCcOpcionMultiple.add(ccOpcionMultiple); 
		         }
				 ccPreguntasFtaV1.setListCcOpcionMultiple(listCcOpcionMultiple);
			}
	     	
	     	ccPreguntasHdrV1.setCcPreguntasFtaV1(ccPreguntasFtaV1);
	     	
	     	listCcPreguntasHdrV1.add(ccPreguntasHdrV1);
	 	}
	 	
	 	retval.setListCcPreguntasHdrV1(listCcPreguntasHdrV1);
	 	
		
		return retval;
	}

}
