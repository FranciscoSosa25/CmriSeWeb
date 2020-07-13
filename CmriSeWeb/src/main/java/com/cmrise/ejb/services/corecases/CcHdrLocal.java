package com.cmrise.ejb.services.corecases;

import java.util.List;

import javax.ejb.Local;

import com.cmrise.ejb.model.corecases.CcHdrForAction;
import com.cmrise.ejb.model.corecases.CcHdrV1;
import com.cmrise.jpa.dto.admin.KeysDto;
import com.cmrise.jpa.dto.corecases.CcHdrDto;
import com.cmrise.jpa.dto.corecases.CcHdrV1Dto;

@Local
public interface CcHdrLocal {

	public void insert(CcHdrDto pCcHdrDto);
	public void delete(long pNumero);
	public void deletePregunta(long pNumero);
	public void update(CcHdrV1Dto pCcHdrV1Dto
			          ,CcHdrDto pCcHdrDto) ;
	
	public List<CcHdrV1> findAll();
	public CcHdrV1Dto findByNumero(long pNumero);
	public List<KeysDto> findKeys();
	public List<CcHdrForAction> findCoreCasesForExam(long pNumeroExamen);
	public CcHdrV1 findByNumeroObjMod(long pNumeroCcHdr);
	public long insert(CcHdrV1 pCcHdrV1); 
	
}
