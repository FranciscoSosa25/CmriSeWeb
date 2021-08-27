package com.cmrise.jpa.dao.corecases;

import java.util.List;

import com.cmrise.ejb.model.corecases.CcPreguntasHdrV1;
import com.cmrise.jpa.dto.corecases.CcPreguntasHdrDto;
import com.cmrise.jpa.dto.corecases.CcPreguntasHdrV1Dto;

public interface CcPreguntasHdrDao {

	public long insert(CcPreguntasHdrDto pCcPreguntasHdrDto); 
	public void delete(long pNumero); 
	public void update(long pNumero
			          ,CcPreguntasHdrDto pCcPreguntasHdrDto); 
	public CcPreguntasHdrV1Dto findByNumero(long pNumero);
	public List<CcPreguntasHdrV1Dto> findListByNumeroCcHdr(long pNumeroCcHdr);
	public List<CcPreguntasHdrV1> findListByNumeroCcHdrWD(long pNumeroCcHdr);
}
