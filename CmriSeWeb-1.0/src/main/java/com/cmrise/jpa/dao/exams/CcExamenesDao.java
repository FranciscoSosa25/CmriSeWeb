package com.cmrise.jpa.dao.exams;

import java.util.List;

import com.cmrise.ejb.model.exams.CcExamenes;
import com.cmrise.jpa.dto.exams.CcExamenesDto;
import com.cmrise.jpa.dto.exams.CcExamenesV1Dto;

public interface CcExamenesDao {
	public long insert(CcExamenesDto pCcExamenesDto);
	public List<CcExamenesDto> findAll();
	public List<CcExamenesV1Dto> findAllWD();
	public void delete(long pNumero);
	public CcExamenesDto findById(long pNumero); 
	public void update(long pNumero,CcExamenesDto pCcExamenesDto);
	public List<CcExamenes> findByNumeroWD(long pNumero,long pNCandidato);
}
