package com.cmrise.ejb.services.exams;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.cmrise.ejb.model.corecases.CcHdrV1;
import com.cmrise.ejb.model.corecases.CcPreguntasHdrV1;
import com.cmrise.ejb.model.exams.CcExamAsignaciones;
import com.cmrise.ejb.model.exams.CcExamenes;
import com.cmrise.jpa.dao.corecases.CcHdrDao;
import com.cmrise.jpa.dao.corecases.CcPreguntasHdrDao;
import com.cmrise.jpa.dao.exams.CcExamAsignacionesDao;
import com.cmrise.jpa.dao.exams.CcExamenesDao;
import com.cmrise.jpa.dto.corecases.CcHdrV1Dto;
import com.cmrise.jpa.dto.corecases.CcPreguntasHdrV1Dto;
import com.cmrise.jpa.dto.exams.CcExamAsignacionesDto;
import com.cmrise.jpa.dto.exams.CcExamenesDto;
import com.cmrise.jpa.dto.exams.CcExamenesV1Dto;
import com.cmrise.utils.Utilitarios;

@Stateless
public class CcExamenesLocalImpl implements CcExamenesLocal {

	@Inject 
	CcExamenesDao ccExamenesDao; 

	@Inject 
	CcExamAsignacionesDao ccExamAsignacionesDao; 
	
	@Inject 
	CcHdrDao ccHdrDao; 
	
	@Inject 
	CcPreguntasHdrDao  ccPreguntasHdrDao;
	
	@Override
	public long insert(CcExamenesDto pCcExamenesDto) {
		return ccExamenesDao.insert(pCcExamenesDto);
	}

	@Override
	public List<CcExamenesDto> findAll() {
		return ccExamenesDao.findAll();
	}

	@Override
	public List<CcExamenesV1Dto> findAllWD() {
		return ccExamenesDao.findAllWD();
	}

	@Override
	public void delete(long pNumero) {
		ccExamenesDao.delete(pNumero);
	}

	@Override
	public CcExamenesDto findById(long pNumero) {
		return ccExamenesDao.findById(pNumero);
	}

	@Override
	public void update(long pNumero, CcExamenesDto pCcExamenesDto) {
		ccExamenesDao.update(pNumero, pCcExamenesDto);
	}

	@Override
	public CcExamenes findByNumeroObjMod(long pNumeroCcExamen) {
		CcExamenes retval = new CcExamenes(); 
		CcExamenesDto ccExamenesDto = ccExamenesDao.findById(pNumeroCcExamen); 
		retval.setTitulo(ccExamenesDto.getTitulo());
		retval.setNombre(ccExamenesDto.getNombre());
		retval.setDescripcion(ccExamenesDto.getDescripcion());
		retval.setComentarios(ccExamenesDto.getComentarios());
		retval.setTiempoLimite(ccExamenesDto.getTiempoLimite());
		retval.setSaltarPreguntas(ccExamenesDto.getSaltarPreguntas());
		retval.setSaltarCasos(ccExamenesDto.getSaltarCasos());
		retval.setMostrarRespuestas(ccExamenesDto.getMostrarRespuestas());
		retval.setTienePassmark(ccExamenesDto.getTienePassmark());
		retval.setAleatorioGrupo(ccExamenesDto.getAleatorioGrupo());
		retval.setAleatorioPreguntas(ccExamenesDto.getAleatorioPreguntas());
		retval.setSeleccionCasosAleatorios(ccExamenesDto.getSeleccionCasosAleatorios());
		retval.setMensajeFinalizacion(ccExamenesDto.getMensajeFinalizacion());
		retval.setConfirmacionAsistencia(ccExamenesDto.getConfirmacionAsistencia());
		retval.setDiploma(ccExamenesDto.getDiploma());
		retval.setTipoPregunta(ccExamenesDto.getTipoPregunta());
		retval.setTipoExamen(ccExamenesDto.getTipoExamen());
		retval.setVisibilidad(ccExamenesDto.getVisibilidad());
		retval.setEstatus(ccExamenesDto.getEstatus());
		retval.setTema(ccExamenesDto.getTema());
		retval.setSociedad(ccExamenesDto.getSociedad());
		retval.setFechaEfectivaDesde(new java.util.Date(ccExamenesDto.getFechaEfectivaDesde().getTime()));
		if(Utilitarios.endOfTime.equals(ccExamenesDto.getFechaEfectivaHasta())) {
			retval.setFechaEfectivaHasta(null);
		}else {
			retval.setFechaEfectivaHasta(new java.util.Date(ccExamenesDto.getFechaEfectivaHasta().getTime()));
		}
		
		List<CcExamAsignacionesDto> listCcExamAsignacionesDto  = ccExamAsignacionesDao.findByNumeroExamenDtos(pNumeroCcExamen); 
		if(null!=listCcExamAsignacionesDto) {
			List<CcExamAsignaciones> listCcExamAsignaciones = new ArrayList<CcExamAsignaciones>(); 
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
				ccHdrV1.setNombre(ccHdrV1Dto.getNombre());
				ccHdrV1.setTema(ccHdrV1Dto.getTema());
				ccHdrV1.setTemaDesc(ccHdrV1Dto.getTemaDesc());
				ccHdrV1.setHistorialClinico(ccHdrV1Dto.getHistorialClinico());
				ccHdrV1.setDescripcionTecnica(ccHdrV1Dto.getDescripcionTecnica());
				ccHdrV1.setOpcionInsegura(ccHdrV1Dto.getOpcionInsegura());
				ccHdrV1.setEtiquetas(ccHdrV1Dto.getEtiquetas());
				ccHdrV1.setNota(ccHdrV1Dto.getNota());
				ccHdrV1.setEstatus(ccHdrV1Dto.getEstatus());
				ccHdrV1.setEstatusDesc(ccHdrV1Dto.getEstatusDesc());
				ccHdrV1.setSociedad(ccHdrV1Dto.getSociedad());
				ccHdrV1.setFechaEfectivaDesde(new java.util.Date(ccHdrV1Dto.getFechaEfectivaDesde().getTime()));
				ccHdrV1.setFechaEfectivaHasta(new java.util.Date(ccHdrV1Dto.getFechaEfectivaHasta().getTime()));
				
				List<CcPreguntasHdrV1Dto> listCcPreguntasHdrV1Dto =  ccPreguntasHdrDao.findListByNumeroCcHdr(i.getNumeroCoreCase()); 
				if(null!=listCcPreguntasHdrV1Dto) {
					List<CcPreguntasHdrV1> listCcPreguntasHdrV1 = new ArrayList<CcPreguntasHdrV1>();
				 	for(CcPreguntasHdrV1Dto j :listCcPreguntasHdrV1Dto) {
				 		CcPreguntasHdrV1 ccPreguntasHdrV1 = new CcPreguntasHdrV1();
				 		ccPreguntasHdrV1.setNumero(j.getNumero());
				     	ccPreguntasHdrV1.setNumeroCcHdr(j.getNumeroCcHdr());
				     	ccPreguntasHdrV1.setTitulo(j.getTitulo());
				     	ccPreguntasHdrV1.setTipoPregunta(j.getTipoPregunta());
				     	ccPreguntasHdrV1.setTipoPreguntaDesc(j.getTipoPreguntaDesc());
				     	ccPreguntasHdrV1.setTemaPregunta(j.getTemaPregunta());
				     	ccPreguntasHdrV1.setTemaPreguntaDesc(j.getTemaPreguntaDesc());
				     	ccPreguntasHdrV1.setEstatus(j.getEstatus());
				     	ccPreguntasHdrV1.setEstatusDesc(j.getEstatusDesc());
				     	ccPreguntasHdrV1.setMaxPuntuacion(j.getMaxPuntuacion());
				     	ccPreguntasHdrV1.setEtiquetas(j.getEtiquetas());
				     	listCcPreguntasHdrV1.add(ccPreguntasHdrV1);  	
				  	}	
				 	ccHdrV1.setListCcPreguntasHdrV1(listCcPreguntasHdrV1);
				}
				
				ccExamAsignaciones.setCcHdrV1(ccHdrV1);
				listCcExamAsignaciones.add(ccExamAsignaciones); 
				
			}
			retval.setListCcExamAsignaciones(listCcExamAsignaciones);
		}
		
		return retval;
	}

}
