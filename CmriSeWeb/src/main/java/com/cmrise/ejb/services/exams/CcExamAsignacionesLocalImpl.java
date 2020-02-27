package com.cmrise.ejb.services.exams;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.cmrise.ejb.model.exams.CcExamAsignaciones;
import com.cmrise.jpa.dao.exams.CcExamAsignacionesDao;
import com.cmrise.jpa.dto.exams.CcExamAsignacionesDto;

@Stateless
public class CcExamAsignacionesLocalImpl implements CcExamAsignacionesLocal {

	@Inject 
	CcExamAsignacionesDao ccExamAsignacionesDao; 
	
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
				if(row[3] instanceof Object) {
									
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


}
