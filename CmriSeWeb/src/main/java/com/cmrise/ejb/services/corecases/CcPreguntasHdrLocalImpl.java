package com.cmrise.ejb.services.corecases;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.cmrise.jpa.dao.corecases.CcPreguntasHdrDao;
import com.cmrise.jpa.dto.corecases.CcHdrV1Dto;
import com.cmrise.jpa.dto.corecases.CcPreguntasHdrDto;
import com.cmrise.jpa.dto.corecases.CcPreguntasHdrV1Dto;

@Stateless 
public class CcPreguntasHdrLocalImpl implements CcPreguntasHdrLocal {

	@Inject 
	CcPreguntasHdrDao ccPreguntasHdrDao; 
	
	@Override
	public long insert(CcPreguntasHdrDto pCcPreguntasHdrDto) {
		return ccPreguntasHdrDao.insert(pCcPreguntasHdrDto);
	}

	@Override
	public void delete(long pNumero) {
		ccPreguntasHdrDao.delete(pNumero);
	}

	@Override
	public void update(long pNumero
			         , CcPreguntasHdrDto pCcPreguntasHdrDto) {
		ccPreguntasHdrDao.update(pNumero
				               , pCcPreguntasHdrDto);
	}

	@Override
	public CcPreguntasHdrV1Dto findByNumero(long pNumero) {
		return ccPreguntasHdrDao.findByNumero(pNumero);
	}

	@Override
	public List<CcPreguntasHdrV1Dto> findListByNumeroCcHdr(long pNumeroCcHdr) {
		return ccPreguntasHdrDao.findListByNumeroCcHdr(pNumeroCcHdr);
	}

	

}
