package com.cmrise.ejb.services.exams;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.cmrise.ejb.model.candidates.exams.CandExamenesV2;
import com.cmrise.ejb.model.exams.Examenes;
import com.cmrise.jpa.dao.exams.ExamenesDao;
import com.cmrise.jpa.dto.candidates.exams.CandExamenesV2Dto;

@Stateless
public class ExamenesLocalImpl implements ExamenesLocal {

	@Inject 
	ExamenesDao examenesDao; 
	
	@Override
	public List<Examenes> findAllObjMod() {
		List<Object> listObjects = examenesDao.findAll();
		List<Examenes> retval = new ArrayList<Examenes>(); 
		for(Object object:listObjects) {
			Examenes examen = objToExamenes(object); 
			retval.add(examen);
		}
		return retval;
	}
	
	@Override
	public List<Examenes> findByTituloExamen(int idExamen, String nombreExamen, Date fechaDesde, Date fechaHasta, int tiempo, String tipoExamen){
		List<Object> listObjects = examenesDao.findByTitulo(idExamen,nombreExamen,fechaDesde,fechaHasta,tiempo,tipoExamen);
		List<Examenes> retval = new ArrayList<Examenes>(); 
		for(Object object:listObjects) {
			Examenes examen = objToExamenes(object); 
			retval.add(examen);
		}
		return retval;
	}
	
	private Examenes objToExamenes(Object pObject) {
		Examenes retval = new Examenes(); 
		if(pObject instanceof Object[]) {
			Object[] row = (Object[]) pObject;
			if(row[0] instanceof BigInteger) { /** DISTINCT NUMERO **/
				retval.setNumero(((BigInteger)row[0]).longValue()); 
			}
			if(row[1] instanceof String) { /** TITULO **/
				retval.setTitulo((String)row[1]); 
			}
			if(row[2] instanceof String) { /** NOMBRE **/
				retval.setNombre((String)row[2]); 
			}
			if(row[3] instanceof String) { /** DESCRIPCION **/
				retval.setDescripcion((String)row[3]); 
			}
			if(row[4] instanceof Short) { /** TIEMPO_LIMITE **/
				retval.setTiempoLimite(((Short)row[4]).shortValue()); 
			}
			if(row[5] instanceof Integer) { /** TOTAL_CANDIDADATOS **/
			retval.setTotalCandidatos(((Integer)row[5]).longValue());
			}
			if(row[6] instanceof String) { /** TIPO_EXAMEN_DESC **/
				retval.setTipoExamenDesc((String)row[6]); 
			}
			if(row[7] instanceof java.sql.Timestamp) { /** FECHA_EFECTIVA_DESDE **/
				retval.setFechaEfectivaDesde(new java.util.Date(((java.sql.Timestamp)row[7]).getTime()));
			}
			if(row[8] instanceof String) { /** TIPO_EXAMEN_CODE **/
				retval.setTipoExamenCode((String)row[8]); 
			}
			if(row[9] instanceof java.sql.Timestamp) { /** FECHA_EFECTIVA_HASTA **/
				retval.setFechaEfectivaHasta(new java.util.Date(((java.sql.Timestamp)row[9]).getTime()));
			}
		}
		return retval; 
	}

	
}
