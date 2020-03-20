package com.cmrise.ejb.services.mrqs;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import com.cmrise.jpa.dao.mrqs.MrqsPreguntasHdrDao;
import com.cmrise.jpa.dto.mrqs.MrqsPreguntasHdrDto;
import com.cmrise.jpa.dto.mrqs.MrqsPreguntasHdrV1Dto;

@Stateless 
public class MrqsPreguntasHdrLocalImpl implements MrqsPreguntasHdrLocal {

	@Inject
	MrqsPreguntasHdrDao mrqsPreguntasHdrDao; 
	
	@Override
	public void insert(MrqsPreguntasHdrDto pMrqsPreguntasHdrDto) {
		mrqsPreguntasHdrDao.insert(pMrqsPreguntasHdrDto);
	}

	@Override
	public void delete(long pNumero) {
		mrqsPreguntasHdrDao.delete(pNumero);
	}

	@Override
	public void update(long pNumero, MrqsPreguntasHdrDto pMrqsPreguntasHdrDto) {
		mrqsPreguntasHdrDao.update(pNumero, pMrqsPreguntasHdrDto);
	}

	@Override
	public List<MrqsPreguntasHdrV1Dto> findAll() {
		return mrqsPreguntasHdrDao.findAll();
	}

	@Override
	public MrqsPreguntasHdrV1Dto findByNumero(long pNumero) {
		return mrqsPreguntasHdrDao.findByNumero(pNumero);
	}

	@Override
	public MrqsPreguntasHdrDto copyPaste(long pNumero) {
		return mrqsPreguntasHdrDao.copyPaste(pNumero);
	}

}
