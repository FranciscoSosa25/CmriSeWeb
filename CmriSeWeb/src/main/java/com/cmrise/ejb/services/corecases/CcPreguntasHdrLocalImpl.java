package com.cmrise.ejb.services.corecases;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.cmrise.ejb.model.corecases.CcHdrV1;
import com.cmrise.ejb.model.corecases.CcOpcionMultiple;
import com.cmrise.ejb.model.corecases.CcPreguntasFtaV1;
import com.cmrise.ejb.model.corecases.CcPreguntasHdrV1;
import com.cmrise.jpa.dao.corecases.CcOpcionMultipleDao;
import com.cmrise.jpa.dao.corecases.CcPreguntasFtaDao;
import com.cmrise.jpa.dao.corecases.CcPreguntasHdrDao;
import com.cmrise.jpa.dto.corecases.CcHdrV1Dto;
import com.cmrise.jpa.dto.corecases.CcOpcionMultipleDto;
import com.cmrise.jpa.dto.corecases.CcPreguntasFtaV1Dto;
import com.cmrise.jpa.dto.corecases.CcPreguntasHdrDto;
import com.cmrise.jpa.dto.corecases.CcPreguntasHdrV1Dto;
import com.cmrise.utils.Utilitarios;

@Stateless 
public class CcPreguntasHdrLocalImpl implements CcPreguntasHdrLocal {

	@Inject 
	CcPreguntasHdrDao ccPreguntasHdrDao; 
	
	@Inject 
	CcPreguntasFtaDao ccPreguntasFtaDao; 
	
	@Inject 
	CcOpcionMultipleDao ccOpcionMultipleDao; 
	
	@Override
	public long insert(CcPreguntasHdrDto pCcPreguntasHdrDto) {
		return ccPreguntasHdrDao.insert(pCcPreguntasHdrDto);
	}

	@Override
	public void delete(long pNumero) {
		ccPreguntasHdrDao.delete(pNumero);
	}

	@Override
	public void update(long pNumero
			         , CcPreguntasHdrDto pCcPreguntasHdrDto) {
		ccPreguntasHdrDao.update(pNumero
				               , pCcPreguntasHdrDto);
	}

	@Override
	public CcPreguntasHdrV1Dto findByNumero(long pNumero) {
		return ccPreguntasHdrDao.findByNumero(pNumero);
	}

	@Override
	public List<CcPreguntasHdrV1Dto> findListByNumeroCcHdr(long pNumeroCcHdr) {
		return ccPreguntasHdrDao.findListByNumeroCcHdr(pNumeroCcHdr);
	}

	@Override
	public CcPreguntasHdrV1 findByNumeroObjMod(long pNumeroCcPreguntaHdr) {
		CcPreguntasHdrV1 retval = new CcPreguntasHdrV1(); 
		
		CcPreguntasHdrV1Dto ccPreguntasHdrV1Dto = ccPreguntasHdrDao.findByNumero(pNumeroCcPreguntaHdr);
		retval.setNumero(ccPreguntasHdrV1Dto.getNumero());
		retval.setNumeroCcHdr(ccPreguntasHdrV1Dto.getNumeroCcHdr());
		retval.setTipoPregunta(ccPreguntasHdrV1Dto.getTipoPregunta());
		retval.setTipoPreguntaDesc(ccPreguntasHdrV1Dto.getTipoPreguntaDesc());
		retval.setEstatus(ccPreguntasHdrV1Dto.getEstatus());
		retval.setEstatusDesc(ccPreguntasHdrV1Dto.getEstatusDesc());
		retval.setMaxPuntuacion(ccPreguntasHdrV1Dto.getMaxPuntuacion());
		retval.setEtiquetas(ccPreguntasHdrV1Dto.getEtiquetas());
		retval.setComentarios(ccPreguntasHdrV1Dto.getComentarios());
	    retval.setAdmonExamen(ccPreguntasHdrV1Dto.getAdmonExamen());
	    retval.setAdmonMateria(ccPreguntasHdrV1Dto.getAdmonMateria());
	    retval.setAdmonSubMateria(ccPreguntasHdrV1Dto.getAdmonSubmateria());
	    retval.setFechaElaboracion(Utilitarios.sqlDateToUtilDate(ccPreguntasHdrV1Dto.getFechaElaboracion()));
	    
		
		 long lNumeroCcFta = ccPreguntasFtaDao.finNumeroByHdr(ccPreguntasHdrV1Dto.getNumero());
		 System.out.println("lNumeroCcFta*"+lNumeroCcFta);
		 if(0!=lNumeroCcFta) {
			 CcPreguntasFtaV1  ccPreguntasFtaV1 = new  CcPreguntasFtaV1(); 
			 CcPreguntasFtaV1Dto ccPreguntasFtaV1Dto =ccPreguntasFtaDao.findDtoByNumeroHdr(ccPreguntasHdrV1Dto.getNumero());
			 ccPreguntasFtaV1.setTituloPregunta(ccPreguntasFtaV1Dto.getTituloPregunta());
			 ccPreguntasFtaV1.setTextoPregunta(ccPreguntasFtaV1Dto.getTextoPregunta());
			 ccPreguntasFtaV1.setTextoSugerencias(ccPreguntasFtaV1Dto.getTextoSugerencias());
			 ccPreguntasFtaV1.setRespuestaCorrecta(ccPreguntasFtaV1Dto.getRespuestaCorrecta());
			 ccPreguntasFtaV1.setNumero(ccPreguntasFtaV1Dto.getNumero());
			 ccPreguntasFtaV1.setSingleAnswerMode(ccPreguntasFtaV1Dto.isSingleAnswerMode());
			 ccPreguntasFtaV1.setSuffleAnswerOrder(ccPreguntasFtaV1Dto.isSuffleAnswerOrder());
			 
			 List<CcOpcionMultipleDto> listCcOpcionMultipleDto =  ccOpcionMultipleDao.findByNumeroFta(ccPreguntasFtaV1Dto.getNumero());
			 if(null!=listCcOpcionMultipleDto) {
				 List<CcOpcionMultiple> listCcOpcionMultiple = new ArrayList<CcOpcionMultiple>(); 
				 for(CcOpcionMultipleDto i:listCcOpcionMultipleDto) {
					 System.out.println("*");
					 CcOpcionMultiple ccOpcionMultiple = new CcOpcionMultiple(); 
		        	 ccOpcionMultiple.setNumeroLinea(i.getNumeroLinea());
		        	 ccOpcionMultiple.setEstatus(i.isEstatus());
		        	 ccOpcionMultiple.setNumero(i.getNumero());
		        	 ccOpcionMultiple.setNumeroFta(i.getNumeroFta());
		        	 ccOpcionMultiple.setTextoExplicacion(i.getTextoExplicacion());
		        	 ccOpcionMultiple.setTextoRespuesta(i.getTextoRespuesta());
		        	 listCcOpcionMultiple.add(ccOpcionMultiple); 
				 }
				 ccPreguntasFtaV1.setListCcOpcionMultiple(listCcOpcionMultiple);	 
			 }
			 
			 retval.setCcPreguntasFtaV1(ccPreguntasFtaV1);
			 
		 }
		
		return retval;
	}

	@Override
	public long insert(CcPreguntasHdrV1 pCcPreguntasHdrV1) {
		CcPreguntasHdrDto ccPreguntasHdrDto = new CcPreguntasHdrDto();
		ccPreguntasHdrDto.setEstatus(pCcPreguntasHdrV1.getEstatus());
		ccPreguntasHdrDto.setNumeroCcHdr(pCcPreguntasHdrV1.getNumeroCcHdr());
		ccPreguntasHdrDto.setAdmonExamen(pCcPreguntasHdrV1.getAdmonExamen());
		ccPreguntasHdrDto.setAdmonMateria(pCcPreguntasHdrV1.getAdmonMateria());
		ccPreguntasHdrDto.setAdmonSubmateria(pCcPreguntasHdrV1.getAdmonSubMateria());
		ccPreguntasHdrDto.setFechaElaboracion(Utilitarios.utilDateToSqlDate(pCcPreguntasHdrV1.getFechaElaboracion()));
		ccPreguntasHdrDto.setTipoPregunta(pCcPreguntasHdrV1.getTipoPregunta());
		ccPreguntasHdrDto.setEtiquetas(pCcPreguntasHdrV1.getEtiquetas());
		ccPreguntasHdrDto.setComentarios(pCcPreguntasHdrV1.getComentarios());
		ccPreguntasHdrDto.setCreadoPor(pCcPreguntasHdrV1.getCreadoPor());
		ccPreguntasHdrDto.setActualizadoPor(pCcPreguntasHdrV1.getActualizadoPor());
		ccPreguntasHdrDto.setFechaCreacion(Utilitarios.utilDateToTimestamp(pCcPreguntasHdrV1.getFechaCreacion()));
		ccPreguntasHdrDto.setFechaActualizacion(Utilitarios.utilDateToTimestamp(pCcPreguntasHdrV1.getFechaActualizacion()));
		ccPreguntasHdrDto.setFechaEfectivaDesde(Utilitarios.startOfTime);
		ccPreguntasHdrDto.setFechaEfectivaHasta(Utilitarios.endOfTime);	
		ccPreguntasHdrDao.insert(ccPreguntasHdrDto); 
		pCcPreguntasHdrV1.setNumero(ccPreguntasHdrDto.getNumero());
		return ccPreguntasHdrDto.getNumero();
	}

	
	

}
