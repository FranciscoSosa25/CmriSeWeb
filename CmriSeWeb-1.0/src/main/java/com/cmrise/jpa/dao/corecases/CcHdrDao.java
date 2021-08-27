package com.cmrise.jpa.dao.corecases;

import java.util.List;

import javax.persistence.Query;

import com.cmrise.ejb.model.corecases.CcHdrV1;
import com.cmrise.jpa.dto.admin.KeysDto;
import com.cmrise.jpa.dto.corecases.CcHdrDto;
import com.cmrise.jpa.dto.corecases.CcHdrV1Dto;
import com.cmrise.jpa.dto.exams.MrqsGrupoHdrDto;

public interface CcHdrDao {

	public void insert(CcHdrDto pCcHdrDto);
	public void delete(long pNumero);
	public void deletePregunta(long pNumero);
	public void update(long pNumero ,CcHdrDto pCcHdrDto);
	public List<CcHdrV1Dto> findAll();
	public CcHdrV1Dto findByNumero(long pNumero);
	public List<Object> findCCNotInExam(long pNumeroExamen, long tipoExamen);
	public List<Object> findCCInExam(long pNumeroExamen);
	public List<KeysDto> findKeys(); 
	public List<Object> findCoreCasesForExam(long pNumeroExamen);
	public List<CcHdrV1> findCCByExamen(long pNumeroExamen);
}
