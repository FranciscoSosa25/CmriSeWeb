package com.cmrise.ejb.services.mrqs;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.cmrise.jpa.dao.mrqs.MrqsOpcionMultipleDao;
import com.cmrise.jpa.dto.mrqs.MrqsOpcionMultipleDto;

@Stateless 
public class MrqsOpcionMultipleLocalImpl implements MrqsOpcionMultipleLocal {

	@Inject 
	 MrqsOpcionMultipleDao  mrqsOpcionMultipleDao; 
	
	@Override
	public long insert(MrqsOpcionMultipleDto pMrqsOpcionMultipleDto) {
		return mrqsOpcionMultipleDao.insert(pMrqsOpcionMultipleDto);
	}

	@Override
	public List<MrqsOpcionMultipleDto> findByNumeroFta(long pNumeroFta) {
		return mrqsOpcionMultipleDao.findByNumeroFta(pNumeroFta);
	}

	@Override
	public void update(long pNumero, MrqsOpcionMultipleDto pMrqsOpcionMultipleDto) {
		mrqsOpcionMultipleDao.update(pNumero, pMrqsOpcionMultipleDto);
	}

	@Override
	public void delete(long pNumero) {
		mrqsOpcionMultipleDao.delete(pNumero);
	}

	@Override
	public void deleteByNumeroFta(long pNumeroFta) {
		mrqsOpcionMultipleDao.deleteByNumeroFta(pNumeroFta);
	}

	@Override
	public void copyPaste(long pNumeroFtaOld, long longpNumeroFtaCopy) {
		mrqsOpcionMultipleDao.copyPaste(pNumeroFtaOld, longpNumeroFtaCopy);
	}

}
