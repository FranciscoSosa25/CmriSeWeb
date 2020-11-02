package com.cmrise.ejb.services.admin;

import java.util.List;

import javax.ejb.Local;

import com.cmrise.ejb.model.candidates.exams.Examinations;
import com.cmrise.jpa.dto.admin.AdmonCcCandidatosDto;
import com.cmrise.jpa.dto.admin.AdmonCcCandidatosV1Dto;

@Local
public interface AdmonCcCandidatosLocal {
	 public long insert(AdmonCcCandidatosDto pAdmonCcCandidatosDto); 
	 public List<AdmonCcCandidatosV1Dto> findByNumeroCcExamenWD(long pNumeroExamen); 
	 public void delete(long pNumero); 
	 public void deleteAll(long pNumeroCcExamen); 
	 public List<Examinations> findExaminationsByCandidato(long pNumeroCandidato);
}

