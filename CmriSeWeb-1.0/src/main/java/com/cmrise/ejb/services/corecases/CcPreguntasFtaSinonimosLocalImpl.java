package com.cmrise.ejb.services.corecases;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.cmrise.jpa.dao.corecases.CcPreguntasFtaDao;
import com.cmrise.jpa.dao.corecases.CcPreguntasFtaSinonimosDao;
import com.cmrise.jpa.dto.corecases.CcPreguntasFtaSinonimos;
@Stateless
public class CcPreguntasFtaSinonimosLocalImpl implements CcPreguntasFtaSinonimosLocal {
	 @Inject
	 CcPreguntasFtaSinonimosDao ccPreguntasFtaSinonimosDao;
	@Override
	public long insert(List<CcPreguntasFtaSinonimos> sinonimos, long lNumeroFta, String texto) throws Exception {
		// TODO Auto-generated method stub
		return ccPreguntasFtaSinonimosDao.insert(sinonimos,lNumeroFta,texto);
	}

	@Override
	public List<CcPreguntasFtaSinonimos> findByFta(long pNumeroFta) {
		// TODO Auto-generated method stub
		return ccPreguntasFtaSinonimosDao.findByFta(pNumeroFta);
	}

	@Override
	public void deleteSinonimo(CcPreguntasFtaSinonimos item) throws Exception {
		ccPreguntasFtaSinonimosDao.delete(item);
		
	}

	
}
