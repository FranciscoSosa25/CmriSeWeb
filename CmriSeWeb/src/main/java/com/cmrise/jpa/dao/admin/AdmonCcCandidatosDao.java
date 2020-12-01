package com.cmrise.jpa.dao.admin;

import java.util.List;

import com.cmrise.jpa.dto.admin.AdmonCcCandidatosDto;
import com.cmrise.jpa.dto.admin.AdmonCcCandidatosV1Dto;

public interface AdmonCcCandidatosDao {
     public long insert(AdmonCcCandidatosDto pAdmonCcCandidatosDto); 
     public List<AdmonCcCandidatosV1Dto> findByNumeroCcExamenWD(long pNumeroExamen);
     public void delete(long pNumero); 
     public void deleteAll(long pNumeroCcExamen);
     public List<Object> findExaminationsByCandidato(long pNumeroCandidato);
}
