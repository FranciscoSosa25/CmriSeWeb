package com.cmrise.ejb.services.exams;

import java.util.List;

import javax.ejb.Local;

import com.cmrise.ejb.model.exams.CcExamenes;
import com.cmrise.jpa.dto.exams.CcExamenesDto;
import com.cmrise.jpa.dto.exams.CcExamenesV1Dto;

@Local
public interface CcExamenesLocal {
	public long insert(CcExamenesDto pCcExamenesDto);
	public List<CcExamenesDto> findAll();
	public List<CcExamenesV1Dto> findAllWD();
	public void delete(long pNumero); 
	public CcExamenesDto findById(long pNumero); 
	public void update(long pNumero,CcExamenesDto pCcExamenesDto);
	public CcExamenes findByNumeroObjMod(long numeroCcExamen);
	public CcExamenes findByNumeroObjModCand(long numeroCcExamen);
	
}
