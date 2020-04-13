package com.cmrise.ejb.services.corecases;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.cmrise.jpa.dao.corecases.CcOpcionMultipleDao;
import com.cmrise.jpa.dto.corecases.CcOpcionMultipleDto;

@Stateless 
public class CcOpcionMultipleLocalImpl implements CcOpcionMultipleLocal {

	@Inject 
	CcOpcionMultipleDao ccOpcionMultipleDao;
	
	@Override
	public long insert(CcOpcionMultipleDto pCcOpcionMultipleDto) {
		return ccOpcionMultipleDao.insert(pCcOpcionMultipleDto);
	}

	@Override
	public List<CcOpcionMultipleDto> findByNumeroFta(long pNumeroFta) {
		return ccOpcionMultipleDao.findByNumeroFta(pNumeroFta);
	}

	@Override
	public void update(long pNumero, CcOpcionMultipleDto pCcOpcionMultipleDto) {
		ccOpcionMultipleDao.update(pNumero, pCcOpcionMultipleDto);
	}

	@Override
	public void delete(long pNumero) {
		ccOpcionMultipleDao.delete(pNumero); 
	}

}
