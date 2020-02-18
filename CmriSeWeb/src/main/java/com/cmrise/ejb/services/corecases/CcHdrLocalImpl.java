package com.cmrise.ejb.services.corecases;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.cmrise.jpa.dao.corecases.CcHdrDao;
import com.cmrise.jpa.dto.admin.KeysDto;
import com.cmrise.jpa.dto.corecases.CcHdrDto;
import com.cmrise.jpa.dto.corecases.CcHdrV1Dto;

@Stateless 
public class CcHdrLocalImpl implements CcHdrLocal {

	@Inject 
	CcHdrDao ccHdrDao; 
	
	@Override
	public void insert(CcHdrDto pCcHdrDto) {
		ccHdrDao.insert(pCcHdrDto);
	}

	@Override
	public void delete(long pNumero) {
		ccHdrDao.delete(pNumero);
	}

	@Override
	public void update(long pNumero, CcHdrDto pCcHdrDto) {
		ccHdrDao.update(pNumero
				      , pCcHdrDto);
	}

	@Override
	public List<CcHdrV1Dto> findAll() {
		return ccHdrDao.findAll();
	}

	@Override
	public CcHdrV1Dto findByNumero(long pNumero) {
		return ccHdrDao.findByNumero(pNumero);
	}

	@Override
	public List<KeysDto> findKeys() {
		return ccHdrDao.findKeys();
	}

}
