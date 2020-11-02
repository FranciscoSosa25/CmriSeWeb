package com.cmrise.ejb.services.mrqs;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.cmrise.jpa.dao.mrqs.MrqsListasPalabrasDao;
import com.cmrise.jpa.dto.mrqs.MrqsListasPalabrasDto;

@Stateless 
public class MrqsListasPalabrasLocalImpl implements MrqsListasPalabrasLocal {

	@Inject 
	MrqsListasPalabrasDao  mrqsListasPalabrasDao; 
	
	@Override
	public long insert(MrqsListasPalabrasDto pMrqsListasPalabrasDto) {
		return mrqsListasPalabrasDao.insert(pMrqsListasPalabrasDto);
	}

	@Override
	public void update(long pNumero, MrqsListasPalabrasDto pMrqsListasPalabrasDto) {
		mrqsListasPalabrasDao.update(pNumero, pMrqsListasPalabrasDto);
	}

	@Override
	public List<MrqsListasPalabrasDto> findByFta(long pNumeroFta, String pTipoRegistro) {
		return mrqsListasPalabrasDao.findByFta(pNumeroFta, pTipoRegistro);
	}

	@Override
	public void delete(long pNumero) {
		 mrqsListasPalabrasDao.delete(pNumero);
	}

}
