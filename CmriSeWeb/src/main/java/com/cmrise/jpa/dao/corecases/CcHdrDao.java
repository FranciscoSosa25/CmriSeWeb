package com.cmrise.jpa.dao.corecases;

import java.util.List;

import com.cmrise.jpa.dto.admin.KeysDto;
import com.cmrise.jpa.dto.corecases.CcHdrDto;
import com.cmrise.jpa.dto.corecases.CcHdrV1Dto;

public interface CcHdrDao {

	public void insert(CcHdrDto pCcHdrDto);
	public void delete(long pNumero);
	public void update(long pNumero
			          ,CcHdrDto pCcHdrDto);
	public List<CcHdrV1Dto> findAll();
	public CcHdrV1Dto findByNumero(long pNumero);
	public List<KeysDto> findKeys(); 
	public List<Object> findCoreCasesForExam(long pNumeroExamen);
}
