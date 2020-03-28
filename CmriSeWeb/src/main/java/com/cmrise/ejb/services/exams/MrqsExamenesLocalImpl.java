package com.cmrise.ejb.services.exams;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.cmrise.jpa.dao.exams.MrqsExamenesDao;
import com.cmrise.jpa.dto.exams.MrqsExamenesDto;
import com.cmrise.jpa.dto.exams.MrqsExamenesV1Dto;

@Stateless
public class MrqsExamenesLocalImpl implements MrqsExamenesLocal {

	@Inject 
	MrqsExamenesDao mrqsExamenesDao; 
	
	@Override
	public long insert(MrqsExamenesDto pMrqsExamenesDto) {
		return mrqsExamenesDao.insert(pMrqsExamenesDto);
	}

	@Override
	public List<MrqsExamenesDto> findAll() {
		return mrqsExamenesDao.findAll();
	}
	
	@Override
	public List<MrqsExamenesV1Dto> findAllWD() {
		return mrqsExamenesDao.findAllWD();
	}

	@Override
	public void delete(long pNumero) {
		mrqsExamenesDao.delete(pNumero);
	}

	@Override
	public MrqsExamenesDto findById(long pNumero) {
		return mrqsExamenesDao.findById(pNumero);
	}

	@Override
	public void update(long pNumero, MrqsExamenesDto pMrqsExamenesDto) {
		mrqsExamenesDao.update(pNumero, pMrqsExamenesDto);
	}

	
}
