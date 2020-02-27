package com.cmrise.jpa.dao.candidates.exams;

import java.util.List;

import com.cmrise.jpa.dto.candidates.exams.CandCcExamenesDto;

public interface CandCcExamenesDao {
  public long insert(CandCcExamenesDto pCandCcExamenesDto); 
  public List<Object> findCandCcExamInfoByNumero(long pNumeroCandCcExamen); 
  public long findNumeroCandCcExamen(long pNumeroCcExamen,long pNumeroCandidato); 
}
