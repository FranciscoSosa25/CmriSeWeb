package com.cmrise.ejb.services.exams;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.cmrise.jpa.dao.exams.CcExamenesDao;
import com.cmrise.jpa.dto.exams.CcExamenesDto;
import com.cmrise.jpa.dto.exams.CcExamenesV1Dto;

@Stateless
public class CcExamenesLocalImpl implements CcExamenesLocal {

	@Inject 
	CcExamenesDao ccExamenesDao; 
	
	@Override
	public long insert(CcExamenesDto pCcExamenesDto) {
		return ccExamenesDao.insert(pCcExamenesDto);
	}

	@Override
	public List<CcExamenesDto> findAll() {
		return ccExamenesDao.findAll();
	}

	@Override
	public List<CcExamenesV1Dto> findAllWD() {
		return ccExamenesDao.findAllWD();
	}

	@Override
	public void delete(long pNumero) {
		ccExamenesDao.delete(pNumero);
	}

	@Override
	public CcExamenesDto findById(long pNumero) {
		return ccExamenesDao.findById(pNumero);
	}

	@Override
	public void update(long pNumero, CcExamenesDto pCcExamenesDto) {
		ccExamenesDao.update(pNumero, pCcExamenesDto);
	}

}
