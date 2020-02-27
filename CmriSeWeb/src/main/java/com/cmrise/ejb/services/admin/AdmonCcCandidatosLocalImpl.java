package com.cmrise.ejb.services.admin;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.cmrise.ejb.model.candidates.exams.Examinations;
import com.cmrise.jpa.dao.admin.AdmonCcCandidatosDao;
import com.cmrise.jpa.dto.admin.AdmonCcCandidatosDto;
import com.cmrise.jpa.dto.admin.AdmonCcCandidatosV1Dto;

@Stateless 
public class AdmonCcCandidatosLocalImpl implements AdmonCcCandidatosLocal {

	@Inject
	AdmonCcCandidatosDao  admonCcCandidatosDao;
	
	@Override
	public long insert(AdmonCcCandidatosDto pAdmonCcCandidatosDto) {
		return admonCcCandidatosDao.insert(pAdmonCcCandidatosDto);
	}

	@Override
	public List<AdmonCcCandidatosV1Dto> findByNumeroCcExamenWD(long pNumeroExamen) {
		return admonCcCandidatosDao.findByNumeroCcExamenWD(pNumeroExamen);
	}

	@Override
	public void delete(long pNumero) {
		admonCcCandidatosDao.delete(pNumero);
	}

	@Override
	public void deleteAll(long pNumeroCcExamen) {
		admonCcCandidatosDao.deleteAll(pNumeroCcExamen);
	}

	@Override
	public List<Examinations> findExaminationsByCandidato(long pNumeroCandidato) {
		List<Examinations> listExaminations = new ArrayList<Examinations>(); 
		List<Object> listObjects = admonCcCandidatosDao.findExaminationsByCandidato(pNumeroCandidato); 
		for(Object object:listObjects) {
			if(object instanceof Object[]) {
				Examinations examinations = new Examinations(); 
				Object[] row = (Object[]) object;
				if(row[0] instanceof BigInteger) { /** NUMERO **/
					examinations.setNumeroAdmonCcCandidato(((BigInteger)row[0]).longValue());
				}
				if(row[1] instanceof BigInteger) { /** NUMERO_USUARIO **/
					examinations.setNumeroAdmonUsuario(((BigInteger)row[1]).longValue());
				}
				if(row[2] instanceof BigInteger) { /** NUMERO_CC_EXAMEN **/
					examinations.setNumeroCcExamen(((BigInteger)row[2]).longValue());
				}
				if(row[3] instanceof String) { /** TITULO **/
					examinations.setTituloExamen((String)row[3]);
				}
				if(row[4] instanceof Short) { /** TIEMPO_LIMITE **/
					examinations.setTiempoLimite((short)row[4]);
				}
				listExaminations.add(examinations); 
			}
		}	
		return listExaminations;
	}

}
